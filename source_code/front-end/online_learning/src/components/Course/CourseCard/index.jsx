import { FaBook, FaRegClock } from 'react-icons/fa';
import "./CourseCard.scss";

export default function CourseCard(props) {
    const { course, handleClick, showPrice } = props;

    return (
        <div className="course-card">
            <div
                className="course-card__item"
                onClick={() => handleClick(course.id)}
                tabIndex={0}
                role="button"
            >
                <div className="course-card__image-wrap">
                    <img
                        src={course.imageUrl || "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTm3R3-To53B_Si_auM8ivKyijnhACF6BPYwA&s"}
                        className="course-card__image"
                        alt={course.title}
                    />
                </div>
                <div className="course-card__body">
                    <h5 className="course-card__title">{course.title}</h5>
                    <p className="course-card__desc">
                        {course.short_desc || "Mô tả khóa học chưa có"}
                    </p>
                    {showPrice && (
                        <p className="course-card__price">
                            {course.price?.toLocaleString() || 0} <span>VND</span>
                        </p>
                    )}
                    <div className="course-card__meta">
                        <span>
                            <FaRegClock /> {course.duration}
                        </span>
                        <span>
                            <FaBook /> {course.number_of_lessons} bài học
                        </span>
                    </div>
                </div>
            </div>
        </div>
    );
}