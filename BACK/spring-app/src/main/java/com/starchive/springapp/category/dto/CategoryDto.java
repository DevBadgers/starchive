package com.starchive.springapp.category.dto;

import com.starchive.springapp.category.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryDto {
    @Schema(description = "카테고리 식별 id 값", example = "1")
    private Long id;
    @Schema(description = "카테고리 이름", example = "알고리즘")
    private String name;
    @Schema(description = "하위 카테고리 목록", example = "{"
            + "          \"id\": 2,\n"
            + "          \"name\": \"자료구조\",\n"
            + "          \"children\": null\n"
            + "        }")
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
