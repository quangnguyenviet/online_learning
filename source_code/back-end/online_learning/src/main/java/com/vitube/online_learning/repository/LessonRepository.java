package com.vitube.online_learning.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vitube.online_learning.entity.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, String> {}
