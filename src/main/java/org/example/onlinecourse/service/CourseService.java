package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CourseRequestDto;
import org.example.onlinecourse.dto.response.CourseResponseDto;
import org.example.onlinecourse.mapper.CourseMapper;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseResponseDto save(CourseRequestDto courseRequestDto) {
        Course course = courseMapper.toCourse(courseRequestDto);
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
