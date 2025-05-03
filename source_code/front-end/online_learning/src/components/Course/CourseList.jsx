import { useNavigate } from "react-router-dom";
import { FaBook, FaRegClock } from 'react-icons/fa';

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
                    <div
                        className="card h-100 course__item"
                        onClick={() => handleClick(course.id)}
                        style={{ cursor: 'pointer' }}
                    >
                        <img
                            src="https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?fm=jpg&q=60&w=3000&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8aW1hZ2V8ZW58MHx8MHx8fDA%3D"
                            className="card-img-top course__image"
                            alt={course.title}
                        />
                        <div className="card-body d-flex flex-column">
                            <h5 className="card-title course__title">{course.title}</h5>
                            <p className="course__desc text-muted truncate-2-lines">
                                Khóa học giúp bạn nắm vững kiến thức nền tảng về lập trình web.
                                Khóa học giúp bạn nắm vững kiến thức nền tảng về lập trình web.Khóa học giúp bạn nắm vững kiến thức nền tảng về lập trình web.
                            </p>
                            <p className="course__price fw-bold">{course.price}VND</p>
                            <hr />
                            <div className="meta-data">
                                <div className="d-flex justify-content-between align-items-center">
                                    <div className="d-flex align-items-center me-auto">
                                    <FaRegClock className="me-1"/>
                                    12h 30m
                                      
                                    </div>
                                    <div className="d-flex align-items-center">
                                        <FaBook className="me-1"/> {course.number_of_lessons} bài học
                                    </div>
                                    

                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            ))}
        </>
    );
    
}