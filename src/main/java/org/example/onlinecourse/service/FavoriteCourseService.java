package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.FavoriteCourseRequestDto;
import org.example.onlinecourse.dto.response.FavoriteCourseResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.FavoriteCourseMapper;
import org.example.onlinecourse.model.*;
import org.example.onlinecourse.repository.CourseRepository;
import org.example.onlinecourse.repository.FavoriteCourseRepository;
import org.example.onlinecourse.repository.StudentRepository;
import org.example.onlinecourse.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FavoriteCourseService {
    private final FavoriteCourseRepository favoriteCourseRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final FavoriteCourseMapper favoriteCourseMapper;
    private final SecurityUtil securityUtil;

    public FavoriteCourseResponseDto addFavoriteCourse(FavoriteCourseRequestDto dto) {
        User currentUser = securityUtil.getCurrentUser();

        Student student = studentRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.STUDENT_NOT_FOUND,
                        "Student not found",
                        HttpStatus.NOT_FOUND)
                );

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COURSE_NOT_FOUND,
                        "Course not found",
                        HttpStatus.NOT_FOUND)
                );

        boolean alreadyExists  = favoriteCourseRepository.findByStudentUserIdAndCourseCourseId(
                student.getUserId(), course.getCourseId()).isPresent();

        if(alreadyExists ) {
            throw new ErrorMessage(
                    MessageType.COURSE_ALREADY_IN_FAVORITES,
                    "Course Already in Favorites",
                    HttpStatus.BAD_REQUEST
            );
        }

        FavoriteCourse favoriteCourse = favoriteCourseMapper.toFavoriteCourse(dto,student,course);

        favoriteCourseRepository.save(favoriteCourse);

        return favoriteCourseMapper.toFavoriteCourseResponseDto(favoriteCourse);
    }

    public Page<FavoriteCourseResponseDto> getFavoriteCourses(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return favoriteCourseRepository.findAll(pageable).map(favoriteCourseMapper::toFavoriteCourseResponseDto);
    }

    public void deleteFavoriteCourse(long courseId) {
        User currentUser = securityUtil.getCurrentUser();
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COURSE_NOT_FOUND,
                        "Course not found",
                        HttpStatus.NOT_FOUND)
                );

        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isStudent = course.getEnrollments().stream().anyMatch(enrollment ->
                        enrollment.getStudent().getUserId().equals(currentUser.getUserId()));

        if(!(isAdmin || isStudent)) {
            throw new ErrorMessage(
                    MessageType.NO_OPERATION_PERMISSION,
                    "No Permission",
                    HttpStatus.FORBIDDEN
            );
        }
        favoriteCourseRepository.deleteById(courseId);
    }

}
