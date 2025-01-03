package com.starchive.springapp.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.repository.CategoryRepository;
import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.hashtag.repository.HashTagRepository;
import com.starchive.springapp.image.domain.PostImage;
import com.starchive.springapp.image.repository.PostImageRepository;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.post.dto.PostCreateRequest;
import com.starchive.springapp.post.repository.PostRepository;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import com.starchive.springapp.posthashtag.repository.PostHashTagRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    HashTagRepository hashTagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    PostHashTagRepository postHashTagRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    PostService postService;

    @Test
    public void 게시글_작성_통합_테스트() throws Exception {
        //given
        HashTag hashTag = new HashTag("tag1");
        hashTagRepository.save(hashTag);
        HashTag hashTag2 = new HashTag("tag2");
        hashTagRepository.save(hashTag2);

        PostImage postImage = new PostImage("imagePath");
        postImageRepository.save(postImage);

        Category category = new Category("예시카테고리", null);
        categoryRepository.save(category);

        List<Long> hashTagIds = new ArrayList<>(List.of(hashTag.getId(), hashTag2.getId()));
        List<Long> postImageIds = new ArrayList<>(List.of(postImage.getId()));

        PostCreateRequest postCreateRequest =
                new PostCreateRequest("title", "content", "author", "password"
                        , category.getId(), hashTagIds, postImageIds);

        //when
        postService.createPost(postCreateRequest);

        Post createdPost = postRepository.findAll().getFirst();
        List<PostHashTag> postHashTags = postHashTagRepository.findAll();

        //then
        assertThat(createdPost.getCategory().getId()).isEqualTo(category.getId());
        assertThat(postImage.getPost().getId()).isEqualTo(createdPost.getId());
        assertThat(postHashTags.size()).isEqualTo(2);

    }

}