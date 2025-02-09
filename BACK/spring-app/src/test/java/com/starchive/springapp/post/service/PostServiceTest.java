package com.starchive.springapp.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.repository.CategoryRepository;
import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.hashtag.repository.HashTagRepository;
import com.starchive.springapp.image.domain.PostImage;
import com.starchive.springapp.image.repository.PostImageRepository;
import com.starchive.springapp.image.service.PostImageService;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.post.dto.PostCreateRequest;
import com.starchive.springapp.post.dto.PostDto;
import com.starchive.springapp.post.dto.PostListResponse;
import com.starchive.springapp.post.dto.PostUpdateRequest;
import com.starchive.springapp.post.exception.InvalidPasswordException;
import com.starchive.springapp.post.repository.PostRepository;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import com.starchive.springapp.posthashtag.repository.PostHashTagRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
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
    @Autowired
    private PostImageService postImageService;

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
        assertThat(response.getPosts().get(0).getHashTags()).extracting("name").containsExactly("tag1");
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

    @Test
    public void 게시글_수정_틀린_비밀번호_예외_테스트(){
        //given
        Category category = new Category("예시카테고리", null);
        categoryRepository.save(category);

        PostCreateRequest postCreateRequest =
                new PostCreateRequest("title", "content", "author", "1234"
                        , category.getId(), null, null);


        postService.createPost(postCreateRequest);

        Post createdPost = postRepository.findAll().getFirst();
        //when
        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(createdPost.getId(), "title", "content", "author", "12345"
                , category.getId(), null, null);

        //then
        Assertions.assertThatThrownBy(()-> postService.update(postUpdateRequest)).isInstanceOf(InvalidPasswordException.class);
    }

    @Test
    public void 게시글_수정_게시글에_포함되는_이미지_변경_테스트(){
        //given
        PostImage postImage1 = new PostImage("imagePath1.jpg");
        postImageRepository.save(postImage1);
        PostImage postImage2 = new PostImage("imagePath2.jpg");
        postImageRepository.save(postImage2);

        Category category = new Category("예시카테고리", null);
        categoryRepository.save(category);

        List<Long> postImageIds = new ArrayList<>(List.of(postImage1.getId(), postImage2.getId()));
        String markdownText = """
            Here is an image example:
            ![이미지](imagePath1.jpg)
            ![이미지](imagePath2.jpg)
            """;

        PostCreateRequest postCreateRequest =
                new PostCreateRequest("title", markdownText, "author", "password"
                        , category.getId(), null, postImageIds);

        postService.createPost(postCreateRequest);

        Post findOne = postRepository.findAll().getFirst();

        String updatedMarkdownText = """
            Here is an image example:
            ![이미지](imagePath1.jpg)
            """;

        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(findOne.getId(), "title", updatedMarkdownText, "author", "password"
                , category.getId(), null, postImageIds);

        //when
        postService.update(postUpdateRequest);

        //then
        Assertions.assertThat(postImageRepository.findAll()).hasSize(1);
        Assertions.assertThat(postImageRepository.findAll().getFirst().getImagePath()).isEqualTo(postImage1.getImagePath());
    }


}