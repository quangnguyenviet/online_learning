import { useState, useEffect } from "react";
import './Course.scss';
import CourseList from "./CourseList";
import { getCourses } from "utils/CoursesUtil";
import { useSelector } from "react-redux";

export default function FreeCourse() {

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);

    const query  = useSelector((state) => state.search);


    useEffect(() => {
    
        getCourses({ type: "free"})
            .then((response) => {
                const data = response.data;
                setCourses(data);
                setLoading(false);
            })
            .catch((error) => {
                console.error("Error fetching courses:", error);
                setLoading(false);
            });
    }, []);

    return (
        <>
            {loading ? (
                <h2>Loading...</h2>
            ) : (
                
                <div className="free-course">
                    <h2 className="text-center section-title">Free Courses</h2>
                        <div className="row">
                            <CourseList courses={courses} />
                        </div>
                </div>

                
            )}
        </>
    );
    
}