package org.example.onlinecourse.specification;

import jakarta.persistence.criteria.Join;
import org.example.onlinecourse.dto.request.CourseFilterRequest;
import org.example.onlinecourse.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class CourseSpecification {
    public static Specification<Course> getCourseSpecification(CourseFilterRequest filter){
        return Specification.where(hasTitle(filter.getTitle()))
                .and(hasDescription(filter.getDescription()))
                .and(hasMinPrice(filter.getMinPrice()))
                .and(hasMaxPrice(filter.getMaxPrice()))
                .and(hasCourseDuration(filter.getMinDuration(), filter.getMaxDuration()))
                .and(hasCategoryName(filter.getCategoryName()))
                .and(hasInstructorName(filter.getInstructorName()))
                .and(hasCountComment(filter.getCountComment()))
                .and(hasCompletedStudent(filter.getCompletedStudent()))
                .and(hasTotalStudent(filter.getTotalStudent()))
                .and(hasSubCategoryName(filter.getSubCategoryName()))
                .and(findAllByTotalStudentsDesc())
                .and(hasCourseLanguage(filter.getLanguage()))
                .and(hasDifficulty(filter.getDifficulty()))
                .and(isFavoriteOfStudent(filter.getStudentId()));
    }

    public static Specification<Course> hasTitle(String title) {
        return (root, query, cb) ->
                Optional.ofNullable(title)
                .map(name ->
                        cb.like(root.get("title"), "%" + name + "%"))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasDescription(String description) {
        return (root, query, cb) ->
                Optional.ofNullable(description)
                .map(desc ->
                        cb.like(root.get("description"), "%" + desc + "%"))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasMinPrice(Double minPrice) {
        return (root, query, cb) ->
                Optional.ofNullable(minPrice)
                .map(min ->
                        cb.greaterThanOrEqualTo(root.get("price"), min))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasMaxPrice(Double maxPrice) {
        return (root, query, cb) ->
                Optional.ofNullable(maxPrice)
                .map(max ->
                        cb.lessThanOrEqualTo(root.get("price"), max))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasCourseDuration(Integer min, Integer max) {
        return (root, query, cb) ->
                (min != null && max != null)
                ? cb.between(root.get("courseDuration"), min, max)
                : cb.conjunction();
    }

    public static Specification<Course> hasCategoryName(String categoryName) {
        return (root, query, cb) ->
                Optional.ofNullable(categoryName)
                .map(name ->
                        cb.equal(root.get("category").get("categoryName"), name))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasSubCategoryName(String subCategoryName) {
        return (root, query, cb) ->
                Optional.ofNullable(subCategoryName)
                .map(name -> cb.equal(root.get("category")
                        .get("subCategoryDetails")
                        .get("categoryName"), name))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasInstructorName(String instructorName) {
        return (root, query, cb) ->
                Optional.ofNullable(instructorName)
                .map(name -> cb.equal(root.get("instructor")
                        .get("user").get("firstName"), name))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasCourseLanguage(String language) {
        return (root, query, cb) ->
                Optional.ofNullable(language)
                .map(lang -> cb.equal(root.get("language"), lang))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasDifficulty(String difficulty) {
        return (root, query, cb) ->
                Optional.ofNullable(difficulty)
                .map(dif -> cb.equal(root.get("difficulty"), dif))
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasCountComment(Long countComment) {
        return (root, query, cb) ->
                Optional.ofNullable(countComment)
                .map(count -> {

                    Join<Course, Comment> commentJoin = root.join("comments");

                    query.groupBy(commentJoin.get("commentId"));
                    query.having(cb.equal(cb.count(commentJoin.get("commentId")), count));

                    return cb.conjunction();
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasCompletedStudent(Integer completedStudent) {
        return (root, query, cb) ->
                Optional.ofNullable(completedStudent)
                .map(count -> {

                    Join<Course, Enrollment> enrollmentJoin = root.join("enrollments");
                    Join<Enrollment, Student> studentJoin = enrollmentJoin.join("student");

                    return cb.greaterThanOrEqualTo(studentJoin.get("completedCourseCount"), count);
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Course> hasTotalStudent(Long totalStudent) {
        return (root, query, cb) ->
                Optional.ofNullable(totalStudent)
                .map(total -> {

                    query.groupBy(root.get("courseId"));
                    query.having(cb.greaterThanOrEqualTo(
                            cb.count(root.get("enrollments").get("enrollmentId")), total));

                    return cb.conjunction();
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Course> findAllByTotalStudentsDesc() {
        return (root, query, cb) -> {

            Join<Course, Enrollment> enrollmentJoin = root.join("enrollments");

            query.groupBy(root.get("courseId"));
            query.orderBy(cb.desc(cb.count(enrollmentJoin.get("enrollmentId"))));

            return cb.conjunction();
        };
    }

    public static Specification<Course> isFavoriteOfStudent(Long studentId) {
        return (root, query, cb) ->
                Optional.ofNullable(studentId)
                .map(student -> {
                    Join<Course, FavoriteCourse> favoriteCourseJoin = root.join("favoriteCourses");
                    Join<FavoriteCourse, Student> studentJoin = favoriteCourseJoin.join("student");

                    return cb.equal(studentJoin.get("studentId"), student);
                })
                .orElse(cb.conjunction());
    }

}