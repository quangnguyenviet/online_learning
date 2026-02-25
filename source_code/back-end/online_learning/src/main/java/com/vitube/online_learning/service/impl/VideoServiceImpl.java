package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.entity.Lesson;
import com.vitube.online_learning.enums.LessonStatusEnum;
import com.vitube.online_learning.repository.LessonRepository;
import com.vitube.online_learning.service.S3Service;
import com.vitube.online_learning.service.VideoService;
import com.vitube.online_learning.utils.FileUtil;
import com.vitube.online_learning.utils.VideoUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final LessonRepository lessonRepository;
    private final VideoUtil videoUtil;
    private final S3Service s3Service;

    @Override
    @Async
    @Transactional
    public void processVideo(String lessonId, String filePath) {
        try {
            Lesson lesson = lessonRepository.findById(lessonId)
                    .orElseThrow(() -> new RuntimeException("Lesson not found"));

            File file = new File(filePath);

            // Lấy duration trước
            long duration = videoUtil.getVideoDuration(file);

            // Upload sau khi chắc chắn ok
            String key = FileUtil.generateFileName(file.getName());
            String videoUrl = s3Service.uploadPrivate(file, key);

            lesson.setVideoUrl(videoUrl);
            lesson.setDuration(duration);
            lesson.setStatus(LessonStatusEnum.VIDEO_UPLOADED.name());

            lessonRepository.save(lesson);

            file.delete();

        } catch (Exception e) {
            // Nếu fail → cập nhật trạng thái FAIL
            lessonRepository.updateStatus(lessonId, LessonStatusEnum.FAILED.name());
        }
    }
}
