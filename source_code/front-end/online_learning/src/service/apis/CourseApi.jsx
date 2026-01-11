import apiClient from "./apiClient";

class CourseApi {
    static async createCourse(formData){
        const response = await apiClient.post('/courses', formData);
        const json = response.data;
        return json;
    }
}
export default CourseApi;