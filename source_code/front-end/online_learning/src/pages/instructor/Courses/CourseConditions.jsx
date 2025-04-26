import { useRef, useState } from "react";
import { saveConditions } from "utils/InstructorUtil/ConditionUtil";
import { useParams } from "react-router-dom"; // Import useParams for getting URL parameters

export function CourseConditions(props) {
    const { courseId } = useParams(); // Get courseId from URL parameters

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
            if (i === index) {
                console.log(condition.id);
                if (condition.id != null) {
                    delIdRef.current.push(condition.id);

                }
            }
            return i !== index;
        }


        ));
    };

    const handleAddCondition = () => {

        if (newCondition.trim() !== "") {
            let newConditionObject = {
                description: newCondition.trim(),
                tag: "Y"
            }

            setConditions([...conditions, newConditionObject]);
            setNewCondition("");
            setShowAddCondition(false);
        }
    };

    const handleSave = () => {

        const otherList = conditions
            .filter(condition => condition.tag === "Y");

        const delIdList = delIdRef.current;

        saveConditions(courseId, {
            otherList: otherList,
            delIdList: delIdList
        }).then(response => {
            console.log(response);
        }).catch(error => {
            console.error("Error saving conditions:", error);
        });
    }

    return (
        <div style={{ marginBottom: "30px" }}>
            <h2>Điều kiện để học được khóa học</h2>
            <ul>
                {conditions.map((condition, index) => (
                    <li key={index} style={{ marginBottom: "8px" }}>
                        {editConditionIndex === index ? (
                            <input
                                type="text"
                                defaultValue={condition.description}
                                onBlur={(e) => handleConditionEdit(index, e.target.value)}
                                autoFocus
                            />
                        ) : (
                            <>
                                <p className="condition">{condition.description}</p>
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
                <button onClick={handleSave}>save</button>
            </div>

        </div>
    );
}
