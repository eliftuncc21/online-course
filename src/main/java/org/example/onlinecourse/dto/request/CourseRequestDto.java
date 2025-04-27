package org.example.onlinecourse.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    private double price;
    private int courseDuration;
    private String courseUrl;
    private InstructorRequestDto instructorId;
    private CategoryRequestDto categoryId;
}
