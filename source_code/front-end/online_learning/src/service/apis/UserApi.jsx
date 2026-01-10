import apiClient from "./apiClient";
class UserApi {
    static async createUser(data) {
        const response = await apiClient.post('/users', data);
        const json = response.data;
        return json;

    }
}
export default UserApi;