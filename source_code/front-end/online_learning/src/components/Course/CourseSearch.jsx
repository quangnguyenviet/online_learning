import { useState, useEffect, use } from "react";
import { getCourses } from "utils/CoursesUtil";
import { useSelector } from "react-redux";
import CourseList from "./CourseList";
export default function CourseSearch() {

    const [courses, setCourses] = useState([]);
    const query = useSelector((state) => state.search);

    useEffect(() => {
        getCourses({ query: query,
            type: ""
        })
            .then((response) => {
                const data = response.data;
                console.log(data);
                setCourses(data);
            })
            .catch((error) => {
                console.error("Error fetching courses:", error);
            });
    }, [query]);

    return (
        <>
            <div className="course-search">
                                <h2 className="text-center section-title">Courses</h2>
                                    <div className="row">
                                        <CourseList courses={courses} />
                                    </div>
                            </div>
        </>

    );

}