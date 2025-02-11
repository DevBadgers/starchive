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
import com.starchive.springapp.post.dto.PostDto;
import com.starchive.springapp.post.dto.PostListResponse;
import com.starchive.springapp.post.repository.PostRepository;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import com.starchive.springapp.posthashtag.repository.PostHashTagRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
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

    @Autowired
    EntityManager em;

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

    @Test
    public void 게시글_전체조회_통합_테스트() throws Exception {
        //given
        Category category = new Category("카테고리", null);
        categoryRepository.save(category);

        Post post1 = new Post(null, "title1", "content", "author1", "123", LocalDateTime.now(), category);
        Post post2 = new Post(null, "title2", "content", "author2", "123", LocalDateTime.now(), category);
        postRepository.saveAll(List.of(post1, post2));

        HashTag hashTag = new HashTag("tag1");
        hashTagRepository.save(hashTag);
        postHashTagRepository.save(new PostHashTag(post1, hashTag));

        //when
        PostListResponse response = postService.findPosts(category.getId(), null, 0, 10);

        //then
        assertThat(response.getPosts()).hasSize(2);
        assertThat(response.getPosts()).extracting("title").containsExactlyInAnyOrder("title1", "title2");
        assertThat(response.getPosts().get(1).getHashTags()).extracting("name").containsExactly("tag1");
    }

    @Test
    public void 게시글_상세조회_통합_테스트() throws Exception {
        //given
        Category category = new Category("카테고리", null);
        categoryRepository.save(category);

        Post post1 = new Post(null, "title1", "content", "author1", "123", LocalDateTime.now(), category);
        postRepository.save(post1);
        HashTag hashTag = new HashTag("tag1");
        hashTagRepository.save(hashTag);

        postHashTagRepository.save(new PostHashTag(post1, hashTag));

        em.flush();
        em.clear();
        //when
        PostDto findOne = postService.findOne(post1.getId());

        //then
        assertThat(findOne.getTitle()).isEqualTo(post1.getTitle());

    }


}