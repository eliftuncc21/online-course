package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.EnrollmentRequestDto;
import org.example.onlinecourse.dto.response.EnrollmentResponseDto;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.Enrollment;
import org.example.onlinecourse.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = {StudentMapper.class, CourseMapper.class})
public interface EnrollmentMapper {

    @Mapping(target = "enrollmentStatus", constant = "ENROLLED")
    @Mapping(target = "student", expression = "java(student)")
    @Mapping(target = "course", expression = "java(course)")
    Enrollment toEnrollment(EnrollmentRequestDto enrollmentRequestDto, Student student, Course course);

    @Mapping(target = "enrollmentId", ignore = true)
    @Mapping(target = "enrollmentDate", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "enrollmentStatus", constant = "COMPLETED")
    Enrollment toUpdateEnrollment(@MappingTarget Enrollment updateEnrollment, Enrollment enrollment);

    EnrollmentResponseDto toEnrollmentResponseDto(Enrollment enrollment);
}
