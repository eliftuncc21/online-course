package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorResponseDto {
    private Long instructorId;
    private String biography;
    private boolean isActive;
    private Integer totalCourseCount;
    private UserResponseDto userResponse;
}