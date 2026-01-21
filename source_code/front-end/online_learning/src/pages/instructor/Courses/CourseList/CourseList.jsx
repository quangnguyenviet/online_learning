import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import CourseCard from 'components/Course/CourseCard';
import styles from './CourseList.module.scss';
import CourseApi from 'service/apis/CourseApi';

export default function CourseList() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const fetchCourses = async () => {
        try {
            const response = await CourseApi.getMyCourses();
            const data = response.data;
            setCourses(data);
            setLoading(false);
            return response;
        } catch (error) {
            console.error('Error fetching courses:', error);
            throw error;
        }
    }

    useEffect(() => {
        fetchCourses();
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
