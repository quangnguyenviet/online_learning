import { FaBook, FaRegClock } from 'react-icons/fa';
import "./CourseCard.scss";

export default function CourseCard(props) {
    const { course, handleClick, showPrice, showProgress = true } = props;

    const getLevelClass = (level) => {
        if (!level) return "beginner";
        return level.toLowerCase().replace(/_/g, "-");
    };

    const getLevelText = (level) => {
        const levelMap = {
            "BEGINNER": "Mới bắt đầu",
            "INTERMEDIATE": "Trung cấp",
            "ADVANCED": "Nâng cao",
            "ALL_LEVELS": "Tất cả cấp độ"
        };
        return levelMap[level] || level;
    };

    const getDiscountedPrice = () => {
        if (!course.price || !course.discount) return course.price;
        return Math.floor(course.price * ((100 - course.discount) / 100));
    };

    const discountedPrice = getDiscountedPrice();

    return (
        <div className="course-card">
            <div
                className="course-card__item"
                onClick={() => handleClick(course.id)}
                tabIndex={0}
                role="button"
            >
                <div className="course-card__image-wrap">
                    {course.discount > 0 && (
                        <div className="course-card__discount-badge">
                            <span className="course-card__discount-value">-{course.discount}%</span>
                        </div>
                    )}
                    <div className={`course-card__level-badge level-${getLevelClass(course.level)}`}>
                        {getLevelText(course.level)}
                    </div>
                    <img
                        src={course.imageUrl || "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTm3R3-To53B_Si_auM8ivKyijnhACF6BPYwA&s"}
                        className="course-card__image"
                        alt={course.title}
                    />
                </div>
                <div className="course-card__body">
                    <h5 className="course-card__title">{course.title}</h5>
                    <p className="course-card__desc">
                        {course.shortDesc || "Mô tả khóa học chưa có"}
                    </p>
                    {showPrice && (
                        <div className="course-card__price-wrapper">
                            {course.discount > 0 && (
                                <p className="course-card__original-price">
                                    {course.price?.toLocaleString() || 0} <span>VND</span>
                                </p>
                            )}
                            <p className="course-card__price">
                                {discountedPrice?.toLocaleString() || 0} <span>VND</span>
                            </p>
                        </div>
                    )}
                    <div className="course-card__meta">
                        <span>
                            <FaRegClock /> {course.duration}
                        </span>
                        <span>
                            <FaBook /> {course.numberOfLessons} bài học
                        </span>
                    </div>
                    {showProgress && course.completionPercentage !== undefined && course.completionPercentage !== null && (
                        <div className="course-card__progress">
                            <div className="course-card__progress-info">
                                <span className="course-card__progress-label">Tiến độ học</span>
                                <span className="course-card__progress-percent">{Math.round(course.completionPercentage)}%</span>
                            </div>
                            <div className="course-card__progress-bar">
                                <div 
                                    className="course-card__progress-fill" 
                                    style={{ width: `${course.completionPercentage}%` }}
                                ></div>
                            </div>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
}