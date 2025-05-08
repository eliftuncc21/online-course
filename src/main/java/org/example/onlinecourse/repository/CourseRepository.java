package org.example.onlinecourse.repository;

import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    int countByInstructor(Instructor instructor);
}
