import { Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getMyCourses } from 'utils/InstructorUtil/CourseUtil';
import CourseCard from 'components/Course/CourseCard';

export default function CourseList() {

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate(); // Thêm useNavigate

    useEffect(() => {
        getMyCourses().then((data) => {
            console.log("Courses Data:", data);
            if (data.status === 1000) {
                setCourses(data.data);
            }
            setLoading(false);
        }).catch((error) => {
            console.error("Error fetching courses:", error);
            setLoading(false); // đừng quên setLoading kể cả bị lỗi
        });
    }, []);

    // Hàm xử lý khi bấm nút "Thêm khóa học"
    const handleAddCourse = () => {
        navigate('/instructor/courses/add-new'); // Sử dụng navigate để chuyển hướng
    };

    console.log("Courses:", courses);
    return (
        <>
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2>My Courses</h2>
                <button className="btn btn-success" onClick={handleAddCourse}>
                    + Thêm Khóa Học
                </button>
            </div>

            <div className="row">
                {courses.map((course) => (
                    // <div className="col-3" key={course.id}>
                    //     <div className="card mb-4">
                    //         <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTm3R3-To53B_Si_auM8ivKyijnhACF6BPYwA&s" className="card-img-top" alt={course.title} />
                    //         <div className="card-body">
                    //             <h5 className="card-title">{course.title}</h5>
                    //             <p className="card-text">{course.description}</p>
                    //             <Link to={`/instructor/courses/${course.id}`} className="btn btn-primary">Xem Khóa Học</Link>
                    //         </div>
                    //     </div>
                    // </div>
                    <CourseCard course={course} key={course.id} handleClick={(id) => navigate(`/instructor/courses/${id}`)} showPrice={true} />
                ))}
            </div>
        </>
    );
}
