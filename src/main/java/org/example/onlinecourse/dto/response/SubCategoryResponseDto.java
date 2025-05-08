package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponseDto {
    private Long subCategoryId;
    private String subCategoryName;
    private String subDescription;
    private CategoryResponseDto categoryResponseDto;
}