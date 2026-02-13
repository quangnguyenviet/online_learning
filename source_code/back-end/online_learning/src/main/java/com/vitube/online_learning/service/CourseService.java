package com.vitube.online_learning.service;

import com.vitube.online_learning.dto.CourseDTO;
import com.vitube.online_learning.dto.ObjectiveDTO;
import com.vitube.online_learning.dto.request.CourseCreattionRequest;
import com.vitube.online_learning.dto.request.UpdateCourseRequest;
import com.vitube.online_learning.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface cung cấp các phương thức liên quan đến khóa học.
 */
public interface CourseService {

    CourseDTO entityToDto(Course course);

    /**
     * Tạo mới một khóa học.
     *
     * @param request Yêu cầu tạo khóa học.
     * @param image Tệp hình ảnh của khóa học.
     * @return Đối tượng phản hồi khóa học.
     */
    CourseDTO createCourse(CourseCreattionRequest request, MultipartFile image);

    /**
     * Lấy thông tin khóa học theo ID.
     *
     * @param id ID của khóa học.
     * @return Đối tượng phản hồi khóa học.
     */
    CourseDTO getCourseById(String id);


    CourseDTO updateCourse(UpdateCourseRequest request, MultipartFile imageFile, List<ObjectiveDTO> updatedObjectives);

    /**
     * Xóa khóa học theo ID.
     *
     * @param id ID của khóa học.
     */
    void deleteCourse(String id);

    Page<CourseDTO> getCourses(String type, String keyword, Integer page, Integer size);

    /**
     * Lấy danh sách các khóa học miễn phí.
     *
     * @return Danh sách phản hồi khóa học miễn phí.
     */
    List<CourseDTO> getFreeCourse();

    /**
     * Lấy danh sách các khóa học Plus.
     *
     * @return Danh sách phản hồi khóa học Plus.
     */
    List<CourseDTO> getPlusCourse();

    /**
     * Lấy danh sách các khóa học đang học.
     *
     * @return Danh sách phản hồi khóa học đang học.
     */
    List<CourseDTO> getLearningCourses();



    /**
     * Lấy danh sách các khóa học của người dùng hiện tại (instructor only).
     *
     * @return Danh sách phản hồi khóa học của người dùng.
     */
    List<CourseDTO> getMyCourses();

    long countCourseByInstructorId(String instructorId);

    long countPublishedCourseByInstructorId(String instructorId);

    long countTotalStudentsByInstructorId(String instructorId);

    long countTotalVideosByInstructorId(String instructorId);

    CourseDTO updatePublishedStatus(String id, Boolean published);
}