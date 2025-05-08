package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.SubCategoryFilterRequest;
import org.example.onlinecourse.dto.request.SubCategoryRequestDto;
import org.example.onlinecourse.dto.response.SubCategoryResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.SubCategoryMapper;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.model.SubCategory;
import org.example.onlinecourse.repository.CategoryRepository;
import org.example.onlinecourse.repository.SubCategoryRepository;
import org.example.onlinecourse.specification.SubCategorySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public SubCategoryResponseDto save(SubCategoryRequestDto subCategoryRequestDto) {
        Category category = categoryRepository.findById(subCategoryRequestDto.getCategoryId())
                .orElseThrow(()->new ErrorMessage(
                        MessageType.CATEGORY_NOT_FOUND,
                        "Category not found",
                        HttpStatus.NOT_FOUND
                ));
        SubCategory subCategory = subCategoryMapper.toSubCategory(subCategoryRequestDto, category);

        subCategoryRepository.save(subCategory);
        return subCategoryMapper.toSubCategoryResponseDto(subCategory);
    }

    public Page<SubCategoryResponseDto> getAllSubCategory(SubCategoryFilterRequest filterRequest, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Specification<SubCategory> specification = SubCategorySpecification.getSubCategorySpecification(filterRequest);
        return subCategoryRepository.findAll(specification, pageable).map(subCategoryMapper::toSubCategoryResponseDto);
    }

    public SubCategoryResponseDto getSubCategoryById(long id) {
        SubCategory category = subCategoryRepository.findById(id)
                .orElseThrow(()->new ErrorMessage(
                        MessageType.CATEGORY_NOT_FOUND,
                        "Category not found",
                        HttpStatus.NOT_FOUND
                ));
        return subCategoryMapper.toSubCategoryResponseDto(category);
    }

    public void deleteSubCategoryById(long id) {
        subCategoryRepository.deleteById(id);
    }

    public SubCategoryResponseDto updateSubCatgeory(SubCategoryRequestDto subCategoryRequestDto, long id){
        SubCategory subCategory = subCategoryRepository.findById(id)
                .orElseThrow(()->new ErrorMessage(
                        MessageType.CATEGORY_NOT_FOUND,
                        "Category not found",
                        HttpStatus.NOT_FOUND
                ));
        subCategoryMapper.updateSubCategory(subCategoryRequestDto, subCategory);
        subCategoryRepository.save(subCategory);

        return subCategoryMapper.toSubCategoryResponseDto(subCategory);
    }
}
