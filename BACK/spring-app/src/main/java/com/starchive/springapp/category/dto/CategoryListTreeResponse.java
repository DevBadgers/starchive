package com.starchive.springapp.category.dto;

import com.starchive.springapp.category.domain.Category;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryListTreeResponse {
    List<CategoryDto> roots;

    public static CategoryListTreeResponse from(List<Category> roots) {
        CategoryListTreeResponse categoryListTreeResponse = new CategoryListTreeResponse();
        categoryListTreeResponse.roots = roots.stream().map(CategoryDto::from).toList();
        return categoryListTreeResponse;
    }

}

