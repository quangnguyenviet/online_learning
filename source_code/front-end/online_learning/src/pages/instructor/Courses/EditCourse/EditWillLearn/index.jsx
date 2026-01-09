import { saveWillLearn } from "utils/InstructorUtil/WillLearnUtil";
import React, { useContext, useRef, useState } from "react";
import { useParams } from "react-router-dom";
import "./style.scss";
import Swal from 'sweetalert2'
import { ModalContext } from "pages/instructor/Courses/CourseList/ViewDetail"; 
import { setData } from "service/StorageService";

export function EditWillLearn(props) {
    const { courseId } = useParams();

    const [learnings, setLearnings] = useState(props.willLearns || []);
    const [editLearningIndex, setEditLearningIndex] = useState(null);
    const [newLearning, setNewLearning] = useState("");
    const [showAddLearning, setShowAddLearning] = useState(false);
    const delIdRef = useRef([]);

    const context = useContext(ModalContext);
    const setShowModal = context.setShowModal;
    const setData = context.setData;

    const handleLearningEdit = (index, value) => {
        const updated = [...learnings];
        updated[index].description = value;
        updated[index].tag = "Y"; // nhãn Y đánh dấu đây là phần tử cần cập nhật hoặc thêm vào
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
            setLearnings([...learnings, { description: newLearning.trim(), tag: "Y" }]); // thêm mới phần tử với nhãn Y
            setNewLearning("");
            setShowAddLearning(false);
        }
    };

    const handleSave = () => {
        const otherList = learnings.filter(l => l.tag === "Y");
        const delIdList = delIdRef.current;

        saveWillLearn(courseId, { otherList, delIdList })
            .then(res => {
                if(res.status === 1000){
                    Swal.fire({
                        icon: 'success',
                        title: 'Thành công',
                        text: 'Cập nhật thành công!',
                        confirmButtonText: 'OK'
                    });
                    setData((prevData) => {
                        const updatedData = { ...prevData };
                        updatedData.learnWhats = learnings;
                        console.log("Updated data:", updatedData);
                        return updatedData;
                    });
                    console.log("Updated learnings:", learnings);
                    
                    setShowModal(false);
                }
            })
            .catch(err => console.error("Error:", err));
    };

    return (
        <div className="edit-will-learn">
            <h2 className="section-title">Bạn sẽ học được</h2>

            <ul className="learning-list">
                {learnings.map((learning, index) => (
                    <li key={index} className="learning-item">
                        {editLearningIndex === index ? (
                            <input
                                type="text"
                                defaultValue={learning.description}
                                onBlur={(e) => handleLearningEdit(index, e.target.value)}
                                autoFocus
                                className="learning-input"
                            />
                        ) : (
                            <>
                                <span className="learning-text">{learning.description}</span>
                                <button
                                    onClick={() => setEditLearningIndex(index)}
                                    className="btn btn-sm btn-outline-primary"
                                >
                                    Sửa
                                </button>
                                <button
                                    onClick={() => handleLearningDelete(index)}
                                    className="btn btn-sm btn-outline-danger ms-2"
                                >
                                    Xoá
                                </button>
                            </>
                        )}
                    </li>
                ))}
            </ul>

            {showAddLearning ? (
                <div className="add-learning-form">
                    <input
                        type="text"
                        value={newLearning}
                        onChange={(e) => setNewLearning(e.target.value)}
                        placeholder="Nhập mục tiêu mới"
                        className="form-control"
                    />
                    <div className="mt-2">
                        <button onClick={handleAddLearning} className="btn btn-success me-2">Thêm</button>
                        <button onClick={() => setShowAddLearning(false)} className="btn btn-secondary">Hủy</button>
                    </div>
                </div>
            ) : (
                <button onClick={() => setShowAddLearning(true)} className="btn btn-primary mt-3">+ Thêm mục tiêu</button>
            )}

            <div className="mt-4">
                <button onClick={handleSave} className="btn btn-dark">Lưu thay đổi</button>
            </div>
        </div>
    );
}
