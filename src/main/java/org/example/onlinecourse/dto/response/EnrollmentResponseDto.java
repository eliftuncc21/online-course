package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.EnrollmentStatus;
import org.example.onlinecourse.model.Student;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentResponseDto {
    private Long enrollmentId;
    private LocalDateTime enrollmentDate;
    private EnrollmentStatus status;
    private Student student;
    private Course course;
}
