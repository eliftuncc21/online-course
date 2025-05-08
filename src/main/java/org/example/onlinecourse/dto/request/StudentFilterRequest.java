package org.example.onlinecourse.dto.request;

import lombok.Data;

@Data
public class StudentFilterRequest {
    private String studentName;
    private String active;
    private String email;
    private String telephone;
    private Integer completedCourse;
    private Integer count;
    private String course;
    private String category;
    private Long enrolledCount;
    private String instructorName;
    private String language;
    private Long favoriteCourseCount;
    private String favoriteCourseLanguage;
    private String favoriteInstructorName;
}
