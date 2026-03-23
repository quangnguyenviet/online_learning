package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {}
