import React from "react";
import { FaEdit, FaTrashAlt, FaTimes, FaVideo } from "react-icons/fa";
import DeleteLesson from "pages/instructor/Courses/LessonList/DeleteLesson/DeleteLesson";
import ViewVideo from "components/ViewVideo/ViewVideo";
import EditLesson from "pages/instructor/Courses/LessonList/EditLesson/EditLesson";
import styles from "../LessonList.module.scss";

export default function LessonItem({
    lesson,
    index,
    isEditing,
    onToggleEdit,
    onDelete,
    onSaveEdit,
    onTogglePreview,
}) {
    const previewInputId = `lesson-preview-${lesson.id}`;

    return (
        <div className={styles.lessonCard}>
            <div className={styles.lessonHeader}>
                <strong className={styles.lessonTitle}>
                    <span className={styles.orderChip}>#{index + 1}</span>
                    {lesson.title}
                    {lesson.isPreview && (
                        <span className={styles.previewBadge} title="Chế độ preview">
                            Preview
                        </span>
                    )}
                </strong>
                <div className={styles.lessonActions}>
                    <ViewVideo lessonId={lesson.id}>
                        <button className={styles.viewVideoBtn} title="Xem video">
                            <FaVideo />
                        </button>
                    </ViewVideo>
                    <div className={styles.previewToggle} title="Chế độ preview">
                        <label className={styles.previewLabel} htmlFor={previewInputId}>
                            Preview
                        </label>
                        <label className={styles.switch}>
                            <input
                                id={previewInputId}
                                className={styles.switchInput}
                                type="checkbox"
                                checked={!!lesson.isPreview}
                                onChange={() => onTogglePreview(lesson.id, lesson.isPreview)}
                            />
                            <span className={styles.switchSlider} />
                        </label>
                    </div>
                    <button type="button" onClick={() => onToggleEdit(lesson)} title="Chỉnh sửa">
                        {isEditing ? <FaTimes /> : <FaEdit />}
                    </button>
                    <DeleteLesson lessonId={lesson.id} handleDelete={onDelete}>
                        <FaTrashAlt />
                    </DeleteLesson>
                </div>
            </div>

            <div className={styles.lessonInfo}>
                <p className={styles.lessonDescription}>{lesson.description}</p>
                <div className={styles.lessonVideoStatus}>
                    {lesson.videoUrl ? (
                        lesson.duration ? (
                            <span>
                                Thời gian video: {Math.floor(lesson.duration / 60)} phút{" "}
                                {lesson.duration % 60} giây
                            </span>
                        ) : (
                            <span>Đang tải video...</span>
                        )
                    ) : (
                        <span>Đang tải video...</span>
                    )}
                </div>
            </div>

            {isEditing && (
                <EditLesson
                    lesson={lesson}
                    handleSaveEdit={onSaveEdit}
                />
            )}
        </div>
    );
}
