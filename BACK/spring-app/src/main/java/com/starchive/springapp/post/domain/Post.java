package com.starchive.springapp.post.domain;

import static jakarta.persistence.FetchType.LAZY;

import com.starchive.springapp.category.domain.Category;
import com.starchive.springapp.post.dto.PostCreateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Table(name = "Posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postId")
    private Long id;

    @Column(nullable = false, length = 64)
    private String title;

    @Lob
    private String content;

    @Column(nullable = false, length = 32)
    private String author;

    @Column(nullable = false, length = 128)
    private String password;

    @Column(nullable = false, name = "datetime")
    private LocalDateTime createAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "categoryId")
    private Category category;

    public static Post of(PostCreateRequest request, Category category) {
        Post post = new Post();
        post.title = request.getTitle();
        post.content = request.getContent();
        post.author = request.getAuthor();
        post.password = request.getPassword();
        post.createAt = LocalDateTime.now();
        post.category = category;

        return post;
    }

}
