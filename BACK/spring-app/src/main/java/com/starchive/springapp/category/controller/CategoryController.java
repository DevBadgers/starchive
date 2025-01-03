package com.starchive.springapp.category.controller;

import com.starchive.springapp.category.dto.CategoryDto;
import com.starchive.springapp.category.service.CategoryService;
import com.starchive.springapp.global.dto.ResponseDto;
import com.starchive.springapp.hashtag.dto.HashTagDto;
import com.starchive.springapp.hashtag.service.HashTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "카테고리")
public class CategoryController {
    private final CategoryService categoryService;
    private final HashTagService hashTagService;

    @GetMapping("/categories")
    @Operation(summary = "카테고리 목록 전체 조회")
    public ResponseEntity<ResponseDto<List<CategoryDto>>> showCategories() {
        List<CategoryDto> categories = categoryService.findAll();
        ResponseDto<List<CategoryDto>> listResponseDto = new ResponseDto<>(categories);
        return ResponseEntity.ok(listResponseDto);
    }

    @GetMapping("/categories/{categoryId}/hashtags")
    @Operation(summary = "특정 카테고리에 포함되는 해쉬태그 목록 조회")
    public ResponseEntity<ResponseDto<List<HashTagDto>>> showHashTags(@PathVariable("categoryId") Long categoryId) {
        List<HashTagDto> categories = hashTagService.findManyByCategory(categoryId);
        ResponseDto<List<HashTagDto>> listResponseDto = new ResponseDto<>(categories);
        return ResponseEntity.ok(listResponseDto);
    }
}
