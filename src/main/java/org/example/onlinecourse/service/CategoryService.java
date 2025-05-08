package org.example.onlinecourse.service;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CategoryFilterRequest;
import org.example.onlinecourse.dto.request.CategoryRequestDto;
import org.example.onlinecourse.dto.response.CategoryResponseDto;
import org.example.onlinecourse.exception.ErrorMessage;
import org.example.onlinecourse.exception.MessageType;
import org.example.onlinecourse.mapper.CategoryMapper;
import org.example.onlinecourse.model.Admin;
import org.example.onlinecourse.model.Category;
import org.example.onlinecourse.model.User;
import org.example.onlinecourse.repository.AdminRepository;
import org.example.onlinecourse.repository.CategoryRepository;
import org.example.onlinecourse.specification.CategorySpecification;
import org.example.onlinecourse.util.SecurityUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final AdminRepository adminRepository;
    private final CategoryMapper categoryMapper;
    private final SecurityUtil securityUtil;

    public CategoryResponseDto save(CategoryRequestDto categoryRequestDto) {
        User currentUser = securityUtil.getCurrentUser();

        Admin admin = adminRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new ErrorMessage(
                        MessageType.ADMIN_NOT_FOUND,
                        "Admin not found",
                        HttpStatus.NOT_FOUND
                ));

        Category category = categoryMapper.toCategoryWithAdmin(categoryRequestDto, admin);
        categoryRepository.save(category);

        return categoryMapper.toCategoryResponseDto(category);
    }

    @Cacheable("categoryList")
    public Page<CategoryResponseDto> getAllCategory(CategoryFilterRequest filterRequest, int page, int size) {
        Specification<Category> specification = CategorySpecification.getCategorySpecification(filterRequest);
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(specification, pageable).map(categoryMapper::toCategoryResponseDto);
    }

    @Cacheable(value = "category", key = "#id")
    public CategoryResponseDto getCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new ErrorMessage(
                        MessageType.CATEGORY_NOT_FOUND,
                        "Category not found",
                        HttpStatus.NOT_FOUND
                ));
        return categoryMapper.toCategoryResponseDto(category);
    }

    @CacheEvict(value = {"category", "categoryList"}, key = "#id", allEntries = true)
    public void deleteCategoryById(long id) {
        getCategory(id);

        categoryRepository.deleteById(id);
    }

    @CachePut(value = "category", key = "#id")
    public CategoryResponseDto updateCatgeory(CategoryRequestDto categoryRequestDto, long id){
        Category category = getCategory(id);

        categoryMapper.updateCategory(categoryRequestDto, category);
        categoryRepository.save(category);

        return categoryMapper.toCategoryResponseDto(category);
    }

    private Category getCategory(long id) {
        User currentUser = securityUtil.getCurrentUser();
        Category category = categoryRepository.findById(id)
                .orElseThrow(()->new ErrorMessage(
                        MessageType.CATEGORY_NOT_FOUND,
                        "Category not found",
                        HttpStatus.NOT_FOUND
                ));

        if(!category.getAdmin().getUserId().equals(currentUser.getUserId())){
            throw new ErrorMessage(
                    MessageType.NO_PERMISSION_CATEGORY,
                    "No Permission",
                    HttpStatus.FORBIDDEN
            );
        }
        return category;
    }
}