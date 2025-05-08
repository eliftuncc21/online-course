package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CommentFilterRequest;
import org.example.onlinecourse.dto.request.CommentRequestDto;
import org.example.onlinecourse.dto.response.CommentResponseDto;
import org.example.onlinecourse.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save-comment")
    public CommentResponseDto saveComment(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.save(commentRequestDto);
    }

    @GetMapping("/list-comment")
    public Page<CommentResponseDto> listComment(CommentFilterRequest filterRequest, @RequestParam int page, @RequestParam int size) {
        return commentService.getAllComment(filterRequest, page, size);
    }

    @GetMapping("/list-comment/{id}")
    public CommentResponseDto getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @DeleteMapping("/delete-comment/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteCommentById(id);
    }

    @PutMapping("/update-comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto) {
        return commentService.updateComment(commentRequestDto, id);
    }
}
