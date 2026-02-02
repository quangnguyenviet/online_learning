import { useEffect, useState } from "react";
import GoToCourseBtn from "../../../components/GoToCourseBtn";
import ZalopayApi from "service/apis/ZalopayApi";

export default function RegisterBtn(props) {
    const URL_CHECK_REGISTER = "http://localhost:8080/online_learning/registers/check";
    const [loading, setLoading] = useState(false);
    const [isRegistered, setIsRegistered] = useState(false); // state kiểm tra đăng ký

    const { course } = props;
    const token = localStorage.getItem("token");

    const createZaloPayOrder = async () => {
        try {
            const response = await ZalopayApi.createOrder(course.id);
            console.log("ZaloPay order created:", response);
            return response;
        } catch (error) {
            console.error("Error creating ZaloPay order:", error);
            throw error;
        }
    };

    // useEffect(() => {
    //     if (!course?.id) return;

    //     fetch(`${URL_CHECK_REGISTER}?courseId=${course.id}`, {
    //         method: "GET",
    //         headers: {
    //             "Content-Type": "application/json",
    //             Authorization: `Bearer ${token}`,
    //         },
    //     })
    //         .then((response) => response.json())
    //         .then((data) => {
    //             if (data.status === 1000 && data.data === true) {
    //                 setIsRegistered(true); // ✅ dùng setState thay vì gán trực tiếp
    //             }
    //         })
    //         .catch((error) => {
    //             console.error("Error:", error);
    //         })
    //         .finally(() => {
    //             setLoading(false); 
    //         });
    // }, [course?.id]);

    return (
        <>
            {loading ? (
                <h1>Loading...</h1>
            ) : (
                <>
                    {isRegistered ? (
                        // <GoToCourseBtn course={course} /> 
                        <button>Go to Course</button>
                    ) : (
                        <button onClick={createZaloPayOrder}>Register Now</button>
                    )}
                </>
            )}
        </>
    );
}
