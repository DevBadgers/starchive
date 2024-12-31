package com.starchive.springapp.image.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.starchive.springapp.post.domain.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostImage {
    @Id
    @GeneratedValue
    private Long id;

    @Lob
    String imagePath;

    LocalDateTime uploadDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "postId")
    Post post;

    public void setPost(Post post) {
        this.post = post;
    }
}
