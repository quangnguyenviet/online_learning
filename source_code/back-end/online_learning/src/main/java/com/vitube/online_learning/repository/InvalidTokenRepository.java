package com.vitube.online_learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitube.online_learning.entity.InvalidToken;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {}
