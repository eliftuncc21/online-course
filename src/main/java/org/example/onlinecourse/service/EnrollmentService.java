package org.example.onlinecourse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.EnrollmentRequestDto;
import org.example.onlinecourse.dto.response.EnrollmentResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.EnrollmentMapper;
import org.example.onlinecourse.model.*;
import org.example.onlinecourse.repository.CourseRepository;
import org.example.onlinecourse.repository.EnrollmentRepository;
import org.example.onlinecourse.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final MailService mailService;

    @Transactional
    public EnrollmentResponseDto save(EnrollmentRequestDto enrollmentRequestDto) {
        Student student = studentRepository.findById(enrollmentRequestDto.getStudentId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.STUDENT_NOT_FOUND,
                        "Student not found",
                        HttpStatus.NOT_FOUND)
                );

        Course course = courseRepository.findById(enrollmentRequestDto.getCourseId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COURSE_NOT_FOUND,
                        "Course not found",
                        HttpStatus.NOT_FOUND)
                );

        Enrollment enrollment = enrollmentMapper.toEnrollment(enrollmentRequestDto, student, course);

        enrollmentRepository.save(enrollment);
        mailService.sendRegistrationCourse(student.getEmail(), course.getTitle());
        int courseCount = enrollmentRepository.countByStudent(student);
        student.setTotalCourseCount(courseCount);
        studentRepository.save(student);
        return enrollmentMapper.toEnrollmentResponseDto(enrollment);
    }

    public EnrollmentResponseDto getEnrollmentById(long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(()-> new ErrorMessage(
                        MessageType.ENROLLMENT_NOT_FOUND,
                        "Enrollment not found",
                        HttpStatus.NOT_FOUND
                ));
        return enrollmentMapper.toEnrollmentResponseDto(enrollment);
    }

    public Page<EnrollmentResponseDto> getAllEnrollments(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return enrollmentRepository.findAll(pageable).map(enrollmentMapper::toEnrollmentResponseDto);
    }

    @Transactional
    public EnrollmentResponseDto updateEnrollment(long id) {
        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(()-> new ErrorMessage(
                        MessageType.ENROLLMENT_NOT_FOUND,
                        "Enrollment not found",
                        HttpStatus.NOT_FOUND
                ));
        Enrollment updateEnrollment = enrollmentMapper.toUpdateEnrollment(enrollment, enrollment);

        Student student = updateEnrollment.getStudent();
        int courseCount = enrollmentRepository.countByStudentAndEnrollmentStatus(student, EnrollmentStatus.COMPLETED);
        student.setCompletedCourseCount(courseCount);
        studentRepository.save(student);
        enrollmentRepository.save(updateEnrollment);
        return enrollmentMapper.toEnrollmentResponseDto(enrollment);
    }

    public void deleteEnrollment(long id) {
        enrollmentRepository.deleteById(id);
    }
}
