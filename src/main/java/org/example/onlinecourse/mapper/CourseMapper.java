package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.CourseRequestDto;
import org.example.onlinecourse.dto.response.CourseResponseDto;
import org.example.onlinecourse.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {InstructorMapper.class, CategoryMapper.class})
public interface CourseMapper {
    Course toCourse(CourseRequestDto courseRequestDto);

    CourseResponseDto toCourseResponseDto(Course course);

    List<CourseResponseDto> toCourseResponseDtoList(List<Course> courses);

    @Mapping(target= "courseId", ignore = true)
    void updateCourse(CourseRequestDto courseRequestDto,@MappingTarget Course course);
}