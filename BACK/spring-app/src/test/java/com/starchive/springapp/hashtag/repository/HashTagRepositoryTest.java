package com.starchive.springapp.hashtag.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class HashTagRepositoryTest {
    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 카테고리에_포함되는_해쉬태그_조회_테스트() {
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

        // When
        List<HashTag> result = hashTagRepository.findAllByCategoryId(category.getId());

        // Then
        assertThat(result)
                .hasSize(2)
                .extracting(HashTag::getName)
                .containsExactlyInAnyOrder("자료구조", "다이나믹 프로그래밍");
    }
}