package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.EnrollmentRequestDto;
import org.example.onlinecourse.dto.response.EnrollmentResponseDto;
import org.example.onlinecourse.mapper.EnrollmentMapper;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.Enrollment;
import org.example.onlinecourse.model.EnrollmentStatus;
import org.example.onlinecourse.model.Student;
import org.example.onlinecourse.repository.CourseRepository;
import org.example.onlinecourse.repository.EnrollmentRepository;
import org.example.onlinecourse.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public EnrollmentResponseDto save(EnrollmentRequestDto enrollmentRequestDto) {
        Student student = studentRepository.findById(enrollmentRequestDto.getStudentId()).orElse(null);
        Course course = courseRepository.findById(enrollmentRequestDto.getCourseId()).orElse(null);
        Enrollment enrollment = enrollmentMapper.toEnrollment(enrollmentRequestDto);
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrollmentStatus(EnrollmentStatus.ENROLLED);
        Enrollment dbEnrollment = enrollmentRepository.save(enrollment);

        return enrollmentMapper.toEnrollmentResponseDto(dbEnrollment);
    }

    public EnrollmentResponseDto getEnrollmentById(long id) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);
        return enrollmentMapper.toEnrollmentResponseDto(enrollment);
    }

    public List<EnrollmentResponseDto> getAllEnrollments() {
        List<Enrollment> enrollments = enrollmentRepository.findAll();
        return enrollmentMapper.toEnrollmentResponseDtoList(enrollments);
    }

    public EnrollmentResponseDto updateEnrollment(long id, EnrollmentRequestDto enrollmentRequestDto) {
        Enrollment enrollment = enrollmentRepository.findById(id).orElse(null);
        enrollmentMapper.updateEnrollment(enrollmentRequestDto, enrollment);
        enrollment.setEnrollmentStatus(EnrollmentStatus.COMPLETED);
        Enrollment dbEnrollment = enrollmentRepository.save(enrollment);
        return enrollmentMapper.toEnrollmentResponseDto(dbEnrollment);
    }

    public void deleteEnrollment(long id) {
        enrollmentRepository.deleteById(id);
    }
}
