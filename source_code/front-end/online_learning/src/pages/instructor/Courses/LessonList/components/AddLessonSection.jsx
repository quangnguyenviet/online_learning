import React from "react";
import { FaPlus, FaTimes } from "react-icons/fa";
import styles from "../LessonList.module.scss";

export default function AddLessonSection({
    showAddLesson,
    newLesson,
    onChange,
    onAddLesson,
    onCancel,
    onShowForm,
}) {
    if (showAddLesson) {
        return (
            <div className={styles.addLessonForm}>
                <h3>Thêm bài học mới</h3>
                <label>Tên bài học:</label>
                <input
                    type="text"
                    value={newLesson.title}
                    required={true}
                    onChange={(e) => onChange("title", e.target.value)}
                />
                <label>Mô tả bài học:</label>
                <textarea
                    rows="3"
                    value={newLesson.description}
                    required={true}
                    onChange={(e) => onChange("description", e.target.value)}
                />
                <label>Upload video:</label>
                <input
                    type="file"
                    accept="video/*"
                    onChange={(e) => onChange("video", e.target.files[0])}
                />
                <div className={styles.formButtons}>
                    <button onClick={onAddLesson}><FaPlus /> Thêm bài học</button>
                    <button onClick={onCancel}><FaTimes /> Hủy</button>
                </div>
            </div>
        );
    }

    return (
        <button className={styles.addLessonButton} onClick={onShowForm}>
            <FaPlus /> Thêm bài học
        </button>
    );
}
