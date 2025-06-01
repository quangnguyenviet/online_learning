# Turtle Online Learning - Frontend

Đây là source code **frontend** cho hệ thống học trực tuyến Turtle Online Learning, sử dụng ReactJS.

## Yêu cầu hệ thống

- **Node.js** >= 16.x
- **npm** >= 8.x hoặc **yarn**
- Đã cài đặt backend API (tham khảo tài liệu backend nếu có)

## Hướng dẫn cài đặt và chạy dự án

### 1. Clone source code

```bash
git clone <repo-url>
cd source_code/front-end/online_learning
```

### 2. Cài đặt dependencies

Sử dụng npm:

```bash
npm install
```

Hoặc yarn:

```bash
yarn install
```

### 3. Cấu hình môi trường

- Nếu có file `.env.example`, hãy copy thành `.env` và chỉnh sửa các biến phù hợp (ví dụ API endpoint).
- Mặc định, API backend chạy ở `http://localhost:8080/online_learning/`. Nếu khác, hãy sửa lại trong code hoặc file env.

### 4. Chạy dự án

```bash
npm start
```
Hoặc:
```bash
yarn start
```

- Truy cập [http://localhost:3000](http://localhost:3000) trên trình duyệt để sử dụng ứng dụng.

### 5. Build production

```bash
npm run build
```
Hoặc:
```bash
yarn build
```

## Một số lưu ý

- Đảm bảo backend đã chạy trước khi sử dụng frontend.
- Nếu gặp lỗi về SCSS, kiểm tra lại đường dẫn import và cài đặt đúng các package liên quan (`sass`, `node-sass`).
- Để sử dụng đầy đủ icon, hãy đảm bảo đã cài đặt các package như `react-icons` và/hoặc FontAwesome.

## Các lệnh hữu ích

| Lệnh                | Chức năng                |
|---------------------|-------------------------|
| `npm start`         | Chạy dev server         |
| `npm run build`     | Build production        |
| `npm install`       | Cài dependencies        |
| `npm run lint`      | Kiểm tra code style     |

## Thư mục chính

- `src/` - Source code React
- `public/` - File tĩnh
- `src/components/` - Các component dùng chung
- `src/pages/` - Các trang chính
- `src/_var.scss` - Biến SCSS toàn cục

## Liên hệ

- Tác giả: [Tên của bạn]
- Email: [Email của bạn]

---

Chúc bạn cài đặt và sử dụng thành công!