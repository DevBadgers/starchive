package com.starchive.springapp.category.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void init() {
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
    }

    @Test
    public void 목록_전체_조회_테스트() throws Exception {
        // when
        mockMvc.perform(get("/categorys")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roots").isArray())
                .andExpect(jsonPath("$.roots[0].name").value("알고리즘"))
                .andExpect(jsonPath("$.roots[0].children").isArray())
                .andExpect(jsonPath("$.roots[0].children[0].name").value("자료구조"))
                .andExpect(jsonPath("$.roots[0].children[1].name").value("다이나믹프로그래밍"))
                .andExpect(jsonPath("$.roots[1].name").value("프로젝트"))
                .andExpect(jsonPath("$.roots[1].children[0].name").value("요구사항"));

    }
}