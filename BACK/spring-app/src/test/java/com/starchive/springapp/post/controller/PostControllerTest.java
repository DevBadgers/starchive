package com.starchive.springapp.post.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starchive.springapp.post.dto.PostCreateRequest;
import com.starchive.springapp.post.service.PostService;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(PostController.class)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    @DisplayName("게시물 생성 성공")
    void createPost_Success() throws Exception {
        // Given
        PostCreateRequest request = new PostCreateRequest(
                "Test Title",
                "Test Content",
                "Author Name",
                "password123",
                1L,
                Arrays.asList(101L, 102L),
                Arrays.asList(201L, 202L)
        );

        // Mock PostService behavior
        Mockito.doNothing().when(postService).createPost(Mockito.any(PostCreateRequest.class));

        // When & Then)
        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("게시물 생성 실패 - 필수 필드 누락")
    void createPost_Failure_MissingFields() throws Exception {
        // Given
        PostCreateRequest request = new PostCreateRequest(); // 빈 객체로 필수 값 누락

        // When & Then
        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("게시물 생성 실패 - 제목 길이 초과")
    void createPost_Failure_TitleTooLong() throws Exception {
        // Given
        String longTitle = "a".repeat(65); // 제목 길이를 명시적으로 초과시킴 (65자)
        PostCreateRequest request = new PostCreateRequest(
                longTitle,
                "Test Content",
                "Author Name",
                "password123",
                1L,
                null,
                null
        );

        // When & Then
        mockMvc.perform(post("/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

}