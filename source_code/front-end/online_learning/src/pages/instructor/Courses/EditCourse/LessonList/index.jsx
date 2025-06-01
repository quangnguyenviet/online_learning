import { DragDropContext, Droppable, Draggable } from "@hello-pangea/dnd";
import { updateLesson, uploadLesson } from "utils/InstructorUtil/LessonUtil";
import React, { useState } from "react";
import { useParams } from "react-router-dom";
import DeleteLesson from "pages/instructor/Courses/EditCourse/LessonList/DeleteLesson";
import ViewVideo from "pages/instructor/Courses/EditCourse/LessonList/ViewVideo";
import EditLesson from "pages/instructor/Courses/EditCourse/LessonList/EditLesson";
import { FaEdit, FaTrashAlt, FaPlus, FaTimes, FaVideo } from "react-icons/fa";
import Swal from "sweetalert2";
import "./style.scss";

export function LessonList(props) {

    const { courseId } = useParams();
    const [lessons, setLessons] = useState(props.lessons || []);
    const [isUploading, setIsUploading] = useState(false);
    const [editingLessonId, setEditingLessonId] = useState(null);
    const [showAddLesson, setShowAddLesson] = useState(false);
    const [newLesson, setNewLesson] = useState({ title: "", description: "", video: null });
    const [editForm, setEditForm] = useState({ title: "", description: "", video: null });

    // Hàm được gọi **khi người dùng kéo và thả một phần tử xong**. Tham số `result` chứa thông tin về vị trí trước và sau khi kéo.
    const handleDragEnd = (result) => {
        // Nếu không có vị trí đích (nghĩa là người dùng đã thả ra ngoài danh sách), không làm gì cả
        if (!result.destination) return;
        // Tạo một **bản sao mảng bài học (`lessons`)** để thao tác mà không làm thay đổi mảng gốc trực tiếp.
        const updatedLessons = Array.from(lessons);
        // Lấy ra bài học ở vị trí bắt đầu kéo (source.index). số 1 là lấy 1 phần từ
        const [reorderedItem] = updatedLessons.splice(result.source.index, 1);
        // - Chèn phần tử vừa kéo vào vị trí **mới (`destination.index`)**.
        // - `0` nghĩa là không xóa gì cả, chỉ chèn.
        updatedLessons.splice(result.destination.index, 0, reorderedItem);
 
        // Cập nhật lại order mới
        const reorderedWithOrder = updatedLessons.map((lesson, index) => ({
            ...lesson,
            order: index + 1
        }));

        setLessons(reorderedWithOrder);

        // Optional: gửi API cập nhật order lên server nếu cần
        reorderedWithOrder.forEach(lesson => {
            updateLesson(lesson.id, { order: lesson.order,
                tag : "UPDATE_INDEX"
             })
                .then(response => {
                    console.log("Order updated successfully:", response);
                })
                .catch(error => {
                    console.error("Error updating order:", error);
                });
        });
    };

    const toggleEdit = (lesson) => {
        if (editingLessonId === lesson.id) {
            setEditingLessonId(null);
        } else {
            setEditingLessonId(lesson.id);
            setEditForm({ title: lesson.title, description: lesson.description, video: null });
        }
    };

    const handleEditFormChange = (field, value) => {
        setEditForm(prev => ({ ...prev, [field]: value }));
    };

    const handleSaveEdit = (id) => {
        Swal.fire({
            title: 'Bạn có chắc chắn muốn lưu thay đổi?',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Lưu!',
            cancelButtonText: 'Hủy'
        }).then((result) => {
            if (result.isConfirmed) {
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

                updateLesson(id, editForm).catch(err => console.error("Update failed:", err));
                setEditingLessonId(null);

                Swal.fire('Đã lưu!', '', 'success');
            }
        });
    };

    const handleDelete = (id) => {
        setLessons(lessons.filter(lesson => lesson.id !== id));
    };

    const handleNewLessonChange = (field, value) => {
        setNewLesson(prev => ({ ...prev, [field]: value }));
    };

    const handleAddLesson = async () => {
        if (isUploading) return;
        if (newLesson.title.trim() && newLesson.video) {
            try {
                setIsUploading(true);

                const result = await uploadLesson({
                    title: newLesson.title,
                    file: newLesson.video,
                    description: newLesson.description,
                    order: lessons.length + 1,
                    courseId
                });

                setLessons([...lessons, result.data]);
                setNewLesson({ title: "", description: "", video: null });
                setShowAddLesson(false);
                Swal.fire('Thành công!', 'Bài học đã được thêm.', 'success');
            } catch (error) {
                alert("Upload thất bại");
            } finally {
                setIsUploading(false);
            }
        }
    };

    return (
        <div className="lessons">
            <DragDropContext onDragEnd={handleDragEnd}>
                <Droppable droppableId="lessonList">
                    {(provided) => (
                        <div ref={provided.innerRef} {...provided.droppableProps}>
                            {lessons.map((lesson, index) => (
                                <Draggable key={lesson.id} draggableId={lesson.id.toString()} index={index}>
                                    {(provided) => (
                                        <div
                                            className="lesson-card"
                                            ref={provided.innerRef}
                                            {...provided.draggableProps}
                                            {...provided.dragHandleProps}
                                        >
                                            <div className="lesson-header">
                                                <strong className="lesson-title">{lesson.title}</strong>
                                                <div className="lesson-actions">
                                                    <ViewVideo lessonKey={lesson.lessonKey}><FaVideo /></ViewVideo>
                                                    <div onClick={() => toggleEdit(lesson)} title="Chỉnh sửa">
                                                        {editingLessonId === lesson.id ? <FaTimes /> : <FaEdit />}
                                                    </div>
                                                    <DeleteLesson lessonId={lesson.id} handleDelete={handleDelete}>
                                                        <FaTrashAlt />
                                                    </DeleteLesson>
                                                </div>
                                            </div>

                                            <div className="lesson-info">
                                                <p className="lesson-description">{lesson.description}</p>
                                                {lesson.videoUrl && (
                                                    <p className="lesson-video-status">
                                                        {lesson.durationInSeconds ? (
                                                            <span>
                                                                Thời gian video: {Math.floor(lesson.durationInSeconds / 60)} phút{" "}
                                                                {lesson.durationInSeconds % 60} giây
                                                            </span>
                                                        ) : (
                                                            <span>Đang tải video...</span>
                                                        )}
                                                    </p>
                                                )}
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
                                    )}
                                </Draggable>
                            ))}
                            {provided.placeholder}
                        </div>
                    )}
                </Droppable>
            </DragDropContext>

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
                    <div className="form-buttons">
                        <button onClick={handleAddLesson}><FaPlus /> {isUploading ? "Đang tải..." : "Thêm bài học"}</button>
                        <button onClick={() => setShowAddLesson(false)}><FaTimes /> Hủy</button>
                    </div>
                </div>
            ) : (
                <button className="add-lesson-button" onClick={() => setShowAddLesson(true)}>
                    <FaPlus /> Thêm bài học
                </button>
            )}
        </div>
    );
}
