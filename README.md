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

4. **File .env :**  
   Tải tại [Google Drive](https://drive.google.com/drive/folders/1xHSBbIkQuKiJpxfwNxa56hErQ0Vw34I9?usp=sharing)

5. **Xem video hướng dẫn chi tiết để chạy dự án:**  
   [Video hướng dẫn](https://drive.google.com/drive/folders/1xHSBbIkQuKiJpxfwNxa56hErQ0Vw34I9?usp=sharing)

6. **Tạo trigger cho CSDL để ứng dụng chạy đúng:**  
   Sau khi chạy dự án lần đầu, hãy chạy các lệnh SQL sau trong MySQL Workbench hoặc terminal để tạo trigger:

   ```sql
   DELIMITER $$

   CREATE TRIGGER trg_update_instructor_statistic_after_insert
   AFTER INSERT ON register
   FOR EACH ROW
   BEGIN
       DECLARE v_instructor_id VARCHAR(255);
       DECLARE v_month INT;
       DECLARE v_year INT;
       DECLARE v_stat_id VARCHAR(255);

       -- Lấy instructor từ khóa học
       SELECT instructor_id INTO v_instructor_id
       FROM course
       WHERE id = NEW.course_id;

       -- Lấy tháng và năm từ ngày đăng ký
       SET v_month = MONTH(NEW.register_date);
       SET v_year = YEAR(NEW.register_date);

       -- Tạo ID thống kê (theo instructor + tháng + năm)
       SET v_stat_id = CONCAT(v_instructor_id, '_', v_month, '_', v_year);

       -- Nếu đã có bản ghi thống kê → cập nhật
       IF EXISTS (SELECT 1 FROM instructor_statistic WHERE id = v_stat_id) THEN
           UPDATE instructor_statistic
           SET 
               total_registrations = total_registrations + 1,
               total_earnings = total_earnings + IFNULL(NEW.price, 0)
           WHERE id = v_stat_id;
       ELSE
           -- Nếu chưa có → thêm mới
           INSERT INTO instructor_statistic (
               id, month, year, total_registrations, total_earnings, instructor_id
           ) VALUES (
               v_stat_id, v_month, v_year, 1, IFNULL(NEW.price, 0), v_instructor_id
           );
       END IF;
   END$$

   DELIMITER ;

   DELIMITER $$

   CREATE TRIGGER trg_create_payment_after_statistic_insert
   AFTER INSERT ON instructor_statistic
   FOR EACH ROW
   BEGIN
       DECLARE v_payment_id VARCHAR(255);

       -- Tạo id cho instructor_payment (vd: thêm prefix 'pay_' trước id thống kê)
       SET v_payment_id = CONCAT('pay_', NEW.id);

       -- Thêm bản ghi vào bảng instructor_payment, để paid_at là NULL
       INSERT INTO instructor_payment (
           id, statistic_id, paid_at
       ) VALUES (
           v_payment_id, NEW.id, NULL
       );
   END$$

   DELIMITER ;
   ```

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

   - Truy cập [http://localhost:3000](http://localhost:3000) trên trình duyệt.

### Một số lưu ý
- Đảm bảo backend đã chạy trước khi sử dụng frontend.
- Nếu gặp lỗi về SCSS, kiểm tra lại đường dẫn import và cài đặt đúng các package liên quan (`sass`, `node-sass`).
- Để sử dụng đầy đủ icon, hãy đảm bảo đã cài đặt các package như `react-icons` và/hoặc FontAwesome.

---



