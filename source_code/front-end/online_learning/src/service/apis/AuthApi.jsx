import apiClient from 'service/apis/apiClient';
const BASE_URL = process.env.REACT_APP_BASE_URL
class AuthApi {
    static async login(data) {
        console.log("API BASE URL:", BASE_URL);
        try {
            const response = await apiClient.post('/auth/login', data);
            const json = response.data;
            return json;
        } catch (error) {
            console.error('Error during login:', error);
            throw error;
        }
    }

    static async logout(logoutRequest) {
        console.log("Inside logout API");
        try {
            const response = await apiClient.post('/auth/logout', logoutRequest);
            const json = response.data;
            return json;
        } catch (error) {
            console.error('Error during logout:', error);
            throw error;
        }
    }
}
export default AuthApi;