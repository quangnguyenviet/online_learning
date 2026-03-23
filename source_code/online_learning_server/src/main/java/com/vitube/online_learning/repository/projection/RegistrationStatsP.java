package com.vitube.online_learning.repository.projection;

import java.time.LocalDate;

public interface RegistrationStatsP {
    LocalDate getRegistrationDay();
    Long getTotalRegistrations();
}

