// src/components/WillLearn.jsx

import React, { useState } from "react";

export function WillLearn({ learnings, setLearnings }) {
    const [editLearningIndex, setEditLearningIndex] = useState(null);
    const [newLearning, setNewLearning] = useState("");
    const [showAddLearning, setShowAddLearning] = useState(false);

    const handleLearningEdit = (index, value) => {
        const updated = [...learnings];
        updated[index] = value;
        setLearnings(updated);
        setEditLearningIndex(null);
    };

    const handleLearningDelete = (index) => {
        setLearnings(learnings.filter((_, i) => i !== index));
    };

    const handleAddLearning = () => {
        if (newLearning.trim() !== "") {
            setLearnings([...learnings, newLearning.trim()]);
            setNewLearning("");
            setShowAddLearning(false);
        }
    };

    return (
        <div style={{ marginBottom: "30px" }}>
            <h2>Bạn sẽ học được</h2>
            <ul>
                {learnings.map((item, index) => (
                    <li key={index} style={{ marginBottom: "8px" }}>
                        {editLearningIndex === index ? (
                            <input
                                type="text"
                                defaultValue={item}
                                onBlur={(e) => handleLearningEdit(index, e.target.value)}
                                autoFocus
                            />
                        ) : (
                            <>
                                {item}
                                <button onClick={() => setEditLearningIndex(index)} style={{ marginLeft: "10px" }}>Sửa</button>
                                <button onClick={() => handleLearningDelete(index)} style={{ marginLeft: "5px", color: "red" }}>Xoá</button>
                            </>
                        )}
                    </li>
                ))}
            </ul>
            {showAddLearning ? (
                <div>
                    <input
                        type="text"
                        value={newLearning}
                        onChange={(e) => setNewLearning(e.target.value)}
                        placeholder="Nhập mục tiêu mới"
                        style={{ marginRight: "10px" }}
                    />
                    <button onClick={handleAddLearning}>Thêm</button>
                    <button onClick={() => setShowAddLearning(false)} style={{ marginLeft: "5px" }}>Hủy</button>
                </div>
            ) : (
                <button onClick={() => setShowAddLearning(true)}>+ Thêm mục tiêu</button>
            )}
        </div>
    );
}
