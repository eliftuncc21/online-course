package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.SubCategoryRequestDto;
import org.example.onlinecourse.dto.response.SubCategoryResponseDto;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.model.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SubCategoryMapper {
    @Mapping(target = "category", expression = "java(category)")
    SubCategory toSubCategory(SubCategoryRequestDto subCategoryRequestDto, Category category);

    @Mapping(source = "category", target = "categoryResponseDto")
    SubCategoryResponseDto toSubCategoryResponseDto(SubCategory subCategory);

    @Mapping(target = "subCategoryId", ignore = true)
    void updateSubCategory(SubCategoryRequestDto subCategoryRequestDto, @MappingTarget SubCategory subCategory);
}
