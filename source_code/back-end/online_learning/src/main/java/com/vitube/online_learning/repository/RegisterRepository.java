package com.vitube.online_learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitube.online_learning.entity.Register;

public interface RegisterRepository extends JpaRepository<Register, String> {}
