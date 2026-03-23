import apiClient from "./apiClient";

class LessonProgressApi {
    static async markLessonAsCompleted(lessonId) {
        const response = await apiClient.post(`/lesson-progress/complete/${lessonId}`);
        const json = response.data;
        return json;
    }
}

export default LessonProgressApi;