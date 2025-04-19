import { useEffect, useState } from "react";
import GoToCourseBtn from "../../../components/GoToCourseBtn";
import RegisterFreeCourseBtn from "./RegisterFreeCourseBtn";
import RegisterProCourseBtn from "./RegisterProCourseBtn";

export default function RegisterBtn(props) {
    const URL_CHECK_REGISTER = "http://localhost:8080/online_learning/registers/check";
    const [loading, setLoading] = useState(true); // ✅ mở lại loading
    const [isRegistered, setIsRegistered] = useState(false); // state kiểm tra đăng ký

    const { course } = props;
    const token = localStorage.getItem("token");

    const handleRegister = () => {
        fetch("http://localhost:8080/online_learning/registers", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({
                courseId: course.id,
                studentId: null,
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                if (data.status === 1000) {
                    alert("Đăng ký khóa học thành công!");
                    setIsRegistered(true); // ✅ cập nhật state sau khi đăng ký thành công
                } else {
                    alert("Đăng ký khóa học thất bại!");
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    useEffect(() => {
        if (!course?.id) return;

        fetch(`${URL_CHECK_REGISTER}?courseId=${course.id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => response.json())
            .then((data) => {
                if (data.status === 1000 && data.data === true) {
                    setIsRegistered(true); // ✅ dùng setState thay vì gán trực tiếp
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            })
            .finally(() => {
                setLoading(false); // ✅ đúng chính tả
            });
    }, [course?.id]);

    return (
        <>
            {loading ? (
                <h1>Loading...</h1>
            ) : (
                <>
                    {isRegistered ? (
                        <GoToCourseBtn course={course} /> 
                    ) : (
                        (course.price === 0 ? (
                            <RegisterFreeCourseBtn course={course} /> 
                        ) : (
                            <RegisterProCourseBtn course={course} /> 
                        ))
                        
                    )}
                </>
            )}
        </>
    );
}
