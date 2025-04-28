package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CourseRequestDto;
import org.example.onlinecourse.dto.response.CourseResponseDto;
import org.example.onlinecourse.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/course")
public class CourseController {
    private final CourseService courseService;

    @PostMapping("/save-course")
    public CourseResponseDto saveCourse(@RequestBody CourseRequestDto courseRequestDto) {
        return courseService.save(courseRequestDto);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/list-course")
    public List<CourseResponseDto> listCourse() {
        return courseService.getAllCourses();
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/list-course/{id}")
    public CourseResponseDto listCourseById(@PathVariable Long id) {
        return courseService.findCourseById(id);
    }

    @DeleteMapping("/delete-course/{id}")
    public void deleteCourseById(@PathVariable Long id) {
        courseService.deleteCourseById(id);
    }

    @PutMapping("/update-course/{id}")
    public CourseResponseDto updateCourseById(@PathVariable Long id, @RequestBody CourseRequestDto courseRequestDto) {
        return courseService.updateCourse(courseRequestDto, id);
    }
}
