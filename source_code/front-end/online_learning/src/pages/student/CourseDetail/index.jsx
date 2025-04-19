import React, { useEffect, useState } from "react";
import RegisterBtn from "./RegisterBtn";
import { useParams } from "react-router-dom";

export default function CourseDetail() {
    const { courseId } = useParams();


    const url = "http://localhost:8080/online_learning/courses/" + courseId;

    const [course, setCourse] = useState({});
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchCourse = async (url) => {
            try {
                const response = await fetch(url);
                const data = await response.json();
                // console.log(data);
                setCourse(data.data);
            } catch (error) {
                console.error("Error fetching course:", error);
            } finally {
                setLoading(false);
            }
        };

        fetchCourse(url);
    }, []);

    const handleRegister = () => {
        // Handle registration logic here
        alert("Đăng ký thành công!");
    }

    return (
        <>
            {loading ? (
                <h2>Loading...</h2>
            ) : (
                <>
                    <div className="container">
                        <div className="row">
                            <div className="col-9">
                                <h1>{course.title}</h1>
                                <p>{course.description}</p>
                            </div>
                            <div className="col-3">
                                <img src={"https://static.vecteezy.com/system/resources/thumbnails/045/132/934/small_2x/a-beautiful-picture-of-the-eiffel-tower-in-paris-the-capital-of-france-with-a-wonderful-background-in-wonderful-natural-colors-photo.jpg"} alt="anh" style={{width : "100%"}} />
                                
                                <div>{parseInt( course.price * ( (100 - course.discount) / 100), 10 )}VND</div>

                                <RegisterBtn course={course} />
                            </div>
                        </div>
                    </div>
                </>
            )}
        </>
    );

}