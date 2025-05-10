import 'pages/instructor/Courses/ViewVideo.scss';

export default function ViewVideo(props) {
    const { lessonKey } = props;

    const handleView = () => {

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
            <div className="modal modal__inactive" onClick={handleClose}>
                        <div className="modal__content" onClick={(e) => e.stopPropagation()}>
                            <video width="100%" height="100%" controls>
                                <source src="movie.mp4" type="video/mp4" />
                                <source src="movie.ogg" type="video/ogg" />
                                Your browser does not support the video tag.
                            </video>

                        </div>
                    </div>
            <button
                onClick={handleView}
                style={{ color: "blue" }}
            >
                View
            </button>
        </>
    );
}