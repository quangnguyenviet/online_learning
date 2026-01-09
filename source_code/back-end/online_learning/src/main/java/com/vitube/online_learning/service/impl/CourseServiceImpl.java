package com.vitube.online_learning.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vitube.online_learning.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.dto.response.LearnWhatResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Lesson;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.mapper.CousreMapper;
import com.vitube.online_learning.mapper.LearnWhatMapper;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.UserRepository;

import lombok.RequiredArgsConstructor;

/**
 * Lớp triển khai các phương thức liên quan đến khóa học.
 */
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CousreMapper cousreMapper;
    private final UserService userService;
    private final LessonService lessonService;
    private final LearnWhatMapper learnWhatMapper;
    private final RequireMapper requireMapper;
    private final S3Service s3Service;

    /**
     * Chuyển đổi đối tượng Course thành CourseResponse.
     *
     * @param course Đối tượng khóa học.
     * @param type Loại phản hồi (0: chung, 1: chi tiết).
     * @return Đối tượng phản hồi khóa học.
     */
    @Override
    public CourseResponse courseToCourseResponse(Course course, int type) {
        CourseResponse response = cousreMapper.courseToCourseResponse(course);
        int hour = 0;
        int minute = 0;
        int second = 0;
        long totalSecond = 0;
        List<LessonResponse> lessonResponses = new ArrayList<>();
        for (Lesson lesson : course.getLessons()) {
            totalSecond += lesson.getDuration();
            lessonResponses.add(lessonService.lessonToLessonResponse(lesson));
        }
        hour = (int) (totalSecond / 3600);
        minute = (int) ((totalSecond % 3600) / 60);
        second = (int) (totalSecond % 60);
        response.setHour(hour);
        response.setMinute(minute);
        response.setSecond(second);

        response.setNumber_of_lessons(course.getLessons().size());

        List<LearnWhatResponse> learnWhatResponses = new ArrayList<>();
        course.getLearnWhats().forEach(learnWhat -> {
            learnWhatResponses.add(learnWhatMapper.learnWhatTooLearnWhatResponse(learnWhat));
        });

        List<RequireResponse> requireResponses = new ArrayList<>();
        course.getRequires().forEach(require -> {
            requireResponses.add(requireMapper.requireToRequireResponse(require));
        });

        response.setInstructorId(course.getInstructor().getId());

        if (type == 0) {
            return response;
        } else {
            Collections.sort(lessonResponses);

            response.setLessons(lessonResponses);
            response.setLearnWhats(learnWhatResponses);
            response.setRequires(requireResponses);
        }

        response.setShort_desc(course.getShortDesc());

        return response;
    }

    /**
     * Tạo khóa học mới.
     *
     * @param request Yêu cầu tạo khóa học.
     * @param image Tệp hình ảnh của khóa học.
     * @return Đối tượng phản hồi khóa học sau khi tạo.
     */
    @Override
    public CourseResponse createCourse(CourseRequest request, MultipartFile image) {
        String key = generateKey();
        String imageUrl = "";
        try {
            imageUrl = s3Service.uploadPublicFile(image, key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        User instructor;
        if (request.getInstructorId() == null) {
            instructor = userService.getCurrentUser();
        } else {
            instructor = userRepository
                    .findById(request.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
        }

        Course course = cousreMapper.requestToCourse(request);
        course.setInstructor(instructor);
        course.setImageUrl(imageUrl);

        Course saved = courseRepository.save(course);

        CourseResponse courseResponse = cousreMapper.courseToCourseResponse(course);
        courseResponse.setInstructorId(instructor.getId());

        return courseResponse;
    }

    /**
     * Lấy thông tin khóa học theo ID.
     *
     * @param id ID của khóa học.
     * @return Đối tượng phản hồi khóa học.
     */
    @Override
    public CourseResponse getCourseById(String id) {
        Course course = courseRepository.findById(id).get();

        CourseResponse response = courseToCourseResponse(course, 1);
        return response;
    }

    /**
     * Cập nhật thông tin khóa học.
     *
     * @param id ID của khóa học.
     * @param request Yêu cầu cập nhật khóa học.
     * @return Đối tượng phản hồi khóa học sau khi cập nhật.
     */
    @Override
    public CourseResponse updateCourse(String id, CourseRequest request) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));

        if (request.getTag().equals("GENERAL")) {
            course.setTitle(request.getTitle());
            course.setShortDesc(request.getShortDesc());
            course.setPrice(request.getPrice());
        }

        Course saved = courseRepository.save(course);

        CourseResponse response = cousreMapper.courseToCourseResponse(saved);
        response.setInstructorId(course.getInstructor().getId());

        return response;
    }

    /**
     * Xóa khóa học theo ID.
     *
     * @param id ID của khóa học.
     */
    @Override
    public void deleteCourse(String id) {}

    /**
     * Lấy danh sách các khóa học theo loại và từ khóa tìm kiếm.
     *
     * @param type Loại khóa học (free, plus, hoặc null).
     * @param query Từ khóa tìm kiếm.
     * @return Danh sách phản hồi khóa học.
     */
    @Override
    public List<CourseResponse> getCourses(String type, String query) {
        List<CourseResponse> responseList = new ArrayList<>();
        if (type == null || type.equals("")) {
            if (query != null) {
                courseRepository.findByTitleContaining(query).forEach(course -> {
                    if (course.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        CourseResponse response = courseToCourseResponse(course, 0);
                        responseList.add(response);
                    }
                });
            } else {
                courseRepository.findAll().forEach(course -> {
                    CourseResponse response = courseToCourseResponse(course, 0);
                    responseList.add(response);
                });
            }

        } else if (type.equals("free")) {
            courseRepository.findAll().forEach(course -> {
                if (course.getPrice() == 0 || course.getNewPrice() == 0) {
                    CourseResponse response = courseToCourseResponse(course, 0);
                    responseList.add(response);
                }
            });
        } else if (type.equals("plus")) {
            courseRepository.findAll().forEach(course -> {
                if (course.getPrice() != 0 && course.getNewPrice() != 0) {
                    CourseResponse response = courseToCourseResponse(course, 0);
                    responseList.add(response);
                }
            });
        }
        return responseList;
    }

    /**
     * Lấy danh sách các khóa học miễn phí.
     *
     * @return Danh sách phản hồi khóa học miễn phí.
     */
    @Override
    public List<CourseResponse> getFreeCourse() {
        List<CourseResponse> responseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            if (course.getPrice() == 0 || course.getNewPrice() == 0) {
                CourseResponse response = cousreMapper.courseToCourseResponse(course);
                response.setInstructorId(course.getInstructor().getId());
                responseList.add(response);
            }
        });
        return responseList;
    }

    /**
     * Lấy danh sách các khóa học trả phí.
     *
     * @return Danh sách phản hồi khóa học trả phí.
     */
    @Override
    public List<CourseResponse> getPlusCourse() {
        List<CourseResponse> responseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            if (course.getPrice() != 0 && course.getNewPrice() != 0) {
                CourseResponse response = cousreMapper.courseToCourseResponse(course);
                response.setInstructorId(course.getInstructor().getId());
                responseList.add(response);
            }
        });
        return responseList;
    }

    /**
     * Lấy danh sách các khóa học mà người dùng đang học.
     *
     * @return Danh sách phản hồi khóa học đang học.
     */
    @Override
    public List<CourseResponse> getLearningCourses() {
        List<CourseResponse> responses = new ArrayList<>();

        User user = userService.getCurrentUser();
        user.getRegisters().forEach(registration -> {
            CourseResponse response = cousreMapper.courseToCourseResponse(registration.getCourse());
            response.setInstructorId(registration.getCourse().getInstructor().getId());
            responses.add(response);
        });
        return responses;
    }

    /**
     * Lấy danh sách các khóa học của giảng viên theo ID.
     *
     * @param instructorId ID của giảng viên.
     * @return Danh sách phản hồi khóa học của giảng viên.
     */
    @Override
    public List<CourseResponse> getCoursesOfInstructor(String instructorId) {
        User instructor =
                userRepository.findById(instructorId).orElseThrow(() -> new RuntimeException("Instructor not found"));
        List<CourseResponse> responseList = new ArrayList<>();
        instructor.getCourses().forEach(course -> {
            CourseResponse response = cousreMapper.courseToCourseResponse(course);
            response.setInstructorId(course.getInstructor().getId());
            responseList.add(response);
        });
        return responseList;
    }

    /**
     * Lấy danh sách các khóa học của giảng viên hiện tại.
     *
     * @return Danh sách phản hồi khóa học của giảng viên hiện tại.
     */
    @Override
    public List<CourseResponse> getMyCourses() {
        User instructor = userService.getCurrentUser();
        List<CourseResponse> responseList = new ArrayList<>();
        instructor.getCourses().forEach(course -> {
            CourseResponse response = courseToCourseResponse(course, 1);
            responseList.add(response);
        });
        return responseList;
    }

    /**
     * Cập nhật giá của khóa học.
     *
     * @param courseId ID của khóa học.
     * @param price Giá mới của khóa học.
     */
    @Override
    public void setPrice(String courseId, Float price) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setPrice(price);
        courseRepository.save(course);
    }

    /**
     * Tạo khóa học mới với một khóa duy nhất.
     *
     * @return Chuỗi khóa duy nhất.
     */
    public static String generateKey() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return now.format(formatter);
    }
}