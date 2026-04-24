package com.example.notification.event;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EnrollmentEvent {
    String studentEmail;
    String studentName;
    String courseTitle;
    BigDecimal price;
    LocalDate enrollmentDate;
}
