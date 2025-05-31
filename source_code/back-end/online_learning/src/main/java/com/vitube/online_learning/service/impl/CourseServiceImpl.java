package com.vitube.online_learning.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import com.vitube.online_learning.service.CourseService;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;
import com.vitube.online_learning.service.SecurityContextService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CousreMapper cousreMapper;
    private final SecurityContextService securityContextService;
    private final LessonService lessonService;
    private final LearnWhatMapper learnWhatMapper;
    private final RequireMapper requireMapper;
    private final S3Service s3Service;

    @Override
    // type = 1 get details type = 0 get general
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
            instructor = securityContextService.getUser();
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

    @Override
    public CourseResponse getCourseById(String id) {
        Course course = courseRepository.findById(id).get();

        CourseResponse response = courseToCourseResponse(course, 1);
        return response;
    }

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

    @Override
    public void deleteCourse(String id) {}

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

    @Override
    public List<CourseResponse> getLearningCourses() {
        List<CourseResponse> responses = new ArrayList<>();

        User user = securityContextService.getUser();
        user.getRegisters().forEach(registration -> {
            CourseResponse response = cousreMapper.courseToCourseResponse(registration.getCourse());
            response.setInstructorId(registration.getCourse().getInstructor().getId());
            responses.add(response);
        });
        return responses;
    }

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

    @Override
    public List<CourseResponse> getMyCourses() {
        User instructor = securityContextService.getUser();
        List<CourseResponse> responseList = new ArrayList<>();
        instructor.getCourses().forEach(course -> {
            CourseResponse response = courseToCourseResponse(course, 1);
            responseList.add(response);
        });
        return responseList;
    }

    @Override
    public void setPrice(String courseId, Float price) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        course.setPrice(price);
        courseRepository.save(course);
    }

    public static String generateKey() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return now.format(formatter);
    }
}
