package org.example.onlinecourse.specification;

import jakarta.persistence.criteria.Join;
import org.example.onlinecourse.dto.request.StudentFilterRequest;
import org.example.onlinecourse.model.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class StudentSpecification {

    public static Specification<Student> getStudentSpecification(StudentFilterRequest filterRequest) {
        return Specification.where(hasStudentName(filterRequest.getStudentName()))
                .and(hasIsActive(filterRequest.getActive()))
                .and(hasEmail(filterRequest.getEmail()))
                .and(hasTelephone(filterRequest.getTelephone()))
                .and(hasCompletedCourse(filterRequest.getCompletedCourse()))
                .and(hasTotalCourseCountLessThan(filterRequest.getCount()))
                .and(enrolledCourse(filterRequest.getCourse()))
                .and(enrolledCategory(filterRequest.getCategory()))
                .and(enrolledInAtLeastNCourses(filterRequest.getEnrolledCount()))
                .and(hasComments())
                .and(hasInstructorName(filterRequest.getInstructorName()))
                .and(commentedOnLanguageCourses(filterRequest.getLanguage()))
                .and(favoritedCoursesInLanguage(filterRequest.getFavoriteCourseLanguage()))
                .and(hasAtLeastNFavorites(filterRequest.getFavoriteCourseCount()))
                .and(favoritedCoursesOfInstructor(filterRequest.getFavoriteInstructorName()));
    }

    public static Specification<Student> hasStudentName(String studentName) {
        return (root, query, cb) ->
                Optional.ofNullable(studentName)
                        .map(name -> cb.equal(root.get("user")
                                .get("firstName"), name))
                        .orElse(cb.conjunction());
    }

    public static Specification<Student> hasIsActive(String active) {
        return (root, query, cb) ->
                Optional.ofNullable(active)
                        .map(status -> cb.equal(root.get("user")
                                .get("isActive"), status))
                        .orElse(cb.conjunction());
    }

    public static Specification<Student> hasEmail(String email) {
        return (root, query, cb) ->
                Optional.ofNullable(email)
                        .map(mail -> cb.like(root.get("user")
                                .get("email"), "%" + mail + "%"))
                        .orElse(cb.conjunction());
    }

    public static Specification<Student> hasTelephone(String telephone) {
        return (root, query, cb) ->
                Optional.ofNullable(telephone)
                        .map(tel -> cb.like(root.get("user")
                                .get("telephoneNumber"), "%" + tel + "%"))
                        .orElse(cb.conjunction());
    }

    public static Specification<Student> hasCompletedCourse(Integer completedCourse) {
        return (root, query, cb) ->
                Optional.ofNullable(completedCourse)
                        .map(count -> cb.greaterThanOrEqualTo(
                                root.get("completedCourseCount"), count))
                        .orElse(cb.conjunction());
    }

    public static Specification<Student> hasTotalCourseCountLessThan(Integer count) {
        return (root, query, cb) ->
                Optional.ofNullable(count)
                        .map(total -> cb.lessThan(
                                root.get("totalCourseCount"), total))
                        .orElse(cb.conjunction());
    }

    public static Specification<Student> hasAtLeastNFavorites(Long favoriteCourseCount) {
        return (root, query, cb) -> {
            if (favoriteCourseCount == null) {
                return cb.conjunction();
            }

            Join<Student, FavoriteCourse> favoriteCourseJoin = root.join("favoriteCourses");
            query.groupBy(root.get("studentId"));
            query.having(cb.greaterThanOrEqualTo(cb.count(favoriteCourseJoin.get("id")), favoriteCourseCount));

            return cb.conjunction();
        };
    }


    public static Specification<Student> favoritedCoursesInLanguage(String favoriteCourseLanguage) {
        return (root, query, cb) ->
                Optional.ofNullable(favoriteCourseLanguage)
                .map(language -> {

                    Join<Student, FavoriteCourse> favoriteCourseJoin = root.join("favoriteCourses");
                    Join<FavoriteCourse, Course> courseJoin = favoriteCourseJoin.join("course");

                    return cb.equal(courseJoin.get("language"), language);
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Student> favoritedCoursesOfInstructor(String favoriteInstructorName) {
        return (root, query, cb) ->
                Optional.ofNullable(favoriteInstructorName)
                .map(name -> {

                    Join<Student, FavoriteCourse> favoriteCourseJoin = root.join("favoriteCourses");
                    Join<FavoriteCourse, Course> courseJoin = favoriteCourseJoin.join("course");
                    Join<Course, Instructor> instructorJoin = courseJoin.join("instructor");
                    Join<Instructor, User> userJoin = instructorJoin.join("user");

                    return cb.equal(userJoin.get("firstName"), name);
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Student> enrolledCourse(String course){
        return (root, query, cb) ->
                Optional.ofNullable(course)
                .map(c -> {

                    Join<Student, Enrollment> enrollmentJoin = root.join("enrollments");
                    Join<Enrollment, Course> courseJoin = enrollmentJoin.join("course");

                    return cb.like(courseJoin.get("title"), '%' + c + '%');
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Student> enrolledCategory(String category){
        return (root, query, cb) ->
                Optional.ofNullable(category)
                .map(c -> {

                    Join<Student, Enrollment> enrollmentJoin = root.join("enrollments");
                    Join<Enrollment, Course> courseJoin = enrollmentJoin.join("course");
                    Join<Course, Category> categoryJoin = courseJoin.join("category");

                    return cb.like(categoryJoin.get("categoryName"), '%' + c + '%');
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Student> enrolledInAtLeastNCourses(Long enrolledCount){
        return (root, query, cb) ->
                Optional.ofNullable(enrolledCount)
                .map(count -> {
                    Join<Student, Enrollment> enrollmentJoin = root.join("enrollments");

                    query.groupBy(root.get("studentId"));
                    query.having(cb.greaterThanOrEqualTo(
                            cb.count(enrollmentJoin.get("enrollmentId")), count));

                    return cb.conjunction();
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Student> hasComments(){
        return (root, query, cb) ->
                cb.isNotNull(root.get("comment"));
    }

    public static Specification<Student> hasInstructorName(String instructorName){
        return (root, query, cb) ->
                Optional.ofNullable(instructorName)
                .map(name -> {

                    Join<Student, Enrollment> enrollmentJoin = root.join("enrollments");
                    Join<Enrollment, Course> courseJoin = enrollmentJoin.join("course");
                    Join<Course, Instructor> instructorJoin = courseJoin.join("instructor");
                    Join<Instructor, User> userJoin = instructorJoin.join("user");

                    return cb.equal(userJoin.get("firstName"), name);
                })
                .orElse(cb.conjunction());
    }

    public static Specification<Student> commentedOnLanguageCourses(String language){
        return (root, query, cb) ->
                Optional.ofNullable(language)
                        .map(courseLanguage -> cb.and(
                                cb.isNotNull(root.get("comments")),
                                cb.equal(root.get("language"), courseLanguage)))
                        .orElse(cb.conjunction());
    }
}