package com.starchive.springapp.hashtag.repository;

import com.starchive.springapp.hashtag.domain.HashTag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {
    Optional<HashTag> findByName(String name);

    @Query("select distinct h from HashTag h "
            + "join fetch PostHashTag ph on h.id = ph.hashTag.id "
            + "join fetch Post p on ph.post.id = p.id "
            + "join fetch Category c on p.category.id = c.id "
            + "where c.id = :categoryId")
    List<HashTag> findAllByCategoryId(@Param("categoryId") Long categoryId);

    @Query("select h from HashTag h "
            + "join fetch PostHashTag ph on h.id = ph.hashTag.id "
            + "where ph.post.id = :postId")
    List<HashTag> findAllByPostId(@Param("postId") Long postId);

    List<HashTag> findManyByIdIn(@Param("ids") List<Long> ids);
}
