// const BASE_URL = "http://localhost:8080/online_learning/lessons";
const INSTRUCTOR_URL = "http://localhost:8080/online_learning/instructor-lesson";

export async function uploadLesson({ file, title, courseId, description, order }) {
    const token = localStorage.getItem("token");

    const formData = new FormData();
    formData.append("file", file);
    formData.append("title", title);
    formData.append("courseId", courseId);
    formData.append("description", description); // Assuming description is optional
    formData.append("order", order); // Assuming order is optional and you want to set it to 1

    try {
        const response = await fetch(INSTRUCTOR_URL, {
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
    let formData = new FormData();
    if (!lessonData.tag) {
        if (lessonData.video) {
            formData.append("video", lessonData.video);
        }
        formData.append("title", lessonData.title);
        formData.append("description", lessonData.description);
    }
    else if (lessonData.tag) {
        formData.append("order", lessonData.order);
        formData.append("tag", lessonData.tag);
    }


    console.log("lessondata", lessonData);

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
