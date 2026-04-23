package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.SaveRequireRequest;
import com.vitube.online_learning.dto.response.RequireResponse;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Require;
import com.vitube.online_learning.mapper.RequireMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.RequireRepository;
import com.vitube.online_learning.service.RequireService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Lớp triển khai các phương thức liên quan đến yêu cầu của khóa học.
 */
@Service
@RequiredArgsConstructor
public class RequireServiceImpl implements RequireService {
    private final RequireMapper requireMapper;
    private final RequireRepository requireRepository;
    private final CourseRepository courseRepository;

    /**
     * Lấy danh sách yêu cầu của một khóa học theo ID.
     *
     * @param courseId ID của khóa học.
     * @return Danh sách phản hồi yêu cầu.
     */
    @Override
    public List<RequireResponse> getAllRequireByCourseId(String courseId) {
        List<Require> requires = requireRepository.findByCourseId(courseId);
        return requires.stream().map(requireMapper::requireToRequireResponse).collect(Collectors.toList());
    }

    /**
     * Lưu thông tin yêu cầu cho một khóa học.
     *
     * @param request Yêu cầu lưu thông tin yêu cầu.
     * @param courseId ID của khóa học.
     * @return Đối tượng phản hồi yêu cầu (null).
     */
    @Override
    public RequireResponse saveRequire(SaveRequireRequest request, String courseId) {
        // Tìm khóa học theo ID, nếu không tìm thấy thì ném ngoại lệ
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

        // Lấy danh sách yêu cầu của khóa học
        List<Require> requires = requireRepository.findByCourseId(courseId);

        // Xóa các yêu cầu theo danh sách ID được cung cấp
        requires.forEach(require -> {
            request.getDelIdList().forEach(delId -> {
                requireRepository.deleteById(delId);
            });
        });

        // Lưu hoặc cập nhật các yêu cầu khác
        request.getOtherList().forEach(other -> {
            if (other.get("id") == null) {
                // Tạo mới yêu cầu nếu ID không tồn tại
                Require require = new Require();
                require.setCourse(course);
                require.setDescription(other.get("description").toString());
                course.getRequires().add(require);
                requireRepository.save(require);
            } else {
                // Cập nhật yêu cầu nếu ID tồn tại
                Require require = requireRepository
                        .findById(other.get("id").toString())
                        .orElseThrow(() -> new RuntimeException("Require not found"));
                require.setDescription(other.get("description").toString());
                requireRepository.save(require);
            }
        });

        return null;
    }
}