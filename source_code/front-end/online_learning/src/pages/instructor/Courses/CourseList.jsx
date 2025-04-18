import { Link, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import { getMyCourses } from 'utils/InstructorUtil/CourseUtil';

export default function CourseList() {

    const [courses, setCourses] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getMyCourses().then((data) => {
            console.log("Courses Data:", data);
            if(data.status === 1000){
                setCourses(data.data);
            }
            setLoading(false);
        }).catch((error) => {
            console.error("Error fetching courses:", error);
        });
    }, []);
    

    return (
        <>
            <div className="row">

                {courses.map((course) => (
                    <div className="col-3" key={course.id}>

                    
                        <div className="card mb-4" >
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTm3R3-To53B_Si_auM8ivKyijnhACF6BPYwA&s" className="card-img-top" alt={course.title} />
                            <div className="card-body">
                                <h5 className="card-title">{course.title}</h5>
                                <p className="card-text">{course.description}</p>
                                <Link to={`/instructor/courses/${course.id}`} className="btn btn-primary">View Course</Link>
                            </div>
                        </div>

                    </div>

                ))}
            </div>

        </>
    );
}