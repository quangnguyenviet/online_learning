import FreeCourse from "./FreeCourse";
import PlusCourse from "./PlusCourse";
import './Course.scss';

export default function Course() {

    return (
        <>
            <div className="course container">
                <FreeCourse />
                <PlusCourse />
            </div>
        </>
    );
}