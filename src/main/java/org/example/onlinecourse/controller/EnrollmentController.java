package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.EnrollmentRequestDto;
import org.example.onlinecourse.dto.response.EnrollmentResponseDto;
import org.example.onlinecourse.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/save-enrollment")
    public EnrollmentResponseDto addEnrollment(EnrollmentRequestDto enrollmentRequestDto){
        return enrollmentService.save(enrollmentRequestDto);
    }

    @GetMapping("/list-enrollment")
    public List<EnrollmentResponseDto> listEnrollment(){
        return enrollmentService.getAllEnrollments();
    }

    @GetMapping("/list-enrollment/{id}")
    public EnrollmentResponseDto listEnrollmentById(@PathVariable Long id){
        return enrollmentService.getEnrollmentById(id);
    }

    @DeleteMapping("/delete-enrollment/{id}")
    public void deleteEnrollment(@PathVariable Long id){
        enrollmentService.deleteEnrollment(id);
    }

    @PutMapping("/update-enrollment/{id}")
    public EnrollmentResponseDto updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentRequestDto requestDto){
        return enrollmentService.updateEnrollment(id, requestDto);
    }

}
