package com.starchive.springapp.category.dto;

import com.starchive.springapp.category.domain.Category;
import lombok.Data;

@Data
public class CategorySimpleDto {
    private Long categoryId;
    private String categoryName;

    public static CategorySimpleDto from(Category category) {
        CategorySimpleDto categorySimpleDto = new CategorySimpleDto();
        categorySimpleDto.categoryId = category.getId();
        categorySimpleDto.categoryName = category.getName();

        return categorySimpleDto;
    }
}
