package com.example.notification.listener;

import com.example.notification.event.EnrollmentEvent;
import com.example.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final EmailService emailService;

    @KafkaListener(topics = "enrollment-topic", groupId = "notification-group")
    public void listen(EnrollmentEvent event) {
        log.info("Received enrollment event for student: {}", event.getStudentEmail());
        try {
            emailService.sendEnrollmentEmail(event);
        } catch (Exception e) {
            log.error("Error processing enrollment event: {}", e.getMessage());
        }
    }
}
