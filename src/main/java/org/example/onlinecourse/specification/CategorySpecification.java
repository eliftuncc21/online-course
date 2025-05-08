package org.example.onlinecourse.specification;

import org.example.onlinecourse.dto.request.CategoryFilterRequest;
import org.example.onlinecourse.model.Category;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class CategorySpecification {

    public static Specification<Category> getCategorySpecification(CategoryFilterRequest categoryFilterRequest) {
        return Specification.where(hasCategoryName(categoryFilterRequest.getCategoryName()))
                .and(hasDescription(categoryFilterRequest.getDescription()))
                .and(hasCourse(categoryFilterRequest.getMin(), categoryFilterRequest.getMax()))
                .and(hasAdminName(categoryFilterRequest.getAdmin()));
    }

    public static Specification<Category> hasCategoryName(String categoryName) {
        return (root, query, cb) ->
                Optional.ofNullable(categoryName)
                .map(name -> cb.equal(root.get("categoryName"), name))
                .orElse(cb.conjunction());
    }

    public static Specification<Category> hasDescription(String description) {
        return (root, query, cb) ->
                Optional.ofNullable(description)
                .map(desc -> cb.equal(root.get("categoryDescription"), desc))
                .orElse(cb.conjunction());
    }

    public static Specification<Category> hasCourse(Long min, Long max) {
        return (root, query, cb) ->
                (min != null && max != null)
                        ? cb.between(cb.count(root.get("courses").get("courseId")), min, max)
                        : cb.conjunction();
    }

    public static Specification<Category> hasAdminName(String adminName) {
        return (root, query, cb) ->
                Optional.ofNullable(adminName)
                .map(admin ->
                        cb.equal(root.get("admin").get("user")
                        .get("firstName"), admin))
                .orElse(cb.conjunction());
    }

}
