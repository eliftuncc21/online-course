package org.example.onlinecourse.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinecourse.model.Difficulty;

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
    private Difficulty difficulty;
    private Long subCategoryId;
}
