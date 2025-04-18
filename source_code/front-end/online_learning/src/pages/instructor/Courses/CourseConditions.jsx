import { useState } from "react";

export function CourseConditions({ conditions, setConditions }) {
    const [editConditionIndex, setEditConditionIndex] = useState(null);
    const [newCondition, setNewCondition] = useState("");
    const [showAddCondition, setShowAddCondition] = useState(false);

    const handleConditionEdit = (index, value) => {
        const updated = [...conditions];
        updated[index] = value;
        setConditions(updated);
        setEditConditionIndex(null);
    };

    const handleConditionDelete = (index) => {
        setConditions(conditions.filter((_, i) => i !== index));
    };

    const handleAddCondition = () => {
        if (newCondition.trim() !== "") {
            setConditions([...conditions, newCondition.trim()]);
            setNewCondition("");
            setShowAddCondition(false);
        }
    };

    return (
        <div style={{ marginBottom: "30px" }}>
            <h2>Điều kiện để học được khóa học</h2>
            <ul>
                {conditions.map((condition, index) => (
                    <li key={index} style={{ marginBottom: "8px" }}>
                        {editConditionIndex === index ? (
                            <input
                                type="text"
                                defaultValue={condition}
                                onBlur={(e) => handleConditionEdit(index, e.target.value)}
                                autoFocus
                            />
                        ) : (
                            <>
                                {condition}
                                <button onClick={() => setEditConditionIndex(index)} style={{ marginLeft: "10px" }}>Sửa</button>
                                <button onClick={() => handleConditionDelete(index)} style={{ marginLeft: "5px", color: "red" }}>Xoá</button>
                            </>
                        )}
                    </li>
                ))}
            </ul>
            {showAddCondition ? (
                <div>
                    <input
                        type="text"
                        value={newCondition}
                        onChange={(e) => setNewCondition(e.target.value)}
                        placeholder="Nhập điều kiện mới"
                        style={{ marginRight: "10px" }}
                    />
                    <button onClick={handleAddCondition}>Thêm</button>
                    <button onClick={() => setShowAddCondition(false)} style={{ marginLeft: "5px" }}>Hủy</button>
                </div>
            ) : (
                <button onClick={() => setShowAddCondition(true)}>+ Thêm điều kiện</button>
            )}

            <div>
                <button>save</button>
            </div>
            
        </div>
    );
}
