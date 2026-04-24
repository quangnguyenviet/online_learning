# Skill: Generate API Documentation (SpringDoc / Swagger)

## Goal
Tự động sinh hoặc bổ sung các annotation của `springdoc-openapi` (Swagger) vào các `RestController` và `DTO` trong dự án Spring Boot để tạo tài liệu API rõ ràng, chuẩn xác.

## Trigger
Người dùng yêu cầu: "Tạo apidoc cho controller X", "Thêm swagger cho class Y", hoặc "Generate API doc".

## Prerequisites
- Đảm bảo dự án đã có dependency `springdoc-openapi-starter-webmvc-ui` (kiểm tra `pom.xml`).
- Các class mục tiêu thường nằm trong thư mục `controller` hoặc `dto`.

## Execution Steps

### 1. Phân tích Controller
- Mở file Controller mục tiêu.
- Đọc hiểu ý nghĩa của từng API (dựa trên URL, HTTP Method, tên hàm, và javadoc nếu có).

### 2. Thêm Class-level Annotation
- Import `io.swagger.v3.oas.annotations.tags.Tag`.
- Thêm `@Tag(name = "[Tên Resource]", description = "[Mô tả ngắn gọn về resource này]")` ngay trên khai báo class.
- Ví dụ:
  ```java
  @RestController
  @RequestMapping("/courses")
  @Tag(name = "Course Management", description = "APIs for managing courses and lessons")
  public class CourseController { ... }
  ```

### 3. Thêm Method-level Annotations
Với mỗi API Endpoint (`@GetMapping`, `@PostMapping`, v.v.), thêm các annotation sau:
- Import `io.swagger.v3.oas.annotations.Operation` và `io.swagger.v3.oas.annotations.responses.*`.
- Thêm `@Operation(summary = "[Mô tả ngắn]", description = "[Chi tiết hơn về logic]")`.
- Thêm `@ApiResponses` chứa các `@ApiResponse` mô tả mã lỗi trả về:
  ```java
  @Operation(summary = "Get course by ID", description = "Retrieve detailed information of a course")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation"),
      @ApiResponse(responseCode = "404", description = "Course not found", content = @Content)
  })
  @GetMapping("/{id}")
  public ApiResponse<CourseDTO> getCourseById(@PathVariable String id) { ... }
  ```

### 4. Xử lý Parameters (nếu cần)
- Nếu có `@RequestParam` hoặc `@PathVariable`, có thể kết hợp thêm `@Parameter(description = "...")` bên trong hàm để làm rõ ý nghĩa biến đầu vào.

### 5. Review & Cập nhật
- Đảm bảo các imports không bị lỗi.
- Đảm bảo giữ nguyên các logic kinh doanh, chỉ thêm annotation.
- Trả về thông báo hoàn tất và gợi ý người dùng mở giao diện Swagger UI (thường là `http://localhost:8080/swagger-ui/index.html`) để kiểm tra.
