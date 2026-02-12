package com.vitube.online_learning.repository;

import com.vitube.online_learning.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, String> {}
