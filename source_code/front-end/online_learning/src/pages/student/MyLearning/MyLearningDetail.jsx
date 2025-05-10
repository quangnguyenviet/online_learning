import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./MyLearningDetail.scss";
import { getSignedUrl } from "utils/LessonUtil";

export default function MyLearningDetail() {
    const { courseId } = useParams();
    const URL = `http://localhost:8080/online_learning/lessons/${courseId}`;

    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [videoUrl, setVideoUrl] = useState("");

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

                if (data.data.length > 0) {
                    const firstKey = data.data[0].lessonKey;
                    const firstUrl = await getSignedUrl(firstKey);
                    setVideoUrl(firstUrl.data);
                }
            } catch (error) {
                console.error("Error:", error);
            }
        };

        fetchLessons();
    }, [URL]);

    const handleView = (lessonKey) => {
        getSignedUrl(lessonKey)
            .then((data) => {
                console.log(data);
                setVideoUrl(data.data);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    return (
        <>
            <h1>My Learning Detail</h1>
            {loading ? (
                <div>Loading...</div>
            ) : (
                <div className="lessons">
                    <div className="container">
                        <div className="row">
                            <div className="col-9">
                                {videoUrl && (
                                    <div className="video-player mb-3">
                                        <video width="100%" height="auto" controls autoPlay
                                        key={videoUrl} >
                                            <source src={videoUrl} type="video/mp4" />
                                            Your browser does not support the video tag.
                                        </video>
                                    </div>
                                )}
                            </div>
                            <div className="col-3">
                                {lessons.map((lesson) => (
                                    <div
                                        key={lesson.lessonKey}
                                        className="card mb-2 shadow-sm lesson-card"
                                        onClick={() => handleView(lesson.lessonKey)}
                                        style={{ cursor: "pointer" }}
                                    >
                                        <div className="card-body d-flex flex-column align-items-center">
                                            <h5 className="card-title">{lesson.title}</h5>
                                            <p className="card-text">{lesson.description}</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>
            )}
        </>
    );
}
