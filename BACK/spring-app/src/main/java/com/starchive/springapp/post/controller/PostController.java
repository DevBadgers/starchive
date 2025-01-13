package com.starchive.springapp.post.controller;

import com.starchive.springapp.post.dto.PostCreateRequest;
import com.starchive.springapp.post.dto.PostListResponse;
import com.starchive.springapp.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "게시글")
public class PostController {
    private final PostService postService;

    @PostMapping("/post")
    @Operation(summary = "게시글 작성")
    public ResponseEntity<?> post(@Valid @RequestBody PostCreateRequest request) {

        postService.createPost(request);

        return ResponseEntity.status(201).build();
    }

    @GetMapping("/posts")
    @Operation(summary = "게시글 목록 조회")
    public ResponseEntity<?> findPosts(
            @RequestParam(name = "category", required = false) Long category,
            @RequestParam(name = "tag", required = false) Long tag,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        PostListResponse postListResponse = postService.findPosts(category, tag, page, pageSize);

        return ResponseEntity.ok(postListResponse);
    }
}
