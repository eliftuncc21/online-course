package org.example.onlinecourse.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryRequestDto {
    private String subCategoryName;
    private String subDescription;
    private Long categoryId;
}