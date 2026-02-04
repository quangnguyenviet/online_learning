import { useState, useEffect } from "react";
import { getCourses } from "utils/CoursesUtil";
import { useSelector } from "react-redux";
import CourseList from "../CourseList/CourseList";
import "./style.scss";
import CourseApi from "service/apis/CourseApi";

export default function CourseSearch() {
    const [courses, setCourses] = useState([]);
    const query = useSelector((state) => state.search);

    const fetchCourses = async () => {
        try {
            const response = await CourseApi.getCourses({ type: "", query: query, page: 0, size: 10 });
            console.log(response);
            setCourses(response.data.content);
        } catch (error) {
            console.error("Error fetching courses:", error);
        }
    };

    useEffect(() => {
        fetchCourses();
    }, [query]);



    return (
        <section className="course-search-section">
            <h2 className="course-search-section__title">
                <i className="fas fa-search"></i> Kết quả tìm kiếm
            </h2>
            {courses.length === 0 ? (
                <div className="course-search-section__notfound">
                    Không tìm thấy khóa học phù hợp.
                </div>
            ) : (
                <div className="course-search-section__result-list">
                    <CourseList courses={courses} />
                </div>
            )}
        </section>
    );
}