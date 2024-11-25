package com.starchive.springapp.category.controller;

import com.starchive.springapp.category.dto.CategoryListTreeResponse;
import com.starchive.springapp.category.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "카테고리")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categorys")
    @Operation(summary = "카테고리 목록 전체 조회")
    public CategoryListTreeResponse showCategories() {
        return categoryService.findAll();
    }
}
