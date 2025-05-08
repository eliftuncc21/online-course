package org.example.onlinecourse.specification;

import jakarta.persistence.criteria.Join;
import org.example.onlinecourse.dto.request.InstructorFilterRequest;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.model.Course;
import org.example.onlinecourse.model.Instructor;
import org.example.onlinecourse.model.SubCategory;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class InstructorSpecification {

    public static Specification<Instructor> getInstructorSpecification(InstructorFilterRequest filterRequest) {
        return Specification.where(hasInstructorName(filterRequest.getInstructorName()))
                .and(hasTotalCourse(filterRequest.getMin(), filterRequest.getMax()))
                .and(hasIsActive(filterRequest.getActive()))
                .and(hasEmail(filterRequest.getEmail()))
                .and(hasTelephone(filterRequest.getPhone()))
                .and(hasCourseLanguage(filterRequest.getLanguage()))
                .and(hasCourseDifficulty(filterRequest.getDifficulty()))
                .and(hasInstructorCategory(filterRequest.getCategory()))
                .and(hasInstructorSubCategory(filterRequest.getSubCategory()))
                .and(hasBiography(filterRequest.getBiography()));
    }

    public static Specification<Instructor> hasInstructorName(String instructorName) {
        return (root, query, cb) ->
                Optional.ofNullable(instructorName)
                        .map(name -> cb.equal(root.get("user").get("firstName"), name))
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasTotalCourse(Integer min, Integer max) {
        return (root, query, cb) ->
                (min != null && max != null)
                        ? cb.between(root.get("totalCourseCount"), min, max)
                        : cb.conjunction();
    }

    public static Specification<Instructor> hasIsActive(String active) {
        return (root, query, cb) ->
                Optional.ofNullable(active)
                        .map(act -> cb.equal(root.get("user").get("isActive"), act))
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasEmail(String email) {
        return (root, query, cb) ->
                Optional.ofNullable(email)
                        .map(mail -> cb.like(root.get("user")
                                .get("email"), "%" + mail + "%"))
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasTelephone(String telephone) {
        return (root, query, cb) ->
                Optional.ofNullable(telephone)
                        .map(tel -> cb.like(root.get("user")
                                .get("telephoneNumber"), "%" + tel + "%"))
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasCourseLanguage(String language) {
        return (root, query, cb) ->
                Optional.ofNullable(language)
                        .map(courseLanguage -> cb.equal(root.get("courses")
                                .get("language"), courseLanguage))
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasCourseDifficulty(String difficulty) {
        return (root, query, cb) ->
                Optional.ofNullable(difficulty)
                        .map(dif -> cb.equal(root.get("courses")
                                .get("difficulty"), dif))
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasInstructorCategory(String category) {
        return (root, query, cb) ->
                Optional.ofNullable(category)
                        .map(name -> {

                            Join<Instructor, Course> courseJoin = root.join("courses");
                            Join<Course, Category> categoryJoin = courseJoin.join("category");

                            return cb.equal(categoryJoin.get("categoryName"), name);
                        })
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasInstructorSubCategory(String subCategory) {
        return (root, query, cb) ->
                Optional.ofNullable(subCategory)
                        .map(name -> {

                            Join<Instructor, Course> courseJoin = root.join("courses");
                            Join<Course, SubCategory> subCategoryJoin = courseJoin.join("subCategory");

                            return cb.equal(subCategoryJoin.get("categoryName"), name);
                        })
                        .orElse(cb.conjunction());
    }

    public static Specification<Instructor> hasBiography(String biography) {
        return (root, query, cb) ->
                Optional.ofNullable(biography)
                        .map(bio ->
                                cb.like(root.get("biography"), "%" + bio + "%"))
                        .orElse(cb.conjunction());
    }
}
