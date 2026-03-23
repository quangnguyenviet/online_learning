import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import RegisterBox from "./RegisterBox/RegisterBox";
import CourseDescription from "./CourseDescription/CourseDescription";
import styles from "./CourseDetail.module.scss";
import CourseApi from "service/apis/CourseApi";

export default function CourseDetail() {
    const { courseId } = useParams();

    const [course, setCourse] = useState({});
    const [loading, setLoading] = useState(true);

    const fetchCourseById = async (id) => {
        try {
            const response = await CourseApi.getCourseById(id);
            setCourse(response.data);
            setLoading(false);
            console.log(response);
        } catch (error) {
            console.error("Error fetching course by ID:", error);
            throw error;
        }
    };

    useEffect(() => {
        fetchCourseById(courseId);
    }, [courseId]);

    return (
        <section className={styles.section}>
            <div className={styles.container}>
                {/* <div className={styles.header}>
                    <h1 className={styles.title}>Chi tiết khóa học</h1>
                    <p className={styles.subtitle}>
                        Thông tin tổng quan, nội dung và đăng ký khóa học
                    </p>
                </div> */}
                {loading ? (
                    <div className={styles.loading}>Đang tải thông tin khóa học...</div>
                ) : (
                    <div className={styles.content}>
                        <div className={styles.main}>
                            <CourseDescription course={course} />
                        </div>
                        <aside className={styles.sidebar}>
                            <RegisterBox course={course} />
                        </aside>
                    </div>
                )}
            </div>
        </section>
    );
}
