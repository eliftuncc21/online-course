package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CategoryRequestDto;
import org.example.onlinecourse.dto.request.SubCategoryRequestDto;
import org.example.onlinecourse.dto.response.CategoryResponseDto;
import org.example.onlinecourse.dto.response.SubCategoryResponseDto;
import org.example.onlinecourse.mapper.SubCategoryMapper;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.model.SubCategory;
import org.example.onlinecourse.repository.CategoryRepository;
import org.example.onlinecourse.repository.SubCategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public SubCategoryResponseDto save(SubCategoryRequestDto subCategoryRequestDto) {
        Category category = categoryRepository.findById(subCategoryRequestDto.getCategoryId()).orElse(null);
        SubCategory subCategory = subCategoryMapper.toSubCategory(subCategoryRequestDto);
        subCategory.setCategory(category);
        SubCategory dbCategory = subCategoryRepository.save(subCategory);
        return subCategoryMapper.toSubCategoryResponseDto(dbCategory);
    }

    public List<SubCategoryResponseDto> getAllSubCategory(){
        List<SubCategory> categoryList = subCategoryRepository.findAll();
        return subCategoryMapper.toSubCategoryResponseDtoList(categoryList);
    }

    public SubCategoryResponseDto getSubCategoryById(long id) {
        SubCategory category = subCategoryRepository.findById(id).orElse(null);
        return subCategoryMapper.toSubCategoryResponseDto(category);
    }

    public void deleteSubCategoryById(long id) {
        subCategoryRepository.deleteById(id);
    }

    public SubCategoryResponseDto updateSubCatgeory(SubCategoryRequestDto subCategoryRequestDto, long id){
        SubCategory subCategory = subCategoryRepository.findById(id).orElse(null);
        subCategoryMapper.updateSubCategory(subCategoryRequestDto, subCategory);
        SubCategory categoryDb = subCategoryRepository.save(subCategory);
        return subCategoryMapper.toSubCategoryResponseDto(categoryDb);
    }
}
