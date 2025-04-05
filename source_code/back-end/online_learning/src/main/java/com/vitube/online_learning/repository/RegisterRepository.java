package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Register;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegisterRepository extends JpaRepository<Register, String> {
}
