import React, { useEffect, useState, useRef } from "react";
import { useParams } from "react-router-dom";
import "./MyLearningDetail.scss";
import { getSignedUrl } from "utils/LessonUtil";
import LessonApi from "service/apis/LessonApi";
import LessonProgressApi from "service/apis/LessonProgressApi";

export default function MyLearningDetail() {
    const { courseId } = useParams();
    const URL = `http://localhost:8080/online_learning/lessons/${courseId}`;

    const [lessons, setLessons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [videoUrl, setVideoUrl] = useState("");
    const [currentLessonId, setCurrentLessonId] = useState(null);
    const videoRef = useRef(null);
    const hasMarkedCompleted = useRef(new Set());

    const fetchLessons = async () => {
        try {
            const {data} = await LessonApi.getLessonsByCourseId(courseId);
            console.log(data);

            setLessons(data);
            setLoading(false);
            if (data.length > 0) {
                const firstLessonId = data[0].id;
                const firstUrl = await LessonApi.getSignedUrl(firstLessonId);
                setVideoUrl(firstUrl.data.presignedUrl);
                setCurrentLessonId(firstLessonId);
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
        setCurrentLessonId(lessonId);
        LessonApi.getSignedUrl(lessonId)
            .then((data) => {
                console.log(data);
                setVideoUrl(data.data.presignedUrl);
            })
            .catch((error) => {
                console.error("Error:", error);
            });
    };

    const markLessonAsCompleted = async (lessonId) => {
        try {
            await LessonProgressApi.markLessonAsCompleted(lessonId);
            hasMarkedCompleted.current.add(lessonId);
            console.log("Đã đánh dấu bài học hoàn thành:", lessonId);
            
            // Cập nhật lại danh sách lessons để hiển thị dấu tích
            setLessons(prevLessons => 
                prevLessons.map(lesson => 
                    lesson.id === lessonId
                        ? {
                            ...lesson,
                            lessonProgressDTO: {
                                ...lesson.lessonProgressDTO,
                                completed: true,
                                lessonId: lessonId
                            }
                        }
                        : lesson
                )
            );
        } catch (error) {
            console.error("Lỗi khi đánh dấu bài học hoàn thành:", error);
        }
    };

    // const watchedTimeRef = useRef(0);
    // const lastTimeRef = useRef(0);

    const handleVideoTimeUpdate = async () => {
        const video = videoRef.current;
        if (!video || !currentLessonId) return;

        const progress = (video.currentTime / video.duration) * 100;
        
        // Nếu đã xem được 80% và chưa đánh dấu hoàn thành cho bài học này
        if (progress >= 80 && !hasMarkedCompleted.current.has(currentLessonId)) {
            await markLessonAsCompleted(currentLessonId);
        }
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
                                    <video 
                                        ref={videoRef}
                                        controls 
                                        key={videoUrl}
                                        autoPlay
                                        onTimeUpdate={handleVideoTimeUpdate}
                                    >
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
                                        key={lesson.id}
                                        className={`lesson-card ${lesson.lessonProgressDTO?.completed ? 'lesson-card--completed' : ''} ${currentLessonId === lesson.id ? 'lesson-card--active' : ''}`}
                                        onClick={() => handleView(lesson.id)}
                                        tabIndex={0}
                                        role="button"
                                    >
                                        {lesson.lessonProgressDTO?.completed && (
                                            <span className="lesson-card__completed-badge">
                                                <i className="fas fa-check"></i>
                                            </span>
                                        )}
                                        <div className="lesson-card__body">
                                            <h5 className="lesson-card__title">
                                                {lesson.title}
                                                {lesson.lessonProgressDTO?.completed && (
                                                    <i className="fas fa-check-circle lesson-card__check-icon"></i>
                                                )}
                                            </h5>
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
