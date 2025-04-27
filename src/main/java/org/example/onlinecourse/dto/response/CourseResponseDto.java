package org.example.onlinecourse.dto.response;

import java.time.LocalDateTime;

public class CourseResponseDto {
    private Long courseId;
    private String title;
    private String description;
    private double price;
    private LocalDateTime creationDate;
    private int courseDuration;
    private String courseUrl;
    private InstructorResponseDto instructorResponse;
    private CategoryResponseDto categoryResponse;
}
