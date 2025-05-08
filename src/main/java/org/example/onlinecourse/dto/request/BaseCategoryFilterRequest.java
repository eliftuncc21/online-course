package org.example.onlinecourse.dto.request;

import lombok.Data;

@Data
public class BaseCategoryFilterRequest {
    private String categoryName;
    private String description;
    private Long min;
    private Long max;
}
