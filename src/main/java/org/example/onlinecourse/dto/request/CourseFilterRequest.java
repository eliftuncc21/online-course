package org.example.onlinecourse.dto.request;

import lombok.Data;

@Data
public class CourseFilterRequest {
    private String title;
    private String description;
    private Double minPrice;
    private Double maxPrice;
    private Integer minDuration;
    private Integer maxDuration;
    private String categoryName;
    private String instructorName;
    private Long countComment;
    private Integer completedStudent;
    private Long totalStudent;
    private String subCategoryName;
    private String language;
    private String difficulty;
    private Long studentId;
}
