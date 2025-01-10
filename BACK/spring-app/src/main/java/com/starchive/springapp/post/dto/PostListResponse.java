package com.starchive.springapp.post.dto;

import java.util.List;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
public class PostListResponse {
    private int currentPage;
    private int totalPages;
    private long totalCount;
    private List<PostDto> posts;

    public static PostListResponse from(Page<PostDto> dtoPage) {
        PostListResponse postListResponse = new PostListResponse();
        postListResponse.currentPage = dtoPage.getNumber();
        postListResponse.totalPages = dtoPage.getTotalPages();
        postListResponse.totalCount = dtoPage.getTotalElements();
        postListResponse.posts = dtoPage.getContent();
        return postListResponse;
    }

}
