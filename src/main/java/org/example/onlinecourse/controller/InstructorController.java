package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.InstructorRequestDto;
import org.example.onlinecourse.dto.response.InstructorResponseDto;
import org.example.onlinecourse.service.InstructorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/instructor")
public class InstructorController {
    private final InstructorService instructorService;

    @PostMapping("/save-instructor")
    public InstructorResponseDto saveInstructor(@RequestBody InstructorRequestDto instructorRequestDto) {
        return instructorService.addInstructors(instructorRequestDto);
    }

    @GetMapping("/list-instructor")
    public List<InstructorResponseDto> listInstructors() {
        return instructorService.listInstructors();
    }

    @GetMapping("/list-instructor/{id}")
    public InstructorResponseDto getInstructorById(@PathVariable long id) {
        return instructorService.findInstructorsById(id);
    }

    @DeleteMapping("/delete-instructor/{id}")
    public void deleteInstructor(@PathVariable long id) {
        instructorService.deleteInstructor(id);
    }

    @PutMapping("/update-instructor/{id}")
    public InstructorResponseDto updateInstructor(@RequestBody InstructorRequestDto instructorRequestDto, @PathVariable long id) {
        return instructorService.updateInstructor(id, instructorRequestDto);
    }
}
