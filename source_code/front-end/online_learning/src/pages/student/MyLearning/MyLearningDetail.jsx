import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./MyLearningDetail.scss";

export default function MyLearningDetail() {
    const { courseId } = useParams();

    const URL = `http://localhost:8080/online_learning/lessons/${courseId}`;
    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchLessons = async () => {
            try {
                const response = await fetch(URL, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                        Authorization: `Bearer ${localStorage.getItem("token")}`,
                    },
                });
                const data = await response.json();
                setLessons(data.data);
                setLoading(false);
            } catch (error) {
                console.error("Error:", error);
            }
        };

        fetchLessons();
    }, [URL]);


    const handleView = (lessonKey) => {
        fetch(`http://localhost:8080/online_learning/lessons/signed-url`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
            body: JSON.stringify({
                filename: lessonKey,
            })
        })
            .then((response) => response.json())
            .then((data) => {
                const modal = document.querySelector(".modal");
                const video = modal.querySelector("video");
                video.src = data.data;
                modal.classList.remove("modal__inactive");
                modal.classList.add("modal__active");

                // Wait until the video is ready before playing
                video.onloadedmetadata = () => {
                    video.play().catch(err => {
                        console.warn("Auto-play failed:", err);
                    });
                };
            })
            .catch((error) => {
                console.error("Error:", error);
            });


        // video.addEventListener("ended", () => {
        //     modal.classList.add("modal__inactive");
        //     video.src = ""; // Clear the video source when it ends
        // });
    }

    const handleClose = () => {
        const modal = document.querySelector(".modal");
        const video = modal.querySelector("video");
        modal.classList.add("modal__inactive");
        video.src = ""; // Clear the video source when it ends
        video.pause(); // Pause the video when closing the modal
    }



    return (
        <>
            <h1>My Learning Detail</h1>
            {loading ? (
                <div>Loading...</div>
            ) : (
                <>
                    <div className="modal modal__inactive" onClick={handleClose}>
                        <div className="modal__content" onClick={(e) => e.stopPropagation()}>
                            <video width="100%" height="100%" controls>
                                <source src="movie.mp4" type="video/mp4" />
                                <source src="movie.ogg" type="video/ogg" />
                                Your browser does not support the video tag.
                            </video>

                        </div>
                    </div>

                    <div className="lessons">
                        <div className="row">
                            {lessons.map((lessonlesson) => (
                                <div className="col-md-3" key={lessonlesson.id}>
                                    <div className="card mb-4 shadow-sm lesson-card" onClick={() => { handleView(lessonlesson.lessonKey) }}>
                                        <div className="card-body">
                                            <h5 className="card-title">{lessonlesson.title}</h5>
                                            <p className="card-text">{lessonlesson.description}</p>

                                        </div>
                                    </div>
                                </div>
                            ))}
                        </div>
                    </div>

                </>


            )}

        </>
    )
}
