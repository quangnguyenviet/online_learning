import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import RegisterBox from "./RegisterBox";
import CourseDescription from "./CourseDescription";

export default function CourseDetail() {
    const { courseId } = useParams();
    const url = "http://localhost:8080/online_learning/courses/" + courseId;

    const [course, setCourse] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchCourse = async () => {
            try {
                const response = await fetch(url);
                const data = await response.json();
                setCourse(data.data);
            } catch (error) {
                console.error("Error fetching course:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchCourse();
    }, [url]);

    return (
        <>
            {loading ? (
                <h2>Loading...</h2>
            ) : (
                <div className="container" style={{ position: "relative" }}>
                    <div className="row">
                        {/* LEFT: Course Content */}
                        <CourseDescription course={course} />

                        {/* RIGHT: Register Box */}
                        <RegisterBox course={course} />
                    </div>
                </div>
            )}
        </>
    );
}
