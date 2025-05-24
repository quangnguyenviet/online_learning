package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.LessonRequest;
import com.vitube.online_learning.dto.response.LessonResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Lesson;
import com.vitube.online_learning.mapper.LessonMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.LessonRepository;
import com.vitube.online_learning.service.LessonService;
import com.vitube.online_learning.service.S3Service;
import com.vitube.online_learning.utils.VideoUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
//    private final Cloudinary cloudinary;
    private final CourseRepository courseRepository;
    private final S3Service s3Service;
    private final LessonMapper lessonMapper;
    private final VideoUtil videoUtil;

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

        // Upload lên S3
        String videoUrl = s3Service.uploadPrivate(request.getFile(), key);

        File tempFile = File.createTempFile("video", ".mp4");
        request.getFile().transferTo(tempFile); //

// Ensure the file was transferred correctly
        if (!tempFile.exists()) {
            throw new RuntimeException("Temp file transfer failed.");
        }

        System.out.println("Temp file path: " + tempFile.getAbsolutePath());
        System.out.println("File exists: " + tempFile.exists());


// Now get duration
        long durationInSeconds = videoUtil.getVideoDuration(tempFile);





        // Tạo lesson
        Lesson lesson = Lesson.builder()
                .title(request.getTitle())
                .course(courseRepository.findById(request.getCourseId()).orElseThrow())
                .lessonKey(key)
                .videoUrl(videoUrl)
                .duration(durationInSeconds)
                .description(request.getDescription())
                .idx(request.getOrder())
                .build();

        Lesson addedLesson = lessonRepository.save(lesson);

        // Xoá file tạm
        tempFile.delete();

        return lessonToLessonResponse(addedLesson);

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
        Lesson lesson = lessonRepository.findById(lessonId).get();
        if (request.getTag() != null && request.getTag().equals("UPDATE_INDEX")) {
            lesson.setIdx(request.getOrder());
        }
        else{
            String key = generateKey();
            lesson.setTitle(request.getTitle());
            lesson.setDescription(request.getDescription());

            if (request.getFile() != null) {
                String videoUrl = s3Service.uploadPrivate(request.getFile(), key);
                lesson.setVideoUrl(videoUrl);

                String oldKey = lesson.getLessonKey();
                s3Service.deletePrivateFile(oldKey);
                lesson.setLessonKey(key);
            }
        }

        lessonRepository.save(lesson);

        return null;
    }


    public LessonResponse lessonToLessonResponse(Lesson lesson) {
        LessonResponse lessonResponse = lessonMapper.lessonToLessonResponse(lesson);
        lessonResponse.setDurationInSeconds(lesson.getDuration());
        return lessonResponse;
    }
}
