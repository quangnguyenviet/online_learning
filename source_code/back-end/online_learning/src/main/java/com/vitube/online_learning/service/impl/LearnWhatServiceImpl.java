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

/**
 * Lớp triển khai các phương thức liên quan đến "Learn What".
 */
@Service
@RequiredArgsConstructor
public class LearnWhatServiceImpl implements LearnWhatService {
    private final CourseRepository courseRepository;
    private final LearnWhatRepository learnWhatRepository;

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
        List<LearnWhat> learnWhats = learnWhatRepository.findByCourseId(courseId);

        // Xóa các "Learn What" theo danh sách ID được cung cấp
        learnWhats.forEach(require -> {
            request.getDelIdList().forEach(delId -> {
                learnWhatRepository.deleteById(delId);
            });
        });

        // Lưu hoặc cập nhật các "Learn What" khác
        request.getOtherList().forEach(other -> {
            if (other.get("id") == null) {
                // Tạo mới "Learn What" nếu ID không tồn tại
                LearnWhat learnWhat = new LearnWhat();
                learnWhat.setCourse(course);
                learnWhat.setDescription(other.get("description").toString());
                course.getLearnWhats().add(learnWhat);
                learnWhatRepository.save(learnWhat);
            } else {
                // Cập nhật "Learn What" nếu ID tồn tại
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