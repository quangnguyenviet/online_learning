import {useState, useEffect} from "react";
import CourseList from "./CourseList";
import {getCourses} from "utils/CoursesUtil";

export default function PlusCourse() {

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getCourses("plus")
            .then((response) => {
                const data = response.data;
                console.log(data);
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
                <div className="plus-course">
                    <h2>Plus Courses</h2>
                    <div className="row">
                        <CourseList courses={courses}/>
                    </div>
                </div>
            )}
        </>
    );
}