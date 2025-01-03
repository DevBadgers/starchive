package com.starchive.springapp.image.repository;

import com.starchive.springapp.image.domain.PostImage;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    @Query("SELECT p FROM PostImage p WHERE p.post IS NULL AND p.uploadDate < :cutoffDate")
    List<PostImage> findOldOrphanedPostImages(@Param("cutoffDate") LocalDateTime cutoffDate);

    @Modifying
    @Query("DELETE FROM PostImage p WHERE p.id IN :ids")
    void deleteByIds(@Param("ids") List<Long> ids);

    List<PostImage> findManyByIdIn(@Param("ids") List<Long> ids);
}
