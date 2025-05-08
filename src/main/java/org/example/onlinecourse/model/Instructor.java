package org.example.onlinecourse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "instructor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Instructor extends User{

    @Column(name = "biography", nullable = false, length = 250)
    private String biography;

    @Column(name = "total_course_count")
    private Integer totalCourseCount;

    @OneToMany(mappedBy = "instructor")
    private List<Course> courses;
}
