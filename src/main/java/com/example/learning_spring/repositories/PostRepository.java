package com.example.learning_spring.repositories;

import com.example.learning_spring.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("select p from Post p where p.author.id = :authorId")
    Page<Post> findByAuthorId(Pageable pageable, @Param("authorId") Long id);

    @Query("select p from Post p join p.topics t where t.id = :topicId")
    Page<Post> findByTopicId(Pageable pageable, @Param("topicId") Long id);

    @Query("select p from Post p join p.topics t where t.id = :topicId")
    List<Post> getAllByTopicId(@Param("topicId") Long id);

    @Query("select p from Post p where p.author.id = :authorId and p.published = true")
    Page<Post> findByAuthorIdAndPublished(Pageable pageable, @Param("authorId") Long id);

    @Query("select p from Post p where p.author.id = :authorId and p.published = false")
    List<Post> findByAuthorIdAndNotPublished(Pageable pageable, @Param("authorId") Long id);

    @Query("select p from Post p join p.readLists rl where rl.id = :readListId")
    Page<Post> findByReadListId(Pageable pageable, @Param("readListId") Long id);
}
