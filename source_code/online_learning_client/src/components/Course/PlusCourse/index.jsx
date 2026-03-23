import {useState, useEffect} from "react";
import CourseList from "components/Course/CourseList/CourseList";
import {getCourses} from "utils/CoursesUtil";

export default function PlusCourse() {

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getCourses({type: "plus"
        })
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
                <div className="plus-course">
                    <h2 className="text-center">Plus Courses</h2>
                    <div className="row">
                        <CourseList courses={courses}/>
                    </div>
                </div>
            )}
        </>
    );
}