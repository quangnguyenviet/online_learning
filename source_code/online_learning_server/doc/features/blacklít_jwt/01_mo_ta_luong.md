# Blacklist JWT khi logout - Mô tả luồng

## 1. Mục tiêu
- Thu hồi JWT ngay sau khi người dùng logout.
- Token đã logout không thể tiếp tục truy cập tài nguyên bảo vệ.
- Không cần job dọn dữ liệu thủ công nhờ TTL của Redis.

## 2. Ý tưởng chính
- Khi logout, lấy `jti` và `exp` từ token.
- Ghi key vào Redis theo mẫu `auth:blacklist:{jti}`.
- Set TTL bằng thời gian còn lại của token.
- Mỗi lần xác thực token, kiểm tra `jti` có nằm trong blacklist hay không.

## 3. Luồng chi tiết

### 3.1 Logout
1. Client gửi `POST /auth/logout` kèm token.
2. Server parse token, lấy:
   - `jti`: định danh token.
   - `exp`: thời điểm hết hạn.
3. Tính `ttl = exp - now`.
4. Nếu `ttl > 0`:
   - Lưu Redis key `auth:blacklist:{jti}` với value đơn giản (ví dụ `1`).
   - Set TTL đúng bằng `ttl`.
5. Trả về logout thành công.

### 3.2 Xác thực request tiếp theo
1. Request đi qua filter xác thực.
2. Server verify chữ ký JWT + hạn dùng cơ bản.
3. Lấy `jti` từ JWT.
4. Check Redis key `auth:blacklist:{jti}`.
5. Nếu key tồn tại: từ chối request (`401`).
6. Nếu không tồn tại: cho phép request đi tiếp.

## 4. Thiết kế key Redis
- **Key**: `auth:blacklist:{jti}`
- **Value**: `1` (hoặc metadata tối thiểu)
- **TTL**: `exp - now`

Ví dụ:
- Token còn 15 phút thì key tồn tại 15 phút.
- Hết 15 phút key tự bị Redis xóa.

## 5. Trường hợp biên
- Token hết hạn rồi mới logout: bỏ qua ghi blacklist (TTL <= 0).
- Redis tạm lỗi:
  - Khuyến nghị fail-safe theo policy hệ thống (thường từ chối để an toàn).
- Token không có `jti`: cần chuẩn hóa bước phát hành token để luôn có `jti`.

## 6. Lợi ích
- Truy vấn blacklist rất nhanh (`O(1)` theo key).
- Giảm tải DB so với lưu bảng invalid token.
- Cơ chế tự dọn dẹp theo TTL, không phát sinh rác lâu dài.
