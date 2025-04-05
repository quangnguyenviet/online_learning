export default function RegisterBtn(props) {
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

    return (
        <button onClick={handleRegister} className="btn btn-primary">
            Đăng ký khóa học
        </button>
    );
}