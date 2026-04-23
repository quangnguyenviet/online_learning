# Sơ đồ tuần tự (Sequence Diagram) - Kafka Enrollment Notification

Sơ đồ này mô tả chi tiết các bước tương tác giữa người dùng, các microservices, hệ thống Kafka và máy chủ Email.

```mermaid
sequenceDiagram
    autonumber
    actor Student as Sinh viên
    participant Server as Online Learning Server
    participant DB as Database (Postgres)
    participant Kafka as Kafka Broker
    participant NS as Notification Service
    participant SMTP as Email Server

    Student->>Server: Thanh toán thành công (ZaloPay Callback)
    
    rect rgb(240, 248, 255)
        Note over Server, DB: Xử lý nghiệp vụ chính
        Server->>DB: Lưu bản ghi Register (sinh viên + khóa học)
        DB-->>Server: Lưu thành công
    end

    rect rgb(255, 245, 238)
        Note over Server, Kafka: Phát sự kiện (Async Start)
        Server->>Kafka: Gửi EnrollmentEvent tới topic "enrollment-topic"
        Kafka-->>Server: Xác nhận đã nhận (ACK)
    end

    Server-->>Student: Trả về kết quả đăng ký thành công cho Client

    rect rgb(245, 255, 250)
        Note over Kafka, NS: Xử lý thông báo (Background)
        Kafka->>NS: Đẩy tin nhắn (Push) tới Consumer
        NS->>NS: Chuẩn bị nội dung Email từ Event Data
        NS->>SMTP: Gửi Mail thông báo (JavaMailSender)
        SMTP-->>Student: Gửi Email vào hộp thư sinh viên
    end
```

## Các giai đoạn chính:
1.  **Giai đoạn Đồng bộ (Synchronous)**: (Bước 1-3) Xử lý lưu trữ dữ liệu vào cơ sở dữ liệu chính. Đây là bước quan trọng nhất để đảm bảo tính toàn vẹn dữ liệu.
2.  **Giai đoạn Phát sự kiện**: (Bước 4-5) Server chính đẩy thông tin sang Kafka. Việc này diễn ra rất nhanh và không phụ thuộc vào việc email có được gửi thành công ngay lập tức hay không.
3.  **Giai đoạn Phản hồi**: (Bước 6) Người dùng nhận được thông báo thành công trên giao diện ngay lập tức mà không phải chờ quá trình gửi mail.
4.  **Giai đoạn Bất đồng bộ (Asynchronous)**: (Bước 7-10) Diễn ra hoàn toàn tách biệt ở phía sau. Nếu hệ thống email chậm hoặc gặp sự cố, Kafka sẽ lưu trữ tin nhắn để xử lý lại sau.
