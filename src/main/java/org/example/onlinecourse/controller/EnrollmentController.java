package org.example.onlinecourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.EnrollmentRequestDto;
import org.example.onlinecourse.dto.response.EnrollmentResponseDto;
import org.example.onlinecourse.service.EnrollmentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/enrollment")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/save-enrollment")
    public EnrollmentResponseDto addEnrollment(@RequestBody @Valid EnrollmentRequestDto enrollmentRequestDto){
        return enrollmentService.save(enrollmentRequestDto);
    }

    @GetMapping("/list-enrollment")
    public Page<EnrollmentResponseDto> listEnrollment(@RequestParam int page, @RequestParam int size) {
        return enrollmentService.getAllEnrollments(page, size);
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
    public EnrollmentResponseDto updateEnrollment(@PathVariable @Valid Long id){
        return enrollmentService.updateEnrollment(id);
    }

}
