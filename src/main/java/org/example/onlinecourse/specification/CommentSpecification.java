package org.example.onlinecourse.specification;

import jakarta.persistence.criteria.Join;
import org.example.onlinecourse.dto.request.CommentFilterRequest;
import org.example.onlinecourse.model.Comment;
import org.example.onlinecourse.model.Student;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.Optional;

public class CommentSpecification {

    public static Specification<Comment> getCommentSpecification(CommentFilterRequest filterRequest) {
        return Specification.where(hasComment(filterRequest.getComment()))
                .and(hasCommentDate(filterRequest.getCommentDateFrom(), filterRequest.getCommentDateTo()))
                .and(hasStudent(filterRequest.getStudentName()))
                .and(hasCourse(filterRequest.getCourseName()))
                .and(sortByDate(filterRequest.isSortAscending()))
                .and(orderByCompletedCourses());
    }

    public static Specification<Comment> hasComment(String title) {
        return (root, query, cb) ->
                Optional.ofNullable(title)
                        .map(name -> cb.like(root.get("title"), "%" + name + "%"))
                        .orElse(cb.conjunction());
    }

    public static Specification<Comment> hasCommentDate(LocalDate commentDateFrom, LocalDate commentDateTo) {
        return (root, query, cb) ->
                (commentDateFrom != null && commentDateTo != null)
                        ? cb.between(root.get("commentDate"), commentDateFrom, commentDateTo)
                        : cb.conjunction();
    }

    public static Specification<Comment> hasStudent(String studentName) {
        return (root, query, cb) ->
                Optional.ofNullable(studentName)
                        .map(student ->
                                cb.equal(root.get("student")
                                        .get("user").get("firstName"), student))
                        .orElse(cb.conjunction());
    }

    public static Specification<Comment> hasCourse(String courseName) {
        return (root, query, cb) ->
                Optional.ofNullable(courseName)
                        .map(course ->
                                cb.equal(root.get("course")
                                        .get("title"), course))
                        .orElse(cb.conjunction());
    }

    public static Specification<Comment> sortByDate(Boolean sortAscending) {
        return (root, query, cb) -> {
            query.orderBy(sortAscending
                    ? cb.asc(root.get("commentDate"))
                    : cb.desc(root.get("commentDate")));
            return cb.conjunction();
        };
    }

    public static Specification<Comment> orderByCompletedCourses() {
        return (root, query, cb) -> {
            Join<Comment, Student> studentJoin = root.join("student");

            query.orderBy(
                    cb.desc(studentJoin.get("completedCourseCount")),
                    cb.desc(root.get("commentDate"))
            );
            return cb.conjunction();
        };
    }
}
