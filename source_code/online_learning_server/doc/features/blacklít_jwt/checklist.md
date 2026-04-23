# Checklist triển khai tính năng Blacklist JWT bằng Redis

> Mục tiêu: Khi logout/refresh, token cũ bị vô hiệu hóa ngay; mọi request dùng token đã blacklist phải bị từ chối.

## 1) Chuẩn bị hạ tầng

- [x] Thêm Redis service vào [docker-compose.yaml](../../../docker-compose.yaml).
- [x] Xác định thông số kết nối Redis cho môi trường local/dev/prod.
- [x] Bổ sung cấu hình Redis vào:
  - [x] [src/main/resources/application.properties](../../../src/main/resources/application.properties)
  - [x] [src/main/resources/application-dev.properties](../../../src/main/resources/application-dev.properties)
  - [x] [src/main/resources/application-prod.properties](../../../src/main/resources/application-prod.properties)
  - [x] [src/main/resources/application-example.properties](../../../src/main/resources/application-example.properties)
- [x] Đảm bảo có dependency Redis trong [pom.xml](../../../pom.xml) (Spring Data Redis + client mặc định).

## 2) Thiết kế kỹ thuật

- [x] Chốt format key blacklist: `auth:blacklist:{jti}`.
- [x] Chốt value lưu trong Redis ("1").
- [x] Chốt công thức TTL: `exp - now` (đơn vị giây).
- [x] Quy định rõ policy khi Redis lỗi (khuyến nghị fail-safe để đảm bảo an toàn).
- [x] Quy định rõ xử lý khi token không có `jti`.

### Quy ước đã chốt cho Bước 2

- Key prefix: `auth:blacklist:`
- Key đầy đủ: `auth:blacklist:{jti}`
- Value: `1`
- TTL: `max(0, expEpochSeconds - nowEpochSeconds)`
- Nếu TTL = 0 thì không ghi key blacklist.
- Policy khi Redis lỗi:
  - Luồng xác thực request bảo vệ: fail-safe (coi token không hợp lệ, trả `401`).
  - Luồng logout/refresh cần ghi blacklist: trả lỗi và không báo thành công giả.
- Token không có `jti`:
  - Luồng phát hành token phải luôn gắn `jti` (UUID).
  - Nếu token đến mà thiếu `jti`, coi là token không hợp lệ và từ chối.

## 3) Tạo lớp service cho blacklist Redis

- [x] Tạo `TokenBlacklistService` (interface).
- [x] Tạo `TokenBlacklistServiceImpl` dùng `StringRedisTemplate`/`RedisTemplate`.
- [x] Implement hàm:
  - [x] `blacklist(jti, ttlSeconds)`
  - [x] `isBlacklisted(jti)`
  - [x] `buildKey(jti)` (private)
- [x] Bổ sung constant/prefix key theo convention của project.

## 4) Refactor luồng logout/introspect/verify token

- [x] Cập nhật [src/main/java/com/vitube/online_learning/service/impl/AuthenticationServiceImpl.java](../../../src/main/java/com/vitube/online_learning/service/impl/AuthenticationServiceImpl.java):
  - [x] `logout(...)`: parse token, lấy `jti`, `exp`, tính TTL, ghi Redis nếu TTL > 0.
  - [x] `introspect(...)`: sau bước verify chữ ký + hạn, kiểm tra `isBlacklisted(jti)`.
- [x] Cập nhật [src/main/java/com/vitube/online_learning/service/impl/JWTServiceImpl.java](../../../src/main/java/com/vitube/online_learning/service/impl/JWTServiceImpl.java):
  - [x] `verifyToken(...)`: thay check `InvalidTokenRepository` bằng check Redis blacklist.
  - [x] Đảm bảo không còn lỗi `NoSuchElementException` khi token chưa bị blacklist.
- [x] Cập nhật [src/main/java/com/vitube/online_learning/configuration/MyJwtDecoder.java](../../../src/main/java/com/vitube/online_learning/configuration/MyJwtDecoder.java) để luồng decode vẫn dựa trên `introspect` mới.

## 5) Xử lý luồng refresh token

- [x] Trong `refreshToken(...)`, blacklist refresh token cũ bằng Redis trước khi cấp access token mới.
- [x] Đảm bảo TTL cho token refresh bị blacklist theo đúng thời gian còn hiệu lực.

## 6) Dọn phần blacklist cũ bằng DB

- [x] Đánh giá ảnh hưởng của entity InvalidToken (đã xóa khỏi codebase).
- [x] Đánh giá ảnh hưởng của repository InvalidTokenRepository (đã xóa khỏi codebase).
- [x] Loại bỏ dependency vào bảng `invalid_token` ở các service auth/JWT.
- [x] Quyết định giữ hay xóa bảng `invalid_token` trong migration (nếu xóa cần kế hoạch migration an toàn).

### Quyết định migration cho Bước 6

- Chọn xóa bảng `invalid_tokens` bằng migration mới: [src/main/resources/db/migration/V3__drop_invalid_tokens_table.sql](../../../src/main/resources/db/migration/V3__drop_invalid_tokens_table.sql).
- Không chỉnh sửa ngược file [src/main/resources/db/migration/V1__init.sql](../../../src/main/resources/db/migration/V1__init.sql) để tránh phá lịch sử Flyway đã áp dụng.

## 7) Kiểm thử

- [ ] Unit test cho `TokenBlacklistService`:
  - [ ] lưu key + TTL đúng.
  - [ ] `isBlacklisted` trả về đúng.
- [ ] Unit test cho `AuthenticationServiceImpl.logout`:
  - [ ] TTL > 0 thì ghi blacklist.
  - [ ] TTL <= 0 thì bỏ qua.
- [ ] Unit test cho `JWTServiceImpl.verifyToken`:
  - [ ] token hợp lệ + không blacklist => pass.
  - [ ] token hợp lệ + bị blacklist => fail.
- [ ] Integration test:
  - [ ] login -> gọi API protected thành công.
  - [ ] logout -> gọi lại API protected bằng token cũ nhận `401`.
  - [ ] refresh -> token cũ không dùng lại được.

## 8) Quan sát và vận hành

- [ ] Thêm log ở mức phù hợp cho hành vi blacklist (không log token raw).
- [ ] Theo dõi metric cơ bản: số token blacklist, tỉ lệ request bị chặn vì blacklist, lỗi Redis.
- [ ] Tài liệu hóa quy trình fallback khi Redis unavailable.

## 9) Tiêu chí hoàn thành (Definition of Done)

- [ ] Logout làm token hiện tại mất hiệu lực ngay.
- [ ] Request dùng token đã logout luôn nhận `401`.
- [ ] Không còn phụ thuộc DB `invalid_token` trong luồng xác thực chính.
- [ ] Test quan trọng đã chạy pass.
- [ ] Tài liệu cấu hình Redis và cách chạy local đã cập nhật.
