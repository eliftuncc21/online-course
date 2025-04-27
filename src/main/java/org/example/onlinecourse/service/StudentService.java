package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.StudentRequestDto;
import org.example.onlinecourse.dto.response.StudentResponseDto;
import org.example.onlinecourse.mapper.StudentMapper;
import org.example.onlinecourse.model.Student;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.StudentRepository;
import org.example.onlinecourse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final StudentMapper studentMapper;

    public StudentResponseDto addStudent(StudentRequestDto studentRequestDto) {
        User user = userRepository.findById(studentRequestDto.getUserId()).orElse(null);
        Student student = studentMapper.toStudent(studentRequestDto);
        student.setUser(user);
        Student dbStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDto(dbStudent);
    }

    public List<StudentResponseDto> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return studentMapper.toStudentResponseDtoList(students);
    }

    public StudentResponseDto findStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return studentMapper.toStudentResponseDto(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        studentRepository.delete(student);
    }

    public StudentResponseDto updateStudent(StudentRequestDto studentRequestDto, Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        studentMapper.update(studentRequestDto, student);

        Student dbStudent = studentRepository.save(student);
        return studentMapper.toStudentResponseDto(dbStudent);
    }
}
