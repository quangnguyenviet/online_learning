# Hướng dẫn chạy dự án Backend Online Learning

Tài liệu này hướng dẫn chi tiết cách cài đặt và chạy dự án **Back-end (Spring Boot)**.

## 1. Yêu cầu hệ thống (Prerequisites)
Trước khi bắt đầu, hãy đảm bảo máy tính của bạn đã cài đặt:

*   **Java Development Kit (JDK):** Phiên bản **17** hoặc mới hơn.
*   **Database:** MySQL Server (khuyến nghị bản 8.0).
*   **Build Tool:** Maven.
*   **FFmpeg:** Bộ công cụ xử lý video (Bắt buộc).

## 2. Cài đặt FFmpeg (Quan trọng)
Dự án cần **FFprobe** (thành phần của FFmpeg) để xử lý video (lấy thời lượng, metadata...).

1.  **Tải và cài đặt:**
    *   Xem video hướng dẫn chi tiết tại đây: [Youtube: Hướng dẫn tải và cài đặt FFmpeg trên Windows](https://www.youtube.com/watch?v=DMEP82yrs5g)

2.  **Cấu hình đường dẫn:**
    *   Sau khi giải nén, tìm đến file `ffprobe.exe` trong thư mục `bin`.
    *   Mở file `src/main/resources/application.properties` và thêm dòng sau (thay đổi đường dẫn phù hợp với máy của bạn):

```properties
# Lưu ý: Sử dụng hai dấu gạch ngược (\\) hoặc một dấu gạch chéo (/) để phân cách thư mục
ffmpeg.path=D:\\download\\ffmpeg-master-latest-win64-gpl-shared\\bin\\ffprobe.exe
```

## 3. Cấu hình Database
1.  Mở MySQL Workbench hoặc công cụ quản lý DB bất kỳ.
2.  Tạo một database rỗng với tên `online_learning_db`.

```sql
CREATE DATABASE online_learning_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 4. Cấu hình Môi trường
1.  Tạo file `.env` tại thư mục gốc (tham khảo file `.env-example`):
1. Cập nhật các thông số trong `src/main/resources/application.properties`:


## 5. Cài đặt Dependencies
Mở Terminal tại thư mục gốc của dự án và chạy lệnh:

```sh
mvn clean install
```

## 6. Chạy dự án

### Cách 1: Chạy bằng dòng lệnh (Terminal)
Tại thư mục gốc dự án:

```sh
mvn spring-boot:run
```

### Cách 2: Chạy bằng IDE (Android Studio / IntelliJ)
1.  Mở file Main Class: `src/main/java/com/vitube/online_learning/OnlineLearningApplication.java`.
2.  Nhấn nút **Run** (biểu tượng tam giác xanh).


## 7. Các lỗi thường gặp (Troubleshooting)

| Lỗi | Nguyên nhân & Khắc phục |
| :--- | :--- |
| `IOException: Cannot run program "..."` | Chưa cài đặt hoặc cấu hình sai đường dẫn `ffmpeg.path`. Kiểm tra lại bước 2. |
| `Port 8080 was already in use` | Cổng 8080 đang bận. Đổi cổng trong `application.properties`. |
| `Access denied for user` | Sai thông tin đăng nhập Database. |
