package com.example.notification.service;

import com.example.notification.event.EnrollmentEvent;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendEnrollmentEmail(EnrollmentEvent event) {
        log.info("Preparing to send enrollment email to: {}", event.getStudentEmail());
        
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, 
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, 
                    StandardCharsets.UTF_8.name());

            String content = String.format(
                "Chào bạn,\n\nBạn đã đăng ký thành công khóa học: %s.\nNgày đăng ký: %s\nHọc phí: %s\n\nChúc bạn học tập tốt!",
                event.getCourseTitle(),
                event.getEnrollmentDate(),
                event.getPrice()
            );

            helper.setTo(event.getStudentEmail());
            helper.setSubject("Đăng ký khóa học thành công - Vitube");
            helper.setText(content, false);

            mailSender.send(message);
            log.info("Successfully sent enrollment email to: {}", event.getStudentEmail());
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", event.getStudentEmail(), e.getMessage());
        }
    }
}
