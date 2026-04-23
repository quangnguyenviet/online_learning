# Mô tả các chức năng hiện có (Backend Online Learning)

Tài liệu này tổng hợp các chức năng đang có dựa trên các API controller hiện tại trong hệ thống.

## 1) Xác thực & phiên đăng nhập

**Base path:** `/auth`

- `POST /auth/login`: Đăng nhập, trả token.
- `POST /auth/introspect`: Kiểm tra tính hợp lệ của token.
- `POST /auth/logout`: Đăng xuất.
- `POST /auth/refresh-token`: Làm mới token.

## 2) Quản lý người dùng

**Base path:** `/users`

- `POST /users`: Đăng ký tài khoản người dùng mới.
- `DELETE /users/{id}`: Xóa người dùng.
- `GET /users`: Lấy danh sách người dùng.
- `GET /users/my-info`: Lấy thông tin cá nhân của người dùng đang đăng nhập.
- `PUT /users/my-info`: Cập nhật thông tin cá nhân.

### Endpoint đã khai báo nhưng **chưa hoàn thiện**

- `GET /users/{id}`: Hiện đang trả `null`.
- `GET /users/{id}/stats`: Hiện đang trả `null`.
- `GET /users/{id}/enrolled-courses`: Hiện đang trả `null`.

## 3) Quản lý danh mục khóa học

**Base path:** `/categories`

- `POST /categories`: Tạo danh mục mới (**yêu cầu quyền ADMIN**).
- `GET /categories`: Lấy toàn bộ danh mục.

## 4) Quản lý khóa học

**Base path:** `/courses`

- `GET /courses`: Lấy danh sách khóa học (phân trang, hỗ trợ lọc/tìm kiếm).
- `GET /courses/{id}`: Lấy chi tiết khóa học.
- `GET /courses/free`: Lấy danh sách khóa học miễn phí.
- `GET /courses/plus`: Lấy danh sách khóa học trả phí.
- `GET /courses/learning`: Lấy danh sách khóa học đang học của người dùng hiện tại.
- `POST /courses` (multipart): Tạo khóa học mới (**yêu cầu quyền INSTRUCTOR**).
- `PUT /courses` (multipart): Cập nhật khóa học (**yêu cầu quyền INSTRUCTOR**).

## 5) Chức năng khóa học cho giảng viên

**Base path:** `/instructor/courses`

- `GET /instructor/courses/my-courses`: Danh sách khóa học của giảng viên (phân trang, **INSTRUCTOR**).
- `PATCH /instructor/courses/{id}/publish?published=true|false`: Bật/tắt trạng thái xuất bản khóa học (**INSTRUCTOR**).
- `GET /instructor/courses/{id}`: Xem chi tiết khóa học.

## 6) Quản lý bài học

**Base path:** `/lessons`

- `POST /lessons/signed-url`: Tạo signed URL (phục vụ upload video/tài nguyên).
- `GET /lessons/course/{courseId}`: Lấy danh sách bài học theo khóa học.
- `POST /lessons` (multipart): Tạo bài học mới kèm video (**INSTRUCTOR**).
- `PUT /lessons/{lessonId}` (multipart): Cập nhật bài học, có thể kèm video mới (**INSTRUCTOR**).
- `PATCH /lessons/{lessonId}/preview`: Cập nhật trạng thái xem thử (`isPreview`) (**INSTRUCTOR**).
- `DELETE /lessons/{lessonId}`: Xóa bài học (**INSTRUCTOR**).

## 7) Theo dõi tiến độ học tập

**Base path:** `/lesson-progress`

- `POST /lesson-progress/complete/{lessonId}`: Đánh dấu hoàn thành bài học.

## 8) Đăng ký khóa học

**Base path:** `/registers`

- `POST /registers`: Đăng ký khóa học miễn phí.
- `GET /registers/check?courseId=...`: Kiểm tra người dùng đã đăng ký khóa học hay chưa.

### Lưu ý hiện trạng

- Trong code hiện tại, `POST /registers` mới trả thành công theo khung API, chưa thấy gọi luồng lưu đăng ký thực tế (dòng xử lý đang bị comment).

## 9) Thanh toán ZaloPay

**Base path:** `/zalopay`

- `POST /zalopay/create-order?courseId=...`: Tạo đơn thanh toán.
- `POST /zalopay/callback`: Nhận callback từ ZaloPay, xác thực MAC, tạo dữ liệu đăng ký khóa học sau thanh toán thành công.

## 10) Dashboard giảng viên

- `GET /instructor/stats/summary`: Tổng quan thống kê giảng viên (**INSTRUCTOR**).
- `GET /instructor/stats/registrations?filter=...`: Dữ liệu biểu đồ lượt đăng ký (**INSTRUCTOR**).
- `GET /instructor/stats/courses?page=0&size=10`: Thống kê theo khóa học của giảng viên (**INSTRUCTOR**).

## 11) Thanh toán giảng viên (phía quản trị)

**Base path:** `/admin-instructor-payment`

- `GET /admin-instructor-payment?month=...&year=...`: Lấy danh sách cần thanh toán theo tháng/năm.
- `PUT /admin-instructor-payment`: Cập nhật trạng thái/thông tin thanh toán giảng viên.

## 12) Endpoint kiểm tra nhanh

- `GET /hello`: Trả chuỗi test `Hello, Vitube Online Learning!`.

---

## Tóm tắt nhanh theo nhóm vai trò

- **Khách/Người dùng thường:** xem khóa học, xem danh mục, đăng ký/đăng nhập, xem bài học theo khóa.
- **Học viên:** cập nhật thông tin cá nhân, theo dõi hoàn thành bài học, kiểm tra trạng thái đăng ký, thanh toán khóa học.
- **Giảng viên (INSTRUCTOR):** tạo/sửa/xóa bài học, tạo/sửa khóa học, publish khóa học, xem dashboard thống kê.
- **Quản trị (ADMIN):** tạo danh mục, xử lý thanh toán giảng viên.
