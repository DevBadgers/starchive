package com.starchive.springapp.post.repository;

import com.starchive.springapp.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
