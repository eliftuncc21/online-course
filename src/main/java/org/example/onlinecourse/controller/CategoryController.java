package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.CategoryRequestDto;
import org.example.onlinecourse.dto.response.CategoryResponseDto;
import org.example.onlinecourse.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/save-category")
    public CategoryResponseDto saveCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.save(categoryRequestDto);
    }

    @GetMapping("/list-category")
    public List<CategoryResponseDto> listCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/list-category/{id}")
    public CategoryResponseDto getCategoryById(@PathVariable long id) {
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("/delete-category/{id}")
    public void deleteCategoryById(@PathVariable long id) {
        categoryService.deleteCategoryById(id);
    }

    @PutMapping("/update-category/{id}")
    public CategoryResponseDto updateCategory(@PathVariable long id, @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.updateCatgeory(categoryRequestDto, id);
    }
}
