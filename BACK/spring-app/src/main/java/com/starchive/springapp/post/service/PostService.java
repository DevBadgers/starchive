package com.starchive.springapp.post.service;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.category.service.CategoryService;
import com.starchive.springapp.image.service.PostImageService;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.post.dto.PostCreateRequest;
import com.starchive.springapp.post.repository.PostRepository;
import com.starchive.springapp.posthashtag.service.PostHashTagService;
import lombok.RequiredArgsConstructor;
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

    public void createPost(PostCreateRequest request) {
        Category category = categoryService.findOne(request.getCategoryId());
        Post post = Post.of(request, category);

        postRepository.save(post);

        postHashTagService.storePostHashTag(request.getHashTagIds(), post);

        postImageService.setPost(request.getImageIds(), post);

    }

}
