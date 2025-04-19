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
                <div className="col-3 mb-4" key={course.id}>
                    <div className="card h-100 course__item" onClick={() => handleClick(course.id)} style={{ cursor: 'pointer' }}>
                        <img
                            src="https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D"
                            className="card-img-top course__image"
                            alt={course.title}
                        />
                        <div className="card-body d-flex flex-column">
                            <h5 className="card-title course__title">{course.title}</h5>
                            <p className="card-text mt-auto course__price fw-bold">{course.price}</p>
                        </div>
                    </div>
                </div>
            ))}
        </>
    );
    
}