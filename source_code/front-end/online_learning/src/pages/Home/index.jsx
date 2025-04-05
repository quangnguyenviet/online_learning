import FreeCourse from "../../components/Course/FreeCourse";
import PlusCourse from "../../components/Course/PlusCourse";


export default function Home() {

    return (
        <>
            <div className="container">
                <h1>welcome to my course</h1>
                <FreeCourse />
                <PlusCourse />
            </div>
           
        </>
    );
}