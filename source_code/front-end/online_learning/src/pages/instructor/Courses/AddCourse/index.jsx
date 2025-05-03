import { useState } from 'react';
import { createNewCourse } from 'utils/InstructorUtil/CourseUtil';

export default function AddCourse() {

    const handleSubmit = (event) => {
        event.preventDefault(); // prevent default form submission

        const formData = new FormData(event.target); // get form data
        const title = formData.get('title'); // get course name
        // const courseImage = formData.get('courseImage'); // get course image
        const price = formData.get('price'); // get course price

        createNewCourse(title, price)
            .then(response => {
                console.log("Course created successfully:", response);
            })
            .catch(error => {
                console.error("Error creating course:", error);
                alert("Có lỗi xảy ra khi thêm khóa học!");
            });
    }

    return (
        <>
            <h1>Thêm mới khóa học</h1>
            <form className='formCreate' onSubmit={handleSubmit}>
                <div className="row form-group mb-3">
                    <div className="col-6">
                        <label htmlFor="courseName">Tên khóa học</label>
                        <input type="text" className="form-control" id="courseName" placeholder="Nhập tên khóa học"
                        name='title' required
                        />
                    </div>
                </div>

                <div className="row form-group mb-3">
                    <div className="col-6">
                        <label htmlFor="courseImage">Ảnh khóa học</label>
                        <input type="file" className="form-control" id="courseImage" />
                    </div>
                </div>

                <div className="row form-group mb-3">
                    <div className="col-6">
                        <label htmlFor="coursePrice">Giá khóa học</label>
                        <input type="number" className="form-control" id="coursePrice" placeholder="Nhập giá (VNĐ)" 
                        name='price'
                        />
                    </div>
                </div>

                <button type="submit" className="btn btn-primary">Thêm khóa học</button>
            </form>
        </>
    );
}
