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
import org.springframework.transaction.annotation.Transactional;

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
    // type = 1 get details type = 0 get general
    public CourseResponse courseToCourseResponse(Course course, int type) {
        CourseResponse response = cousreMapper.courseToCourseResponse(course);

        List<LessonResponse> lessonResponses = new ArrayList<>();
        course.getLessons().forEach(lesson -> {
            lessonResponses.add(
                    lessonService.lessonToLessonResponse(lesson)
            );}
        );
        response.setNumber_of_lessons(course.getLessons().size());

        List<LearnWhatResponse> learnWhatResponses = new ArrayList<>();
        course.getLearnWhats().forEach(learnWhat -> {
            learnWhatResponses.add(
                    learnWhatMapper.learnWhatTooLearnWhatResponse(learnWhat)
            );
        });


        List<RequireResponse> requireResponses = new ArrayList<>();
        course.getRequires().forEach(require -> {
            requireResponses.add(
                    requireMapper.requireToRequireResponse(require)
            );
        });


        response.setInstructorId(course.getInstructor().getId());


        if (type == 0){
            return response;
        }
        else{
            response.setLessons(lessonResponses);
            response.setLearnWhats(learnWhatResponses);
            response.setRequires(requireResponses);
        }

        return response;

    }

    @Override
    public CourseResponse createCourse(CourseRequest request) {
        User instructor;
        if (request.getInstructorId() == null) {
            instructor = securityContextService.getUser();
        }
        else{
            instructor = userRepository.findById(request.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor not found"));
        }

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

        CourseResponse response = courseToCourseResponse(course, 1);
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
    public List<CourseResponse> getAllCourse(String key) {
        List<CourseResponse> responseList = new ArrayList<>();
        if (key == null){
            courseRepository.findAll().forEach(course -> {
                CourseResponse response = courseToCourseResponse(course, 0);

            });
            return responseList;
        }
        else if (key.equals("free")){
            courseRepository.findAll().forEach(course -> {
                if (course.getPrice() == 0 || course.getNewPrice() == 0){
                    CourseResponse response = courseToCourseResponse(course, 0);
                    responseList.add(response);
                }
            });
            return responseList;
        }
        else if (key.equals("plus")){
            courseRepository.findAll().forEach(course -> {
                if (course.getPrice() != 0 && course.getNewPrice() != 0){
                    CourseResponse response = courseToCourseResponse(course, 0);
                    responseList.add(response);
                }
            });
            return responseList;
        }
        else{
            return null;
        }
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

    @Override
    public void setPrice(String courseId, Float price) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));
        course.setPrice(price);
        courseRepository.save(course);
    }
}
