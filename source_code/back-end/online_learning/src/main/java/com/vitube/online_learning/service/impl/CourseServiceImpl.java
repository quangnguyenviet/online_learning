package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.CourseDTO;
import com.vitube.online_learning.dto.LessonDTO;
import com.vitube.online_learning.dto.ObjectiveDTO;
import com.vitube.online_learning.dto.request.CourseCreattionRequest;
import com.vitube.online_learning.dto.request.UpdateCourseRequest;
import com.vitube.online_learning.entity.Category;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Objective;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.enums.S3DeleteEnum;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.mapper.CourseMapper;
import com.vitube.online_learning.mapper.LessonMapper;
import com.vitube.online_learning.mapper.ObjectiveMapper;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.CategoryRepository;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.CourseService;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;
import com.vitube.online_learning.service.UserService;
import com.vitube.online_learning.utils.FileUtil;
import com.vitube.online_learning.utils.ValidateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final LessonMapper lessonMapper;


    @Override
    public CourseDTO entityToDto(Course course) {
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
        Course course = courseRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.NOT_FOUND)
        );

        CourseDTO response = courseMapper.toDto(course);

        if (course.getLessons() == null || course.getLessons().isEmpty()) {
            response.setLessons(Collections.emptyList());
            return response;
        }

        List<LessonDTO> lessonResponses = new ArrayList<>();
        course.getLessons().forEach(lesson -> {
            LessonDTO lessonResponse = lessonMapper.entityToDto(lesson);
            lessonResponses.add(lessonResponse);
        });
        // sort by createdAt
        lessonResponses.sort((l1, l2) -> l1.getCreatedAt().compareTo(l2.getCreatedAt()));
        response.setLessons(lessonResponses);

        response.setDuration(calculateCourseDuration(lessonResponses));

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


    @Override
    public Page<CourseDTO> getCourses(String type, String keyword, Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Course> coursePage;

        List<CourseDTO> responseList = new ArrayList<>();
        // have type
        if (ValidateUtil.customValidateString(type)) {
            // have type and have quey
            if (ValidateUtil.customValidateString(keyword)) {
                coursePage = courseRepository.findByTitleContainingAndCategoryNameContaining(keyword, type, pageable);

            }
            else { // have type, no query
                coursePage = courseRepository.findByCategoryNameContaining(type, pageable);

            }
        }
        else{ // no type
            if(ValidateUtil.customValidateString(keyword)){ // no type, have query
                coursePage = courseRepository.findByTitleContaining(keyword, pageable);
            }
            // no type, no query
            else{
                coursePage = courseRepository.findAll(pageable);
            }
        }
        Page<CourseDTO> responsePage = coursePage.map(course -> {
            CourseDTO courseDTO = courseMapper.toDto(course);
            return courseDTO;
        });
        return responsePage;
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

            response.setDuration(
                    calculateCourseDuration(response.getLessons())
            );

            response.setNumberOfLessons(
                    response.getLessons() != null ? response.getLessons().size() : 0
            );

            responseList.add(response);
        });

        return responseList;
    }

    @Override
    public long countCourseByInstructorId(String instructorId) {
        return courseRepository.countCourseByInstructorId(instructorId);
    }

    @Override
    public long countPublishedCourseByInstructorId(String instructorId) {
        return courseRepository.countCourseByInstructorIdAndPublished(instructorId, true);
    }

    @Override
    public long countTotalStudentsByInstructorId(String instructorId) {
        return courseRepository.countTotalStudentsByInstructorId(instructorId);
    }

    @Override
    public long countTotalVideosByInstructorId(String instructorId) {
        return courseRepository.countTotalVideosByInstructorId(instructorId);
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

    private String calculateCourseDuration(List<LessonDTO> lessons) {
        if (lessons == null || lessons.isEmpty()) {
            return "0h 0m 0s";
        }

        int totalSeconds = (int)lessons.stream()
                .mapToLong(LessonDTO::getDuration)
                .sum();

        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;

        return hours + "h " + minutes + "m " + seconds + "s";
    }

}
