package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.EnrollmentRequestDto;
import org.example.onlinecourse.dto.response.EnrollmentResponseDto;
import org.example.onlinecourse.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface EnrollmentMapper {
    Enrollment toEnrollment(EnrollmentRequestDto enrollmentRequestDto);

    EnrollmentResponseDto toEnrollmentResponseDto(Enrollment enrollment);

    List<EnrollmentResponseDto> toEnrollmentResponseDtoList(List<Enrollment> enrollments);

    @Mapping(target = "enrollmentId", ignore = true)
    EnrollmentResponseDto updateEnrollment(EnrollmentRequestDto enrollmentRequestDto, @MappingTarget Enrollment enrollment);
}