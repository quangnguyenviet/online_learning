package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.SaveLearnWhatRequest;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.LearnWhat;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.LearnWhatRepository;
import com.vitube.online_learning.service.LearnWhatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearnWhatServiceImpl implements LearnWhatService {
    private final CourseRepository courseRepository;
    private final LearnWhatRepository learnWhatRepository;

    @Override
    public Object saveLearnWhat(SaveLearnWhatRequest request, String courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));
        List<LearnWhat> learnWhats = learnWhatRepository.findByCourseId(courseId);
        learnWhats.forEach(require -> {
            request.getDelIdList().forEach(delId -> {
                learnWhatRepository.deleteById(delId);
            });
        });
        request.getOtherList().forEach(other -> {
            if (other.get("id") == null) {
                LearnWhat learnWhat = new LearnWhat();
                learnWhat.setCourse(course);
                learnWhat.setDescription(other.get("description").toString());
                course.getLearnWhats().add(learnWhat);
                learnWhatRepository.save(learnWhat);
            } else {
                LearnWhat learnWhat = learnWhatRepository
                        .findById(other.get("id").toString())
                        .orElseThrow(() -> new RuntimeException("Require not found"));
                learnWhat.setDescription(other.get("description").toString());
                learnWhatRepository.save(learnWhat);
            }
        });

        return null;
    }
}
