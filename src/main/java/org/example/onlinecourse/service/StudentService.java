package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.StudentFilterRequest;
import org.example.onlinecourse.dto.request.StudentRequestDto;
import org.example.onlinecourse.dto.response.StudentResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.StudentMapper;
import org.example.onlinecourse.model.Role;
import org.example.onlinecourse.model.Student;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.StudentRepository;
import org.example.onlinecourse.specification.StudentSpecification;
import org.example.onlinecourse.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final StudentMapper studentMapper;
    private final SecurityUtil securityUtil;
    private final MailService mailService;

    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) {
        if(studentRequestDto.getRole() != Role.STUDENT){
            throw new ErrorMessage(
                    MessageType.INVALID_ROLE_STUDENT,
                    "Invalid Role",
                    HttpStatus.BAD_REQUEST
            );
        }

        Student student = studentMapper.toStudent(studentRequestDto, passwordEncoder);
        studentRepository.save(student);
        mailService.sendRegistrationMail(studentRequestDto.getEmail(), studentRequestDto.getFirstName());

        return studentMapper.toStudentResponseDto(student);
    }

    public Page<StudentResponseDto> getAllStudents(StudentFilterRequest filterRequest, int page, int size, String sortField, String sortDir) {
        Specification<Student> spec = StudentSpecification.getStudentSpecification(filterRequest);
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return studentRepository.findAll(spec, pageable).map(studentMapper::toStudentResponseDto);
    }

    public StudentResponseDto findStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.STUDENT_NOT_FOUND,
                        "Student not found",
                        HttpStatus.NOT_FOUND)
                );

        return studentMapper.toStudentResponseDto(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.STUDENT_NOT_FOUND,
                        "Student not found",
                        HttpStatus.NOT_FOUND)
                );

        getStudent(student);
        studentRepository.delete(student);
    }

    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.STUDENT_NOT_FOUND,
                        "Student not found",
                        HttpStatus.NOT_FOUND)
                );

        getStudent(student);

        studentMapper.update(studentRequestDto, student);

        studentRepository.save(student);
        return studentMapper.toStudentResponseDto(student);
    }

    private void getStudent(Student student) {
        User currentUser = securityUtil.getCurrentUser();

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isStudent = currentUser.getUserId().equals(student.getUserId());

        if(!(isAdmin || isStudent)){
            throw new ErrorMessage(
                    MessageType.NO_PERMISSION_COURSE,
                    "No Permission",
                    HttpStatus.FORBIDDEN
            );
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkInactiveStudents(){
        LocalDateTime date = LocalDateTime.now().minusDays(30);

        for(Student student : studentRepository.findAll()){
            if(student.getLastLoginDate() != null && student.getLastLoginDate().isBefore(date)){
                mailService.sendReminderMail(student.getEmail(), student.getFirstName());
            }
        }
    }
}
