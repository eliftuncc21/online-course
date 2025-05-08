package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.CommentRequestDto;
import org.example.onlinecourse.dto.response.CommentResponseDto;
import org.example.onlinecourse.model.Comment;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface CommentMapper {
    @Mapping(target = "student", expression = "java(student)")
    @Mapping(target = "course", expression = "java(course)")
    Comment toComment(CommentRequestDto commentRequestDto, Student student, Course course);

    CommentResponseDto toCommentResponseDto(Comment comment);

    @Mapping(target = "commentId", ignore = true)
    void update(CommentRequestDto commentRequestDto, @MappingTarget Comment comment);
}
