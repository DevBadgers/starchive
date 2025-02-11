package com.starchive.springapp.posthashtag.repository;

import com.starchive.springapp.post.domain.Post;
import com.starchive.springapp.posthashtag.domain.PostHashTag;

import java.util.HashSet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {

    @Query("select ph from PostHashTag ph WHERE ph.hashTag.id = :hashTagId")
    List<PostHashTag> findAllByHashTagId(@Param("hashTagId") Long hasTagId);

    List<PostHashTag> post(Post post);

    @Query("select ph from PostHashTag ph where ph.post.id = :postId")
    List<PostHashTag> findAllByPostId(@Param("postId") Long postId);

    @Modifying
    @Query("DELETE FROM PostHashTag ph WHERE ph.hashTag.id = :hashTagId")
    void deleteAllByHashTagId(@Param("hashTagId") Long hasTagId);

    @Modifying
    @Query("delete from PostHashTag ph where ph.hashTag.id in :hashTagIds")
    void deleteAllByHashTagIds(@Param("hashTagIds") HashSet<Long> hashTagIds);

    @Modifying
    @Query("delete from PostHashTag ph where ph.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
