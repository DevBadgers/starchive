package com.starchive.springapp.post.service;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.service.CategoryService;
import com.starchive.springapp.hashtag.dto.HashTagResponse;
import com.starchive.springapp.hashtag.service.HashTagService;
import com.starchive.springapp.image.service.PostImageService;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.post.dto.PostCreateRequest;
import com.starchive.springapp.post.dto.PostDto;
import com.starchive.springapp.post.dto.PostListResponse;
import com.starchive.springapp.post.repository.PostRepository;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import com.starchive.springapp.posthashtag.service.PostHashTagService;
import java.util.ArrayList;
import java.util.List;
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

    public void createPost(PostCreateRequest request) {
        Category category = categoryService.findOne(request.getCategoryId());
        Post post = Post.of(request, category);

        postRepository.save(post);

        postHashTagService.storePostHashTag(request.getHashTagIds(), post);

        postImageService.setPost(request.getImageIds(), post);

    }

    public Post findOne(Long postId) {
        return postRepository.findById(postId).orElseThrow();
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

        Page<PostDto> dtoPage = getPostDtos(posts);

        return PostListResponse.from(dtoPage);
    }

    private List<Long> extractCategoryIds(Category category) {
        List<Long> categoryIds = new ArrayList<>();

        categoryIds.add(category.getId());

        if (category.getChildren() != null) {
            category.getChildren().stream().forEach(child -> categoryIds.add(child.getId()));
        }

        return categoryIds;
    }


    private Page<PostDto> getPostDtos(Page<Post> posts) {
        Page<PostDto> dtoPage = posts.map(post -> {
            List<HashTagResponse> hashTagDtos = hashTagService.findManyByPost(post.getId());
            return PostDto.of(post, hashTagDtos);
        });
        return dtoPage;
    }
}
