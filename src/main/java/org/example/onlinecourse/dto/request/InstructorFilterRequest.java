package org.example.onlinecourse.dto.request;

import lombok.Data;

@Data
public class InstructorFilterRequest {
    private String instructorName;
    private String active;
    private Integer min;
    private Integer max;
    private String email;
    private String phone;
    private String language;
    private String difficulty;
    private String category;
    private String subCategory;
    private String biography;
}
