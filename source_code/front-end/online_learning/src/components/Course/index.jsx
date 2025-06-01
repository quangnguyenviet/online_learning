import PlusCourse from "./PlusCourse";
import './Course.scss';
import { useSelector } from "react-redux";
import CourseSearch from "components/Course/CourseSearch";
import FreeCourse from "components/Course/FreeCourse";

export default function Course() {
    const query = useSelector((state) => state.search);

    return (
        <section className="course-section">
            <div className="course-section__container">
                <h2 className="course-section__title">
                    <i className="fas fa-graduation-cap"></i> Khóa học nổi bật
                </h2>
                {query === "" ? (
                    <div className="course-section__list">
                        <div className="course-section__column">
                            <FreeCourse />
                        </div>
                        <div className="course-section__column">
                            <PlusCourse />
                        </div>
                    </div>
                ) : (
                    <CourseSearch />
                )}
            </div>
        </section>
    );
}