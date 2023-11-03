package com.example.learning_spring.repositories;

import com.example.learning_spring.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    boolean existsByName(String name);

    boolean existsById(Long id);
}
