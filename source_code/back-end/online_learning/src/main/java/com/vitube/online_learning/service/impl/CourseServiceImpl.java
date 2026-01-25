package com.vitube.online_learning.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vitube.online_learning.dto.CourseDTO;
import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.ObjectiveDTO;
import com.vitube.online_learning.dto.request.CourseCreattionRequest;
import com.vitube.online_learning.dto.request.UpdateCourseRequest;
import com.vitube.online_learning.entity.*;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.enums.S3DeleteEnum;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.mapper.CourseMapper;
import com.vitube.online_learning.mapper.ObjectiveMapper;
import com.vitube.online_learning.repository.CategoryRepository;
import com.vitube.online_learning.service.*;
import com.vitube.online_learning.utils.FileUtil;
import com.vitube.online_learning.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

/**
 * Lớp triển khai các phương thức liên quan đến khóa học.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserService userService;
    private final LessonService lessonService;
    private final ObjectiveMapper objectiveMapper;
    private final RequireMapper requireMapper;
    private final S3Service s3Service;
    private final CategoryRepository categoryRepository;

    /**
     * Chuyển đổi đối tượng Course thành CourseResponse.
     *
     * @param course Đối tượng khóa học.
     * @param type Loại phản hồi (0: chung, 1: chi tiết).
     * @return Đối tượng phản hồi khóa học.
     */
    @Override
    public CourseDTO toDto(Course course, int type) {
        return null;
    }

    /**
     * Tạo khóa học mới.
     *
     * @param request Yêu cầu tạo khóa học.
     * @param image Tệp hình ảnh của khóa học.
     * @return Đối tượng phản hồi khóa học sau khi tạo.
     */
    @Override
    public CourseDTO createCourse(CourseCreattionRequest request, MultipartFile image) {
        String key = FileUtil.generateFileName(image.getOriginalFilename());
        String imageUrl = "";
        try {
            imageUrl = s3Service.uploadPublicFile(image, key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        User instructor = userService.getCurrentUser();
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        Course course = courseMapper.createRequestToEntity(request);
        course.setInstructor(instructor);
        course.setImageUrl(imageUrl);
        course.setCategory(category);

        // create objectives
        if (request.getObjectives() == null || request.getObjectives().isEmpty()) {

        }
        else{
            List<Objective> objectives = request.getObjectives().stream()
                    .map(o -> {
                        Objective objective = Objective.builder()
                                .description(o)
                                .course(course)
                                .build();
                        return objective;
                    })
                    .toList();
            course.setObjectives(objectives);
        }

        course.setCreatedAt(LocalDateTime.now());


        Course saved = courseRepository.save(course);

        CourseDTO courseDTO = courseMapper.toDto(saved);

        return courseDTO;
    }

    /**
     * Lấy thông tin khóa học theo ID.
     *
     * @param id ID của khóa học.
     * @return Đối tượng phản hồi khóa học.
     */
    @Override
    public CourseDTO getCourseById(String id) {
        Course course = courseRepository.findById(id).get();

        CourseDTO response = courseMapper.toDto(course);

        Collections.sort(response.getLessons(), (o1, o2) -> {
            return o1.getCreatedAt().compareTo(o2.getCreatedAt());
        });

        return response;
    }

    /**
     * Cập nhật thông tin khóa học.
     *
     * @param request Yêu cầu cập nhật khóa học.
     * @return Đối tượng phản hồi khóa học sau khi cập nhật.
     */
    @Override
    @Transactional
    public CourseDTO updateCourse(UpdateCourseRequest request,
                                  MultipartFile imageFile,
                                  List<ObjectiveDTO> updatedObjectives) {
        Course course = courseRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        // basic fields
        if (ValidateUtil.customValidateString(request.getTitle())) {
            course.setTitle(request.getTitle());
        }
        if (ValidateUtil.customValidateString(request.getShortDesc())) {
            course.setShortDesc(request.getShortDesc());
        }
        if (request.getPrice() != null) {
            course.setPrice(request.getPrice());
        }
        if (request.getDiscount() != null) {
            course.setDiscount(request.getDiscount());
        }

        // set category only if provided
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
            course.setCategory(category);
        }

        // image handling (if provided)
        if (imageFile != null && !imageFile.isEmpty()) {
            handleImageUpdate(course, imageFile);
        }

        // objectives handling (delete / add / update)
        handleObjectivesUpdate(course, request, updatedObjectives);

        Course saved = courseRepository.save(course);
        CourseDTO reponse = courseMapper.toDto(saved);

        return reponse;

    }

    // helper: handle image replacement with graceful S3 delete logging
    private void handleImageUpdate(Course course, MultipartFile imageFile) {
        String oldImageUrl = course.getImageUrl();
        if (oldImageUrl != null && oldImageUrl.contains("/")) {
            String oldKey = oldImageUrl.substring(oldImageUrl.lastIndexOf("/") + 1);
            try {
                s3Service.deleteFile(oldKey, S3DeleteEnum.IMAGE.name());
            } catch (Exception e) {
                log.warn("Old image deletion failed or not found: {}", oldKey, e);
                // continue - do not abort update because old image deletion failed
            }
        }

        String key = FileUtil.generateFileName(imageFile.getOriginalFilename());
        try {
            String imageUrl = s3Service.uploadPublicFile(imageFile, key);
            course.setImageUrl(imageUrl);
        } catch (Exception e) {
            log.error("Failed to upload new image", e);
            throw new RuntimeException(e);
        }
    }

    // helper: handle objective deletions, additions, and updates in a clear and safe way
    private void handleObjectivesUpdate(Course course, UpdateCourseRequest request,  List<ObjectiveDTO> updatedObjectives) {
        // must follow delete -> update -> add order to avoid conflicts

        // delete objectives by id
        if (request.getDeleteObjectiveIds() != null && !request.getDeleteObjectiveIds().isEmpty()) {
            for (String objId : request.getDeleteObjectiveIds()) {
                course.getObjectives().removeIf(obj -> obj.getId().equals(objId));
            }
        }

        // update existing objectives safely (index-bounded)
        if (updatedObjectives != null && !updatedObjectives.isEmpty()) {
            List<Objective> currentObjectives = course.getObjectives();
            for (ObjectiveDTO objDto : updatedObjectives) {
                for (int i = 0; i < currentObjectives.size(); i++) {
                    Objective obj = currentObjectives.get(i);
                    if (obj.getId().equals(objDto.getId())) {
                        obj.setDescription(objDto.getDescription());
                        break;
                    }
                }
            }
        }

        // add new objectives
        if (request.getNewObjectives() != null && !request.getNewObjectives().isEmpty()) {
            List<Objective> newObjectives = request.getNewObjectives().stream()
                    .map(desc -> Objective.builder().description(desc).course(course).build())
                    .toList();
            course.getObjectives().addAll(newObjectives);
        }



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
    public List<CourseDTO> getCourses(String type, String query) {
        List<CourseDTO> responseList = new ArrayList<>();
        if (type == null || type.equals("")) {
            if (query != null) {
                courseRepository.findByTitleContaining(query).forEach(course -> {
                    if (course.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        CourseDTO response = courseMapper.toDto(course);
                        responseList.add(response);
                    }
                });
            } else {
                courseRepository.findAll().forEach(course -> {
                    CourseDTO response = courseMapper.toDto(course);
                    responseList.add(response);
                });
            }

        } else if (type.equals("free")) {
            courseRepository.findAll().forEach(course -> {
                if (course.getPrice() == 0 || course.getNewPrice() == 0) {
                    CourseDTO response = courseMapper.toDto(course);
                    responseList.add(response);
                }
            });
        } else if (type.equals("plus")) {
            courseRepository.findAll().forEach(course -> {
                if (course.getPrice() != 0 && course.getNewPrice() != 0) {
                    CourseDTO response = courseMapper.toDto(course);
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
    public List<CourseDTO> getFreeCourse() {
        List<CourseDTO> responseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            if (course.getPrice() == 0 || course.getNewPrice() == 0) {
                CourseDTO response = courseMapper.toDto(course);
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
    public List<CourseDTO> getPlusCourse() {
        List<CourseDTO> responseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            if (course.getPrice() != 0 && course.getNewPrice() != 0) {
                CourseDTO response = courseMapper.toDto(course);
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
    public List<CourseDTO> getLearningCourses() {
        List<CourseDTO> responses = new ArrayList<>();

        User user = userService.getCurrentUser();
        user.getRegisters().forEach(registration -> {
            CourseDTO response = courseMapper.toDto(registration.getCourse());
            responses.add(response);
        });
        return responses;
    }




    /**
     * Lấy danh sách các khóa học của giảng viên hiện tại.
     *
     * @return Danh sách phản hồi khóa học của giảng viên hiện tại.
     */
    @Override
    public List<CourseDTO> getMyCourses() {
        User instructor = userService.getCurrentUser();
        List<CourseDTO> responseList = new ArrayList<>();
        instructor.getCourses().forEach(course -> {
            CourseDTO response = courseMapper.toDto(course);

            // calculate duration
            int totalSeconds = 0;
            for (LessonDTO lessonDTO: response.getLessons()){
                totalSeconds += lessonDTO.getDuration();
            }
            // convert format
            int hours = totalSeconds / 3600;
            int minutes = (totalSeconds % 3600) / 60;
            int seconds = totalSeconds % 60;
            String duration = hours + "h " + minutes + "m " + seconds + "s";
            response.setDuration(duration);

            // set number of lessons
            response.setNumberOfLessons(response.getLessons().size());

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
    public void setPrice(String courseId, Double price) {
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

