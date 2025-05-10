import { updateLesson, uploadLesson } from "utils/InstructorUtil/LessonUtil";
import React, { useState } from "react";
import { useParams } from "react-router-dom";
import DeleteLesson from "pages/instructor/Courses/EditCourse/LessonList/DeleteLesson";
import ViewVideo from "pages/instructor/Courses/EditCourse/LessonList/ViewVideo";
import EditLesson from "pages/instructor/Courses/EditLesson";
import "./style.scss";

export function LessonList(props) {
    const { courseId } = useParams();

    const [editingLessonId, setEditingLessonId] = useState(null);
    const [lessons, setLessons] = useState(props.lessons || []);
    const [showAddLesson, setShowAddLesson] = useState(false);
    const [newLesson, setNewLesson] = useState({
        title: "",
        description: "",
        video: null
    });

    const [editForm, setEditForm] = useState({
        title: "",
        description: "",
        video: null
    });

    const toggleEdit = (lesson) => {
        if (editingLessonId === lesson.id) {
            setEditingLessonId(null);
        } else {
            setEditingLessonId(lesson.id);
            setEditForm({
                title: lesson.title,
                description: lesson.description,
                video: null
            });
        }
    };

    const handleEditFormChange = (field, value) => {
        setEditForm(prev => ({ ...prev, [field]: value }));
    };

    const handleSaveEdit = (id) => {
        setLessons(prev =>
            prev.map(lesson =>
                lesson.id === id
                    ? {
                        ...lesson,
                        title: editForm.title,
                        description: editForm.description,
                        video: editForm.video ? URL.createObjectURL(editForm.video) : lesson.video
                    }
                    : lesson
            )
        );

        updateLesson(editingLessonId, editForm)
            .then(response => console.log("Lesson updated:", response))
            .catch(error => console.error("Error updating lesson:", error));

        setEditingLessonId(null);
    };

    const handleDelete = (id) => {
        setLessons(lessons.filter(lesson => lesson.id !== id));
    };

    const handleNewLessonChange = (field, value) => {
        setNewLesson(prev => ({ ...prev, [field]: value }));
    };

    const handleAddLesson = async () => {
        if (newLesson.title.trim() !== "" && newLesson.video) {
            try {
                const result = await uploadLesson({
                    title: newLesson.title,
                    file: newLesson.video,
                    courseId
                });

                const addedLesson = {
                    id: Date.now(),
                    title: newLesson.title,
                    description: newLesson.description,
                    video: URL.createObjectURL(newLesson.video)
                };
                setLessons([...lessons, addedLesson]);
                setNewLesson({ title: "", description: "", video: null });
                setShowAddLesson(false);
                console.log("Upload success:", result);
            } catch (error) {
                alert("Upload thất bại");
            }
        }
    };

    return (
        <div className="lessons">
            <h2>Danh sách bài học</h2>
            {lessons.map((lesson) => (
                <div className="lesson-card" key={lesson.id}>
                    <div className="lesson-header">
                        <strong>{lesson.title}</strong>
                        <div className="lesson-actions">
                            <ViewVideo lessonKey={lesson.lessonKey} />
                            <button onClick={() => toggleEdit(lesson)}>
                                {editingLessonId === lesson.id ? "Đóng" : "Chỉnh sửa"}
                            </button>
                            <DeleteLesson lessonId={lesson.id} handleDelete={handleDelete} />
                        </div>
                    </div>
                    {editingLessonId === lesson.id && (
                        <EditLesson
                            lesson={lesson}
                            handleEditFormChange={handleEditFormChange}
                            editForm={editForm}
                            handleSaveEdit={handleSaveEdit}
                        />
                    )}
                </div>
            ))}

            {showAddLesson ? (
                <div className="add-lesson-form">
                    <h3>Thêm bài học mới</h3>
                    <label>Tên bài học:</label>
                    <input
                        type="text"
                        value={newLesson.title}
                        onChange={(e) => handleNewLessonChange("title", e.target.value)}
                    />
                    <label>Mô tả bài học:</label>
                    <textarea
                        value={newLesson.description}
                        onChange={(e) => handleNewLessonChange("description", e.target.value)}
                        rows="3"
                    />
                    <label>Upload video:</label>
                    <input
                        type="file"
                        accept="video/*"
                        onChange={(e) => handleNewLessonChange("video", e.target.files[0])}
                    />
                    <div className="form-buttons">
                        <button onClick={handleAddLesson}>Thêm</button>
                        <button onClick={() => setShowAddLesson(false)}>Hủy</button>
                    </div>
                </div>
            ) : (
                <button className="add-lesson-button" onClick={() => setShowAddLesson(true)}>+ Thêm bài học</button>
            )}
        </div>
    );
}
