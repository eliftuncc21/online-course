package org.example.onlinecourse.repository;

import org.example.onlinecourse.model.FavoriteCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoriteCourseRepository extends JpaRepository<FavoriteCourse, Long> {
    Optional<FavoriteCourse> findByStudentUserIdAndCourseCourseId(Long studentId, Long courseId);
}