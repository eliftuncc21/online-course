package org.example.onlinecourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.InstructorFilterRequest;
import org.example.onlinecourse.dto.request.InstructorRequestDto;
import org.example.onlinecourse.dto.response.InstructorResponseDto;
import org.example.onlinecourse.service.InstructorService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping("/save-instructor")
    public InstructorResponseDto saveInstructor(@RequestBody @Valid InstructorRequestDto instructorRequestDto) {
        return instructorService.addInstructors(instructorRequestDto);
    }

    @GetMapping("/list-instructor")
    public Page<InstructorResponseDto> listInstructors(InstructorFilterRequest filterRequest,@RequestParam int page, @RequestParam int size) {
        return instructorService.listInstructors(filterRequest, page, size);
    }

    @GetMapping("/list-instructor/{id}")
    public InstructorResponseDto getInstructorById(@PathVariable long id) {
        return instructorService.findInstructorsById(id);
    }

    @DeleteMapping("/delete-instructor/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public void deleteInstructor(@PathVariable long id) {
        instructorService.deleteInstructor(id);
    }

    @PutMapping("/update-instructor/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public InstructorResponseDto updateInstructor(@RequestBody @Valid InstructorRequestDto instructorRequestDto, @PathVariable long id) {
        return instructorService.updateInstructor(id, instructorRequestDto);
    }
}
