import apiClient from "./apiClient";
import { User } from "models/user/User";
class UserApi {
    static async createUser(data) {
        const response = await apiClient.post('/users', data);
        const json = response.data;
        return json;

    }
    static async getMyInfo(){
        const response = await apiClient.get('/users/my-info');
        const json = response.data;
        return json;
    }

    static async updateMyInfo(data) {
        const response = await apiClient.put('/users/my-info', data);
        const json = response.data;
        return json;
    }
}
export default UserApi;