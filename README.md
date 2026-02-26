# 🎓 Online Learning Platform - Hệ thống Học Trực Tuyến

Đây là một nền tảng học trực tuyến toàn diện cho phép **Giảng viên** tạo khóa học và **Học viên** có thể đăng ký, thanh toán và học tập một cách dễ dàng. Dự án được xây dựng với kiến trúc mạnh mẽ, đảm bảo tính mở rộng và bảo mật cao.

---

## 📋 Mục Lục

- [Tính Năng Chính](#-tính-năng-chính)
- [Công Nghệ Sử Dụng](#️-công-nghệ-sử-dụng)
- [Cấu Trúc Dự Án](#-cấu-trúc-dự-án)
- [Hướng Dẫn Cài Đặt](#-hướng-dẫn-cài-đặt)
- [Lộ Trình Phát Triển](#-lộ-trình-phát-triển)

---

## 🚀 Tính Năng Chính

### 👨‍🏫 Dành cho Giảng viên (Instructor)

- ✅ **Quản lý khóa học**: Tạo mới, chỉnh sửa và xóa các khóa học chuyên nghiệp
- ✅ **Quản lý nội dung**: Tổ chức bài học theo chương, quản lý tài liệu và video bài giảng
- ✅ **Kiểm soát hiển thị**: Tính năng Publish/Unpublish để kiểm soát thời gian ra mắt khóa học
- ✅ **Dashboard thống kê**: Theo dõi số lượng học viên, doanh thu và tương tác
- 🔧 **Báo cáo chuyên sâu**: *(Đang phát triển)* Xem chi tiết tỷ lệ hoàn thành và phản hồi

### 👨‍🎓 Dành cho Học viên (Student)

- ✅ **Khám phá khóa học**: Tìm kiếm khóa học thông minh theo danh mục
- ✅ **Đăng ký dễ dàng**: Quy trình đăng ký khóa học nhanh chóng
- ✅ **Theo dõi tiến độ**: Progress tracking để biết mức độ hoàn thành
- ✅ **Thanh toán tích hợp**: Hỗ trợ thanh toán qua ví điện tử ZaloPay

### 🛡️ Hệ Thống & Bảo Mật

- ✅ **Xác thực người dùng**: Hệ thống Đăng ký/Đăng nhập bảo mật
- ✅ **JWT Authentication**: Bảo vệ tài nguyên API và xác thực phiên làm việc
- ✅ **Spring Security**: Quản lý phân quyền và bảo mật tổng thể

---

## 🛠️ Công Nghệ Sử Dụng

### Backend
- **Framework**: Spring Boot (Java)
- **Security**: Spring Security & JWT
- **Database**: MySQL/PostgreSQL
- **Storage**: Firebase Storage (Quản lý hình ảnh và video)
- **Real-time**: WebSocket (STOMP)

### Frontend
- **Framework**: React.js 19
- **Styling**: SASS/SCSS
- **State Management**: Redux
- **HTTP Client**: Axios
- **Icons**: React Icons, FontAwesome
- **UI Components**: React Modal, SweetAlert2

### Third-party Services
- **Payment Gateway**: ZaloPay Sandbox
- **Cloud Storage**: Firebase Storage
- **Analytics**: Firebase Analytics

---

## 📁 Cấu Trúc Dự Án

```
online_learning/
├── source_code/
│   ├── back-end/
│   │   └── online_learning/        # Spring Boot Backend
│   │       ├── src/
│   │       ├── pom.xml
│   │       └── RUN_GUIDE.md
│   │
│   └── front-end/
│       └── online_learning/         # React Frontend
│           ├── src/
│           ├── package.json
│           └── HUONG_DAN_CHAY_DU_AN.md
│
├── finalReport/                     # Báo cáo dự án
├── plan/                           # Kế hoạch
└── weeklyReport/                   # Báo cáo tuần
```

---

## 💻 Hướng Dẫn Cài Đặt

### Yêu Cầu Hệ Thống

**Backend:**
- Java JDK 17 trở lên
- Maven 3.6+
- MySQL/PostgreSQL
- IDE: IntelliJ IDEA / Eclipse

**Frontend:**
- Node.js >= 16.x
- npm >= 8.x hoặc yarn

### Cài Đặt Backend

Chi tiết xem tại: [source_code/back-end/online_learning/RUN_GUIDE.md](source_code/back-end/online_learning/BACKEND_RUN_GUIDE.md.md)


### Cài Đặt Frontend

Chi tiết xem tại: [source_code/front-end/online_learning/HUONG_DAN_CHAY_DU_AN.md](source_code/front-end/online_learning/FRONTEND_RUN_GUIDE.md.md)


### Cấu Hình Môi Trường

**Frontend** - Tạo file `.env`:
```env
REACT_APP_BASE_URL=http://localhost:8080/online_learning
```

---

## 📅 Lộ Trình Phát Triển

- [ ] Hoàn thiện hệ thống báo cáo chi tiết cho giảng viên
- [ ] Tích hợp thông báo qua Email
- [ ] Thêm tính năng chat trực tiếp giữa giảng viên và học viên
- [ ] Xây dựng forum thảo luận cho mỗi bài học

---

## 📞 Hỗ Trợ

Nếu gặp vấn đề khi cài đặt hoặc sử dụng:
1. Kiểm tra hướng dẫn chi tiết trong thư mục tương ứng
2. Xem phần troubleshooting trong file HUONG_DAN_CHAY_DU_AN.md
3. Kiểm tra logs trong console/terminal

---

## 📄 License

Dự án này được phát triển cho mục đích học tập và nghiên cứu.

---

**Chúc bạn cài đặt và sử dụng thành công! 🎉**