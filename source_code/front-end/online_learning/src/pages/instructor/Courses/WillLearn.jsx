// src/components/WillLearn.jsx
import { saveWillLearn } from "utils/InstructorUtil/WillLearnUtil";
import React, { useRef, useState } from "react";
import { useParams } from "react-router-dom"; // Import useParams for getting URL parameters

export function WillLearn(props) {
    const { courseId } = useParams(); // Get courseId from URL parameters


    const [learnings, setLearnings] = useState(props.willLearns || []);
    const [editLearningIndex, setEditLearningIndex] = useState(null);
    const [newLearning, setNewLearning] = useState("");
    const [showAddLearning, setShowAddLearning] = useState(false);

    const delIdRef = useRef([]);

    const handleLearningEdit = (index, value) => {
        const updated = [...learnings];
        updated[index].description = value;
        updated[index].tag = "Y";
        setLearnings(updated);
        setEditLearningIndex(null);
    };

    const handleLearningDelete = (index) => {
        setLearnings(learnings.filter((learning, i) => {
            if (i === index && learning.id != null) {
                delIdRef.current.push(learning.id);
            }
            return i !== index;
        }));
    };

    const handleAddLearning = () => {
        if (newLearning.trim() !== "") {
            const newLearningObj = {
                description: newLearning.trim(),
                tag: "Y"
            };
            setLearnings([...learnings, newLearningObj]);
            setNewLearning("");
            setShowAddLearning(false);
        }
    };

    const handleSave = () => {
        const otherList = learnings.filter(learning => learning.tag === "Y");
        const delIdList = delIdRef.current;

        saveWillLearn(courseId, { otherList, delIdList })
            .then(res => console.log("Saved", res))
            .catch(err => console.error("Error:", err));
        
    };

    return (
        <div style={{ marginBottom: "30px" }}>
            <h2>Bạn sẽ học được</h2>
            <ul>
                {learnings.map((learning, index) => (
                    <li key={index} style={{ marginBottom: "8px" }}>
                        {editLearningIndex === index ? (
                            <input
                                type="text"
                                defaultValue={learning.description}
                                onBlur={(e) => handleLearningEdit(index, e.target.value)}
                                autoFocus
                            />
                        ) : (
                            <>
                                <p className="learning">{learning.description}</p>
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
            <div>
                <button onClick={handleSave}>save</button>
            </div>
        </div>
    );
}
