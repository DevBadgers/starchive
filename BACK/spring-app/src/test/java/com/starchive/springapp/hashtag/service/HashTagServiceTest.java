package com.starchive.springapp.hashtag.service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.hashtag.dto.HashTagDto;
import com.starchive.springapp.hashtag.exception.HashTagNotFoundException;
import com.starchive.springapp.hashtag.repository.HashTagRepository;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import com.starchive.springapp.posthashtag.repository.PostHashTagRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class HashTagServiceTest {
    @Autowired
    HashTagService hashTagService;

    @Autowired
    EntityManager em;
    @Autowired
    private HashTagRepository hashTagRepository;

    @Autowired
    private PostHashTagRepository postHashTagRepository;

    @Test
    public void 해쉬태그_저장_테스트() throws Exception {
        //given
        //when
        hashTagService.save("DP");
        HashTag findOne = hashTagService.findOne("DP");

        //then
        assertThat(findOne.getName()).isEqualTo("DP");
    }

    @Test
    public void 존재하지_않는_해쉬태그_조회_예외_테스트() throws Exception {
        assertThatThrownBy(() -> hashTagService.findOne("DP"))
                .isInstanceOf(HashTagNotFoundException.class);
    }

    @Test
    public void 해쉬태그_존재_여부_확인_없으면_저장_테스트() {
        HashTagDto hashTagDto = hashTagService.findOneOrSave("DP");
        assertThat(hashTagDto.getName()).isEqualTo("DP");
    }

    @Test
    public void 해쉬태그_이름_수정_테스트() throws Exception {
        //given
        HashTag hashTag = hashTagService.save("DP");

        //when
        hashTagService.updateName(hashTag.getId(), "다이나믹 프로그래밍");
        HashTag findOne = hashTagService.findOne("다이나믹 프로그래밍");

        //then
        assertThat(findOne.getId()).isEqualTo(hashTag.getId());
    }

    @Test
    public void 해쉬태그_삭제_테스트() throws Exception {
        //given
        Post post1 = Post.builder().title("타이틀1").author("content").password("1234").createAt(LocalDateTime.now())
                .build();
        Post post2 = Post.builder().title("타이틀2").author("content").password("1234").createAt(LocalDateTime.now())
                .build();
        HashTag hashTag1 = hashTagService.save("DP");

        PostHashTag postHashTag1 = new PostHashTag(post1, hashTag1);
        PostHashTag postHashTag2 = new PostHashTag(post2, hashTag1);
        em.persist(post1);
        em.persist(post2);
        em.persist(postHashTag1);
        em.persist(postHashTag2);
        em.flush();
        em.clear();

        //when
        hashTagService.delete(hashTag1.getId());

        //then
        assertThat(hashTagRepository.findByName("DP")).isEmpty();
        assertThat(postHashTagRepository.findAllByHashTagId(hashTag1.getId())).isEmpty();

    }
}