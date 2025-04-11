import { useEffect } from "react";

export default function RegisterBtn(props) {
    const URL_CHECK_REGISTER = "http://localhost:8080/online_learning/registers/check";

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
                studentId: null
            }),
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                if (data.status === 1000) {
                    alert("Đăng ký khóa học thành công!");
                } else {
                    alert("Đăng ký khóa học thất bại!");
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    useEffect(() => {
        if (!course?.id) return; // kiểm tra có courseId không
    
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
                    alert("Bạn đã đăng ký khóa học này rồi!");
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }, [course?.id]); // chạy lại khi courseId thay đổi
    

    return (
        <button onClick={handleRegister} className="btn btn-primary">
            Đăng ký khóa học
        </button>
    );
}