package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinecourse.model.Difficulty;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private Long courseId;
    private String title;
    private String description;
    private double price;
    private LocalDateTime creationDate;
    private int courseDuration;
    private String courseUrl;
    private Difficulty difficulty;
    private InstructorResponseDto instructorResponse;
    private SubCategoryResponseDto subCategoryResponse;
}
