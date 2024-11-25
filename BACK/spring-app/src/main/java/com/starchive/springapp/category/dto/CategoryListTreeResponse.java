package com.starchive.springapp.category.dto;

import com.starchive.springapp.category.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryListTreeResponse {
    @Schema(description = "최상위 카테고리 목록")
    List<CategoryDto> roots;

    public static CategoryListTreeResponse from(List<Category> roots) {
        CategoryListTreeResponse categoryListTreeResponse = new CategoryListTreeResponse();
        categoryListTreeResponse.roots = roots.stream().map(CategoryDto::from).toList();
        return categoryListTreeResponse;
    }

}

