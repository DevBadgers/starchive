package com.starchive.springapp.category.repository;

import com.starchive.springapp.category.domain.Category;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.children left join fetch c.parent WHERE c.id = :id")
    Optional<Category> findByIdWithChildren(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Category c LEFT JOIN FETCH c.children WHERE c.parent IS NULL")
    List<Category> findRootCategoriesWithChildren();

    Optional<Category> findByName(@Param("name") String name);
}
