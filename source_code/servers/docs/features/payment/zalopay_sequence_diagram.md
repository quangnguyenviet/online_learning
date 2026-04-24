# Sơ đồ tuần tự (Sequence Diagram) - Luồng Thanh toán ZaloPay

Tài liệu này mô tả chi tiết luồng thực thi khi người dùng thực hiện thanh toán khóa học qua cổng ZaloPay, từ lúc khởi tạo đơn hàng cho đến khi hệ thống nhận được callback xác nhận và cấp quyền truy cập khóa học.

## Sơ đồ Mermaid

```mermaid
sequenceDiagram
    autonumber
    actor Client as Người dùng (Frontend)
    participant ZP_Ctrl as ZaloPayController
    participant ZP_Svc as ZaloPayService
    participant ZP_API as ZaloPay Server (External)
    participant Reg_Svc as RegisterService
    participant DB as PostgreSQL
    participant Kafka as Kafka Broker

    %% Phase 1: Khởi tạo đơn hàng (Create Order)
    rect rgb(240, 248, 255)
        Note over Client, ZP_API: Giai đoạn 1: Tạo đơn hàng thanh toán
        Client->>ZP_Ctrl: POST /zalopay/create-order?courseId={id}
        ZP_Ctrl->>ZP_Svc: createOrder(courseId)
        ZP_Svc->>ZP_API: Gửi yêu cầu tạo đơn hàng (kèm chữ ký MAC)
        ZP_API-->>ZP_Svc: Trả về order_url / app_trans_id
        ZP_Svc-->>ZP_Ctrl: Dữ liệu đơn hàng (JSON)
        ZP_Ctrl-->>Client: 200 OK (Trả về URL thanh toán cho Frontend)
    end

    %% Giai đoạn trung gian: User thanh toán trên App ZaloPay
    Note right of Client: Người dùng mở App ZaloPay và thanh toán...

    %% Phase 2: Xử lý Callback từ ZaloPay (Webhook)
    rect rgb(255, 245, 238)
        Note over ZP_API, Kafka: Giai đoạn 2: Xử lý Webhook (Callback) từ ZaloPay
        ZP_API->>ZP_Ctrl: POST /zalopay/callback (kèm Payload & MAC)
        
        ZP_Ctrl->>ZP_Ctrl: Xác thực chữ ký (HMACUtil.HMacHexStringEncode)
        
        alt Chữ ký (MAC) KHÔNG hợp lệ
            ZP_Ctrl-->>ZP_API: 200 OK (return_code = 0, "Invalid MAC")
        else Chữ ký hợp lệ
            ZP_Ctrl->>ZP_Ctrl: Parse JSON lấy app_user, amount, courseId
            ZP_Ctrl->>Reg_Svc: createRegisterData(RegisterDTO)
            
            %% Lưu Database
            Reg_Svc->>DB: Lưu bản ghi Register (Khóa học + Sinh viên)
            DB-->>Reg_Svc: Lưu thành công
            
            %% Bắn Kafka Event
            Reg_Svc-)Kafka: Gửi EnrollmentEvent tới "enrollment-topic"
            
            Reg_Svc-->>ZP_Ctrl: Hoàn tất đăng ký
            ZP_Ctrl-->>ZP_API: 200 OK (return_code = 1, "Success")
        end
    end
```

## Giải thích các giai đoạn

### Giai đoạn 1: Tạo đơn hàng
1. Người dùng bấm nút "Thanh toán" trên giao diện, Frontend gọi API `create-order` kèm ID khóa học.
2. `ZaloPayService` tính toán số tiền, tạo mã giao dịch (`app_trans_id`) và mã hóa chữ ký (MAC) rồi gửi sang server thật của ZaloPay.
3. ZaloPay trả về một URL (hoặc mã QR), hệ thống Vitube chuyển tiếp URL này về cho Frontend để người dùng mở ứng dụng ZaloPay thanh toán.

### Giai đoạn 2: Xử lý Callback (Webhook)
4. Sau khi người dùng chuyển tiền thành công, Server ZaloPay sẽ tự động gọi ngược lại API `/zalopay/callback` của Vitube để báo tin.
5. `ZaloPayController` bắt buộc phải kiểm tra tính toàn vẹn của dữ liệu bằng hàm băm `HMAC SHA256`. Nếu mã băm không khớp, yêu cầu bị từ chối (tránh giả mạo).
6. Nếu hợp lệ, hệ thống trích xuất thông tin người dùng và khóa học, sau đó gọi `RegisterService` để lưu thông tin đăng ký vào Database.
7. Cuối cùng, `RegisterService` bắn một sự kiện bất đồng bộ sang **Kafka** để báo cho `Notification Service` gửi email cảm ơn, đồng thời phản hồi `return_code = 1` cho ZaloPay để đóng giao dịch.
