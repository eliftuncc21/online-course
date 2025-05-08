package org.example.onlinecourse.repository;

import org.example.onlinecourse.model.Enrollment;
import org.example.onlinecourse.model.EnrollmentStatus;
import org.example.onlinecourse.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    int countByStudent(Student student);
    int countByStudentAndEnrollmentStatus(Student student, EnrollmentStatus enrollmentStatus);
}
