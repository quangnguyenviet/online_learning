import CourseCard from "./CourseCard";
import { useNavigate } from "react-router-dom";
export default function CourseList(props) {
    const { courses } = props;

    const navigation = useNavigate();

    const handleClick = (courseId) => {
        console.log(courseId);
        navigation(`/courses/${courseId}`);
    };


    return (
        <>
            {courses.map((course) => (
                <CourseCard course={course} key={course.id} handleClick={handleClick} showPrice={true} />
            ))}
        </>
    );
    
}