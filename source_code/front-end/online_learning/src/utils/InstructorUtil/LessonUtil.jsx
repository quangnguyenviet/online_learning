const BASE_URL = "http://localhost:8080/online_learning/lessons";
const INSTRUCTOR_URL = "http://localhost:8080/online_learning/instructor-lesson";

export async function uploadLesson({ file, title, courseId }) {
    const token = localStorage.getItem("token");

    const formData = new FormData();
    formData.append("file", file);
    formData.append("title", title);
    formData.append("courseId", courseId);

    try {
        const response = await fetch(BASE_URL, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${token}`
                // Không thêm Content-Type khi dùng FormData (trình duyệt sẽ tự thêm boundary)
            },
            body: formData
        });

       

        return await response.json();
    } catch (error) {
        console.error("Error uploading lesson:", error);
        throw error;
    }
}

export async function deleteLesson(lessonId) {
    const token = localStorage.getItem("token");

    try {
        const response = await fetch(`${INSTRUCTOR_URL}/${lessonId}`, {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${token}`
            }
        });

        return await response.json();
    } catch (error) {
        console.error("Error deleting lesson:", error);
        throw error;
    }
}
export async function updateLesson(lessonId, lessonData) {
    const token = localStorage.getItem("token");

    const formData = new FormData();
    if (lessonData.video) {
        formData.append("video", lessonData.video);
    }
    formData.append("title", lessonData.title);
    formData.append("description", lessonData.description);

    console.log("lessonData", lessonData);

    try {
        const response = await fetch(`${INSTRUCTOR_URL}/${lessonId}`, {
            method: "PUT",
            headers: {
                Authorization: `Bearer ${token}`
            },
            body: formData
        });

        return await response.json();
    } catch (error) {
        console.error("Error updating lesson:", error);
        throw error;
    }
}
