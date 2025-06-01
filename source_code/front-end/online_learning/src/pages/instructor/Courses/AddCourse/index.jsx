import { useState } from 'react';
import { createNewCourse } from 'utils/InstructorUtil/CourseUtil';
import './AddCourse.scss';
import Swal from 'sweetalert2';

export default function AddCourse() {

    const handleSubmit = (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const title = formData.get('title');
        const price = formData.get('price');
        const image = formData.get('courseImage');
        const shortDesc = formData.get('shortDesc');
        const data = {
            title: title,
            price: price,
            image: image,
            shortDesc: shortDesc,
        };

        createNewCourse(data)
            .then(response => {
                if (response.status === 1000) {
                    Swal.fire({
                        icon: 'success',
                        title: 'Thêm khóa học thành công!',
                        text: 'Khóa học đã được thêm vào hệ thống.',
                    }).then(() => {
                        window.location.href = '/instructor/courses';
                    });
                } else {
                    Swal.fire({
                        icon: 'error',
                        title: 'Thêm khóa học thất bại!',
                        text: response.message,
                    });
                }
            })
            .catch(error => {
                console.error("Error creating course:", error);
                alert("Có lỗi xảy ra khi thêm khóa học!");
            });
    }

    return (
        <div className="add-course-container">
            <h1 className="form-title">
                <i className="fas fa-plus-circle icon-title"></i> Thêm mới khóa học
            </h1>
            <form className="add-course-form" onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="courseName">Tên khóa học <span className="required">*</span></label>
                    <input
                        type="text"
                        className="form-control"
                        id="courseName"
                        name="title"
                        placeholder="Nhập tên khóa học"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="courseImage">Ảnh khóa học</label>
                    <input
                        type="file"
                        className="form-control"
                        id="courseImage"
                        name="courseImage"
                        accept="image/*"
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="coursePrice">Giá khóa học <span className="required">*</span></label>
                    <input
                        type="number"
                        className="form-control"
                        id="coursePrice"
                        name="price"
                        placeholder="Nhập giá (VNĐ)"
                        min="0"
                        required
                    />
                </div>

                <div className="form-group">
                    <label htmlFor="courseDescription">Mô tả ngắn</label>
                    <textarea
                        className="form-control"
                        id="courseDescription"
                        name="shortDesc"
                        rows="3"
                        placeholder="Nhập mô tả ngắn cho khóa học"
                    ></textarea>
                </div>

                <button type="submit" className="btn-submit">
                    <i className="fas fa-save"></i> Thêm khóa học
                </button>
            </form>
        </div>
    );
}
