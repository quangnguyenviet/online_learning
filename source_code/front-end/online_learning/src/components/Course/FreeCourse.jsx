import { useState, useEffect } from "react";
import './Course.scss';
import CourseList from "./CourseList";

export default function FreeCourse() {
    const url = "http://localhost:8080/online_learning/courses/free";

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchCourses = async (url) => {
            try {
                const response = await fetch(url);
                const data = await response.json();
                setCourses(data.data);
            } catch (error) {
                console.error("Error fetching courses:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchCourses(url);
    }, []);

    return (
        <>
            {loading ? (
                <h2>Loading...</h2>
            ) : (
                
                <div className="free-course">
                    <h2>Free Courses</h2>
                        <div className="row">
                            <CourseList courses={courses} />
                        </div>
                    </div>

                
            )}
        </>
    );
    
}