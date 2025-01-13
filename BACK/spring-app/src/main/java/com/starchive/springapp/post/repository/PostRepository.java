package com.starchive.springapp.post.repository;

import com.starchive.springapp.post.domain.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p "
            + "where (:categoryIds is null or p.category.id in :categoryIds) "
            + "and (:postIds is null or p.id in :postIds)")
    Page<Post> findManyByCategoryIds(@Param("categoryIds") List<Long> categoryIds,
                                     @Param("postIds") List<Long> postIds,
                                     Pageable pageable);

}
