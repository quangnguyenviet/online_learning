import styles from "./ViewVideo.module.scss";
import LessonApi from "service/apis/LessonApi";
import { useRef, useState } from "react";

export default function ViewVideo({ videoUrl, children }) {
    const [isOpen, setIsOpen] = useState(false);
    const [signedUrl, setSignedUrl] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const videoRef = useRef(null);

    const openModal = async () => {
        setIsOpen(true);
        setError("");

        if (!videoUrl) {
            setError("Video chưa sẵn sàng.");
            return;
        }

        // Avoid re-fetching if we already have a playable URL
        if (signedUrl) return;

        setLoading(true);
        try {
            const res = await LessonApi.getSignedUrl(videoUrl);
            setSignedUrl(res.data.presignedUrl);
        } catch (e) {
            console.error(e);
            setError("Không thể mở video. Vui lòng thử lại.");
        } finally {
            setLoading(false);
        }
    };

    const closeModal = () => {
        if (videoRef.current) {
            videoRef.current.pause();
            videoRef.current.currentTime = 0;
        }
        setIsOpen(false);
    };

    return (
        <>
            <div onClick={openModal} className={styles.trigger}>
                {children}
            </div>

            <div
                className={`${styles.modal} ${isOpen ? styles.modalActive : ""}`}
                onClick={closeModal}
            >
                <div className={styles.modal__content} onClick={(e) => e.stopPropagation()}>
                    <div className={styles.modal__bar}>
                        <span className={styles.modal__title}>Xem video bài học</span>
                        <button type="button" className={styles.closeButton} onClick={closeModal}>
                            ×
                        </button>
                    </div>

                    <div className={styles.modal__body}>
                        {loading && <div className={styles.state}>Đang lấy liên kết phát...</div>}
                        {!loading && error && (
                            <div className={`${styles.state} ${styles.stateError}`}>{error}</div>
                        )}
                        {!loading && !error && signedUrl && (
                            <video
                                className={styles.video}
                                ref={videoRef}
                                controls
                                autoPlay
                                src={signedUrl}
                            />
                        )}
                    </div>
                </div>
            </div>
        </>
    );
}
