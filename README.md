## Phần backend
## Yêu Cầu
- Java 21 hoặc cao hơn. https://vntalking.com/huong-dan-download-va-cai-dat-jdk-java-development-kit.html
- Maven 3.8 hoặc cao hơn. https://viblo.asia/p/cai-dat-maven-tren-mac-va-windows-6BAMYVXBGnjz
- Cơ sở dữ liệu MySQL. https://www.thegioididong.com/game-app/huong-dan-cach-tai-cai-dat-mysql-ban-moi-nhat-chi-tiet-tung-1299084
- FFmpeg được cài đặt và cấu hình.

## hướng dẫn cài đặt FFmpeg và cấu hình
truy câp link sau để tải: https://drive.google.com/drive/folders/1W8yWbGigS4UgH5fIUo5hoBDmYAqlG7Jn?usp=sharing
- Giải nén tệp tải về và đặt vào ổ đĩa D:\
- Thêm đường dẫn đến thư mục chứa FFmpeg vào biến môi trường PATH:
  - Mở Control Panel > System and Security > System > Advanced system settings.
  - Nhấp vào nút "Environment Variables".
  - Trong phần "System variables", tìm biến `Path` và nhấp vào "Edit".
  - Thêm đường dẫn đến thư mục chứa FFmpeg (ví dụ: `D:\ffmpeg-master-latest-win64-gpl-shared\bin`).
  - Nhấn OK để lưu thay đổi.
Kiểm tra việc cài đặt FFmpeg bằng cách mở Command Prompt và chạy lệnh:
```bash
ffmpeg -version
```
## chạy dự án trên intellij
1. Clone repository:
2. Di chuyển vào thư mục dự án:
   ```bash
   online_learning
3. Cấu hình thông tin cơ sở dữ liệu trong file src/main/resources/application.properties:
chú ý thay thế `your_username` và `your_password` bằng thông tin đăng nhập của bạn và cổng mà mysql bn đang dùng.
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/online_learning
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```
4. dữ liệu file .env (dùng trong video ở sau)

https://drive.google.com/drive/folders/1xHSBbIkQuKiJpxfwNxa56hErQ0Vw34I9?usp=sharing
5. trong mysql tạo 1 data base có tên online_learning

create database online_learning

6. Các bước sau hãy truy cập để xem video hướng dẫn
   https://drive.google.com/drive/folders/1xHSBbIkQuKiJpxfwNxa56hErQ0Vw34I9?usp=sharing


## phần front end
Turtle Online Learning - Frontend
Đây là source code frontend cho hệ thống học trực tuyến Turtle Online Learning, sử dụng ReactJS.

Yêu cầu hệ thống
Node.js >= 16.x
npm >= 8.x hoặc yarn
Đã cài đặt backend API
Hướng dẫn cài đặt và chạy dự án
1. Clone source code
git clone <repo-url>
cd source_code/front-end/online_learning
2. Cài đặt dependencies
Sử dụng npm:

npm install
3. Cấu hình môi trường
Nếu có file .env.example, hãy copy thành .env và chỉnh sửa các biến phù hợp (ví dụ API endpoint).
Mặc định, API backend chạy ở http://localhost:8080/online_learning/. Nếu khác, hãy sửa lại trong code hoặc file env.
4. Chạy dự án
npm start
Truy cập http://localhost:3000 trên trình duyệt để sử dụng ứng dụng.
Một số lưu ý
Đảm bảo backend đã chạy trước khi sử dụng frontend.
Nếu gặp lỗi về SCSS, kiểm tra lại đường dẫn import và cài đặt đúng các package liên quan (sass, node-sass).
Để sử dụng đầy đủ icon, hãy đảm bảo đã cài đặt các package như react-icons và/hoặc FontAwesome.
Thư mục chính
src/ - Source code React
public/ - File tĩnh
src/components/ - Các component dùng chung
src/pages/ - Các trang chính
src/_var.scss - Biến SCSS toàn cục
Chúc bạn cài đặt và sử dụng thành công!