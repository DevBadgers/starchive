package com.starchive.springapp.category.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.starchive.springapp.category.domain.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void 카테고리_단일_저장_테스트() throws Exception {
        //given
        Category category = new Category("스터디", null);
        Category save = categoryRepository.save(category);
        //when
        Category findOne = categoryRepository.findById(category.getId()).get();
        //then
        assertThat(findOne).isEqualTo(category);
    }

    @Test
    void 카테고리_여러개_저장_부모_자식_관계_테스트() {
        //given
        Category category1 = new Category("스터디", null);
        Category category2 = new Category("디자인패턴", category1);
        categoryRepository.save(category1);
        categoryRepository.save(category2);

        Category findParent = categoryRepository.findById(category1.getId()).get();
        Category findChild = categoryRepository.findById(category2.getId()).get();

        //then
        assertThat(findParent.getChildren().size()).isEqualTo(1);
        assertThat(findChild.getParent().getId()).isEqualTo(category1.getId());
    }

    @Test
    public void 카테고리_여러개_저장_테스트() throws Exception {
        //given
        Category parent = new Category("스터디", null);
        Category child1 = new Category("디자인패턴", parent);
        Category descendent = new Category("싱글톤", child1);
        Category child2 = new Category("소프트웨어공학", parent);
        categoryRepository.save(parent);
        categoryRepository.save(child1);
        categoryRepository.save(descendent);
        categoryRepository.save(child2);

        //when
        Category findParent = categoryRepository.findById(parent.getId()).get();
        Category findChild = findParent.getChildren().stream().filter(category -> category.getName().equals("디자인패턴"))
                .findFirst().get();
        Category findDescendant = findChild.getChildren().stream().findFirst().get();
        //then
        assertThat(findParent.getChildren().size()).isEqualTo(2);
        assertThat(findDescendant.getName()).isEqualTo("싱글톤");
    }
}