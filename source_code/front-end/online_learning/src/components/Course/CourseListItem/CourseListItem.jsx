import { FaEdit, FaChartBar, FaUsers, FaCheck, FaTimes } from 'react-icons/fa';
import "./CourseListItem.scss";

export default function CourseListItem(props) {
    const { course, onEdit, onViewReport } = props;

    const formatCurrency = (value) => {
        return value?.toLocaleString() || 0;
    };

    const isPublished = course.status === 'PUBLISHED' || course.published === true;

    return (
        <div className="course-list-item">
            <div className="course-list-item__image-wrapper">
                <img
                    src={course.imageUrl || "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTm3R3-To53B_Si_auM8ivKyijnhACF6BPYwA&s"}
                    alt={course.title}
                    className="course-list-item__image"
                />
            </div>

            <div className="course-list-item__content">
                <div className="course-list-item__header">
                    <h3 className="course-list-item__title">{course.title}</h3>
                    <div className={`course-list-item__status ${isPublished ? 'published' : 'unpublished'}`}>
                        {isPublished ? (
                            <>
                                <FaCheck /> Đã xuất bản
                            </>
                        ) : (
                            <>
                                <FaTimes /> Chưa xuất bản
                            </>
                        )}
                    </div>
                </div>

                <p className="course-list-item__description">
                    {course.shortDesc || "Mô tả khóa học chưa có"}
                </p>

                <div className="course-list-item__stats">
                    <div className="course-list-item__stat">
                        <FaUsers className="course-list-item__stat-icon" />
                        <span className="course-list-item__stat-label">Học viên:</span>
                        <span className="course-list-item__stat-value">{course.studentCount || 0}</span>
                    </div>

                    <div className="course-list-item__stat">
                        <span className="course-list-item__stat-label">Doanh thu:</span>
                        <span className="course-list-item__stat-value">
                            {formatCurrency(course.revenue)} VND
                        </span>
                    </div>

                    <div className="course-list-item__stat">
                        <span className="course-list-item__stat-label">Bài học:</span>
                        <span className="course-list-item__stat-value">{course.numberOfLessons || 0}</span>
                    </div>
                </div>
            </div>

            <div className="course-list-item__actions">
                <button
                    className="course-list-item__btn course-list-item__btn--edit"
                    onClick={() => onEdit(course.id)}
                    title="Chỉnh sửa"
                >
                    <FaEdit /> Chỉnh sửa
                </button>
                <button
                    className="course-list-item__btn course-list-item__btn--report"
                    onClick={() => onViewReport(course.id)}
                    title="Xem báo cáo"
                >
                    <FaChartBar /> Báo cáo
                </button>
            </div>
        </div>
    );
}
