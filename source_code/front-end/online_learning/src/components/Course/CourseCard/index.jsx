import { useNavigate } from "react-router-dom";
import { FaBook, FaRegClock } from 'react-icons/fa';
import "./CourseCard.scss";
export default function CourseCard(props) {
    const ROLE = localStorage.getItem("role");

    const { course, handleClick, showPrice } = props;
    

    return (
        <div className="col-3 mb-4">
            <div
                className="card h-100 course__item"
                onClick={() => handleClick(course.id)}
                style={{ cursor: 'pointer' }}
            >
                <img
                    src={course.imageUrl || "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTm3R3-To53B_Si_auM8ivKyijnhACF6BPYwA&s"}
                    className="card-img-top course__image"
                    alt={course.title}
                />
                <div className="card-body d-flex flex-column">
                    <h5 className="card-title course__title">{course.title}</h5>
                    <p className="course__desc text-muted truncate-2-lines">
                        {course.short_desc || "Mô tả khóa học chưa có"}
                    </p>
                    {showPrice == true && (
                        <p className="course__price fw-bold">{course.price}VND</p>
                    )}
                    
                    <div className="meta-data">
                        <div className="d-flex justify-content-between align-items-center">
                            <div className="d-flex align-items-center me-auto">
                                <FaRegClock className="me-1" />
                                {course.hour}h {course.minute}m {course.second}s

                            </div>
                            <div className="d-flex align-items-center">
                                <FaBook className="me-1" /> {course.number_of_lessons} bài học
                            </div>


                        </div>
                    </div>

                </div>
            </div>
        </div>

    );
}