package com.starchive.springapp.posthashtag.service;

import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.hashtag.service.HashTagService;
import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.posthashtag.domain.PostHashTag;
import com.starchive.springapp.posthashtag.repository.PostHashTagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostHashTagService {
    private final PostHashTagRepository postHashTagRepository;
    private final HashTagService hashTagService;

    public void storePostHashTag(List<Long> hashTagsIds, Post post) {

        if (hashTagsIds == null || hashTagsIds.isEmpty()) {
            return;
        }

        ArrayList<PostHashTag> postHashTags = new ArrayList<>();
        List<HashTag> hasTags = hashTagService.findManyByIds(hashTagsIds);

        hasTags.stream().forEach(hasTag -> {
            PostHashTag postHashTag = new PostHashTag(post, hasTag);
            postHashTags.add(postHashTag);
        });

        postHashTagRepository.saveAll(postHashTags);
    }
}
