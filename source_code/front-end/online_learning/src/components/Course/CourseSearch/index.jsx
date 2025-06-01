import { useState, useEffect } from "react";
import { getCourses } from "utils/CoursesUtil";
import { useSelector } from "react-redux";
import CourseList from "../CourseList";
import "./style.scss";

export default function CourseSearch() {
    const [courses, setCourses] = useState([]);
    const query = useSelector((state) => state.search);

    useEffect(() => {
        getCourses({ query: query, type: "" })
            .then((response) => {
                const data = response.data;
                setCourses(data);
            })
            .catch((error) => {
                console.error("Error fetching courses:", error);
            });
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