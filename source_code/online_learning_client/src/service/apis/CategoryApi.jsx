import apiClient from "./apiClient";
class CategoryApi {
    static async getAllCategories() {
        const response = await apiClient.get('/categories');
        const json = response.data;
        return json;
    }
}
export default CategoryApi;