import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CourseCard from "components/Course/CourseCard";
import "./style.scss";

export default function MyLearning() {
    const URL = "http://localhost:8080/online_learning/courses/learning";
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    useEffect(() => {
        fetch(URL, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setCourses(data.data || []);
                setLoading(false);
            })
            .catch((error) => {
                console.error("Error:", error);
                setLoading(false);
            });
    }, [token]);

    const handleClick = (courseId) => {
        navigate(`/my-learning/${courseId}`);
    };

    return (
        <section className="my-learning-section">
            <div className="my-learning__container">
                <h2 className="my-learning__title">
                    <i className="fas fa-book-reader"></i> Khóa học của tôi
                </h2>
                {loading ? (
                    <div className="my-learning__loading">Đang tải...</div>
                ) : courses.length === 0 ? (
                    <div className="my-learning__empty">Bạn chưa đăng ký khóa học nào.</div>
                ) : (
                    <div className="my-learning__list">
                        {courses.map((item) => (
                            <CourseCard
                                course={item}
                                key={item.id}
                                handleClick={handleClick}
                                showPrice={false}
                            />
                        ))}
                    </div>
                )}
            </div>
        </section>
    );
}