package org.example.onlinecourse.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CommentFilterRequest {
    private String comment;
    private LocalDate commentDateFrom;
    private LocalDate commentDateTo;
    private String studentName;
    private String courseName;
    private boolean sortAscending;
}
