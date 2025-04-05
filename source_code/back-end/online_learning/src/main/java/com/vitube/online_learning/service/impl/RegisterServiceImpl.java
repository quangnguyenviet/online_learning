package com.vitube.online_learning.service.impl;

import com.vitube.online_learning.dto.request.RegisterRequest;
import com.vitube.online_learning.entity.Course;
import com.vitube.online_learning.entity.Register;
import com.vitube.online_learning.entity.User;
import com.vitube.online_learning.repository.CourseRepository;
import com.vitube.online_learning.repository.RegisterRepository;
import com.vitube.online_learning.repository.UserRepository;
import com.vitube.online_learning.service.RegisterService;
import com.vitube.online_learning.service.SecurityContextService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository userRepository;
    private final RegisterRepository registerRepository;
    private final CourseRepository courseRepository;
    private final SecurityContextService securityContextService;

    @Override
    public void registerCourse(RegisterRequest request) {
        User student = securityContextService.getUser();
        Course course = courseRepository.findById(request.getCourseId()).get();
        Register register = new Register();
        register.setCourse(course);
        register.setStudent(student);
        registerRepository.save(register);

        student.getRegisters().add(register);
        course.getRegisters().add(register);
        userRepository.save(student);
        courseRepository.save(course);

    }
}
