package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.CategoryRequestDto;
import org.example.onlinecourse.dto.response.CategoryResponseDto;
import org.example.onlinecourse.model.Admin;
import org.example.onlinecourse.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "admin", expression = "java(admin)")
    Category toCategoryWithAdmin(CategoryRequestDto categoryRequestDto, Admin admin);

    CategoryResponseDto toCategoryResponseDto(Category category);

    @Mapping(target = "categoryId", ignore = true)
    void updateCategory(CategoryRequestDto categoryRequestDto, @MappingTarget Category category);
}
