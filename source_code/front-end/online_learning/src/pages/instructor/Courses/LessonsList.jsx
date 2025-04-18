// src/components/LessonsList.jsx

import React, { useState } from "react";

export function LessonsList({ lessons }) {
    const [editingLessonId, setEditingLessonId] = useState(null);

    const toggleEdit = (id) => {
        setEditingLessonId(editingLessonId === id ? null : id);
    };

    return (
        <div>
            <h2>Danh sách bài học</h2>
            {lessons.map((lesson) => (
                <div
                    key={lesson.id}
                    style={{
                        border: "1px solid #ccc",
                        padding: "15px",
                        borderRadius: "8px",
                        marginBottom: "20px"
                    }}
                >
                    <div
                        style={{
                            display: "flex",
                            justifyContent: "space-between",
                            alignItems: "center"
                        }}
                    >
                        <strong>{lesson.title}</strong>
                        <div>
                            <button
                                onClick={() => window.open(lesson.video, "_blank")}
                                style={{ marginRight: "10px" }}
                            >
                                Xem
                            </button>
                            <button onClick={() => toggleEdit(lesson.id)}>
                                {editingLessonId === lesson.id ? "Đóng" : "Chỉnh sửa"}
                            </button>
                        </div>
                    </div>
                    {editingLessonId === lesson.id && (
                        <div style={{ marginTop: "15px" }}>
                            <label>Tên bài học:</label>
                            <input
                                type="text"
                                defaultValue={lesson.title}
                                style={{
                                    width: "100%",
                                    marginTop: "5px",
                                    marginBottom: "10px"
                                }}
                            />
                            <label>Mô tả bài học:</label>
                            <textarea
                                defaultValue={lesson.description}
                                rows="3"
                                style={{
                                    width: "100%",
                                    marginTop: "5px",
                                    marginBottom: "10px"
                                }}
                            />
                            <label>Upload video mới:</label>
                            <input
                                type="file"
                                accept="video/*"
                                style={{ display: "block", marginTop: "5px" }}
                            />
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
}
