package com.starchive.springapp.category.controller;

import com.starchive.springapp.category.dto.CategoryListTreeResponse;
import com.starchive.springapp.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categorys")
    public CategoryListTreeResponse showCategories() {
        return categoryService.findAll();
    }
}
