package com.vitube.online_learning.repository.projection;

import java.time.LocalDate;

public interface RegistrationStats {
    LocalDate getRegistrationDay();
    Long getTotalRegistrations();
}

