import { useRef, useState } from "react";
import { saveConditions } from "utils/InstructorUtil/ConditionUtil";
import { useParams } from "react-router-dom";
import "./style.scss";

export function EditConditions(props) {
    const { courseId } = useParams();
    let delIdRef = useRef([]);

    const [editConditionIndex, setEditConditionIndex] = useState(null);
    const [newCondition, setNewCondition] = useState("");
    const [showAddCondition, setShowAddCondition] = useState(false);
    const [conditions, setConditions] = useState(props.conditions);

    const handleConditionEdit = (index, value) => {
        const updated = [...conditions];
        updated[index].description = value;
        updated[index].tag = "Y";
        setConditions(updated);
        setEditConditionIndex(null);
    };

    const handleConditionDelete = (index) => {
        setConditions(conditions.filter((condition, i) => {
            if (i === index && condition.id != null) {
                delIdRef.current.push(condition.id);
            }
            return i !== index;
        }));
    };

    const handleAddCondition = () => {
        if (newCondition.trim() !== "") {
            setConditions([
                ...conditions,
                { description: newCondition.trim(), tag: "Y" },
            ]);
            setNewCondition("");
            setShowAddCondition(false);
        }
    };

    const handleSave = () => {
        const otherList = conditions.filter(c => c.tag === "Y");
        const delIdList = delIdRef.current;

        saveConditions(courseId, {
            otherList,
            delIdList
        }).then(console.log).catch(console.error);
    };

    return (
        <div className="edit-conditions">
            <h2 className="section-title">Điều kiện để học được khóa học</h2>

            <ul className="condition-list">
                {conditions.map((condition, index) => (
                    <li key={index} className="condition-item">
                        {editConditionIndex === index ? (
                            <input
                                type="text"
                                defaultValue={condition.description}
                                onBlur={(e) => handleConditionEdit(index, e.target.value)}
                                autoFocus
                                className="condition-input"
                            />
                        ) : (
                            <>
                                <span className="condition-text">{condition.description}</span>
                                <button onClick={() => setEditConditionIndex(index)} className="btn btn-sm btn-outline-primary">Sửa</button>
                                <button onClick={() => handleConditionDelete(index)} className="btn btn-sm btn-outline-danger ms-2">Xoá</button>
                            </>
                        )}
                    </li>
                ))}
            </ul>

            {showAddCondition ? (
                <div className="add-condition-form">
                    <input
                        type="text"
                        value={newCondition}
                        onChange={(e) => setNewCondition(e.target.value)}
                        placeholder="Nhập điều kiện mới"
                        className="form-control"
                    />
                    <div className="mt-2">
                        <button onClick={handleAddCondition} className="btn btn-success me-2">Thêm</button>
                        <button onClick={() => setShowAddCondition(false)} className="btn btn-secondary">Hủy</button>
                    </div>
                </div>
            ) : (
                <button onClick={() => setShowAddCondition(true)} className="btn btn-primary mt-3">+ Thêm điều kiện</button>
            )}

            <div className="mt-4">
                <button onClick={handleSave} className="btn btn-dark">Lưu thay đổi</button>
            </div>
        </div>
    );
}
