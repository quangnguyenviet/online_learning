import { updateLesson, uploadLesson } from "utils/InstructorUtil/LessonUtil";
import React, { useState } from "react";
import { useParams } from "react-router-dom";
import DeleteLesson from "pages/instructor/Courses/LessonList/DeleteLesson/DeleteLesson";
import ViewVideo from "pages/instructor/Courses/LessonList/ViewVideo/ViewVideo";
import EditLesson from "pages/instructor/Courses/LessonList/EditLesson/EditLesson";
import { FaEdit, FaTrashAlt, FaPlus, FaTimes, FaVideo } from "react-icons/fa";
import Swal from "sweetalert2";
import styles from "./LessonList.module.scss";
import { useSuccess, SuccessDisplay } from "components/common/SucessDisplay/SuccessDisplay";
import { useLoading } from "components/common/Loading/Loading";
import LessonApi from "service/apis/LessonApi";


export function LessonList(props) {
    console.log("props lessons", props.lessons);
    const { courseId } = useParams();
    const [lessons, setLessons] = useState(props.lessons || []);
    const [editingLessonId, setEditingLessonId] = useState(null);
    const [showAddLesson, setShowAddLesson] = useState(false);
    const [newLesson, setNewLesson] = useState({ title: "", description: "", video: null });
    const [editForm, setEditForm] = useState({ title: "", description: "", video: null });

    const { successMessage, showSuccess, dismissSuccess } = useSuccess();
    const { GlobalLoading, showLoading, hideLoading } = useLoading();

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
        const formData = new FormData();
        formData.append("title", newLesson.title);
        formData.append("description", newLesson.description);
        formData.append("videoFile", newLesson.video);
        formData.append("courseId", courseId);
        showLoading("Đang thêm bài học...");
        try {
            const response = await LessonApi.addLesson(formData);
            const addedLesson = response.data;
            setLessons(prev => [...prev, addedLesson]);
            setNewLesson({ title: "", description: "", video: null });
            setShowAddLesson(false);
            showSuccess("Thêm bài học thành công!");
        } catch (error) {
            console.error("Error adding lesson:", error);
        } finally {
            hideLoading();
        }
    };

    return (
        <div className={styles.lessons}>
            <SuccessDisplay
                message={successMessage}
                onDismiss={dismissSuccess}
            />
            <GlobalLoading />
            <div>
                {lessons.map((lesson, index) => (
                    <div key={lesson.id} className={styles.lessonCard}>
                        <div className={styles.lessonHeader}>
                            <strong className={styles.lessonTitle}>
                                <span className={styles.orderChip}>#{index + 1}</span>
                                {lesson.title}
                            </strong>
                            <div className={styles.lessonActions}>
                                <ViewVideo videoUrl={lesson.videoUrl}><FaVideo /></ViewVideo>
                                <button type="button" onClick={() => toggleEdit(lesson)} title="Chỉnh sửa">
                                    {editingLessonId === lesson.id ? <FaTimes /> : <FaEdit />}
                                </button>
                                <DeleteLesson lessonId={lesson.id} handleDelete={handleDelete}>
                                    <FaTrashAlt />
                                </DeleteLesson>
                            </div>
                        </div>

                        <div className={styles.lessonInfo}>
                            <p className={styles.lessonDescription}>{lesson.description}</p>
                            {lesson.videoUrl && (
                                <div className={styles.lessonVideoStatus}>
                                    {lesson.duration ? (
                                        <span>
                                            Thời gian video: {Math.floor(lesson.duration / 60)} phút{" "}
                                            {lesson.duration % 60} giây
                                        </span>
                                    ) : (
                                        <span>Đang tải video...</span>
                                    )}
                                </div>
                            )}
                        </div>

                        {editingLessonId === lesson.id && (
                            <EditLesson
                                lesson={lesson}
                                handleSaveEdit={handleSaveEdit}
                            />
                        )}
                    </div>
                ))}
            </div>

            {showAddLesson ? (
                <div className={styles.addLessonForm}>
                    <h3>Thêm bài học mới</h3>
                    <label>Tên bài học:</label>
                    <input
                        type="text"
                        value={newLesson.title}
                        onChange={(e) => handleNewLessonChange("title", e.target.value)}
                    />
                    <label>Mô tả bài học:</label>
                    <textarea
                        rows="3"
                        value={newLesson.description}
                        onChange={(e) => handleNewLessonChange("description", e.target.value)}
                    />
                    <label>Upload video:</label>
                    <input
                        type="file"
                        accept="video/*"
                        onChange={(e) => handleNewLessonChange("video", e.target.files[0])}
                    />
                    <div className={styles.formButtons}>
                        <button onClick={handleAddLesson}><FaPlus /> Thêm bài học</button>
                        <button onClick={() => setShowAddLesson(false)}><FaTimes /> Hủy</button>
                    </div>
                </div>
            ) : (
                <button className={styles.addLessonButton} onClick={() => setShowAddLesson(true)}>
                    <FaPlus /> Thêm bài học
                </button>
            )}
        </div>
    );
}
