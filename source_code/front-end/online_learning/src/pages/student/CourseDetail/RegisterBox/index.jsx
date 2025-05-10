import React, { useEffect } from "react";
import RegisterBtn from "pages/student/CourseDetail/RegisterBtn";
import { isRegistered as checkRegister } from "utils/StudentUtil/RegisterUtil";
import "./RegisterBox.scss";

export default function RegisterBox({ course }) {
    const [isRegistered, setIsRegistered] = React.useState(false);

    useEffect(() => {
        if (!course?.id) return;

        checkRegister(course.id)
            .then((response) => {
                setIsRegistered(response.data);
                console.log("Registration status:", response);
            })
            .catch((error) => {
                console.error("Error checking registration:", error);
            });
    }, [course?.id]);

    if (!course?.id) return null;
    console.log(course);

    return (
        <div className="register-box col-3">
            <img
                src={
                    course.image ||
                    "https://letsenhance.io/static/73136da51c245e80edc6ccfe44888a99/1015f/MainBefore.jpg"
                }
                alt="Course"
                className="register-box__image"
            />

            {isRegistered ? (<div>
                <p>Bạn đã đăng ký khóa học này rồi</p>
            </div>) : (
                <div className="register-box__price">
                <strong>Giá: </strong>
                <div className="register-box__price-value">
                    {parseInt(course.price * ((100 - course.discount) / 100), 10)} VND
                </div>
            </div>
            )}

            

            

            <div className="register-box__includes">
                <strong>This course includes:</strong>
                <ul>
                    <li> {course.hour} Giờ {course.minute} phút </li>
                    <li>{course.number_of_lessons} video</li>
                    <li>Access on mobile and TV</li>
                    <li>Full lifetime access</li>
                </ul>
            </div>

            <div className="register-box__button">
                <RegisterBtn course={course} />
            </div>
        </div>
    );
}
