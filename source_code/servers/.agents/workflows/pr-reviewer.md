---
description: 
---

# Workflow: Code Reviewer (Pull Request Review)

## Goal
Đóng vai trò là một Senior Engineer (Reviewer) để tự động kiểm tra, đánh giá và đưa ra phản hồi về mã nguồn trước khi hợp nhất (merge) nhánh tính năng vào nhánh chính (ví dụ: merge `feature/*` vào `main`).

## Trigger
Người dùng yêu cầu: "Review code cho tôi", "Dùng workflow PR Reviewer", hoặc "Kiểm tra nhánh hiện tại trước khi merge".

## Execution Steps

### 1. Thu thập dữ liệu thay đổi (Diff Collection)
- Kiểm tra trạng thái Git hiện tại bằng `git status`.
- Nếu có nhánh đích để so sánh (ví dụ branch `main`), chạy lệnh `git diff main...HEAD`.
- Nếu đang ở trạng thái làm việc cục bộ (local), chạy lệnh `git diff --staged` hoặc `git diff` để lấy toàn bộ thay đổi.

### 2. Kiểm tra tính tuân thủ quy tắc dự án (Rule Compliance)
AI phải đọc qua thư mục `.agents/rules/` và đối chiếu mã nguồn:
- **Security (`security-rules.md`)**: Có vô tình hardcode mật khẩu, API key, hoặc token nào vào code không?
- **Database (`database-rules.md`)**: Có thay đổi Entity nào mà quên tạo file Flyway migration không?
- **Exception (`exception-handling-rules.md`)**: Lỗi có được catch và ném ra dưới dạng `AppException` cùng `ErrorCode` hợp lệ không? Hay đang nuốt lỗi (swallow exceptions)?

### 3. Phân tích chất lượng mã nguồn (Code Quality Analysis)
- **Kiến trúc (Architecture)**: Logic nghiệp vụ có bị viết nhầm trong Controller thay vì Service không?
- **Hiệu năng (Performance)**: Có vấn đề N+1 query (trong Spring Data JPA) hay vòng lặp vô tận không?
- **Đặt tên (Naming Convention)**: Tên biến, tên hàm có rõ nghĩa và tuân thủ CamelCase không?
- **Khả năng bảo trì (Maintainability)**: Có đoạn code nào lặp lại (Duplicate) cần tách thành hàm dùng chung (Utils/Helper) không?

### 4. Đánh giá và Phản hồi (Reporting)
Sinh ra một bản báo cáo Review Code trực quan bằng Markdown, chia làm các mục:
1. **✅ Điểm tốt (What's good)**: Khen ngợi những đoạn code xử lý gọn gàng, thông minh.
2. **🚨 Lỗi nghiêm trọng (Blockers - Must Fix)**: Các lỗi bảo mật, lỗi logic gây sập ứng dụng, hoặc vi phạm nghiêm trọng rules của dự án. (Yêu cầu phải sửa mới được commit/merge).
3. **⚠️ Điểm cần cải thiện (Warnings - Should Fix)**: Gợi ý tối ưu hóa hiệu năng, refactor cho code sạch hơn.
4. **💡 Gợi ý nhỏ (Nitpicks - Optional)**: Sai sót nhỏ về khoảng trắng, comment, hoặc cách đặt tên.

### 5. Hành động tiếp theo (Next Actions)
- Hỏi người dùng xem họ muốn tự sửa các lỗi đã phát hiện, hay muốn AI tự động sửa (auto-fix) những lỗi nhỏ.
- Nếu người dùng đồng ý auto-fix, AI sẽ bắt đầu áp dụng sửa đổi.
