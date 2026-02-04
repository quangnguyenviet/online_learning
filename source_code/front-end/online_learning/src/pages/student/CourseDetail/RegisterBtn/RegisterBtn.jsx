import { use, useEffect, useState } from "react";
import GoToCourseBtn from "../../../../components/GoToCourseBtn";
import ZalopayApi from "service/apis/ZalopayApi";
import { useNavigate } from "react-router-dom";
import RegisterApi from "service/apis/RegisterApi";
import styles from "./RegisterBtn.module.scss";

export default function RegisterBtn(props) {
    const [loading, setLoading] = useState(false);
    const [isRegistered, setIsRegistered] = useState(false); // state kiểm tra đăng ký
    const nav = useNavigate();

    const { course } = props;

    const createZaloPayOrder = async () => {
        if (!course?.id) return;

        try {
            const { data } = await ZalopayApi.createOrder(course.id);
            window.location.href = data.order_url;
        } catch (error) {
            console.error("Error creating ZaloPay order:", error);
        }
    };

    const checkRegister = async () => {
        try {
            const { data } = await RegisterApi.isRegistered(course.id);
            console.log("Registration check data:", data);
            if (data){
                setIsRegistered(true);
            }
            
        } catch (error) {
            console.error("Error checking registration:", error);
            return false;
        }
    };

    useEffect(() => {
        if (!course?.id) return;
        checkRegister();
        
    }, [course?.id]);

    return (
        <>
            {loading ? (
                <h1>Loading...</h1>
            ) : (
                <div className={styles.actions}>
                    {isRegistered ? (
                        // <GoToCourseBtn course={course} /> 
                        <button className={`${styles.button} ${styles.secondary}`}>Go to Course</button>
                    ) : (
                        <button className={`${styles.button} ${styles.primary}`} onClick={createZaloPayOrder}>Register</button>
                    )}
                </div>
            )}
        </>
    );
}
