import { saveWillLearn } from "utils/InstructorUtil/WillLearnUtil";
import React, { useContext, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import "./style.scss";
import { ModalContext } from "pages/instructor/Courses/ViewDetail/ViewDetail"; 
import CourseApi from "service/apis/CourseApi";
import { useLoading } from "components/common/Loading/Loading";

export function EditObjectives(props) {
    console.log("EditObjectives props:", props);

    const [objectives, setObjectives] = useState(props.objectives || []);
    const [newObjective, setNewObjective] = useState("");
    const [showAddObjective, setShowAddObjective] = useState(false);
    const delIdRef = useRef([]);
    const originalObjectivesRef = useRef(props.objectives || []);
    const { GlobalLoading, showLoading, hideLoading } = useLoading();


    const context = useContext(ModalContext);
    const setShowModal = context.setShowModal;
    const setData = context.setData;
    const showSuccess = context.showSuccess;

    const handleObjectiveEdit = (index, value) => {
        const updated = [...objectives];
        updated[index] = { ...updated[index], description: value };
        setObjectives(updated);
    };

    const handleObjectiveDelete = (index) => {
        setObjectives(objectives.filter((objective, i) => {
            if (i === index && objective.id != null) {
                delIdRef.current.push(objective.id);
            }
            return i !== index;
        }));
    };

    const handleAddObjective = () => {
        if (newObjective.trim() !== "") {
            setObjectives([...objectives, { description: newObjective.trim() }]);
            setNewObjective("");
            setShowAddObjective(false);
        }
    };

    const handleSave = async () => {
        showLoading();
        // Get original objectives mapping
        const originalMap = new Map(
            originalObjectivesRef.current.map(obj => [obj.id, obj.description])
        );
        
        // Get deleted IDs
        const deletedIds = delIdRef.current;
        
        // Separate updated and new objectives
        const updatedObjectives = [];
        const newObjectives = [];
        const updatedIds = [];
        const updatedValues = [];
        
        objectives.forEach(objective => {
            if (objective.id != null) {
                // This is an existing objective
                const originalDescription = originalMap.get(objective.id);
                // Only include if description was changed
                if (originalDescription !== objective.description) {
                    updatedObjectives.push(objective);
                    updatedIds.push(objective.id);
                    updatedValues.push(objective.description);
                }
            } else {
                // This is a new objective (no id)
                newObjectives.push(objective);
            }
        });
        
        // Log all the tracked information
        console.log("Deleted IDs:", deletedIds);
        console.log("Updated IDs:", updatedIds);
        console.log("Updated Values:", updatedValues);
        console.log("New Objectives:", newObjectives);
        console.log("New Values:", newObjectives.map(obj => obj.description));
        
        // Create change summary object
        const changeSummary = {
            deletedIds: deletedIds,
            updatedIds: updatedIds,
            updatedValues: updatedValues,
            newValues: newObjectives.map(obj => obj.description),
            allUpdatedObjectives: updatedObjectives,
            allNewObjectives: newObjectives
        };
        
        console.log("Change Summary:", changeSummary);

        const formData = new FormData();
        // append course id
        formData.append("id", props.id);
        console.log("Course ID:", props.id);
        // create list of deleted ids
        // loop through deletedIds and append each to formData
        for (const id of deletedIds) {
            formData.append("deleteObjectiveIds", id);
        }
        // list of update 
        formData.append("updatedObjectives", new Blob([JSON.stringify(updatedObjectives)], { type: "application/json" }));
        // list of new objectives
        for(const newValue of newObjectives) {
            formData.append("newObjectives", newValue.description);
        }
        
        try {
            const response = await CourseApi.updateCourse(formData);
            console.log("Save response:", response);
            hideLoading();
            // refresh data in parent component
            setData(response.data);
            setShowModal(false);
            // Show success message
            showSuccess("Cập nhật mục tiêu khóa học thành công!");
        } catch (error) {
            console.error("Error saving objectives:", error);
        }
    };

    return (

        <div className="edit-will-learn">
            <GlobalLoading />
            <h2 className="section-title">Bạn sẽ học được</h2>

            <ul className="objective-list">
                {objectives.map((objective, index) => (
                    <li key={index} className="objective-item">
                        <input
                            type="text"
                            value={objective.description}
                            onChange={(e) => handleObjectiveEdit(index, e.target.value)}
                            className="objective-input"
                        />
                        <button
                            onClick={() => handleObjectiveDelete(index)}
                            className="btn btn-sm btn-outline-danger ms-2"
                        >
                            Xoá
                        </button>
                    </li>
                ))}
            </ul>

            {showAddObjective ? (
                <div className="add-objective-form">
                    <input
                        type="text"
                        value={newObjective}
                        onChange={(e) => setNewObjective(e.target.value)}
                        placeholder="Nhập mục tiêu mới"
                        className="form-control"
                    />
                    <div className="mt-2">
                        <button onClick={handleAddObjective} className="btn btn-success me-2">Thêm</button>
                        <button onClick={() => setShowAddObjective(false)} className="btn btn-secondary">Hủy</button>
                    </div>
                </div>
            ) : (
                <button onClick={() => setShowAddObjective(true)} className="btn btn-primary mt-3">+ Thêm mục tiêu</button>
            )}

            <div className="mt-4">
                <button onClick={handleSave} className="btn btn-dark">Lưu thay đổi</button>
            </div>
        </div>
    );
}
