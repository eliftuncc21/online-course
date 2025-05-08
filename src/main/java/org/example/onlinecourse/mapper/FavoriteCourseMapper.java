package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.FavoriteCourseRequestDto;
import org.example.onlinecourse.dto.response.FavoriteCourseResponseDto;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.FavoriteCourse;
import org.example.onlinecourse.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface FavoriteCourseMapper {
    @Mapping(target = "student", expression = "java(student)")
    @Mapping(target = "course", expression = "java(course)")
    FavoriteCourse toFavoriteCourse(FavoriteCourseRequestDto requestDto, Student student, Course course);

    FavoriteCourseResponseDto toFavoriteCourseResponseDto(FavoriteCourse favoriteCourse);
}
