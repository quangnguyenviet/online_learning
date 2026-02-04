import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./MyLearningDetail.scss";
import { getSignedUrl } from "utils/LessonUtil";
import LessonApi from "service/apis/LessonApi";

export default function MyLearningDetail() {
    const { courseId } = useParams();
    const URL = `http://localhost:8080/online_learning/lessons/${courseId}`;

    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [videoUrl, setVideoUrl] = useState("");

    const fetchLessons = async () => {
        try {
            const {data} = await LessonApi.getLessonsByCourseId(courseId);
            console.log(data);

            setLessons(data);
            setLoading(false);
            if (data.length > 0) {
                const firstKey = data[0].lessonKey;
                const firstUrl = await getSignedUrl(firstKey);
                setVideoUrl(firstUrl.data);
            }
        } catch (error) {
            console.error("Error:", error);
            setLoading(false);
        }
    };

    useEffect(() => {
       

        fetchLessons();
    }, [URL]);

    const handleView = (lessonId) => {
        // getSignedUrl(lessonKey)
        //     .then((data) => {
        //         setVideoUrl(data.data);
        //     })
        //     .catch((error) => {
        //         console.error("Error:", error);
        //     });
        LessonApi.getSignedUrl(lessonId)
            .then((data) => {
                console.log(data);
                setVideoUrl(data.data.presignedUrl);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    return (
        <section className="my-learning-detail-section">
            <div className="my-learning-detail__container">
                <h2 className="my-learning-detail__title">
                    <i className="fas fa-play-circle"></i> Nội dung khóa học
                </h2>
                {loading ? (
                    <div className="my-learning-detail__loading">Đang tải...</div>
                ) : (
                    <div className="my-learning-detail__content">
                        <div className="my-learning-detail__video">
                            {videoUrl && (
                                <div className="video-player">
                                    <video controls autoPlay key={videoUrl}>
                                        <source src={videoUrl} type="video/mp4" />
                                        Trình duyệt của bạn không hỗ trợ video.
                                    </video>
                                </div>
                            )}
                        </div>
                        <aside className="my-learning-detail__lessons">
                            <h3 className="my-learning-detail__lessons-title">Danh sách bài học</h3>
                            <div className="my-learning-detail__lesson-list">
                                {lessons.map((lesson) => (
                                    <div
                                        key={lesson.lessonKey}
                                        className="lesson-card"
                                        onClick={() => handleView(lesson.id)}
                                        tabIndex={0}
                                        role="button"
                                    >
                                        <div className="lesson-card__body">
                                            <h5 className="lesson-card__title">{lesson.title}</h5>
                                            <p className="lesson-card__desc">{lesson.description}</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </aside>
                    </div>
                )}
            </div>
        </section>
    );
}
