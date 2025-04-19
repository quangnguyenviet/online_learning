import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import GoToCourseBtn from "../../../components/GoToCourseBtn";

export default function MyLearning() {
    const URL = "http://localhost:8080/online_learning/courses/learning";

    const [course, setCourse] = useState({});
    const [loading, setLoading] = useState(true);
    const token = localStorage.getItem("token");
   const navigate = useNavigate();

    
    
    useEffect(() => {
        fetch(URL, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`,
            },
        })
            .then((response) => response.json())
            .then((data) => {
                setCourse(data.data);
                setLoading(false);
                // console.log(data.data);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    }, [token]);

    const handleClick = (courseId) => {
        navigate(`/my-learning/${courseId}`);
    }

    

    return (
        <>
            <h1>my learning</h1>
            {loading ? (
                <div>Loading...</div>
            ) : (
                <div className="courses">
                    <div className="row">
                        {course.map((item) => (
                            <div className="col-md-3" key={item.id}>
                                <div className="card mb-4 shadow-sm">
                                    <img
                                        src={"https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR9SRRmhH4X5N2e4QalcoxVbzYsD44C-sQv-w&s"}
                                        alt={item.title}
                                        className="card-img-top"
                                    />
                                    <div className="card-body">
                                        <h5 className="card-title">{item.title}</h5>
                                        <p className="card-text">{item.description}</p>
                                        <p className="card-text">{item.category}</p>
                                        <GoToCourseBtn course={item} />
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}
        </>
    );
}