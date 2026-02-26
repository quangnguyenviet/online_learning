import apiClient from "./apiClient";

class LessonApi {
    static async deleteLesson(lessonId){
        const response = await apiClient.delete(`/lessons/${lessonId}`);
        const json = response.data;
        return json;
    }

    static async addLesson(formData){
        const response = await apiClient.post('/lessons', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        });
        const json = response.data;
        return json;
    }

    static async getSignedUrl(lessonId){
        const response = await apiClient.post('/lessons/signed-url', {
            id: lessonId,
        });
        const json = response.data;
        return json;
    }
    static async editLesson(lessonId, formData){
        const response = await apiClient.put(`/lessons/${lessonId}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            }
        });
        const json = response.data;
        return json;
    }

    static async getLessonsByCourseId(courseId){
        const response = await apiClient.get(`/lessons/course/${courseId}`);
        const json = response.data;
        return json;
    }

    static async updateLessonPreview(lessonId, isPreview){
        const response = await apiClient.patch(`/lessons/${lessonId}/preview`, {
            isPreview: isPreview
        });
        const json = response.data;
        return json;
    }
}
export default LessonApi;