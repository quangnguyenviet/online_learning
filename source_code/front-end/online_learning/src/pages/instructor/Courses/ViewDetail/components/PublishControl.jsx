import { useState } from 'react';
import { FaCheck, FaTimes } from 'react-icons/fa';
import Modal from 'react-modal';
import styles from "../ViewDetail.module.scss";

Modal.setAppElement('#root');

export default function PublishControl({ isPublished, onToggle, courseTitle, courseId }) {
    const [isModalOpen, setIsModalOpen] = useState(false);

    const handleOpenModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const handleConfirm = () => {
        onToggle(!isPublished);
        setIsModalOpen(false);
    };

    return (
        <>
            <div className={styles.publishControl}>
                <button
                    className={`${styles.btnPublish} ${isPublished ? styles.revoke : styles.publish}`}
                    onClick={handleOpenModal}
                >
                    {isPublished ? "Thu hồi khóa học" : "Xuất bản khóa học"}
                </button>
            </div>

            <Modal
                isOpen={isModalOpen}
                onRequestClose={closeModal}
                className={styles.publishModal}
                overlayClassName={styles.publishModalOverlay}
            >
                <div className={styles.modalHeader}>
                    <h2>{isPublished ? "Xác nhận thu hồi khóa học" : "Xác nhận xuất bản khóa học"}</h2>
                    <button
                        className={styles.modalClose}
                        onClick={closeModal}
                    >
                        ×
                    </button>
                </div>
                <div className={styles.modalBody}>
                    {isPublished ? (
                        <>
                            <p>Bạn có chắc chắn muốn thu hồi khóa học "<strong>{courseTitle}</strong>"?</p>
                            <p className={styles.modalNote}>
                                Sau khi thu hồi, khóa học sẽ không còn hiển thị cho học viên. Học viên vẫn giữ quyền truy cập nếu họ đã đăng ký trước đó.
                            </p>
                        </>
                    ) : (
                        <>
                            <p>Bạn có chắc chắn muốn xuất bản khóa học "<strong>{courseTitle}</strong>"?</p>
                            <p className={styles.modalNote}>
                                Sau khi xuất bản, khóa học sẽ hiển thị cho học viên và không thể chỉnh sửa một số thông tin cơ bản.
                            </p>
                        </>
                    )}
                </div>
                <div className={styles.modalFooter}>
                    <button
                        className={`${styles.modalBtn} ${styles.modalBtnCancel}`}
                        onClick={closeModal}
                    >
                        Hủy
                    </button>
                    <button
                        className={`${styles.modalBtn} ${isPublished ? styles.modalBtnRevoke : styles.modalBtnConfirm}`}
                        onClick={handleConfirm}
                    >
                        {isPublished ? (
                            <>
                                <FaTimes /> Thu hồi
                            </>
                        ) : (
                            <>
                                <FaCheck /> Xuất bản
                            </>
                        )}
                    </button>
                </div>
            </Modal>
        </>
    );
}
