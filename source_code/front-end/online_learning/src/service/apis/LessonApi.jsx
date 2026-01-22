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

    static async getSignedUrl(videoUrl){
        const response = await apiClient.post('/lessons/signed-url', {
            videoUrl: videoUrl,
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
}
export default LessonApi;