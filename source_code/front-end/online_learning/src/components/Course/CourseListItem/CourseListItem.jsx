import { FaEdit, FaChartBar, FaUsers, FaCheck, FaTimes, FaRegClock } from 'react-icons/fa';
import { useState } from 'react';
import Modal from 'react-modal';
import "./CourseListItem.scss";

Modal.setAppElement('#root');

export default function CourseListItem(props) {
    const { course, onEdit, onViewReport, onPublish } = props;
    const [isModalOpen, setIsModalOpen] = useState(false);

    const formatCurrency = (value) => {
        return value?.toLocaleString() || 0;
    };

    const formatDate = (dateString) => {
        if (!dateString) return "Chưa có";
        const date = new Date(dateString);
        return date.toLocaleDateString('vi-VN', { 
            year: 'numeric', 
            month: 'long', 
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });
    };

    const isPublished = course.status === 'PUBLISHED' || course.published === true;

    const handlePublish = () => {
        onPublish?.(course.id);
        setIsModalOpen(false);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const calculateDiscountedPrice = (price, discount) => {
        if (discount > 0) {
            return price - (price * discount / 100);
        }
        return price;
    };

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
                    <div>
                        <h3 className="course-list-item__title">{course.title}</h3>
                        <p className="course-list-item__category">{course.categoryName}</p>
                    </div>
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

                <div className="course-list-item__pricing">
                    <div className="course-list-item__price">
                        <span className="course-list-item__price-label">Giá:</span>
                        <span className="course-list-item__price-value">{formatCurrency(calculateDiscountedPrice(course.price, course.discount))} VND</span>
                        {course.discount > 0 && (
                            <span className="course-list-item__discount">-{course.discount}%</span>
                        )}
                    </div>
                    <div className="course-list-item__created-date">
                        <span className="course-list-item__created-label">Ngày tạo:</span>
                        <span className="course-list-item__created-value">{formatDate(course.createdAt)}</span>
                    </div>
                </div>

                <div className="course-list-item__stats">
                    <div className="course-list-item__stat">
                        <FaUsers className="course-list-item__stat-icon" />
                        <span className="course-list-item__stat-label">Học viên:</span>
                        <span className="course-list-item__stat-value">{course.totalRegistrations || 0}</span>
                    </div>

                    <div className="course-list-item__stat">
                        <span className="course-list-item__stat-label">Doanh thu:</span>
                        <span className="course-list-item__stat-value">
                            {formatCurrency(course.totalEarnings)} VND
                        </span>
                    </div>

                    <div className="course-list-item__stat">
                        <span className="course-list-item__stat-label">Bài học:</span>
                        <span className="course-list-item__stat-value">{course.numberOfLessons || 0}</span>
                    </div>

                    <div className="course-list-item__stat">
                        <FaRegClock className="course-list-item__stat-icon" />
                        <span className="course-list-item__stat-label">Thời lượng:</span>
                        <span className="course-list-item__stat-value">{course.duration || "Chưa có"}</span>
                    </div>
                </div>
            </div>

            <div className="course-list-item__actions">
                {isPublished ? (
                    <>
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
                    </>
                ) : (
                    <>
                        <button
                            className="course-list-item__btn course-list-item__btn--edit"
                            onClick={() => onEdit(course.id)}
                            title="Tiếp tục chỉnh sửa"
                        >
                            <FaEdit /> Tiếp tục chỉnh sửa
                        </button>
                        <button
                            className="course-list-item__btn course-list-item__btn--publish"
                            onClick={() => setIsModalOpen(true)}
                            title="Xuất bản"
                        >
                            <FaCheck /> Xuất bản
                        </button>
                    </>
                )}
            </div>

            <Modal
                isOpen={isModalOpen}
                onRequestClose={closeModal}
                className="course-list-item__modal"
                overlayClassName="course-list-item__modal-overlay"
            >
                <div className="course-list-item__modal-header">
                    <h2>Xác nhận xuất bản khóa học</h2>
                    <button
                        className="course-list-item__modal-close"
                        onClick={closeModal}
                    >
                        ×
                    </button>
                </div>
                <div className="course-list-item__modal-body">
                    <p>Bạn có chắc chắn muốn xuất bản khóa học "<strong>{course.title}</strong>"?</p>
                    <p className="course-list-item__modal-note">
                        Sau khi xuất bản, khóa học sẽ hiển thị cho học viên và không thể chỉnh sửa một số thông tin cơ bản.
                    </p>
                </div>
                <div className="course-list-item__modal-footer">
                    <button
                        className="course-list-item__modal-btn course-list-item__modal-btn--cancel"
                        onClick={closeModal}
                    >
                        Hủy
                    </button>
                    <button
                        className="course-list-item__modal-btn course-list-item__modal-btn--confirm"
                        onClick={handlePublish}
                    >
                        <FaCheck /> Xuất bản
                    </button>
                </div>
            </Modal>
        </div>
    );
}
