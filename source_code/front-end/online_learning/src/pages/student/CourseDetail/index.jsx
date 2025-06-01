import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import RegisterBox from "./RegisterBox";
import CourseDescription from "./CourseDescription";
import "./style.scss";

export default function CourseDetail() {
    const { courseId } = useParams();
    const url = "http://localhost:8080/online_learning/courses/" + courseId;

    const [course, setCourse] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchCourse = async () => {
            try {
                const response = await fetch(url);
                const data = await response.json();
                setCourse(data.data);
            } catch (error) {
                console.error("Error fetching course:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchCourse();
    }, [url]);

    return (
        <section className="course-detail-section">
            {loading ? (
                <div className="course-detail__loading">Đang tải thông tin khóa học...</div>
            ) : (
                <div className="course-detail__container">
                    <div className="course-detail__main">
                        <CourseDescription course={course} />
                    </div>
                    <aside className="course-detail__sidebar">
                        <RegisterBox course={course} />
                    </aside>
                </div>
            )}
        </section>
    );
}
