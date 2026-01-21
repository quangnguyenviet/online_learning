import apiClient from "./apiClient";

class CourseApi {
    static async createCourse(formData){
        const response = await apiClient.post('/courses', formData);
        const json = response.data;
        return json;
    }

    static async updateCourse(formData){
        const response = await apiClient.put(`/courses`, formData);
        const json = response.data;
        return json;
    }

    // instructor only
    static async getMyCourses(){
        const response = await apiClient.get(`/courses/my-courses`);
        const json = response.data;
        return json;
    }
}
export default CourseApi;