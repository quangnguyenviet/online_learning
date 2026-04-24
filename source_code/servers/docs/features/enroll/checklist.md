# Checklist: Hoàn thành tính năng Kafka & Email Notification

Tài liệu này liệt kê các bước cần thiết để triển khai hoàn tất luồng đăng ký khóa học có thông báo qua email.

## 1. Thiết lập Hạ tầng (Infrastructure)
- [x] Chạy `zookeeper` và `kafka` bằng Docker (Sử dụng `docker-compose.yaml`).
- [x] Chạy `eureka-server` (Có thể chạy trực tiếp từ IDE hoặc build Dockerfile nội bộ).
- [x] Chạy lệnh `docker-compose up -d` để khởi động hạ tầng.
- [x] Truy cập `http://localhost:8761` để kiểm tra giao diện Eureka Dashboard.
- [x] Kiểm tra kết nối đến Kafka (sử dụng Kafka UI hoặc command line).

## 2. Thiết lập Eureka Server
- [x] Thêm dependency `spring-cloud-starter-netflix-eureka-server` vào `pom.xml` của `eureka-server`.
- [x] Thêm annotation `@EnableEurekaServer` vào class Main.
- [x] Cấu hình `application.properties` cho Eureka Server:
    - `eureka.client.register-with-eureka=false`
    - `eureka.client.fetch-registry=false`

## 3. Cấu hình Producer (online_learning_server)
- [x] Thêm dependency `spring-cloud-starter-netflix-eureka-client` và `spring-kafka`.
- [x] Thêm cấu hình Eureka Client trong `application.properties`:
    - `spring.application.name=online-learning-server`
    - `eureka.client.service-url.defaultZone=http://localhost:8761/eureka/`
- [x] Cấu hình Kafka Producer trong `application.properties`:
    - `spring.kafka.producer.bootstrap-servers=localhost:9092`
    - `spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer`
    - `spring.kafka.producer.value-serializer=org.springframework.kafka.support.serializer.JsonSerializer`
- [x] Tạo DTO `EnrollmentEvent` chứa các thông tin cần thiết (email, courseName, price, date).
- [x] Cập nhật `RegisterServiceImpl`:
    - Inject `KafkaTemplate<String, EnrollmentEvent>`.
    - Gọi `kafkaTemplate.send("enrollment-topic", event)` sau khi lưu thành công vào Database.

## 4. Cấu hình Consumer (notification-service)
- [x] Thêm dependencies: `spring-cloud-starter-netflix-eureka-client`, `spring-kafka`, `spring-boot-starter-mail`.
- [x] Thêm cấu hình Eureka Client trong `application.properties`:
    - `spring.application.name=notification-service`
    - `eureka.client.service-url.defaultZone=http://localhost:8761/eureka/`
- [x] Cấu hình Kafka Consumer trong `application.properties`:
    - `spring.kafka.consumer.bootstrap-servers=localhost:9092`
    - `spring.kafka.consumer.group-id=notification-group`
    - `spring.kafka.consumer.auto-offset-reset=earliest`
- [x] Cấu hình SMTP cho Email (Gmail/Mailtrap/v.v.) trong `application.properties`.
- [x] Tạo DTO `EnrollmentEvent` (tương ứng với Producer).
- [x] Triển khai `EmailService`:
    - Sử dụng `JavaMailSender` để gửi email.
    - Thiết kế template email thông báo đăng ký thành công.
- [x] Triển khai `NotificationListener`:
    - Sử dụng `@KafkaListener(topics = "enrollment-topic")`.
    - Gọi `EmailService` để gửi mail khi nhận được tin nhắn.

## 4. Kiểm thử & Hoàn thiện
- [ ] Test luồng đăng ký trực tiếp (khóa học miễn phí).
- [ ] Test luồng callback từ ZaloPay (khóa học trả phí).
- [ ] Kiểm tra log của Notification Service để xác nhận nhận được tin nhắn.
- [ ] Kiểm tra hòm thư email của người dùng.
- [ ] Xử lý ngoại lệ (Error handling): Retry logic nếu gửi mail thất bại.
