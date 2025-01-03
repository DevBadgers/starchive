package com.starchive.springapp.category.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.repository.CategoryRepository;
import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class CategoryControllerTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    EntityManager entityManager;


    @Test
    public void 목록_전체_조회_테스트() throws Exception {
        //given
        Category parent = new Category("알고리즘", null);
        Category child1 = new Category("자료구조", parent);
        Category child2 = new Category("다이나믹프로그래밍", parent);
        Category parent1 = new Category("프로젝트", null);
        Category child3 = new Category("요구사항", parent1);
        categoryRepository.save(parent);
        categoryRepository.save(child1);
        categoryRepository.save(child2);
        categoryRepository.save(parent1);
        categoryRepository.save(child3);

        // when
        mockMvc.perform(get("/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("알고리즘"))
                .andExpect(jsonPath("$.data[0].children").isArray())
                .andExpect(jsonPath("$.data[0].children[0].name").value("자료구조"))
                .andExpect(jsonPath("$.data[0].children[1].name").value("다이나믹프로그래밍"))
                .andExpect(jsonPath("$.data[1].name").value("프로젝트"))
                .andExpect(jsonPath("$.data[1].children[0].name").value("요구사항"));

    }

    @Test
    public void 특정_카테고리에_포함되는_해쉬태그_목록_조회_통합_테스트() throws Exception {
        // Given
        Category category = new Category("알고리즘", null);
        entityManager.persist(category);

        Post post = Post.builder()
                .title("알고리즘 기초")
                .content("알고리즘을 학습합시다.")
                .author("홍길동")
                .password("1234")
                .createAt(LocalDateTime.now())
                .category(category)
                .build();
        entityManager.persist(post);

        HashTag hashTag1 = new HashTag("자료구조");
        HashTag hashTag2 = new HashTag("다이나믹 프로그래밍");
        entityManager.persist(hashTag1);
        entityManager.persist(hashTag2);

        PostHashTag postHashTag1 = new PostHashTag(post, hashTag1);
        entityManager.persist(postHashTag1);

        PostHashTag postHashTag2 = new PostHashTag(post, hashTag2);
        entityManager.persist(postHashTag2);

        // When & Then
        mockMvc.perform(get("/categories/{categoryId}/hashtags", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(hashTag1.getId()))
                .andExpect(jsonPath("$.data[0].name").value(hashTag1.getName()))
                .andExpect(jsonPath("$.data[1].id").value(hashTag2.getId()))
                .andExpect(jsonPath("$.data[1].name").value(hashTag2.getName()));
    }
}