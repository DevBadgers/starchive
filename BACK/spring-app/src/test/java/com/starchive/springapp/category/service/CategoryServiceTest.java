package com.starchive.springapp.category.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.dto.CategoryListTreeResponse;
import com.starchive.springapp.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryServiceTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    @BeforeEach
    void init() {
        //given
        Category parent1 = new Category("알고리즘", null);
        Category child1 = new Category("자료구조", parent1);
        Category child2 = new Category("다이나믹프로그래밍", parent1);
        Category parent2 = new Category("프로젝트", null);
        Category child3 = new Category("요구사항", parent2);
        Category parent3 = new Category("회고", null);
        categoryRepository.save(parent1);
        categoryRepository.save(child1);
        categoryRepository.save(child2);
        categoryRepository.save(parent2);
        categoryRepository.save(child3);
        categoryRepository.save(parent3);
    }


    @Test
    void 전체_목록_조회_테스트() {
        // when
        CategoryListTreeResponse response = categoryService.findAll();

        // then
        assertThat(response.getRoots()).hasSize(3);
        assertThat(response.getRoots().get(0).getName()).isEqualTo("알고리즘");
        assertThat(response.getRoots().get(0).getChildren()).hasSize(2);
        assertThat(response.getRoots().get(0).getChildren().get(0).getName()).isEqualTo("자료구조");
        assertThat(response.getRoots().get(0).getChildren().get(1).getName()).isEqualTo("다이나믹프로그래밍");
        assertThat(response.getRoots().get(1).getName()).isEqualTo("프로젝트");
        assertThat(response.getRoots().get(1).getChildren().get(0).getName()).isEqualTo("요구사항");
    }

}