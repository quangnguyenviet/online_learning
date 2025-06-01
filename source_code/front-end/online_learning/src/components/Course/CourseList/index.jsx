import CourseCard from "../CourseCard";
import { useNavigate } from "react-router-dom";
import "./style.scss";

export default function CourseList(props) {
    const { courses } = props;
    const navigation = useNavigate();

    const handleClick = (courseId) => {
        navigation(`/courses/${courseId}`);
    };

    return (
        <div className="course-list">
            {courses.map((course) => (
                <CourseCard
                    course={course}
                    key={course.id}
                    handleClick={handleClick}
                    showPrice={true}
                />
            ))}
        </div>
    );
}