package com.vitube.online_learning.repository.projection;

import java.math.BigDecimal;

public interface InstructorCourseP {
    String getId();
    String getTitle();
    Double getPrice();
    Integer getDiscount();
    String getImageUrl();
    Boolean getPublished();
    String getCategoryName();
    Integer getNumberOfLessons();
    Long getTotalDurationInSeconds();
    java.time.LocalDateTime getCreatedAt();
    java.time.LocalDateTime getUpdatedAt();
    int getTotalRegistrations();
    BigDecimal getTotalEarnings();
}
