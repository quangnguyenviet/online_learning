import apiClient from "./apiClient";

class CourseApi {

    static async getCourses({type, query, page, size}){
        const response = await apiClient.get('/courses', {
            params: {
                type,
                query,
                page,
                size
            }
        });
        const json = response.data;
        return json;
    }

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

    // student
    static async getLearningCourses(page, size){
        const response = await apiClient.get(`/courses/learning`, {
            params: {
                page,
                size
            }
        });
        const json = response.data;
        return json;
    }

    // instructor only

    // static async getMyCourses(){
    //     const response = await apiClient.get(`/courses/my-courses`);
    //     const json = response.data;
    //     return json;
    // }

    static async getMyCourses(page, size){
        const response = await apiClient.get(`/instructor/courses/my-courses`, {
            params: {
                page,
                size
            }
        });
        const json = response.data;
        return json;
    }

    static async getCourseById(courseId){
        const response = await apiClient.get(`/courses/${courseId}`);
        const json = response.data;
        return json;
    }

    static async publishCourse(courseId, isPublished){
        const response = await apiClient.patch(`/instructor/courses/${courseId}/publish?published=${isPublished}`);
        const json = response.data;
        return json;
    }
}
export default CourseApi;