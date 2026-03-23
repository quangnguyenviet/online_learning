import apiClient from "./apiClient";
class ZalopayApi {
    static async createOrder(courseId){
        const response = await apiClient.post('/zalopay/create-order?courseId=' + courseId);
        const json = response.data;
        return json;
    }
}
export default ZalopayApi;