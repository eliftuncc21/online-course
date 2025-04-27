package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CategoryRequestDto;
import org.example.onlinecourse.dto.response.CategoryResponseDto;
import org.example.onlinecourse.mapper.CategoryMapper;
import org.example.onlinecourse.model.Admin;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.repository.AdminRepository;
import org.example.onlinecourse.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final CategoryMapper categoryMapper;

    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        Admin admin = adminRepository.findById(categoryRequestDto.getAdminId()).orElse(null);
        Category category = categoryMapper.toCategory(categoryRequestDto);
        category.setAdmin(admin);
        Category dbCategory = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(dbCategory);
    }

    public List<CategoryResponseDto> getAllCategory(){
        List<Category> categoryList = categoryRepository.findAll();
        return categoryMapper.toCategoryResponseDtoList(categoryList);
    }

    public CategoryResponseDto getCategoryById(long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return categoryMapper.toCategoryResponseDto(category);
    }

    public void deleteCategoryById(long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponseDto updateCatgeory(CategoryRequestDto categoryRequestDto, long id){
        Category category = categoryRepository.findById(id).orElse(null);
        categoryMapper.updateCategory(categoryRequestDto, category);
        Category categoryDb = categoryRepository.save(category);
        return categoryMapper.toCategoryResponseDto(categoryDb);
    }
}
