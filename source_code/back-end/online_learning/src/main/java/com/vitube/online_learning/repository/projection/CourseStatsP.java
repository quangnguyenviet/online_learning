package com.vitube.online_learning.repository.projection;

import java.math.BigDecimal;

public interface CourseStatsP {
    String getId();
    String getTitle();
    Integer getTotalRegistrations();
    BigDecimal getTotalEarnings();
    Long getTotalDurationInSeconds();
    Boolean getPublished();
}
