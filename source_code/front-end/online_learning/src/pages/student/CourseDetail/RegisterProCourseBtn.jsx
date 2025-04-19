export default function RegisterProCourseBtn(props) {
    const { course } = props;
    const token = localStorage.getItem("token");
    const URL_CREATE_ORDER = "http://localhost:8080/online_learning/zalopay/create-order" + `?courseId=${course.id}`;

    const handleRegister = () => {
        fetch(URL_CREATE_ORDER, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
           
        })
            .then((response) => response.json())
            .then((data) => {
                console.log(data);
                if (data.return_code === 1) {
                    const { order_url } = data;
                    window.location.href = order_url; // Redirect to the payment page
                    
                } else {
                    alert("Đăng ký khóa học thất bại!");
                }
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }
    
    
    return (
        <>
            <button className="btn btn-primary" onClick={handleRegister}>
                Đăng ký khóa học
            </button>
        </>

    );
}