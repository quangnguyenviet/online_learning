# Turtle Online Learning - Hướng dẫn cài đặt & chạy dự án

---

## PHẦN BACKEND

### Yêu cầu
- **Java 21** hoặc cao hơn ([Tải JDK](https://vntalking.com/huong-dan-download-va-cai-dat-jdk-java-development-kit.html))
- **Maven 3.8** hoặc cao hơn ([Tải Maven](https://viblo.asia/p/cai-dat-maven-tren-mac-va-windows-6BAMYVXBGnjz))
- **MySQL** ([Tải MySQL](https://www.thegioididong.com/game-app/huong-dan-cach-tai-cai-dat-mysql-ban-moi-nhat-chi-tiet-tung-1299084))
- **FFmpeg** đã cài đặt và cấu hình

### Hướng dẫn cài đặt FFmpeg
1. Tải FFmpeg tại: [Google Drive Link](https://drive.google.com/drive/folders/1W8yWbGigS4UgH5fIUo5hoBDmYAqlG7Jn?usp=sharing)
2. Giải nén và đặt vào ổ đĩa `D:\`
3. Thêm đường dẫn `D:\ffmpeg-master-latest-win64-gpl-shared\bin` vào biến môi trường `PATH`:
   - Control Panel → System and Security → System → Advanced system settings
   - Chọn "Environment Variables"
   - Tìm biến `Path` → Edit → Thêm đường dẫn trên
   - Nhấn OK để lưu
4. Kiểm tra bằng lệnh:
   ```bash
   ffmpeg -version
   ```

### Chạy dự án backend trên IntelliJ

1. **Clone repository:**
   ```bash
   git clone <repo-url>
   cd online_learning
   ```

2. **Cấu hình database**  
   Sửa file `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/online_learning
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
   > Thay `your_username` và `your_password` bằng thông tin MySQL của bạn.

3. **Tạo database trong MySQL:**
   ```sql
   create database online_learning;
   ```

4. **File .env (nếu cần):**  
   Tải tại [Google Drive](https://drive.google.com/drive/folders/1xHSBbIkQuKiJpxfwNxa56hErQ0Vw34I9?usp=sharing)

5. **Xem video hướng dẫn chi tiết:**  
   [Video hướng dẫn](https://drive.google.com/drive/folders/1xHSBbIkQuKiJpxfwNxa56hErQ0Vw34I9?usp=sharing)

---

## PHẦN FRONTEND

### Yêu cầu hệ thống
- **Node.js** >= 16.x
- **npm** >= 8.x hoặc **yarn**
- Đã cài đặt backend API (chạy song song)

### Hướng dẫn cài đặt & chạy frontend

1. **Clone source code:**
   ```bash
   git clone <repo-url>
   cd source_code/front-end/online_learning
   ```

2. **Cài đặt dependencies:**
   ```bash
   npm install

3. **Chạy dự án:**
   ```bash
   npm start
   ```
   - Truy cập [http://localhost:3000](http://localhost:3000) trên trình duyệt.

### Một số lưu ý
- Đảm bảo backend đã chạy trước khi sử dụng frontend.
- Nếu gặp lỗi về SCSS, kiểm tra lại đường dẫn import và cài đặt đúng các package liên quan (`sass`, `node-sass`).
- Để sử dụng đầy đủ icon, hãy đảm bảo đã cài đặt các package như `react-icons` và/hoặc FontAwesome.

