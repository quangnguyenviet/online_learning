//package com.vitube.online_learning.service.impl;
//
//import com.vitube.online_learning.dto.request.OrderRequest;
//import com.vitube.online_learning.entity.Course;
//import com.vitube.online_learning.entity.Order;
//import com.vitube.online_learning.entity.User;
//import com.vitube.online_learning.repository.CourseRepository;
//import com.vitube.online_learning.repository.OrderRepository;
//import com.vitube.online_learning.repository.UserRepository;
//import com.vitube.online_learning.service.OrderService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class OrderServiceImpl implements OrderService {
//    private final OrderRepository orderRepository;
//    private final UserRepository userRepository;
//    private final CourseRepository courseRepository;
//
//    @Override
//    public Object createOrder(OrderRequest request) {
//        User student = userRepository.findById(request.getStudentId()).get();
//        Course course = courseRepository.findById(request.getCourseId()).get();
//        Order order = new Order();
//        order.setStudent(student);
//        order.setCourse(course);
//        order.setStatus("Processing");
//        orderRepository.save(order);
//
//        return order;
//    }
//}
