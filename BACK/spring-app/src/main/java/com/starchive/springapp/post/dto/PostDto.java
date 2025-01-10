package com.starchive.springapp.post.dto;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.dto.CategorySimpleDto;
import com.starchive.springapp.hashtag.dto.HashTagResponse;
import com.starchive.springapp.post.domain.Post;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class PostDto {
    private List<CategorySimpleDto> categoryHier;
    private Long postId;
    private String title;
    private String author;
    private LocalDateTime createdAt;
    private String content;
    private List<HashTagResponse> hashTags;

    public static PostDto of(Post post, List<HashTagResponse> hashTagDtos) {
        PostDto postDto = new PostDto();

        postDto.categoryHier = setCategoryHier(post.getCategory());

        postDto.postId = post.getId();
        postDto.title = post.getTitle();
        postDto.author = post.getAuthor();
        postDto.createdAt = post.getCreateAt();
        postDto.content = setContent(post.getContent());

        postDto.hashTags = hashTagDtos;

        return postDto;

    }

    private static String setContent(String content) {
        if (content != null && content.length() > 350) {
            return content.substring(0, 350);
        }
        return content;
    }

    private static List<CategorySimpleDto> setCategoryHier(Category category) {
        List<CategorySimpleDto> categoryHier = new ArrayList<>();
        if (category == null) {
            return categoryHier;
        }
        if (category.getParent() != null) {
            Category parent = category.getParent();
            CategorySimpleDto parentCategoryDto = CategorySimpleDto.from(parent);
            categoryHier.add(parentCategoryDto);
        }

        CategorySimpleDto categoryDto = CategorySimpleDto.from(category);
        categoryHier.add(categoryDto);
        return categoryHier;
    }

}
