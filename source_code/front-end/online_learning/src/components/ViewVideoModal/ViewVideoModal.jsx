import styles from "./ViewVideoModal.module.scss";

export default function ViewVideoModal({ isOpen, onClose, title, children }) {
    if (!isOpen) return null;

    return (
        <div
            className={`${styles.modal} ${isOpen ? styles.modalActive : ""}`}
            onClick={onClose}
        >
            <div
                className={styles.modal__content}
                onClick={(e) => e.stopPropagation()}
            >
                <div className={styles.modal__bar}>
                    <span className={styles.modal__title}>{title}</span>
                    <button
                        type="button"
                        className={styles.closeButton}
                        onClick={onClose}
                    >
                        ×
                    </button>
                </div>

                <div className={styles.modal__body}>
                    {children}
                </div>
            </div>
        </div>
    );
}
