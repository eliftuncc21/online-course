package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.CategoryRequestDto;
import org.example.onlinecourse.dto.response.CategoryResponseDto;
import org.example.onlinecourse.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toCategory(CategoryRequestDto categoryRequestDto);

    CategoryResponseDto toCategoryResponseDto(Category category);

    List<CategoryResponseDto> toCategoryResponseDtoList(List<Category> categoryList);

    @Mapping(target = "categoryId", ignore = true)
    void updateCategory(CategoryRequestDto categoryRequestDto, @MappingTarget Category category);
}
