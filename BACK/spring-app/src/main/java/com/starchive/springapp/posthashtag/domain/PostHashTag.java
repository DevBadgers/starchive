package com.starchive.springapp.posthashtag.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.starchive.springapp.hashtag.domain.HashTag;
import com.starchive.springapp.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PostHashTag")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postHashTagId")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "hashTagId")
    private HashTag hashTag;

    public PostHashTag(Post post, HashTag hashTag) {
        this.post = post;
        this.hashTag = hashTag;
    }


}
