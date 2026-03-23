import axios from 'axios';
import StorageService from 'service/StorageService';
const BASE_URL = process.env.REACT_APP_BASE_URL
// Base API client configured for the Online Learning app
const apiClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    // 'Content-Type': 'application/json',
    Accept: 'application/json',
  },
  // You can set a timeout if needed
  // timeout: 10000,
});

// Attach Authorization header if token is present
apiClient.interceptors.request.use(
  (config) => {
    const token = StorageService.getToken();
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Optional: handle response errors uniformly
// apiClient.interceptors.response.use(
//   (response) => response,
//   (error) => {
//     // Normalize error object
//     const normalized = error.response?.data || {
//       status: -1,
//       message: error.message || 'Network error',
//     };
//     return Promise.reject(normalized);
//   }
// );

export default apiClient;
