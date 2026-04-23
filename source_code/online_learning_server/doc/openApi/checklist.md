# Checklist triển khai OpenAPI cho Online Learning Server

> Mục tiêu: Tạo tài liệu API rõ ràng, đồng bộ với code, dễ chia sẻ cho frontend/mobile/QA.

## 1) Chốt phạm vi tài liệu

- [x] Xác định phạm vi version đầu tiên (toàn bộ API hay chỉ nhóm auth/course/payment).
- [x] Chọn chuẩn tài liệu: OpenAPI 3.1 (hoặc 3.0 nếu cần tương thích).
- [x] Chốt chuẩn đặt tên tag theo module: `Authentication`, `Course`, `Lesson`, `Register`, `User`, ...

### Quyết định cho Bước 1

- Phạm vi v1: tài liệu hóa toàn bộ API đang có trong tất cả controller hiện tại.
- Chuẩn OpenAPI: dùng OpenAPI 3.1 cho tài liệu chính thức.
- Quy ước tag theo module/controller:
  - `Authentication`
  - `User`
  - `Course`
  - `CourseInstructor`
  - `Category`
  - `Lesson`
  - `LessonProgress`
  - `Register`
  - `InstructorDashboard`
  - `InstructorPaymentAdmin`
  - `ZaloPay`
  - `System` (cho endpoint test/health như HelloController)

## 2) Thêm dependency và cấu hình cơ bản

- [x] Thêm `springdoc-openapi` vào [pom.xml](../../pom.xml).
- [x] Cấu hình endpoint tài liệu:
  - [x] `/v3/api-docs`
  - [x] `/swagger-ui/index.html`
- [x] Cấu hình hiển thị theo profile (dev bật, prod giới hạn theo policy).

### Kết quả Bước 2

- Đã thêm dependency OpenAPI UI vào [pom.xml](../../pom.xml).
- Đã cấu hình path tài liệu trong [src/main/resources/application.properties](../../src/main/resources/application.properties).
- Đã bật docs ở dev trong [src/main/resources/application-dev.properties](../../src/main/resources/application-dev.properties).
- Đã tắt docs mặc định ở prod (có thể bật bằng env) trong [src/main/resources/application-prod.properties](../../src/main/resources/application-prod.properties).
- Vì app dùng context path `/online_learning`, URL thực tế ở local dev là:
  - `/online_learning/v3/api-docs`
  - `/online_learning/swagger-ui/index.html`

## 3) Khởi tạo metadata API

- [ ] Tạo class cấu hình OpenAPI (ví dụ `OpenApiConfig`).
- [ ] Khai báo thông tin chung:
  - [ ] `title`, `version`, `description`
  - [ ] contact/team owner
  - [ ] server URL cho local/dev/prod
- [ ] Chuẩn hóa convention versioning (`/api/v1/...` nếu áp dụng).

## 4) Mô tả security scheme JWT

- [ ] Khai báo `bearerAuth` (HTTP bearer, JWT) trong OpenAPI components.
- [ ] Gắn yêu cầu auth cho các endpoint bảo vệ.
- [ ] Đảm bảo endpoint public (login, register, health, docs) không bị yêu cầu token.

## 5) Bổ sung annotation cho controller

- [ ] Thêm `@Tag` cho từng controller chính.
- [ ] Thêm `@Operation(summary, description)` cho endpoint quan trọng.
- [ ] Thêm `@ApiResponses` với mã phản hồi chuẩn (`200`, `201`, `400`, `401`, `403`, `404`, `500`).
- [ ] Mô tả request body/query/path params bằng annotation phù hợp.

## 6) Chuẩn hóa model request/response

- [ ] Bổ sung mô tả schema cho DTO qua `@Schema`.
- [ ] Đánh dấu trường bắt buộc, format (`email`, `date-time`, `uuid`, ...).
- [ ] Bổ sung ví dụ dữ liệu (`example`) cho request/response quan trọng.
- [ ] Chuẩn hóa mô tả lỗi theo `ApiResponse` + `ErrorCode` hiện có.

## 7) Gom nhóm và tinh gọn tài liệu

- [ ] Cấu hình group API nếu cần (public/internal/admin/instructor).
- [ ] Ẩn endpoint nội bộ hoặc endpoint không muốn công khai (`@Hidden` nếu cần).
- [ ] Kiểm tra trùng lặp hoặc tên endpoint khó hiểu trong UI docs.

## 8) Kiểm thử tài liệu

- [ ] Chạy app và xác minh truy cập được Swagger UI.
- [ ] Dùng chức năng "Try it out" test nhanh endpoint đại diện mỗi module.
- [ ] Kiểm tra model hiển thị đúng với validation thực tế.
- [ ] Kiểm tra endpoint có auth trả `401` đúng khi thiếu token.

## 9) Xuất file OpenAPI cho tích hợp

- [ ] Xuất bản đặc tả JSON/YAML cho frontend/mobile.
- [ ] Chốt vị trí lưu artifact (repo hoặc CI artifact).
- [ ] Nếu cần, tạo bước generate client SDK từ OpenAPI (giai đoạn sau).

## 10) Vận hành và duy trì

- [ ] Đưa checklist "cập nhật OpenAPI" vào Definition of Done khi thêm/sửa API.
- [ ] Thiết lập kiểm tra tự động (CI) để phát hiện thay đổi API spec.
- [ ] Quy định owner duyệt thay đổi API contract trước khi merge.

## Definition of Done

- [ ] Swagger UI truy cập được ở môi trường dev.
- [ ] Có mô tả đầy đủ cho endpoint chính và model chính.
- [ ] Security scheme JWT thể hiện chính xác trên docs.
- [ ] Có file OpenAPI xuất ra để các team khác sử dụng.
