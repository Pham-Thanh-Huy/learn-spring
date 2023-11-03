package com.example.learning_spring.repositories;

import com.example.learning_spring.models.Reaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {

    @Query("select r from Reaction r where r.post.id = :postId")
    Page<Reaction> findAllByPostId(@Param("postId") Long postId, Pageable pageable);

    @Modifying
    @Query("delete from Reaction r where r.post.id = :postId and r.user.id = :userId ")
    void deleteByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);
}
