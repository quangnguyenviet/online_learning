import styles from "../ViewDetail.module.scss";

export default function Objective({ outcomes }) {
    if (!outcomes || outcomes.length === 0) {
        return <p className={styles.emptyMessage}>Chưa có nội dung học được.</p>;
    }

    return (
        <ul className={styles.sectionList}>
            {outcomes.map((item, index) => (
                <li key={index}>✅ {item.description}</li>
            ))}
        </ul>
    );
}
