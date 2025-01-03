package com.starchive.springapp.category.dto;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
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

    @Test
    void DTO_트리_변환_테스트() {
        //given
        Category parent1 = new Category("알고리즘", null);
        Category child1 = new Category("자료구조", parent1);
        Category child2 = new Category("다이나믹프로그래밍", parent1);
        Category child4 = new Category("그리디", parent1);
        Category parent2 = new Category("프로젝트", null);
        Category child3 = new Category("요구사항", parent2);
        Category parent3 = new Category("회고", null);
        categoryRepository.save(parent1);
        categoryRepository.save(child1);
        categoryRepository.save(child2);
        categoryRepository.save(child4);
        categoryRepository.save(parent2);
        categoryRepository.save(child3);
        categoryRepository.save(parent3);
        System.out.println("---------------------쿼리1");
        // 트랜잭션 종료 (영속성 컨텍스트 초기화)
        entityManager.flush();
        entityManager.clear();
        System.out.println("---------------------쿼리2");

        //when
        List<Category> rootCategoriesWithChildren = categoryRepository.findRootCategoriesWithChildren();
        System.out.println("---------------------쿼리3");

        for (Category rootCategories : rootCategoriesWithChildren) {
            System.out.println("root " + rootCategories.getName());
            for (Category child : rootCategories.getChildren()) {
                System.out.print(child.getName() + " ");
            }
            System.out.println();
        }
        //then
        Assertions.assertThat(rootCategoriesWithChildren.size()).isEqualTo(3);
    }

}