import apiClient from "./apiClient";

class InstructorDashboardApi {
    static async getInstructorDashboardSummary(){
        console.log("Inside getInstructorDashboardSummary API");
        const response = await apiClient.get('/instructor/stats/summary');
        
        const json = response.data;
        return json;

    }

    static async getRegistrationStats(filter){
        console.log("Inside getRegistrationStats API");
        const response = await apiClient.get('/instructor/stats/registrations?filter=' + filter);
        const json = response.data;
        return json;
    }

    static async getCoursesStats(page, size){
        console.log("Inside getCoursesStats API");
        const response = await apiClient.get('/instructor/stats/courses?page=' + page + '&size=' + size);
        const json = response.data;
        return json;
    }
}
export default InstructorDashboardApi;