package org.example.onlinecourse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequestDto {
    private String categoryName;
    private String description;
    private Long categoryId;
}