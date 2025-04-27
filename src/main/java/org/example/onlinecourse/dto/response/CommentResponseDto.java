package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String comment;
    private LocalDate commentDate;
    private StudentResponseDto studentResponse;
    private CourseResponseDto courseResponse;
}