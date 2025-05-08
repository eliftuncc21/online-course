package org.example.onlinecourse.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CourseFilterRequest;
import org.example.onlinecourse.dto.request.CourseRequestDto;
import org.example.onlinecourse.dto.response.CourseResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.CourseMapper;
import org.example.onlinecourse.model.*;
import org.example.onlinecourse.repository.CourseRepository;
import org.example.onlinecourse.repository.InstructorRepository;
import org.example.onlinecourse.repository.SubCategoryRepository;
import org.example.onlinecourse.specification.CourseSpecification;
import org.example.onlinecourse.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final SecurityUtil securityUtil;
    private final CourseMapper courseMapper;

    @Transactional
    public CourseResponseDto save(CourseRequestDto courseRequestDto) {
        User user = securityUtil.getCurrentUser();

        Instructor instructor = instructorRepository.findById(user.getUserId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.INSTRUCTOR_NOT_FOUND,
                        "Instructor not found",
                        HttpStatus.NOT_FOUND)
                );


        SubCategory category = subCategoryRepository.findById(courseRequestDto.getSubCategoryId())
                .orElseThrow(()->new ErrorMessage(
                        MessageType.CATEGORY_NOT_FOUND,
                        "Category not found",
                        HttpStatus.NOT_FOUND
                ));

        Course course = courseMapper.toCourse(courseRequestDto, instructor, category);
        courseRepository.save(course);

        int courseCount = courseRepository.countByInstructor(instructor);
        instructor.setTotalCourseCount(courseCount);

        instructorRepository.save(instructor);

        return courseMapper.toCourseResponseDto(course);
    }

    public Page<CourseResponseDto> getAllCourses(CourseFilterRequest filter, int page, int size) {
        Specification<Course> spec = CourseSpecification.getCourseSpecification(filter);
        Pageable pageable = PageRequest.of(page, size);
        return courseRepository.findAll(spec, pageable).map(courseMapper::toCourseResponseDto);
    }

    public CourseResponseDto findCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COURSE_NOT_FOUND,
                        "Course not found",
                        HttpStatus.NOT_FOUND)
                );
        return courseMapper.toCourseResponseDto(course);
    }

    @Transactional
    public void deleteCourseById(long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COURSE_NOT_FOUND,
                        "Course not found",
                        HttpStatus.NOT_FOUND)
                );

        Instructor instructor = course.getInstructor();

        getCourse(course);
        courseRepository.delete(course);
        int courseCount = courseRepository.countByInstructor(instructor);
        instructor.setTotalCourseCount(courseCount);

        instructorRepository.save(instructor);
    }

    public CourseResponseDto updateCourse(CourseRequestDto courseRequestDto, Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COURSE_NOT_FOUND,
                        "Course not found",
                        HttpStatus.NOT_FOUND)
                );

        getCourse(course);
        courseMapper.updateCourse(courseRequestDto, course);
        courseRepository.save(course);

        return courseMapper.toCourseResponseDto(course);
    }

    private void getCourse(Course course) {
        User currentUser = securityUtil.getCurrentUser();

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isInstructor = currentUser.getUserId().equals(course.getInstructor().getUserId());

        if(!(isAdmin || isInstructor)) {
            throw new ErrorMessage(
                    MessageType.NO_PERMISSION_COURSE,
                    "No Permission",
                    HttpStatus.FORBIDDEN
            );
        }
    }
}
