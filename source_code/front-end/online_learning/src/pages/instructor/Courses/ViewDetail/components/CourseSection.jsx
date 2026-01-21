import styles from "../ViewDetail.module.scss";
import { FaEdit } from "react-icons/fa";

export default function CourseSection({ icon, title, onEdit, children, showEditButton = true }) {
    return (
        <section className={styles.section}>
            <div className={styles.sectionHeader}>
                <h2>
                    {icon} {title}
                </h2>
                {showEditButton && (
                    <button className={styles.btnEdit} onClick={onEdit}>
                        <FaEdit /> Sửa
                    </button>
                )}
            </div>
            {children}
        </section>
    );
}
