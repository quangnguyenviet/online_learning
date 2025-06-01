package com.vitube.online_learning.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.entity.Course;

/**
 * Interface cung cấp các phương thức liên quan đến khóa học.
 */
public interface CourseService {
    /**
     * Chuyển đổi đối tượng Course thành CourseResponse.
     *
     * @param course Đối tượng khóa học.
     * @param type Loại chuyển đổi.
     * @return Đối tượng phản hồi khóa học.
     */
    CourseResponse courseToCourseResponse(Course course, int type);

    /**
     * Tạo mới một khóa học.
     *
     * @param request Yêu cầu tạo khóa học.
     * @param image Tệp hình ảnh của khóa học.
     * @return Đối tượng phản hồi khóa học.
     */
    CourseResponse createCourse(CourseRequest request, MultipartFile image);

    /**
     * Lấy thông tin khóa học theo ID.
     *
     * @param id ID của khóa học.
     * @return Đối tượng phản hồi khóa học.
     */
    CourseResponse getCourseById(String id);

    /**
     * Cập nhật thông tin khóa học.
     *
     * @param id ID của khóa học.
     * @param request Yêu cầu cập nhật khóa học.
     * @return Đối tượng phản hồi khóa học.
     */
    CourseResponse updateCourse(String id, CourseRequest request);

    /**
     * Xóa khóa học theo ID.
     *
     * @param id ID của khóa học.
     */
    void deleteCourse(String id);

    /**
     * Lấy danh sách các khóa học theo loại và từ khóa tìm kiếm.
     *
     * @param type Loại khóa học.
     * @param query Từ khóa tìm kiếm.
     * @return Danh sách phản hồi khóa học.
     */
    List<CourseResponse> getCourses(String type, String query);

    /**
     * Lấy danh sách các khóa học miễn phí.
     *
     * @return Danh sách phản hồi khóa học miễn phí.
     */
    List<CourseResponse> getFreeCourse();

    /**
     * Lấy danh sách các khóa học Plus.
     *
     * @return Danh sách phản hồi khóa học Plus.
     */
    List<CourseResponse> getPlusCourse();

    /**
     * Lấy danh sách các khóa học đang học.
     *
     * @return Danh sách phản hồi khóa học đang học.
     */
    List<CourseResponse> getLearningCourses();

    /**
     * Lấy danh sách các khóa học của giảng viên.
     *
     * @param instructorId ID của giảng viên.
     * @return Danh sách phản hồi khóa học của giảng viên.
     */
    List<CourseResponse> getCoursesOfInstructor(String instructorId);

    /**
     * Lấy danh sách các khóa học của người dùng hiện tại.
     *
     * @return Danh sách phản hồi khóa học của người dùng.
     */
    List<CourseResponse> getMyCourses();

    /**
     * Thiết lập giá cho khóa học.
     *
     * @param courseId ID của khóa học.
     * @param price Giá của khóa học.
     */
    void setPrice(String courseId, Float price);
}