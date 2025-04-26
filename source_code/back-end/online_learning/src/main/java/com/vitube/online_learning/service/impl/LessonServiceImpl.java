package com.vitube.online_learning.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.vitube.online_learning.dto.request.LessonRequest;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Lesson;
import com.vitube.online_learning.mapper.LessonMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.LessonRepository;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
//    private final Cloudinary cloudinary;
    private final CourseRepository courseRepository;
    private final S3Service s3Service;
    private final LessonMapper lessonMapper;

//    @Override
//    public LessonResponse createLesson(LessonRequest request) throws IOException {
//        MultipartFile file = request.getFile();
//        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
//                "resource_type", "video"
//        ));
//
//        String videoUrl = (String) uploadResult.get("secure_url");
//
//        Lesson lesson = Lesson.builder()
//                .title(request.getTitle())
//                .videoUrl(videoUrl)
//                .course(courseRepository.findById(request.getCourseId()).get())
//                .build();
//         lessonRepository.save(lesson);
//
//        return LessonResponse.builder()
//                .videoUrl(videoUrl)
//                .build();
//
//    }
    public static String generateKey() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return now.format(formatter);
    }

    @Override
    public LessonResponse createLessonS3(LessonRequest request) throws IOException {
        String key = generateKey();

        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .course(courseRepository.findById(request.getCourseId()).get())
                .lessonKey(key)
                .build();


        String videoUrl = s3Service.uploadFile(request.getFile(), key);
        lesson.setVideoUrl(videoUrl);
        lessonRepository.save(lesson);
        return null;

    }

    @Override
    public List<LessonResponse> getLessonOfCourse(String courseId) {
        List<LessonResponse> responses = new ArrayList<>();

        Course course = courseRepository.findById(courseId).get();
        course.getLessons().forEach(lesson -> {
            responses.add(lessonMapper.lessonToLessonResponse(lesson));
        });
        return responses;
    }

    @Override
    public Void deleteLesson(String lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId).get();
        lessonRepository.delete(lesson);
        return null;
    }

    @Override
    public Void updateLesson(String lessonId, LessonRequest request) throws IOException {
        String key = generateKey();

        Lesson lesson = lessonRepository.findById(lessonId).get();
        lesson.setTitle(request.getTitle());
        lesson.setDescription(request.getDescription());

        if (request.getFile() != null) {
            String videoUrl = s3Service.uploadFile(request.getFile(), key);
            lesson.setVideoUrl(videoUrl);

            String oldKey = lesson.getLessonKey();
            s3Service.deleteFile(oldKey);
            lesson.setLessonKey(key);
        }


        lessonRepository.save(lesson);

        return null;
    }


    public LessonResponse lessonToLessonResponse(Lesson lesson) {
        LessonResponse lessonResponse = lessonMapper.lessonToLessonResponse(lesson);
        return lessonResponse;
    }
}
