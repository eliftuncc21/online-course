package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponseDto {
    private Long categoryId;
    private String categoryName;
    private String description;
    private CategoryResponseDto categoryResponseDto;

}
