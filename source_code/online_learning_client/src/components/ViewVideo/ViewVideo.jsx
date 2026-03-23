import styles from "./ViewVideo.module.scss";
import LessonApi from "service/apis/LessonApi";
import { useRef, useState } from "react";
import React from "react";
import ViewVideoModal from "components/ViewVideoModal/ViewVideoModal";

export default function ViewVideo({ lessonId, children }) {
    
    const [isOpen, setIsOpen] = useState(false);
    const [signedUrl, setSignedUrl] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const videoRef = useRef(null);

    const openModal = async () => {
        setIsOpen(true);
        setError("");

        if (signedUrl) return;

        setLoading(true);
        try {
            const res = await LessonApi.getSignedUrl(lessonId);
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
            {/* <div onClick={openModal} className={styles.trigger}>
                {children}
            </div> */}
            {
                 React.cloneElement(children, {
                        onClick: openModal,
                    })
            }

            <ViewVideoModal
                isOpen={isOpen}
                onClose={closeModal}
                title="Xem video bài học"
            >
                {loading && (
                    <div className={styles.state}>
                        Đang lấy liên kết phát...
                    </div>
                )}

                {!loading && error && (
                    <div className={`${styles.state} ${styles.stateError}`}>
                        {error}
                    </div>
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
            </ViewVideoModal>
        </>
    );
}

