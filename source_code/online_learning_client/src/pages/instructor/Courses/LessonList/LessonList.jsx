import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import Swal from "sweetalert2";
import styles from "./LessonList.module.scss";
import { useSuccess, SuccessDisplay } from "components/common/SucessDisplay/SuccessDisplay";
import { useLoading } from "components/common/Loading/Loading";
import LessonApi from "service/apis/LessonApi";
import { useError } from "components/common/ErrorDisplay/ErrorDisplay";
import { ErrorDisplay } from "components/common/ErrorDisplay/ErrorDisplay";
import LessonItem from "./components/LessonItem";
import AddLessonSection from "./components/AddLessonSection";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

export function LessonList(props) {
    const { courseId } = useParams();
    const [lessons, setLessons] = useState(props.lessons || []);
    const [editingLessonId, setEditingLessonId] = useState(null);
    const [showAddLesson, setShowAddLesson] = useState(false);
    const [newLesson, setNewLesson] = useState({ title: "", description: "", video: null });
    const [editForm, setEditForm] = useState({ title: "", description: "", video: null });
    const BASE_URL = process.env.REACT_APP_BASE_URL;

    const { showError, dismissError, errorMessage} = useError();
    const { successMessage, showSuccess, dismissSuccess } = useSuccess();
    const { GlobalLoading, showLoading, hideLoading } = useLoading();
    const clientRef = useRef(null);
    const pendingSubscriptionsRef = useRef(new Set());
    const activeSubscriptionsRef = useRef(new Set());

    

    const toggleEdit = (lesson) => {
        if (editingLessonId === lesson.id) {
            setEditingLessonId(null);
        } else {
            setEditingLessonId(lesson.id);
            setEditForm({ title: lesson.title, description: lesson.description, video: null });
        }
    };

    // const handleEditFormChange = (field, value) => {
    //     setEditForm(prev => ({ ...prev, [field]: value }));
    // };

    const handleSaveEdit = async (id, lessonData) => {
        const result = await Swal.fire({
            title: 'Bạn có chắc chắn muốn lưu thay đổi?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Lưu!',
            cancelButtonText: 'Hủy'
        });

        if (!result.isConfirmed) return;

        try {
            showLoading('Đang cập nhật bài học...');
            const response = await LessonApi.editLesson(id, lessonData);
            const updatedLesson = response.data;
            setLessons(prev => prev.map(lesson => lesson.id === id ? updatedLesson : lesson));
            setEditingLessonId(null);
            showSuccess("Cập nhật bài học thành công!");
        } catch (error) {
            console.error("Error updating lesson:", error);
            await Swal.fire({
                title: 'Cập nhật thất bại',
                text: 'Vui lòng thử lại sau.',
                icon: 'error'
            });
        } finally {
            hideLoading();
        }
    };

    const handleDelete = (id) => {
        setLessons(lessons.filter(lesson => lesson.id !== id));
        showSuccess("Xóa bài học thành công!");
    };

    const handleNewLessonChange = (field, value) => {
        setNewLesson(prev => ({ ...prev, [field]: value }));
    };

    const handleAddLesson = async () => {
        console.log("Adding new lesson:", newLesson);
        
        // Validate input
        if (!newLesson.title.trim()) {
            showError("Vui lòng nhập tên bài học");
            return;
        }
        
        if (!newLesson.description.trim()) {
            showError("Vui lòng nhập mô tả bài học");
            return;
        }
        
        if (!newLesson.video) {
            showError("Vui lòng chọn file video");
            return;
        }
        
        const formData = new FormData();
        formData.append("title", newLesson.title);
        formData.append("description", newLesson.description);
        formData.append("videoFile", newLesson.video);
        formData.append("courseId", courseId);
        showLoading("Đang thêm bài học...");
        try {
            const response = await LessonApi.addLesson(formData);
            const addedLesson = response?.data || response;
            setLessons(prev => [...prev, addedLesson]);
            subscribeLessonStatus(addedLesson?.id);
            setNewLesson({ title: "", description: "", video: null });
            setShowAddLesson(false);
        } catch (error) {
            console.error("Error adding lesson:", error);
        } finally {
            hideLoading();
        }
    };

    const handleTogglePreview = async (lessonId, currentPreviewStatus) => {
        try {
            showLoading('Đang cập nhật...');
            await LessonApi.updateLessonPreview(lessonId, !currentPreviewStatus);
            await refreshLessons();
            showSuccess(!currentPreviewStatus ? "Bài học được chuyển sang chế độ preview!" : "Bài học đã tắt chế độ preview!");
        } catch (error) {
            console.error("Error updating lesson preview:", error);
            showError("Cập nhật chế độ preview thất bại!");
        } finally {
            hideLoading();
        }
    };

    const refreshLessons = async () => {
        showSuccess("Thêm bài học thành công!");
        try {
            const response = await LessonApi.getLessonsByCourseId(courseId);
            const updatedLessons = response?.data || response || [];
            setLessons(updatedLessons);
        } catch (error) {
            console.error("Error refreshing lessons:", error);
        }
    };

    const handleLessonStatusMessage = async (lessonId, message) => {
        console.log("Lesson status update:", lessonId, message?.body);
        await refreshLessons();
    };

    const subscribeLessonStatus = (lessonId) => {
        if (!lessonId || activeSubscriptionsRef.current.has(lessonId)) return;

        if (clientRef.current && clientRef.current.connected) {
            clientRef.current.subscribe(
                `/topic/lesson-status${lessonId}`,
                (message) => handleLessonStatusMessage(lessonId, message)
            );
            activeSubscriptionsRef.current.add(lessonId);
        } else {
            pendingSubscriptionsRef.current.add(lessonId);
        }
    };

    useEffect(() => {
        const socket = new SockJS(`${BASE_URL}/ws`);
        const client = new Client({
            webSocketFactory: () => socket,
            onConnect: () => {
                console.log("WebSocket connected");
                clientRef.current = client;
                pendingSubscriptionsRef.current.forEach((lessonId) => {
                    subscribeLessonStatus(lessonId);
                    pendingSubscriptionsRef.current.delete(lessonId);
                });
            }
        });

        client.activate();

        return () => {
            client.deactivate();
        };
    }, [BASE_URL]);

    return (
        <div className={styles.lessons}>
            <ErrorDisplay message={errorMessage} onDismiss={dismissError} />
            <SuccessDisplay
                message={successMessage}
                onDismiss={dismissSuccess}
            />
            <GlobalLoading />
            <div>
                {lessons.map((lesson, index) => (
                    <LessonItem
                        key={lesson.id}
                        lesson={lesson}
                        index={index}
                        isEditing={editingLessonId === lesson.id}
                        onToggleEdit={toggleEdit}
                        onDelete={handleDelete}
                        onSaveEdit={handleSaveEdit}
                        onTogglePreview={handleTogglePreview}
                    />
                ))}
            </div>

            <AddLessonSection
                showAddLesson={showAddLesson}
                newLesson={newLesson}
                onChange={handleNewLessonChange}
                onAddLesson={handleAddLesson}
                onCancel={() => setShowAddLesson(false)}
                onShowForm={() => setShowAddLesson(true)}
            />
        </div>
    );
}
