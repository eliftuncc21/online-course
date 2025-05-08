package org.example.onlinecourse.dto.request;

import lombok.Data;

@Data
public class SubCategoryFilterRequest extends BaseCategoryFilterRequest {
    private String subCategoryName;
}
