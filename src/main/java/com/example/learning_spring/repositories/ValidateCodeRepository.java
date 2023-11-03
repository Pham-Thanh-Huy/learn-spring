package com.example.learning_spring.repositories;

import com.example.learning_spring.models.ValidateCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ValidateCodeRepository extends JpaRepository<ValidateCode, Long> {

    Optional<ValidateCode> findByValidateCode(String key);

    boolean existsByValidateCode(String key);

}
