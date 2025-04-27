package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.CommentRequestDto;
import org.example.onlinecourse.dto.response.CommentResponseDto;
import org.example.onlinecourse.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface CommentMapper {
    Comment toComment(CommentRequestDto commentRequestDto);

    CommentResponseDto toCommentResponseDto(Comment comment);

    List<CommentResponseDto> toCommentResponseDtoList(List<Comment> commentList);

    @Mapping(target = "commentId", ignore = true)
    void update(CommentRequestDto commentRequestDto, @MappingTarget Comment comment);
}
