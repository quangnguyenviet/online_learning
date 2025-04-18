// src/pages/instructor/Courses/CourseDetail.jsx
import { useEffect, useState } from "react";
import { WillLearn } from "pages/instructor/Courses/WillLearn"; // Import WillLearn component
import { CourseConditions } from "./CourseConditions";
import { LessonsList } from "pages/instructor/Courses/LessonsList"; // Import LessonsList component
import { getCourseConditions } from "utils/InstructorUtil/ConditionUtil"; // Import getCourseConditions function
import { useParams } from "react-router-dom"; // Import useParams for getting URL parameters

export function CourseDetail() {
    const { courseId } = useParams(); // Get courseId from URL parameters

    const [conditions, setConditions] = useState([
        "Biết sử dụng máy tính cơ bản",
        "Hiểu cơ bản về lập trình"
    ]);
    const [learnings, setLearnings] = useState([
        "Xây dựng trang web bằng React",
        "Kết nối frontend với backend bằng REST API"
    ]);
    const [lessons] = useState([
        {
            id: 1,
            title: "Giới thiệu khóa học",
            description: "Tổng quan về khóa học.",
            video: "https://youtube.com/example1"
        },
        {
            id: 2,
            title: "Cài đặt môi trường",
            description: "Hướng dẫn cài đặt công cụ.",
            video: "https://youtube.com/example2"
        }
    ]);

    useEffect(() => {
        getCourseConditions(courseId)
            .then((response) => {
                console.log("Response:", response);
                const data = response.data;
                console.log("Course conditions fetched successfully:", data);

            })
            .catch((error) => {
                console.error("Error fetching course conditions:", error);
            });

    }, []);

    return (
        <div style={{ padding: "20px", maxWidth: "800px", margin: "auto" }}>
            <h1>Chỉnh sửa khóa học</h1>

            {/* Điều kiện học */}
            <CourseConditions conditions={conditions} setConditions={setConditions} />

            {/* Bạn sẽ học được */}
            <WillLearn learnings={learnings} setLearnings={setLearnings} />

            {/* Danh sách bài học */}
            <LessonsList lessons={lessons} />
        </div>
    );
}
