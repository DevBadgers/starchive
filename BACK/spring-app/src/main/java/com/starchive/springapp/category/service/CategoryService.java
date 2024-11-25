package com.starchive.springapp.category.service;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.dto.CategoryListTreeResponse;
import com.starchive.springapp.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryListTreeResponse findAll() {
        List<Category> rootCateGories = categoryRepository.findRootCategoriesWithChildren();
        return CategoryListTreeResponse.from(rootCateGories);
    }

}
