package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CourseRequestDto;
import org.example.onlinecourse.dto.response.CourseResponseDto;
import org.example.onlinecourse.mapper.CourseMapper;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale.Category;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CategoryRepository categoryRepository;
    private final CourseMapper courseMapper;

    public CourseResponseDto save(CourseRequestDto courseRequestDto) {
        Instructor instructor = instructorRepository.findById(courseRequestDto.getInstructorId()).orElse(null);
        Category category = categoryRepository.findById(courseRequestDto.getCategoryId()).orElse(null);
        Course course = courseMapper.toCourse(courseRequestDto);
        course.setInstructor(instructor);
        course.setCategory(category);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toCourseResponseDto(savedCourse);
    }

    public List<CourseResponseDto> getAllCourses() {
        List<Course> courseList = courseRepository.findAll();
        return courseMapper.toCourseResponseDtoList(courseList);
    }

    public CourseResponseDto findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        return courseMapper.toCourseResponseDto(course);
    }

    public void deleteCourseById(long id) {
        courseRepository.deleteById(id);
    }

    public CourseResponseDto updateCourse(CourseRequestDto courseRequestDto, Long id) {
        Course course = courseRepository.findById(id).orElse(null);
        courseMapper.updateCourse(courseRequestDto, course);
        Course updatedCourse = courseRepository.save(course);
        return courseMapper.toCourseResponseDto(updatedCourse);
    }
}
