# Hướng dẫn chạy dự án Backend Online Learning (Spring Boot)

Tài liệu này hướng dẫn chi tiết cách cài đặt và chạy **online_learning_server** (API chính, port `8080`, context-path `/online_learning`).

## 1. Yêu cầu hệ thống

| Công cụ | Phiên bản | Ghi chú |
| :--- | :--- | :--- |
| JDK | **21** | Theo `java.version` trong `pom.xml` |
| Maven | 3.8+ | Dùng `mvnw`/`mvnw.cmd` trong repo cũng được |
| Docker + Docker Compose | mới | Chạy PostgreSQL, Redis, Kafka (bước 2) |
| FFmpeg (ffprobe) | bắt buộc | Lấy thời lượng video — bước 3 |

## 2. Khởi động hạ tầng bằng Docker Compose

Tất cả hạ tầng phụ (PostgreSQL, Redis, Kafka, Kafka UI) được chạy từ file `docker-compose.yaml` **cùng thư mục với dự án này** (`source_code/servers/online_learning_server/`):

```bash
cd source_code/servers/online_learning_server
docker compose up -d
```

| Service | Port | Thông tin |
| :--- | :--- | :--- |
| PostgreSQL 14 | 5432 | user `root` / pass `12345` / db `online_learning_db` (tự tạo, kèm volume `pgdata`) |
| Redis 7 | 6379 | JWT blacklist & cache (`appendonly yes`) |
| Kafka (Confluent 7.4) | 9092 | Broker sự kiện, kèm Zookeeper |
| Kafka UI | 8090 | Xem topic tại `http://localhost:8090` |

Kiểm tra bằng `docker compose ps` — tất cả service phải ở trạng thái `Up`.

> Schema database **không cần chạy SQL thủ công**: project dùng Flyway (`src/main/resources/db/migration/V1__init.sql` ...), tự migration khi backend khởi động lần đầu.

## 3. Cài đặt & cấu hình FFmpeg

Dự án cần **ffprobe** (thành phần của FFmpeg) để xử lý video (lấy thời lượng, metadata).

1. Tải bộ FFmpeg build Windows (ví dụ: `ffmpeg-master-latest-win64-gpl-shared`), giải nén vào một vị trí cố định (VD `D:\download\ffmpeg-master-latest-win64-gpl-shared`).
2. Trong `src/main/resources/application.properties`, sửa `ffmpeg.path` trỏ đúng tới `ffprobe.exe` trên máy bạn:

```properties
ffmpeg.path=D:\\download\\ffmpeg-master-latest-win64-gpl-shared\\bin\\ffprobe.exe
```

Kiểm tra nhanh: chạy `ffprobe -version` trong terminal (thêm thư mục `bin` của FFmpeg vào PATH).

## 4. Cấu hình môi trường (`.env`)

File `application.properties` đọc các biến cấu hình qua placeholder `${...}`, nên bạn cần tạo file `.env` trong thư mục `online_learning_server/`:

1. Copy file `.env-example` → `.env` (`.env` đã được gitignore; `.env-example` có trong repo với giá trị mẫu).
2. Điền tối thiểu các biến sau (`.env` mẫu trong repo đã có sẵn cho chạy local, chỉ cần rà soát lại):

| Biến | Giá trị chạy local với docker-compose |
| :--- | :--- |
| `JWT_SIGNER_KEY` | Chuỗi secret ≥ 32 ký tự (mẫu trong repo dùng được cho dev) |
| `ZALOPAY_*` | Giữ nguyên nếu dùng sandbox (có sẵn); bỏ trống `ZALOPAY_END_POINT` nếu chưa có endpoint riêng |
| `STORAGE_PROVIDER` | `s3` hoặc `r2` — đi kèm `AWS_*` / `R2_*` tương ứng |
| `FRONTEND_URL` | `http://localhost:3000` |
| `REDIS_HOST`, `REDIS_PORT` | `localhost`, `6379` |
| `DB_URL` | `jdbc:postgresql://localhost:5432/online_learning_db` (chỉ cần khi chạy bằng profile `prod`/mặc định; profile `dev` có sẵn DB local) |
| `DB_USERNAME`, `DB_PASSWORD` | `root`, `12345` |

