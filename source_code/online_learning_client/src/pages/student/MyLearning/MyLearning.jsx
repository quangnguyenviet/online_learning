import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import CourseCard from "components/Course/CourseCard/CourseCard";
import styles from "./MyLearning.module.scss";
import CourseApi from "service/apis/CourseApi";

export default function MyLearning() {
    const URL = "http://localhost:8080/online_learning/courses/learning";
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem("token");
    const navigate = useNavigate();

    const fetchMyLearningCourses = async () => {
        try {
            const response = await CourseApi.getLearningCourses(0, 10);
            console.log("Fetched learning courses:", response);
            setCourses(response.data.content || []);
        } catch (error) {
            console.error("Error fetching learning courses:", error);
        } finally {
            setLoading(false);
        }    
    };
        

    useEffect(() => {
        fetchMyLearningCourses();
    }, []);
    

    const handleClick = (courseId) => {
        navigate(`/my-learning/${courseId}`);
    };

    return (
        <section className={styles.myLearningSection}>
            <div className={styles.myLearningContainer}>
                <h2 className={styles.myLearningTitle}>
                    <i className="fas fa-book-reader"></i> Khóa học của tôi
                </h2>
                {loading ? (
                    <div className={styles.myLearningLoading}>Đang tải...</div>
                ) : courses.length === 0 ? (
                    <div className={styles.myLearningEmpty}>Bạn chưa đăng ký khóa học nào.</div>
                ) : (
                    <div className={styles.myLearningList}>
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