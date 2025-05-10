import FreeCourse from "./FreeCourse";
import PlusCourse from "./PlusCourse";
import './Course.scss';
import { useSelector } from "react-redux";
import CourseSearch from "./CourseSearch";

export default function Course() {
    const query = useSelector((state) => state.search);

    return (
        <>
            <div className="course container">
                {query === "" ? (
                    <>
                        <FreeCourse />
                        <PlusCourse />
                    </>
                ) : (
                    <CourseSearch />
                )}
                
            </div>
        </>
    );
}