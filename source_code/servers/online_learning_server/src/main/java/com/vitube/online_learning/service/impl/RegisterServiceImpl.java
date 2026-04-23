package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.RegisterDTO;
import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.enums.ErrorCode;
import com.vitube.online_learning.exception.AppException;
import com.vitube.online_learning.mapper.RegisterMapper;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.RegisterRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.RegisterService;
import com.vitube.online_learning.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Lớp triển khai các phương thức liên quan đến đăng ký khóa học.
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final RegisterMapper registerMapper;

    /**
     * Chuyển đổi đối tượng RegisterRequest thành Register.
     *
     * @param request Yêu cầu đăng ký.
     * @return Đối tượng đăng ký.
     */
    @Override
    public Register toEntity(RegisterRequest request) {
        Register register = registerMapper.toEntity(request);

        // Lấy thông tin sinh viên từ yêu cầu hoặc từ ngữ cảnh bảo mật
        User student;
        if (request.getStudentId() != null) {
            student = userRepository.findById(request.getStudentId()).get();
        } else {
            student = userService.getCurrentUser();
        }

        // Lấy thông tin khóa học từ yêu cầu
        Course course = courseRepository.findById(request.getCourseId()).get();

        register.setCourse(course);
        register.setStudent(student);

        return register;
    }

    /**
     * Tạo dữ liệu đăng ký khóa học.
     *
     * @param request Yêu cầu đăng ký.
     */
    @Override
    public void createRegisterData(RegisterDTO request) {
        User student = userRepository.findByEmail(request.getStudentEmail())
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_EXIST)
                );
        // Lấy thông tin khóa học từ yêu cầu
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(
                        () -> new AppException(ErrorCode.NOT_FOUND)
                );

        // Chuyển đổi yêu cầu thành đối tượng đăng ký
        Register register = Register.builder()
                .price(request.getPrice())
                .course(course)
                .student(student)
                .registerDate(LocalDate.now())
                .build();

        // Lưu thông tin đăng ký vào cơ sở dữ liệu
        registerRepository.save(register);

        // Cập nhật danh sách đăng ký của sinh viên và khóa học
//        student.getRegisters().add(register);
//        course.getRegisters().add(register);
//        userRepository.save(student);
//        courseRepository.save(course);
    }

    /**
     * Kiểm tra xem sinh viên đã đăng ký khóa học hay chưa.
     *
     * @param courseId ID của khóa học.
     * @return true nếu đã đăng ký, false nếu chưa đăng ký.
     */
    @Override
    public boolean isRegistered(String courseId) {
        // Lấy thông tin sinh viên từ ngữ cảnh bảo mật
        User student = userService.getCurrentUser();

        // Kiểm tra danh sách đăng ký của sinh viên
        for (Register register : student.getRegisters()) {
            if (register.getCourse().getId().equals(courseId)) {
                return true; // Đã đăng ký khóa học
            }
        }

        return false; // Chưa đăng ký khóa học
    }
}