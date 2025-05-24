import { useContext, useState } from "react";
import { FaBook, FaMoneyBillWave, FaAlignLeft, FaSave } from "react-icons/fa";
import "./EditGeneral.scss";
import { updateCourse } from "utils/InstructorUtil/CourseUtil";
import Swal from 'sweetalert2'
import { ModalContext } from "pages/instructor/Courses/CourseList/ViewDetail";

export default function EditGeneral({ data }) {
    const [title, setTitle] = useState(data.title || "");
    const [price, setPrice] = useState(data.price || 0);
    const [description, setDescription] = useState(data.short_desc || "");
    const context = useContext(ModalContext);
    const setShowModal = context.setShowModal;
    const setData = context.setData;

    const handleSave = () => {


        const updatedData = {
            title,
            price,
            shortDesc: description,
            tag: "GENERAL"
        };

        updateCourse(data.id, updatedData)
            .then((response) => {

                // Handle success (e.g., show a success message)
                Swal.fire({
                    title: "Thành Công!",
                    icon: "success",
                    draggable: true
                });

                console.log(response);

                // update data in parent component
                setData((prevData) => ({
                    ...prevData,
                    title: updatedData.title,
                    price: updatedData.price,
                    short_desc: updatedData.shortDesc,
                }));
                

                console.log("vao day");

                 // close modal
                setShowModal(false);


            })
            .catch((error) => {
                console.error("Error updating course:", error);
                // Handle error (e.g., show an error message)
            });

        //    console.log("Updated Data:", updatedData);




    };

    return (
        <div className="edit-general-container">
            <h2><FaBook className="icon purple" /> Tên khóa học</h2>
            <input
                type="text"
                className="form-control"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                placeholder="Nhập tên khóa học"
            />

            <h2><FaMoneyBillWave className="icon green" /> Giá khóa học (VNĐ)</h2>
            <input
                type="number"
                className="form-control"
                value={price}
                onChange={(e) => setPrice(e.target.value)}
                min={0}
                placeholder="Nhập giá khóa học"
            />

            <h2><FaAlignLeft className="icon blue" /> Mô tả ngắn</h2>
            <textarea
                className="form-control"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                rows={4}
                placeholder="Nhập mô tả ngắn cho khóa học"
            ></textarea>

            <button type="button" className="btn btn-primary save-btn" onClick={handleSave}>
                <FaSave className="me-2" /> Lưu
            </button>
        </div>
    );
}
