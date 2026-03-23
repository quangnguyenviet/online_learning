import apiClient from 'service/apis/apiClient';
const BASE_URL = process.env.REACT_APP_BASE_URL
class AuthApi {
    static async login(data) {
        console.log("API BASE URL:", BASE_URL);
        const response = await apiClient.post('/auth/login', data);
        const json = response.data;
        return json;
    }

    static async logout(logoutRequest) {
        console.log("Inside logout API");
        const response = await apiClient.post('/auth/logout', logoutRequest);
        const json = response.data;
        return json;
    }
}
export default AuthApi;