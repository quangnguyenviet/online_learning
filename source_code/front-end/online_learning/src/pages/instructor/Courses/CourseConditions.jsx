import { useRef, useState } from "react";

export function CourseConditions(props) {

    let delIdRef = useRef([]);

    const [editConditionIndex, setEditConditionIndex] = useState(null);
    const [newCondition, setNewCondition] = useState("");
    const [showAddCondition, setShowAddCondition] = useState(false);

    const [conditions, setConditions] = useState(props.conditions);

    const handleConditionEdit = (index, value) => {
        const updated = [...conditions];
        updated[index].desc = value;
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
                desc: newCondition.trim(),
                tag: "Y"
            }

            setConditions([...conditions, newConditionObject]);
            setNewCondition("");
            setShowAddCondition(false);
        }
    };

    const handleSave = () => {

        const changeList = conditions
            .filter(condition => condition.tag === "Y");

        const delIdList = delIdRef.current;

        console.log("Edit List (full objects):", changeList);
        console.log("Delete List (IDs):", delIdList);
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
                                defaultValue={condition.desc}
                                onBlur={(e) => handleConditionEdit(index, e.target.value)}
                                autoFocus
                            />
                        ) : (
                            <>
                                <p className="condition">{condition.desc}</p>
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
