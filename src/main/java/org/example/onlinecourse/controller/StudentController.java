package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.StudentFilterRequest;
import org.example.onlinecourse.dto.request.StudentRequestDto;
import org.example.onlinecourse.dto.response.StudentResponseDto;
import org.example.onlinecourse.service.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping("/save-student")
    public StudentResponseDto saveStudent(@RequestBody StudentRequestDto studentRequestDto) {
        return studentService.addStudent(studentRequestDto);
    }

    @GetMapping("/list-student")
    public Page<StudentResponseDto> findAllStudent(StudentFilterRequest filterRequest,
                                                   @RequestParam int page,
                                                   @RequestParam int size,
                                                   @RequestParam(defaultValue = "userId") String sortField,
                                                   @RequestParam(defaultValue = "asc") String sortDir) {
        return studentService.getAllStudents(filterRequest, page, size, sortField, sortDir);
    }

    @GetMapping("/list-student/{id}")
    public StudentResponseDto findStudentById(@PathVariable Long id) {
        return studentService.findStudentById(id);
    }

    @DeleteMapping("/delete-student")
    public void deleteStudent(@RequestParam long id) {
        studentService.deleteStudent(id);
    }

    @PutMapping("/update-student/{id}")
    public StudentResponseDto updateStudent(@RequestBody StudentRequestDto studentRequestDto, @PathVariable long id) {
        return studentService.updateStudent(studentRequestDto, id);
    }
}