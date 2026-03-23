import React, { useEffect } from "react";
import RegisterBtn from "pages/student/CourseDetail/RegisterBtn/RegisterBtn";
import { isRegistered as checkRegister } from "utils/StudentUtil/RegisterUtil";
import styles from "./RegisterBox.module.scss";
import { useSuccess } from "components/common/SucessDisplay/SuccessDisplay";

export default function RegisterBox({ course }) {
    const [isRegistered, setIsRegistered] = React.useState(false);

    const { SuccessDisplay, showSuccess } = useSuccess();

    useEffect(() => {
        if (!course?.id) return;
        // check registration status
        // checkRegister(course.id)
        //     .then((response) => {
        //         setIsRegistered(response.data);
        //         console.log("Registration status:", response);
        //     })
        //     .catch((error) => {
        //         console.error("Error checking registration:", error);
        //     });
    }, [course?.id]);

    if (!course?.id) return null;
    console.log(course);

    const handleRegisterClick = () => {
        showSuccess("Đăng ký khóa học thành công!");
    };

    const discountedPrice = parseInt(course.price * ((100 - course.discount) / 100), 10);
    const formattedPrice = new Intl.NumberFormat("vi-VN").format(discountedPrice);
    const formattedOriginalPrice = new Intl.NumberFormat("vi-VN").format(course.price || 0);

    return (
        <>
            <SuccessDisplay />
            <div className={styles.box}>
                <div className={styles.imageWrapper}>
                    <img
                        src={
                            course.imageUrl ||
                            "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"
                        }
                        alt="Course"
                        className={styles.image}
                    />
                    {course.discount > 0 && (
                        <div className={styles.discountBadge}>
                            <span className={styles.discountValue}>-{course.discount}%</span>
                        </div>
                    )}
                </div>

                {isRegistered ? (
                    <div className={styles.registered}>
                        <p>Bạn đã đăng ký khóa học này.</p>
                    </div>
                ) : (
                    <div className={styles.price}>
                        <div className={styles.priceLabel}>Học phí</div>
                        {course.discount > 0 && (
                            <div className={styles.originalPrice}>
                                <span className={styles.originalPriceText}>
                                    {formattedOriginalPrice} VNĐ
                                </span>
                            </div>
                        )}
                        <div className={styles.priceAmount}>
                            {formattedPrice} <span>VNĐ</span>
                        </div>
                        {course.discount > 0 && (
                            <div className={styles.priceNote}>
                                Đã hạng thấp -{course.discount}%
                            </div>
                        )}
                    </div>
                )}

                <div className={styles.includes}>
                    <strong>Khóa học bao gồm:</strong>
                    <ul>
                        <li>Tổng thời lượng: {course.duration}</li>
                        <li>{course.number_of_lessons} bài học video</li>
                        <li>Học mọi lúc trên điện thoại và TV</li>
                        <li>Quyền truy cập trọn đời</li>
                    </ul>
                </div>

                <div className={styles.button}>
                    <RegisterBtn course={course} onRegister={handleRegisterClick} />
                </div>
            </div>
        </>

    );
}
