package org.example.onlinecourse.dto.request;

import lombok.Data;

@Data
public class CategoryFilterRequest extends BaseCategoryFilterRequest {
    private String admin;
}
