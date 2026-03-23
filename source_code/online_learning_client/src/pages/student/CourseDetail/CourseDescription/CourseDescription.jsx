import {
    FaLightbulb,
    FaCheckCircle,
    FaPlayCircle,
    FaExclamationCircle,
    FaDotCircle,
    FaGraduationCap,
    FaTags
} from "react-icons/fa";
import styles from "./CourseDescription.module.scss";
import ViewVideo from "components/ViewVideo/ViewVideo";

export default function CourseDescription({ course }) {
    const getLevelText = (level) => {
        const levelMap = {
            "BEGINNER": "Mới bắt đầu",
            "INTERMEDIATE": "Trung cấp",
            "ADVANCED": "Nâng cao",
            "ALL_LEVELS": "Tất cả cấp độ"
        };
        return levelMap[level] || level;
    };

    const getLevelColor = (level) => {
        const colorMap = {
            "BEGINNER": "#4caf50",
            "INTERMEDIATE": "#ff9800",
            "ADVANCED": "#f44336",
            "ALL_LEVELS": "#2196f3"
        };
        return colorMap[level] || "#666";
    };

    const categoryName = course?.category?.name || "";

    console.log(course);
    return (
        <div className={styles.container}>
            <div className={styles.header}>
                <h1 className={styles.title}>{course.title}</h1>
                {course.level && (
                    <div className={styles.levelInfo}>
                        <FaGraduationCap className={styles.levelIcon} style={{ color: getLevelColor(course.level) }} />
                        <span className={styles.levelText}>Cấp độ: </span>
                        <span 
                            className={styles.levelBadge} 
                            style={{ backgroundColor: getLevelColor(course.level) }}
                        >
                            {getLevelText(course.level)}
                        </span>
                    </div>
                )}
                {categoryName && (
                    <div className={styles.categoryInfo}>
                        <FaTags className={styles.categoryIcon} />
                        <span className={styles.categoryText}>Danh mục: </span>
                        <span className={styles.categoryBadge}>{categoryName}</span>
                    </div>
                )}
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
                                    <ViewVideo lessonId={lesson.id}>
                                        <button className={styles.previewBtn}>Xem thử</button>
                                    </ViewVideo>
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
