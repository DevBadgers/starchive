package com.starchive.springapp.category.dto;

import com.starchive.springapp.category.domain.Category;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private List<CategoryDto> children;

    public static CategoryDto from(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.id = category.getId();
        categoryDto.name = category.getName();
        categoryDto.children = new ArrayList<>();
        category.getChildren()
                .forEach(child -> categoryDto.children.add(CategoryDto.of(child.getId(), child.getName())));

        return categoryDto;
    }

    public static CategoryDto of(Long id, String name) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.id = id;
        categoryDto.name = name;

        return categoryDto;
    }
}
