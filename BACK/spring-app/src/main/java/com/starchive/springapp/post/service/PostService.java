package com.starchive.springapp.post.service;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.service.CategoryService;
import com.starchive.springapp.hashtag.dto.HashTagResponse;
import com.starchive.springapp.hashtag.service.HashTagService;
import com.starchive.springapp.image.domain.PostImage;
import com.starchive.springapp.image.repository.PostImageRepository;
import com.starchive.springapp.image.service.PostImageService;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.post.dto.*;
import com.starchive.springapp.post.exception.InvalidPasswordException;
import com.starchive.springapp.post.exception.PostNotFoundException;
import com.starchive.springapp.post.repository.PostRepository;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import com.starchive.springapp.posthashtag.service.PostHashTagService;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostHashTagService postHashTagService;
    private final CategoryService categoryService;
    private final PostImageService postImageService;
    private final HashTagService hashTagService;
    private final PostImageRepository postImageRepository;

    public void createPost(PostCreateRequest request) {
        Category category = categoryService.findOne(request.getCategoryId());
        Post post = Post.of(request, category);

        postRepository.save(post);

        postHashTagService.storePostHashTag(request.getHashTagIds(), post);

        postImageService.setPost(request.getImageIds(), post);

    }

    public PostDto findOne(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);

        List<HashTagResponse> hashTagResponses = hashTagService.findManyByPost(post.getId());

        return PostDto.of(post, hashTagResponses);
    }

    //todo: fetch join (양방향 연관관계)
    public PostListResponse findPosts(Long categoryId, Long hashTagId, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<Long> categoryIds = null;
        if (categoryId != null) {
            Category category = categoryService.findOne(categoryId);
            categoryIds = extractCategoryIds(category);

        }

        List<Long> postIds = null;
        if (hashTagId != null) {
            postIds = new ArrayList<>();
            List<PostHashTag> postHashTags = postHashTagService.findManyByHashTag(hashTagId);
            for (PostHashTag postHashTag : postHashTags) {
                postIds.add(postHashTag.getPost().getId());
            }
            if (postIds.isEmpty()) {
                postIds = null;
            }
        }

        Page<Post> posts = postRepository.findManyByCategoryIds(categoryIds, postIds, pageable);

        Page<PostSimpleDto> dtoPage = getPostDtos(posts);

        return PostListResponse.from(dtoPage);
    }

    public PostDto update(PostUpdateRequest request) {
        Post post = postRepository.findById(request.getId()).orElseThrow(PostNotFoundException::new);

        if(request.getPassword() != post.getPassword()) {
            throw new InvalidPasswordException();
        }

        Category category = categoryService.findOne(request.getCategoryId());

        post.update(request, category);

        HashSet<Long> hashTagIds = new HashSet<>();
        postHashTagService.findManyByPost(post.getId()).stream().forEach(postHashTag -> {hashTagIds.add(postHashTag.getHashTag().getId());});

        updatePostHashTags(request, hashTagIds, post);

        updateImages(request, post);

        List<HashTagResponse> hashTagResponses = hashTagService.findManyByPost(post.getId());
        return PostDto.of(post, hashTagResponses);
    }


    public void updateImages(PostUpdateRequest postUpdateRequest, Post post){
        String content = postUpdateRequest.getContent();
        HashSet<String> imageUrlsToUpdate = new HashSet<>();

        imageUrlsToUpdate.addAll(extractImageUrls(content));

        List<PostImage> postImages = postImageRepository.findAllByPostId(postUpdateRequest.getId());
        List<PostImage> imageIdsToDelete = new ArrayList<>();

        for (PostImage postImage : postImages) {
            if (imageUrlsToUpdate.contains(postImage.getImagePath())) {
                imageUrlsToUpdate.remove(postImage.getImagePath());
                continue;
            }
            imageIdsToDelete.add(postImage);
        }

        postImageRepository.deleteAll(imageIdsToDelete);
        postImageService.setPostByImagePath(new ArrayList<>(imageUrlsToUpdate),post);
    }

    public static List<String> extractImageUrls(String markdownContent) {
        List<String> imageUrls = new ArrayList<>();

        // 정규식 패턴 정의
        String regex = "!\\[.*?\\]\\((.*?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(markdownContent);

        // 매칭된 이미지 URL 추출
        while (matcher.find()) {
            imageUrls.add(matcher.group(1));
        }

        return imageUrls;
    }

    private void updatePostHashTags(PostUpdateRequest request, HashSet<Long> hashTagIds, Post post) {
        List<Long> hashTagIdsToUpdate = new ArrayList<>();
        if(request.getHashTagIds() != null) {
            for (Long updatedHashTagId : request.getHashTagIds()) {
                if(hashTagIds.contains(updatedHashTagId)) {
                   hashTagIds.remove(updatedHashTagId);
                   continue;
                }
                hashTagIdsToUpdate.add(updatedHashTagId);
            }
        }

        postHashTagService.storePostHashTag(hashTagIdsToUpdate, post);
        postHashTagService.deleteManyByHashTagIds(hashTagIds);
    }

    private List<Long> extractCategoryIds(Category category) {
        List<Long> categoryIds = new ArrayList<>();

        categoryIds.add(category.getId());

        if (category.getChildren() != null) {
            category.getChildren().stream().forEach(child -> categoryIds.add(child.getId()));
        }

        return categoryIds;
    }


    private Page<PostSimpleDto> getPostDtos(Page<Post> posts) {
        Page<PostSimpleDto> dtoPage = posts.map(post -> {
            List<HashTagResponse> hashTagDtos = hashTagService.findManyByPost(post.getId());
            return PostSimpleDto.of(post, hashTagDtos);
        });
        return dtoPage;
    }
}
