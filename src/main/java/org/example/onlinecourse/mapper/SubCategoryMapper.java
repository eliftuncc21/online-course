package org.example.onlinecourse.mapper;

import org.example.onlinecourse.dto.request.SubCategoryRequestDto;
import org.example.onlinecourse.dto.response.SubCategoryResponseDto;
import org.example.onlinecourse.model.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface SubCategoryMapper {
    SubCategory toSubCategory(SubCategoryRequestDto subCategoryRequestDto);

    SubCategoryResponseDto toSubCategoryResponseDto(SubCategory subCategory);

    List<SubCategoryResponseDto> toSubCategoryResponseDtoList(List<SubCategory> categoryList);

    @Mapping(target = "subCategoryId", ignore = true)
    void updateSubCategory(SubCategoryRequestDto subCategoryRequestDto, @MappingTarget SubCategory subCategory);
}
