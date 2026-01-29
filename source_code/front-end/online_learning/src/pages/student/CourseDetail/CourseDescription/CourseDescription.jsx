import {
    FaLightbulb,
    FaCheckCircle,
    FaPlayCircle,
    FaExclamationCircle,
    FaDotCircle
} from "react-icons/fa";
import styles from "./CourseDescription.module.scss";
import ViewLessonButton from "components/ViewLessonButton/ViewLessonButton";

export default function CourseDescription({ course }) {

    console.log(course);
    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h1 className={styles.title}>{course.title}</h1>
                <p className={styles.description}>{course.description}</p>
            </div>

            <hr className={styles.divider} />
            
            <section className={styles.section}>
                <h3 className={styles.sectionTitle}>
                    <FaLightbulb className={`${styles.icon} ${styles.iconYellow}`} />
                    Những gì bạn sẽ học
                </h3>
                <div className={styles.learnGrid}>
                    {(course.objectives || []).map((item, index) => (
                        <div className={styles.learnItem} key={index}>
                            <FaCheckCircle className={`${styles.icon} ${styles.iconGreen}`} />
                            <span>{item.description}</span>
                        </div>
                    ))}
                </div>
            </section>

            <hr className={styles.divider} />
            
            <section className={styles.section}>
                <h3 className={styles.sectionTitle}>
                    <FaPlayCircle className={`${styles.icon} ${styles.iconBlue}`} />
                    Danh sách bài học
                </h3>
                <ol className={styles.lessonList}>
                    {(course.lessons || []).map((lesson, index) => (
                        <li key={index} className={styles.lessonItem}>
                            <div className={styles.lessonHeader}>
                                <FaPlayCircle className={`${styles.icon} ${styles.iconGray}`} />
                                <span>{lesson.title}</span>
                                {lesson.isPreview && (
                                    <ViewLessonButton>
                                        <button className={styles.previewBtn}>Xem thử</button>
                                    </ViewLessonButton>
                                )}
                            </div>
                            {lesson.description && (
                                <p className={styles.lessonDescription}>
                                    {lesson.description}
                                </p>
                            )}
                        </li>
                    ))}
                </ol>
            </section>

            <hr className={styles.divider} />
            
            <section className={styles.section}>
                <h3 className={styles.sectionTitle}>
                    <FaExclamationCircle className={`${styles.icon} ${styles.iconRed}`} />
                    Yêu cầu / điều kiện tham gia
                </h3>
                <ul className={styles.requireList}>
                    {(course.requires || []).map((item, index) => (
                        <li key={index} className={styles.requireItem}>
                            <FaDotCircle className={`${styles.icon} ${styles.iconDark}`} />
                            <span>{item.description}</span>
                        </li>
                    ))}
                </ul>
            </section>
        </div>
    );
}
