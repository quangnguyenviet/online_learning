import React from "react";
import "./EditLesson.scss";

export default function EditLesson({ lesson, handleEditFormChange, handleSaveEdit }) {
    return (
        <div className="edit-lesson">
            <label htmlFor={`title-${lesson.id}`}>Tên bài học:</label>
            <input
                id={`title-${lesson.id}`}
                type="text"
                defaultValue={lesson.title}
                onChange={(e) => handleEditFormChange("title", e.target.value)}
            />

            <label htmlFor={`desc-${lesson.id}`}>Mô tả bài học:</label>
            <textarea
                id={`desc-${lesson.id}`}
                rows="3"
                defaultValue={lesson.description}
                onChange={(e) => handleEditFormChange("description", e.target.value)}
            />

            <label htmlFor={`video-${lesson.id}`}>Upload video mới:</label>
            <input
                id={`video-${lesson.id}`}
                type="file"
                accept="video/*"
                onChange={(e) => handleEditFormChange("video", e.target.files[0])}
            />

            <button className="btn-save" onClick={() => handleSaveEdit(lesson.id)}>
                Lưu
            </button>
        </div>
    );
}
