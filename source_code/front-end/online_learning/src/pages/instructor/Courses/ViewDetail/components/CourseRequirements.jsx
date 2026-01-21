import styles from "../ViewDetail.module.scss";

export default function CourseRequirements({ requirements }) {
    if (!requirements || requirements.length === 0) {
        return <p className={styles.emptyMessage}>Không có yêu cầu đặc biệt.</p>;
    }

    return (
        <ul className={styles.sectionList}>
            {requirements.map((item, index) => (
                <li key={index}>🔒 {item.description}</li>
            ))}
        </ul>
    );
}
