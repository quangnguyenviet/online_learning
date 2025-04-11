// Import các module cần thiết từ Firebase
import { initializeApp } from 'firebase/app';
import { getStorage } from 'firebase/storage';
import { getAnalytics } from 'firebase/analytics';

// Thêm thông tin cấu hình Firebase của bạn
const firebaseConfig = {
  apiKey: "AIzaSyBiygzhW0QUde0JfWrCEtQdirjy6AeiNYE",
  authDomain: "online-learning-42b28.firebaseapp.com",
  projectId: "online-learning-42b28",
  storageBucket: "online-learning-42b28.firebasestorage.app",
  messagingSenderId: "249968845469",
  appId: "1:249968845469:web:c7e03a59eed4f4ad1aaa74",
  measurementId: "G-8GLJPVJ3N8"
};

// Khởi tạo Firebase với cấu hình
const app = initializeApp(firebaseConfig);

// Khởi tạo Firebase Storage và Firebase Analytics
const storage = getStorage(app);
const analytics = getAnalytics(app);

// Export để sử dụng trong ứng dụng
export { storage, analytics };
