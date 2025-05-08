package org.example.onlinecourse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "comment", nullable = false, length = 250)
    private String comment;

    @Column(name = "comment_date")
    private LocalDate commentDate;

    @ManyToOne
    @JoinColumn(name = "student_ird")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