> Mẹo: profile **`dev`** (`application-dev.properties`) đã hard-code DB/Redis khớp với docker-compose, nên khi chạy dev chỉ cần `.env` có `JWT_SIGNER_KEY` + Zalopay + storage + `FRONTEND_URL` là đủ. **Luôn bật profile `dev` hoặc `prod`; chạy không có profile sẽ không có cấu hình `spring.datasource`.**
>
> - `dev`: DB/Redis local theo docker-compose (`root` / `12345`, `online_learning_db`).
> - `prod`: cần `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `REDIS_HOST`, `REDIS_PORT`.

## 5. Cài đặt dependencies

```bash
cd source_code/servers/online_learning_server
mvn clean install
```

## 6. Chạy dự án

### Cách 1: Dòng lệnh (Terminal)

Nạp `.env` vào biến môi trường của tiến trình rồi chạy (PowerShell):

```powershell
Get-Content .env | Where-Object { $_ -and ($_ -notmatch '^\s*#') } | ForEach-Object { $p = $_ -split '=',2; if ($p.Count -eq 2) { [System.Environment]::SetEnvironmentVariable($p[0].Trim(), $p[1].Trim(), 'Process') } }
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

Trên Windows CMD:

```cmd
for /f "usebackq tokens=1* delims==" %a in (".env") do set "%a=%b"
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

> Lệnh `for` trên CMD chỉ ổn với giá trị không chứa ký tự đặc biệt; nếu `.env` có giá trị phức tạp, dùng PowerShell.

Chạy jar (production):

```powershell
mvn clean package -DskipTests
# (nạp .env như trên, hoặc set biến thủ công)
java -jar target/online_learning-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Cách 2: IDE (IntelliJ / Android Studio)

1. Mở Main Class: `src/main/java/com/vitube/online_learning/OnlineLearningApplication.java`.
2. Thêm **Active profiles**: `dev` (Run Configuration → Active profiles).
3. Với biến trong `.env`, có thể dùng plugin **EnvFile** trỏ tới file `.env`, hoặc paste từng `KEY=VALUE` vào **Environment variables**.
4. Nhấn **Run**.

### Cách 3 (tuỳ chọn): các microservice khác

| Service | Thư mục | Port | Ghi chú |
| :--- | :--- | :--- | :--- |
| Eureka | `source_code/servers/eureka-server` | 8761 | Chạy trước backend nếu muốn dùng service discovery (backend là Eureka client) |
| Notification | `source_code/servers/services/notification` | 8081 | Consumer Kafka gửi email; cần đặt thêm `SPRING_MAIL_USERNAME` / `SPRING_MAIL_PASSWORD` (Gmail + app password) |

```bash
cd source_code/servers/eureka-server
mvn spring-boot:run
```

## 7. Kiểm tra nhanh

- Backend: `http://localhost:8080/online_learning`
- Swagger UI: `http://localhost:8080/online_learning/swagger-ui/index.html`
- Kafka UI: `http://localhost:8090`

## 8. Test & Troubleshooting

```bash
# Test context (profile test, Flyway tắt; H2 được khai báo trong pom.xml)
mvn test
```

| Lỗi | Nguyên nhân & khắc phục |
| :--- | :--- |
| `IOException: Cannot run program "..."` | Sai đường dẫn `ffmpeg.path` (bước 3) |
| `Port 8080 was already in use` | Cổng 8080 bận — tắt tiến trình khác hoặc đổi `server.port` |
| `Access denied for user` | Sai user/pass PostgreSQL — đối chiếu với docker-compose (`root`/`12345`) |
| Backend chờ Eureka / không register được | Chạy `eureka-server` (port 8761) trước, hoặc bỏ qua nếu không cần discovery |
| Flyway báo lỗi migration | Thường do DB cũ không khớp lịch sử — xoá volume `pgdata` và tạo lại (chỉ dev): `docker compose down -v && docker compose up -d` |
| `jwt.signerKey` rỗng / auth lỗi | Chưa nạp `.env` — `JWT_SIGNER_KEY` bắt buộc có giá trị |
