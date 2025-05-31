import Swal from "sweetalert2";

export default function RegisterFreeCourseBtn(props) {
    const URL_CHECK_REGISTER = "http://localhost:8080/online_learning/registers/check";
    const token = localStorage.getItem("token");
    const { course } = props;
    
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
                    Swal.fire({
                        icon: "success",
                        title: "Đăng ký khóa học thành công",
                        text: "Bạn đã đăng ký khóa học thành công!",

                    }
                    ).then(() => {
                        window.location.reload();
                    });

                } else {
                    alert("Đăng ký khóa học thất bại!");
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    return (
        <button onClick={handleRegister} className="btn btn-primary">
                            Đăng ký khóa học
        </button>
    );
}