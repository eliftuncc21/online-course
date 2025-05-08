package org.example.onlinecourse.specification;

import org.example.onlinecourse.dto.request.SubCategoryFilterRequest;
import org.example.onlinecourse.model.SubCategory;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class SubCategorySpecification {

    public static Specification<SubCategory> getSubCategorySpecification(SubCategoryFilterRequest filterRequest) {
        return Specification.where(hasSubCategoryName(filterRequest.getSubCategoryName()))
                .and(hasDescription(filterRequest.getDescription()))
                .and(hasCourse(filterRequest.getMin(), filterRequest.getMax()))
                .and(hasCategoryName(filterRequest.getCategoryName()));
    }

    public static Specification<SubCategory> hasSubCategoryName(String categoryName) {
        return (root, query, cb) ->
                Optional.ofNullable(categoryName)
                        .map(name -> cb.equal(root.get("subCategoryName"), name))
                        .orElse(cb.conjunction());
    }

    public static Specification<SubCategory> hasDescription(String description) {
        return (root, query, cb) ->
                Optional.ofNullable(description)
                        .map(desc -> cb.like(root.get("description"), "%" + desc + "%"))
                        .orElse(cb.conjunction());
    }

    public static Specification<SubCategory> hasCourse(Long min, Long max) {
        return (root, query, cb) ->
                (min != null && max != null)
                        ? cb.between(cb.count(root.get("courses").get("courseId")),min, max)
                        : cb.conjunction();
    }

    public static Specification<SubCategory> hasCategoryName(String categoryName) {
        return (root, query, cb) ->
                Optional.ofNullable(categoryName)
                        .map(name -> cb.equal(root.get("category")
                                .get("categoryName"), name))
                        .orElse(cb.conjunction());
    }

}
