package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CommentRequestDto;
import org.example.onlinecourse.dto.response.CommentResponseDto;
import org.example.onlinecourse.mapper.CommentMapper;
import org.example.onlinecourse.model.Comment;
import org.example.onlinecourse.repository.CommentRepository;
import org.example.onlinecourse.repository.CourseRepository;
import org.example.onlinecourse.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CommentMapper commentMapper;

    public CommentResponseDto save(CommentRequestDto commentRequestDto) {
        Student student = studentRepository.findById(commentRequestDto.getStudentId()).orElse(null);
        Course course = courseRepository.findById(commentRequestDto.getCourseId()).orElse(null);
        Comment comment = commentMapper.toComment(commentRequestDto);
        comment.setStudent(student);
        comment.setCourse(course);
        Comment dbComment = commentRepository.save(comment);
        return commentMapper.toCommentResponseDto(dbComment);
    }

    public List<CommentResponseDto> getAllComment(){
        List<Comment> commentList = commentRepository.findAll();
        return commentMapper.toCommentResponseDtoList(commentList);
    }

    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        return commentMapper.toCommentResponseDto(comment);
    }

    public void deleteCommentById(Long id) {
        commentRepository.deleteById(id);
    }

    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        commentMapper.update(commentRequestDto, comment);
        Comment dbComment = commentRepository.save(comment);
        return commentMapper.toCommentResponseDto(dbComment);
    }
}
