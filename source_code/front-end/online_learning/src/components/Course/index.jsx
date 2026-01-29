import './Course.scss';
import { useSelector } from "react-redux";
import CourseSearch from "components/Course/CourseSearch";
import CourseApi from 'service/apis/CourseApi';
import React, { useEffect } from 'react';
import CourseList from './CourseList';

export default function Course() {
    const [courses, setCourses] = React.useState([]);
    const query = useSelector((state) => state.search);

    const fetchCourses = async () => {
        try {
            const response = await CourseApi.getCourses({type: "", query: query, page: 0, size: 10});
            console.log(response);
            setCourses(response.data.content);
        }
        catch (error) {
            console.error("Error fetching courses:", error);
        }
    };

    useEffect(() => {
        fetchCourses();
    }, []);

    return (
        <section className="course-section">
            <div className="course-section__container">
                <h2 className="course-section__title">
                    <i className="fas fa-graduation-cap"></i> Khóa học nổi bật
                </h2>
                {query === "" ? (
                    <div className="course-section__list">
                        {/* <div className="course-section__column">
                            <FreeCourse />
                        </div>
                        <div className="course-section__column">
                            <PlusCourse />
                        </div> */}
                        <CourseList courses={courses} />
                        
                    </div>
                ) : (
                    <CourseSearch />
                )}
            </div>
        </section>
    );
}