# Online Learning Backend (Spring Boot)

API chính của dự án: **Java 21 / Spring Boot 3.3**, port `8080`, context-path `/online_learning`.

Hướng dẫn chạy chi tiết (cài đặt, docker-compose, FFmpeg, `.env`, Eureka, notification) nằm trong
[**BACKEND_RUN_GUIDE.md**](BACKEND_RUN_GUIDE.md) — xem file đó để chạy.

## Yêu cầu chính

- **JDK 21** và **Maven 3.8+**
- **PostgreSQL, Redis, Kafka** — chạy sẵn bằng Docker Compose, file `docker-compose.yaml` nằm ngay trong thư mục `online_learning_server/` (khuyến nghị dùng)
- **FFmpeg (ffprobe)** — cho tính năng video

## Chạy nhanh

```bash
# 1. Hạ tầng (docker-compose.yaml nằm ngay trong thư mục online_learning_server/)
cd source_code/servers/online_learning_server
docker compose up -d

# 2. Backend (cùng thư mục đó)
# tạo .env từ .env-example nếu chưa có, sửa ffmpeg.path trong application.properties
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

- Backend: `http://localhost:8080/online_learning`
- Swagger UI: `http://localhost:8080/online_learning/swagger-ui/index.html`

## Ghi chú cấu hình

- DB, Redis dùng **profile `dev`** (đã khớp docker-compose: `root`/`12345`, db `online_learning_db`).
- Các bí mật (JWT, Zalopay, storage S3/R2...) nằm trong file `.env` (gitignore) — copy từ `.env-example`.
- Schema tự quản lý bằng **Flyway** (`src/main/resources/db/migration/`), không cần chạy SQL thủ công.
