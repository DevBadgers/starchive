package com.starchive.springapp.category.dto;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryDtoTest {
    @Autowired
    CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    void DTO_단일_변환_테스트() {
        //given
        Category parent = new Category("알고리즘", null);
        Category child1 = new Category("자료구조", parent);
        Category child2 = new Category("다이나믹프로그래밍", parent);
        System.out.println("---------------------쿼리1");
        categoryRepository.save(parent);
        categoryRepository.save(child1);
        categoryRepository.save(child2);
        System.out.println("---------------------쿼리2");
        // 트랜잭션 종료 (영속성 컨텍스트 초기화)
        entityManager.flush();
        entityManager.clear();
        System.out.println("---------------------쿼리3");
        Category findOne = categoryRepository.findByIdWithChildren(parent.getId()).get();
        System.out.println("---------------------쿼리4");

        //when
        CategoryDto categoryDto = CategoryDto.from(findOne);

        //then
        Assertions.assertThat(categoryDto.getChildren().size()).isEqualTo(2);
    }

}