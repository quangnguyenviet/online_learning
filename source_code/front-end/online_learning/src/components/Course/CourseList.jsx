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
            {
                courses.map((course) => (
                    <div className="col-3 course__item" key={course.id} onClick={() => handleClick(course.id)}>
                        <img
                            className="course__image"
                            src="https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D"
                            alt={course.title}
                        />
                        <p className="course__title">{course.title}</p>
                        <span className="course__price">{course.price}</span>
                    </div>

                ))
            }
        </>
    );
}