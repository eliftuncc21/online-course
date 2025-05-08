package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CommentFilterRequest;
import org.example.onlinecourse.dto.request.CommentRequestDto;
import org.example.onlinecourse.dto.response.CommentResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.CommentMapper;
import org.example.onlinecourse.model.*;
import org.example.onlinecourse.repository.CommentRepository;
import org.example.onlinecourse.repository.CourseRepository;
import org.example.onlinecourse.repository.StudentRepository;
import org.example.onlinecourse.specification.CommentSpecification;
import org.example.onlinecourse.util.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final CommentMapper commentMapper;
    private final SecurityUtil securityUtil;

    public CommentResponseDto save(CommentRequestDto commentRequestDto) {
        User user = securityUtil.getCurrentUser();

        Student student = studentRepository.findById(user.getUserId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.STUDENT_NOT_FOUND,
                        "Student not found",
                        HttpStatus.NOT_FOUND)
                );

        Course course = courseRepository.findById(commentRequestDto.getCourseId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COURSE_NOT_FOUND,
                        "Course not found",
                        HttpStatus.NOT_FOUND)
                );

        Comment comment = commentMapper.toComment(commentRequestDto,student,course);
        commentRepository.save(comment);

        return commentMapper.toCommentResponseDto(comment);
    }

    public Page<CommentResponseDto> getAllComment(CommentFilterRequest filterRequest, int page, int size) {
        Specification<Comment> specification = CommentSpecification.getCommentSpecification(filterRequest);
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findAll(specification, pageable).map(commentMapper::toCommentResponseDto);
    }

    public CommentResponseDto getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                            MessageType.COMMENT_NOT_FOUND,
                        "Comment not found",
                        HttpStatus.NOT_FOUND)
                );
        return commentMapper.toCommentResponseDto(comment);
    }

    public void deleteCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COMMENT_NOT_FOUND,
                        "Comment not found",
                        HttpStatus.NOT_FOUND)
                );
        getComment(comment);
        commentRepository.delete(comment);
    }

    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.COMMENT_NOT_FOUND,
                        "Comment not found",
                        HttpStatus.NOT_FOUND)
                );

        getComment(comment);
        commentMapper.update(commentRequestDto, comment);
        commentRepository.save(comment);
        return commentMapper.toCommentResponseDto(comment);
    }

    private void getComment(Comment comment) {
        User currentUser = securityUtil.getCurrentUser();
        boolean isCommentOwner = comment.getStudent().getUserId().equals(currentUser.getUserId());
        boolean isAdmin = currentUser.getRole() == Role.ADMIN;
        boolean isInstructor = currentUser.getRole() == Role.INSTRUCTOR &&
                comment.getCourse().getInstructor().getUserId().equals(currentUser.getUserId());

        if (!(isCommentOwner || isAdmin || isInstructor)) {
            throw new ErrorMessage(
                    MessageType.NO_PERMISSION_COMMENT,
                    "No Permission",
                    HttpStatus.FORBIDDEN);
        }
    }
}
