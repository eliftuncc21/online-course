package org.example.onlinecourse.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CourseFilterRequest;
import org.example.onlinecourse.dto.request.CourseRequestDto;
import org.example.onlinecourse.dto.response.CourseResponseDto;
import org.example.onlinecourse.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/course")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/save-course")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public CourseResponseDto saveCourse(@RequestBody @Valid CourseRequestDto courseRequestDto) {
        return courseService.save(courseRequestDto);
    }

    @GetMapping("/list-course")
    public Page<CourseResponseDto> listCourse(CourseFilterRequest courseFilterRequest, @RequestParam int page, @RequestParam int size) {
        return courseService.getAllCourses(courseFilterRequest, page, size);
    }

    @GetMapping("/list-course/{id}")
    public CourseResponseDto listCourseById(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }

    @DeleteMapping("/delete-course/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public void deleteCourseById(@PathVariable Long id) {
        courseService.deleteCourseById(id);
    }

    @PutMapping("/update-course/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('INSTRUCTOR')")
    public CourseResponseDto updateCourseById(@PathVariable Long id, @RequestBody @Valid CourseRequestDto courseRequestDto) {
        return courseService.updateCourse(courseRequestDto, id);
    }
}
