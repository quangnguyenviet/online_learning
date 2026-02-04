import apiClient from "./apiClient";
class RegisterApi {
    static async isRegistered(courseId) {
        const response = await apiClient.get(`/registers/check?courseId=${courseId}`);
        const json = response.data;
        return json;
    }
}
export default RegisterApi;