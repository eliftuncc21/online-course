package org.example.onlinecourse.controller;

import lombok.RequiredArgsConstructor;
import org.example.onlinecourse.dto.request.SubCategoryFilterRequest;
import org.example.onlinecourse.dto.request.SubCategoryRequestDto;
import org.example.onlinecourse.dto.response.SubCategoryResponseDto;
import org.example.onlinecourse.service.SubCategoryService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/api/sub-category")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @PostMapping("/save-subcategory")
    @PreAuthorize("hasRole('ADMIN')")
    public SubCategoryResponseDto saveCategory(@RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        return subCategoryService.save(subCategoryRequestDto);
    }

    @GetMapping("/list-subcategory")
    public Page<SubCategoryResponseDto> listCategory(SubCategoryFilterRequest filterRequest, @RequestParam int page, @RequestParam int size) {
        return subCategoryService.getAllSubCategory(filterRequest, page, size);
    }

    @GetMapping("/list-subcategory/{id}")
    public SubCategoryResponseDto getCategoryById(@PathVariable long id) {
        return subCategoryService.getSubCategoryById(id);
    }

    @DeleteMapping("/delete-subcategory/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategoryById(@PathVariable long id) {
        subCategoryService.deleteSubCategoryById(id);
    }

    @PutMapping("/update-subcategory/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SubCategoryResponseDto updateCategory(@PathVariable long id, @RequestBody SubCategoryRequestDto subCategoryRequestDto) {
        return subCategoryService.updateSubCatgeory(subCategoryRequestDto, id);
    }
}
