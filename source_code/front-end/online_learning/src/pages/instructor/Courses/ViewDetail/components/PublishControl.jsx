import styles from "../ViewDetail.module.scss";

export default function PublishControl({ isPublished, onToggle }) {
    return (
        <div className={styles.publishControl}>
            <button
                className={`${styles.btnPublish} ${isPublished ? styles.revoke : styles.publish}`}
                onClick={onToggle}
            >
                {isPublished ? "Thu hồi khóa học" : "Xuất bản khóa học"}
            </button>
        </div>
    );
}
