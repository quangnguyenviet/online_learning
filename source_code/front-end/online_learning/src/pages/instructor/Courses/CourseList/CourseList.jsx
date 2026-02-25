import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import CourseListItem from 'components/Course/CourseListItem/CourseListItem';
import styles from './CourseList.module.scss';
import CourseApi from 'service/apis/CourseApi';
import { useError } from 'components/common/ErrorDisplay/ErrorDisplay';
import { ErrorDisplay } from 'components/common/ErrorDisplay/ErrorDisplay';

export default function CourseList() {
    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    const { showError, dismissError, errorMessage } = useError();

    const fetchCourses = async () => {
        try {
            const response = await CourseApi.getMyCourses();
            console.log('Fetched courses:', response);
            const data = response.data.content;
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

    const handleEditCourse = (courseId) => {
        navigate(`/instructor/courses/${courseId}`);
    };

    const handleViewReport = (courseId) => {
        showError("Chức năng đang được phát triển. Vui lòng quay lại sau.");
        // navigate(`/instructor/courses/${courseId}/report`);
    };

    const handlePublishCourse = async (courseId) => {
        try {
            const response = await CourseApi.publishCourse(courseId, true);
            console.log('Published course:', response);
            fetchCourses(); // Refresh the course list
        } catch (error) {
            console.error('Error publishing course:', error);
        }
    };

    return (
        <>
        <ErrorDisplay errorMessage={errorMessage} onDismiss={dismissError} />
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
                <div className={styles['instructor-course-list__list']}>
                    {courses.map((course) => (
                        <CourseListItem
                            course={course}
                            key={course.id}
                            onEdit={handleEditCourse}
                            onViewReport={handleViewReport}
                            onPublish={handlePublishCourse}
                        />
                    ))}
                </div>
            )}
        </section>
        </>
        
    );
}
