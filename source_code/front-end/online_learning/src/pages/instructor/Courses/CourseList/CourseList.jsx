import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getMyCourses } from 'utils/InstructorUtil/CourseUtil';
import CourseCard from 'components/Course/CourseCard';
import styles from './CourseList.module.scss';

export default function CourseList() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        getMyCourses().then((data) => {
            if (data.status === 1000) {
                setCourses(data.data);
            }
            setLoading(false);
        }).catch((error) => {
            setLoading(false);
        });
    }, []);

    const handleAddCourse = () => {
        navigate('/instructor/courses/add-new');
    };

    return (
        <section className={styles['instructor-course-list-section']}>
            <div className={styles['instructor-course-list__header']}>
                <h2 className={styles['instructor-course-list__title']}>
                    <i className="fas fa-book"></i> Khóa học của tôi
                </h2>
                <button className={styles['instructor-course-list__add-btn']} onClick={handleAddCourse}>
                    + Thêm Khóa Học
                </button>
            </div>
            {loading ? (
                <div className={styles['instructor-course-list__loading']}>Đang tải...</div>
            ) : courses.length === 0 ? (
                <div className={styles['instructor-course-list__empty']}>Bạn chưa tạo khóa học nào.</div>
            ) : (
                <div className={styles['instructor-course-list__grid']}>
                    {courses.map((course) => (
                        <CourseCard
                            course={course}
                            key={course.id}
                            handleClick={(id) => navigate(`/instructor/courses/${id}`)}
                            showPrice={true}
                        />
                    ))}
                </div>
            )}
        </section>
    );
}
