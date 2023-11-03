package com.example.learning_spring.repositories;

import com.example.learning_spring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    boolean existsById(Long id);


    Optional<User> findByUsername(String username);


}
