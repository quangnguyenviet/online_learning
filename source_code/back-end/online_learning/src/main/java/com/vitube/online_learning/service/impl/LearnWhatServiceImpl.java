package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.SaveLearnWhatRequest;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Objective;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.ObjectiveRepository;
import com.vitube.online_learning.service.ObjectiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Lớp triển khai các phương thức liên quan đến "Learn What".
 */
@Service
@RequiredArgsConstructor
public class LearnWhatServiceImpl implements ObjectiveService {
    private final CourseRepository courseRepository;
    private final ObjectiveRepository objectiveRepository;

    /**
     * Lưu thông tin "Learn What" cho một khóa học.
     *
     * @param request Yêu cầu lưu thông tin "Learn What".
     * @param courseId ID của khóa học.
     * @return Đối tượng phản hồi (null).
     */
    @Override
    public Object saveLearnWhat(SaveLearnWhatRequest request, String courseId) {
        // Tìm khóa học theo ID, nếu không tìm thấy thì ném ngoại lệ
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        // Lấy danh sách "Learn What" của khóa học
        List<Objective> learnWhats = objectiveRepository.findByCourseId(courseId);

        // Xóa các "Learn What" theo danh sách ID được cung cấp
        learnWhats.forEach(require -> {
            request.getDelIdList().forEach(delId -> {
                objectiveRepository.deleteById(delId);
            });
        });

        // Lưu hoặc cập nhật các "Learn What" khác
        request.getOtherList().forEach(other -> {
            if (other.get("id") == null) {
                // Tạo mới "Learn What" nếu ID không tồn tại
                Objective objective = new Objective();
                objective.setCourse(course);
                objective.setDescription(other.get("description").toString());
                course.getObjectives().add(objective);
                objectiveRepository.save(objective);
            } else {
                // Cập nhật "Learn What" nếu ID tồn tại
                Objective objective = objectiveRepository
                        .findById(other.get("id").toString())
                        .orElseThrow(() -> new RuntimeException("Require not found"));
                objective.setDescription(other.get("description").toString());
                objectiveRepository.save(objective);
            }
        });

        return null;
    }
}