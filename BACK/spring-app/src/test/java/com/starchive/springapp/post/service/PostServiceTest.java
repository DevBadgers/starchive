package com.starchive.springapp.post.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.repository.CategoryRepository;
import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.hashtag.repository.HashTagRepository;
import com.starchive.springapp.image.domain.PostImage;
import com.starchive.springapp.image.dto.PostImageDto;
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
import com.starchive.springapp.s3.S3MockConfig;
import com.starchive.springapp.s3.S3Service;
import jakarta.persistence.EntityManager;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Import(S3MockConfig.class)
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
    @Qualifier("MockS3Client")
    private AmazonS3 amazonS3;

    @Autowired
    S3MockConfig s3MockConfig;

    @Autowired
    S3Service s3Service;

    @Autowired
    private PostImageService postImageService;

    @BeforeEach
    public void resetS3Bucket() {
        String bucketName = s3MockConfig.getTestBucketName();

        // 기존 버킷 삭제 (모든 객체도 함께 삭제됨)
        if (amazonS3.doesBucketExistV2(bucketName)) {
            amazonS3.deleteBucket(bucketName);
        }

        // 버킷 재생성
        amazonS3.createBucket(bucketName);
    }

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
    public void 게시글_수정_통합_테스트() throws NoSuchFieldException, IllegalAccessException {
        //given
        String path1 = "imagePath1.jpg";
        String path2 = "imagePath2.jpg";
        String contentType = "image/jpg";
        String bucket = s3MockConfig.getTestBucketName();

        MockMultipartFile file1 = new MockMultipartFile("test1", path1, contentType, "test1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("test2", path2, contentType, "test2".getBytes());

        //Mock S3에 이미지 저장
        setS3ConfigForTest(bucket);

        PostImageDto postImageDto1 = postImageService.uploadImage(file1);
        PostImageDto postImageDto2 = postImageService.uploadImage(file2);

        Category category = new Category("예시카테고리", null);
        categoryRepository.save(category);

        List<Long> postImageIds = new ArrayList<>(List.of(postImageDto1.getId(), postImageDto2.getId()));
        String markdownText = """
            Here is an image example:
            ![이미지](%s)
            ![이미지](%s)
            """.formatted(postImageDto1.getImagePath(), postImageDto2.getImagePath());

        PostCreateRequest postCreateRequest =
                new PostCreateRequest("title", markdownText, "author", "password"
                        , category.getId(), null, postImageIds);

        postService.createPost(postCreateRequest);

        ListObjectsV2Result s3Objects = amazonS3.listObjectsV2(bucket);
        Assertions.assertThat(s3Objects.getKeyCount()).isEqualTo(2);

        //when
        Post findOne = postRepository.findAll().getFirst();

        String updatedMarkdownText = """
            Here is an image example:
            ![이미지](%s)
            """.formatted(postImageDto1.getImagePath());

        PostUpdateRequest postUpdateRequest = new PostUpdateRequest(findOne.getId(), "title", updatedMarkdownText, "author", "password"
                , category.getId(), null, postImageIds);

        postService.update(postUpdateRequest);

        //then
        s3Objects = amazonS3.listObjectsV2(bucket);

        Assertions.assertThat(s3Objects.getKeyCount()).isEqualTo(1);
        Assertions.assertThat(postImageRepository.findAll()).hasSize(1);
        Assertions.assertThat(postImageRepository.findAll().getFirst().getImagePath()).isEqualTo(postImageDto1.getImagePath());
    }

    @Test
    public void 게시글_삭제_통합_테스트() throws NoSuchFieldException, IllegalAccessException {
        //given
        String path1 = "imagePath1.jpg";
        String path2 = "imagePath2.jpg";
        String contentType = "image/jpg";
        String bucket = s3MockConfig.getTestBucketName();

        MockMultipartFile file1 = new MockMultipartFile("test1", path1, contentType, "test1".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("test2", path2, contentType, "test2".getBytes());

        //Mock S3에 이미지 저장
        setS3ConfigForTest(bucket);

        PostImageDto postImageDto1 = postImageService.uploadImage(file1);
        PostImageDto postImageDto2 = postImageService.uploadImage(file2);

        Category category = new Category("예시카테고리", null);
        categoryRepository.save(category);

        HashTag hashTag = new HashTag("tag1");
        hashTagRepository.save(hashTag);

        List<Long> postImageIds = new ArrayList<>(List.of(postImageDto1.getId(), postImageDto2.getId()));
        String markdownText = """
            Here is an image example:
            ![이미지](%s)
            ![이미지](%s)
            """.formatted(postImageDto1.getImagePath(), postImageDto2.getImagePath());

        PostCreateRequest postCreateRequest =
                new PostCreateRequest("title", markdownText, "author", "password"
                        , category.getId(), Arrays.asList(hashTag.getId()), postImageIds);

        postService.createPost(postCreateRequest);

        ListObjectsV2Result s3Objects = amazonS3.listObjectsV2(bucket);

        Assertions.assertThat(s3Objects.getKeyCount()).isEqualTo(2);
        Assertions.assertThat(postImageRepository.findAll()).hasSize(2);
        Assertions.assertThat(postHashTagRepository.findAll()).hasSize(1);
        Assertions.assertThat(postRepository.findAll()).hasSize(1);

        //when
        Post post = postRepository.findAll().getFirst();
        postService.delete(post.getId());

        //then
        s3Objects = amazonS3.listObjectsV2(bucket);
        Assertions.assertThat(s3Objects.getKeyCount()).isEqualTo(0);
        Assertions.assertThat(postImageRepository.findAll()).hasSize(0);
        Assertions.assertThat(postHashTagRepository.findAll()).hasSize(0);
        Assertions.assertThat(postRepository.findAll()).hasSize(0);
    }

    private void setS3ConfigForTest(String bucket) throws NoSuchFieldException, IllegalAccessException {
        //Reflection s3Service
        Field reflectionFieldFor_amazonS3 = s3Service.getClass().getDeclaredField("amazonS3");
        reflectionFieldFor_amazonS3.setAccessible(true);
        reflectionFieldFor_amazonS3.set(s3Service, amazonS3);

        Field reflectionFieldFor_bucket = s3Service.getClass().getDeclaredField("bucket");
        reflectionFieldFor_bucket.setAccessible(true);
        reflectionFieldFor_bucket.set(s3Service, bucket);
    }


}