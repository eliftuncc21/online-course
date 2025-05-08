package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CategoryFilterRequest;
import org.example.onlinecourse.dto.request.CategoryRequestDto;
import org.example.onlinecourse.dto.response.CategoryResponseDto;
import org.example.onlinecourse.service.CategoryService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/save-category")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponseDto saveCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @GetMapping("/list-category")
    public Page<CategoryResponseDto> listCategory(CategoryFilterRequest filterRequest, @RequestParam int page, @RequestParam int size) {
        return categoryService.getAllCategory(filterRequest, page, size);
    }

    @GetMapping("/list-category/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/delete-category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategoryById(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
    }

    @PutMapping("/update-category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponseDto updateCategory(@PathVariable long id, @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.updateCatgeory(categoryRequestDto, id);
    }
}
