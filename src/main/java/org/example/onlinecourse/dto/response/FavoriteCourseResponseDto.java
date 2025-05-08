package org.example.onlinecourse.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.Student;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteCourseResponseDto {
    private Long id;
    private Student student;
    private Course course;
    private LocalDateTime addedDate;
}
