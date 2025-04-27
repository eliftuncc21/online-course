package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.SubCategoryRequestDto;
import org.example.onlinecourse.dto.response.SubCategoryResponseDto;
import org.example.onlinecourse.service.SubCategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/sub-category")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @PostMapping("/save-subcategory")
    public SubCategoryResponseDto saveCategory(@RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        return subCategoryService.save(subCategoryRequestDto);
    }

    @GetMapping("/list-subcategory")
    public List<SubCategoryResponseDto> listCategory() {
        return subCategoryService.getAllSubCategory();
    }

    @GetMapping("/list-subcategory/{id}")
    public SubCategoryResponseDto getCategoryById(@PathVariable long id) {
        return subCategoryService.getSubCategoryById(id);
    }

    @DeleteMapping("/delete-subcategory/{id}")
    public void deleteCategoryById(@PathVariable long id) {
        subCategoryService.deleteSubCategoryById(id);
    }

    @PutMapping("/update-subcategory/{id}")
    public SubCategoryResponseDto updateCategory(@PathVariable long id, @RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        return subCategoryService.updateSubCatgeory(subCategoryRequestDto, id);
    }
}
