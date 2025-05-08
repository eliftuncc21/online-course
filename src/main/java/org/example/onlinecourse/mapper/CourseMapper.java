package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.CourseRequestDto;
import org.example.onlinecourse.dto.response.CourseResponseDto;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.Instructor;
import org.example.onlinecourse.model.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {InstructorMapper.class, SubCategoryMapper.class})
public interface CourseMapper {
    @Mapping(target = "instructor", expression = "java(instructor)")
    @Mapping(target = "subCategory", expression = "java(subCategory)")
    Course toCourse(CourseRequestDto courseRequestDto, Instructor instructor, SubCategory subCategory);

    CourseResponseDto toCourseResponseDto(Course course);

    @Mapping(target= "courseId", ignore = true)
    void updateCourse(CourseRequestDto courseRequestDto,@MappingTarget Course course);
}