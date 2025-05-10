import {
    FaLightbulb,
    FaCheckCircle,
    FaPlayCircle,
    FaExclamationCircle,
    FaDotCircle
} from "react-icons/fa";
import "./CourseDescription.scss";

export default function CourseDescription({ course }) {

    console.log(course);
    return (
        <div className="col-9 course-detail__info">
            <h1>{course.title}</h1>
            <p>{course.description}</p>

            <hr />
            <h3><FaLightbulb className="icon icon-yellow" /> Những gì bạn sẽ học</h3>
            <div className="row">
                {(course.learnWhats || []).map((item, index) => (
                    <div className="col-md-6 mb-2" key={index}>
                        <FaCheckCircle className="icon icon-green" />
                        {item.description}
                    </div>
                ))}
            </div>


            <hr />
            <h3><FaPlayCircle className="icon icon-blue" /> Danh sách bài học</h3>
            <ol>
                {(course.lessons || []).map((lesson, index) => (
                    <li key={index} className="mb-3">
                        <div className="d-flex align-items-center">
                            <FaPlayCircle className="icon icon-gray me-2" />
                            <strong>{lesson.title}</strong>
                        </div>
                        {lesson.description && (
                            <p className="lesson-description ms-4 mb-0">
                                {lesson.description}
                            </p>
                        )}
                    </li>
                ))}
            </ol>


            <hr />
            <h3><FaExclamationCircle className="icon icon-red" /> Yêu cầu / điều kiện tham gia</h3>
            <ul className="list-unstyled">
                {(course.requires || []).map((item, index) => (
                    <li key={index}>
                        <FaDotCircle className="icon icon-dark" />
                        {item.description}
                    </li>
                ))}
            </ul>
        </div>
    );
}
