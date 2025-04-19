package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.CourseRequest;
import com.vitube.online_learning.dto.response.CourseResponse;
import com.vitube.online_learning.dto.response.LearnWhatResponse;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.mapper.CousreMapper;
import com.vitube.online_learning.mapper.LearnWhatMapper;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.CourseService;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public CourseResponse courseToCourseResponse(Course course) {
        CourseResponse response = cousreMapper.courseToCourseResponse(course);

        List<LessonResponse> lessonResponses = new ArrayList<>();
        course.getLessons().forEach(lesson -> {
            lessonResponses.add(
                    lessonService.lessonToLessonResponse(lesson)
            );}
        );
        response.setLessons(lessonResponses);

        List<LearnWhatResponse> learnWhatResponses = new ArrayList<>();
        course.getLearnWhats().forEach(learnWhat -> {
            learnWhatResponses.add(
                    learnWhatMapper.learnWhatTooLearnWhatResponse(learnWhat)
            );
        });
        response.setLearnWhats(learnWhatResponses);

        List<RequireResponse> requireResponses = new ArrayList<>();
        course.getRequires().forEach(require -> {
            requireResponses.add(
                    requireMapper.requireToRequireResponse(require)
            );
        });
        response.setRequires(requireResponses);

        response.setInstructorId(course.getInstructor().getId());
        return response;

    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = Course.builder()
                .title(request.getTitle())
                .instructor(instructor)
                .price(request.getPrice())
                .build();

        Course saved = courseRepository.save(course);

        CourseResponse courseResponse = cousreMapper.courseToCourseResponse(course);
        courseResponse.setInstructorId(instructor.getId());

        return courseResponse;
    }

    @Override
    public CourseResponse getCourseById(String id) {
        Course course = courseRepository.findById(id).get();

        CourseResponse response = courseToCourseResponse(course);
        return response;
    }

    @Override
    public CourseResponse updateCourse(String id, CourseRequest request) {
        return null;
    }

    @Override
    public void deleteCourse(String id) {

    }

    @Override
    public List<CourseResponse> getAllCourse() {
        List<CourseResponse> responseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            User instructor = course.getInstructor();
            responseList.add(CourseResponse.builder()
                   .id(course.getId())
                   .title(course.getTitle())
                   .instructorId(instructor.getId())
                   .price(course.getPrice())
                   .build());
        });
        return responseList;
    }

    @Override
    public List<CourseResponse> getFreeCourse() {
        List<CourseResponse> responseList = new ArrayList<>();
        courseRepository.findAll().forEach(course -> {
            if (course.getPrice() == 0 || course.getNewPrice() == 0){
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
            if (course.getPrice() != 0 && course.getNewPrice() != 0){
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
        User instructor = userRepository.findById(instructorId)
                .orElseThrow(() -> new RuntimeException("Instructor not found"));
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
            CourseResponse response = cousreMapper.courseToCourseResponse(course);
            response.setInstructorId(course.getInstructor().getId());
            responseList.add(response);
        });
        return responseList;

    }
}
