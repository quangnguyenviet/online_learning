# Hướng Dẫn Chạy Dự Án Online Learning - Frontend

## 📋 Mục Lục
- [Yêu Cầu Hệ Thống](#yêu-cầu-hệ-thống)
- [Cài Đặt Dự Án](#cài-đặt-dự-án)
- [Cấu Hình Môi Trường](#cấu-hình-môi-trường)
- [Chạy Dự Án](#chạy-dự-án)
- [Cấu Trúc Thư Mục](#cấu-trúc-thư-mục)
- [Các Tính Năng Chính](#các-tính-năng-chính)
- [Xử Lý Lỗi Thường Gặp](#xử-lý-lỗi-thường-gặp)

---

## 🖥️ Yêu Cầu Hệ Thống

Trước khi bắt đầu, đảm bảo máy tính của bạn đã cài đặt:

- **Node.js** phiên bản **18.x trở lên** ([Tải tại đây](https://nodejs.org/))
- **npm** phiên bản **9.x trở lên** (đi kèm với Node.js) hoặc **yarn**
- **Git** để clone source code
- **Backend API** đã được cài đặt và đang chạy

### Kiểm tra phiên bản đã cài đặt:
```bash
node --version
npm --version
```

---

## 📦 Cài Đặt Dự Án

### Bước 1: Clone Source Code
```bash
git clone <repository-url>
cd online_learning/source_code/online_learning_client
```

### Bước 2: Cài Đặt Dependencies
Sử dụng **npm**:
```bash
npm install
```

Hoặc sử dụng **yarn**:
```bash
yarn install
```

**Lưu ý:** Quá trình cài đặt có thể mất vài phút tùy thuộc vào tốc độ mạng.

---

## ⚙️ Cấu Hình Môi Trường

### File `.env`
Tạo file `.env` **trong thư mục client** (`source_code/online_learning_client/`) với nội dung sau:

```env
# URL của Backend API (bao gồm context-path /online_learning)
VITE_BASE_URL=http://localhost:8080/online_learning
```

> Lưu ý: `.env` đã được gitignore nên mỗi máy cần tạo lại. Nếu clone từ repo mà chưa có `.env`, frontend sẽ không có `VITE_BASE_URL` và có thể bị lỗi CORS / kết nối — hãy kiểm tra file này trước khi chạy.

> Biến env ở Vite dùng prefix **`VITE_`** (khác CRA dùng `REACT_APP_`). Truy cập trong code bằng `import.meta.env.VITE_BASE_URL` thay vì `process.env.REACT_APP_BASE_URL`.

### Cấu Hình Firebase (Đã có sẵn)
Dự án đã được cấu hình sẵn Firebase trong file [src/firebase.jsx](src/firebase.jsx) với các thông tin:
- **Storage:** Lưu trữ file, video, hình ảnh
- **Analytics:** Theo dõi hoạt động người dùng

**Lưu ý:** Nếu bạn muốn sử dụng Firebase riêng, hãy thay đổi cấu hình trong file `src/firebase.jsx`.

---

## 🚀 Chạy Dự Án

### Chế độ Development (Phát triển)
```bash
npm run dev
```

Hoặc (tương đương):
```bash
npm start
```

Dự án sẽ tự động mở trình duyệt tại địa chỉ: **http://localhost:3000**

**Lưu ý:** 
- Dev server chạy bằng **Vite**, khởi động rất nhanh (không bundle trước như CRA)
- Port mặc định là **3000** — giữ nguyên vì backend (CORS `FRONTEND_URL`) chỉ cho phép `http://localhost:3000`. Nếu muốn chạy port khác, sửa `port` trong [vite.config.js](vite.config.js) **và** `FRONTEND_URL` bên backend cho khớp
- Đảm bảo Backend API đang chạy trước khi sử dụng frontend
- Mặc định backend chạy tại `http://localhost:8080/online_learning/`

### Production Build
```bash
npm run build
```

Xuất bundle vào thư mục `dist/`. Xem trước bản build:

```bash
npm run preview
```

### Chạy Test
```bash
npm test
```

Sử dụng **Vitest** (thay cho Jest của CRA). Config trong `vite.config.js` (mục `test`).

---

## 📁 Cấu Trúc Thư Mục

```
online_learning/
├── index.html              # Template chính (root, dùng cho Vite)
├── public/                 # File tĩnh (logo, manifest, robots.txt, ...)
├── vite.config.js          # Cấu hình Vite (alias @, port, test)
├── src/
│   ├── components/        # Các component dùng chung
│   │   ├── common/       # ErrorDisplay, Modal, Button, ...
│   │   ├── Course/       # CourseListItem, CourseCard, ...
│   │   └── ...
│   ├── pages/            # Các trang chính
│   │   ├── instructor/   # Trang dành cho giảng viên
│   │   │   └── Courses/  # Quản lý khóa học
│   │   └── student/      # Trang dành cho học viên
│   ├── layout/           # Layout chính (Header, Footer, Sidebar)
│   ├── service/          # API services
│   │   └── apis/         # CourseApi, AuthApi, UserApi, ...
│   ├── actions/          # Redux actions
│   ├── reducers/         # Redux reducers
│   ├── utils/            # Utility functions
│   ├── firebase.jsx      # Cấu hình Firebase
│   ├── App.js            # Component chính
│   └── index.js          # Entry point
├── package.json          # Dependencies và scripts
└── README.md            # Tài liệu cơ bản
```

---

## 🎯 Các Tính Năng Chính

### Dành cho Giảng Viên (Instructor)
- ✅ Quản lý khóa học (Thêm, Sửa, Xóa)
- ✅ Quản lý bài học và nội dung
- ✅ Publish/Unpublish khóa học
- ✅ Dashboard thống kê
- 🔧 Xem báo cáo (Đang phát triển)

### Dành cho Học Viên (Student)
- ✅ Xem danh sách khóa học
- ✅ Đăng ký khóa học
- ✅ Học bài và theo dõi tiến độ
- ✅ Thanh toán qua Zalopay
- ✅ Tìm kiếm khóa học theo danh mục

### Tính năng chung
- ✅ Đăng nhập/Đăng ký
- ✅ Xác thực JWT token

---

## 🛠️ Xử Lý Lỗi Thường Gặp

### 1. Lỗi "Cannot connect to API"
**Nguyên nhân:** Backend chưa chạy hoặc sai URL
**Giải pháp:**
- Kiểm tra backend đã chạy chưa
- Xác nhận `VITE_BASE_URL` trong file `.env` đúng với URL backend
- Kiểm tra dev server có đọc được biến (console log `import.meta.env.VITE_BASE_URL`)

### 2. Lỗi "Module not found" / "Failed to resolve import"
**Nguyên nhân:** Dependencies chưa được cài đặt đầy đủ
**Giải pháp:**
```bash
rm -rf node_modules
npm install
```

### 3. Lỗi SCSS/SASS
**Nguyên nhân:** Thiếu package `sass`
**Giải pháp:**
```bash
npm install sass
```

### 4. Alias `@` không resolve (IDE đỏ dòng import)
**Nguyên nhân:** IDE chưa đọc `jsconfig.json`
**Giải pháp:**
- Import dùng alias `@/...` (vd `import ... from '@/service/apis/AuthApi'`)
- Đảm bảo có [jsconfig.json](jsconfig.json) với mapping `"@/*": ["./*"]` và `vite.config.js` có `resolve.alias`
- Restart IDE / reload window để đọc lại config

### 5. Port 3000 đã được sử dụng
**Giải pháp:**
- Tắt ứng dụng đang chạy ở port 3000
- Hoặc chạy với port khác (Windows PowerShell):
```powershell
$env:PORT=3001; npm run dev
```
> ⚠️ Nếu đổi port, nhớ đổi cả `FRONTEND_URL` bên backend cho khớp (CORS).

---

## 📞 Hỗ Trợ

Nếu gặp vấn đề trong quá trình cài đặt hoặc chạy dự án, vui lòng:
1. Kiểm tra lại các bước cài đặt
2. Xem phần xử lý lỗi thường gặp
3. Kiểm tra console log trong trình duyệt (F12)
4. Kiểm tra terminal log khi chạy `npm start`

---

## 📝 Ghi Chú Bổ Sung

### Package quan trọng
- **react-router-dom**: Điều hướng trang
- **axios**: Gọi API
- **firebase**: Lưu trữ file
- **react-redux**: Quản lý state
- **sass**: CSS preprocessor
- **sweetalert2**: Hiển thị thông báo
- **recharts**: Biểu đồ thống kê
- **@stomp/stompjs**: WebSocket client
- **vite**: Dev server & bundler (thay thế `react-scripts` / CRA)
- **@vitejs/plugin-react**: Plugin React cho Vite (JSX transform, HMR)
- **vitest** / **jsdom**: Chạy test (thay thế Jest của CRA)

---

**Chúc bạn cài đặt và sử dụng thành công! 🎉**
