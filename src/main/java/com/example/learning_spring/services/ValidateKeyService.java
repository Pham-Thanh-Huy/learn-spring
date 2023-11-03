package com.example.learning_spring.services;

import com.example.learning_spring.models.User;
import com.example.learning_spring.models.ValidateCode;
import com.example.learning_spring.repositories.ValidateCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ValidateKeyService {

    private final int KEY_LENGTH = 8;
    private final ValidateCodeRepository validateCodeRepository;

    public ValidateCode createValidateCode(User user) {
        ValidateCode validateCode = new ValidateCode();
        validateCode.setValidateCode(randomKey());
        validateCode.setUser(user);
        while (validateCodeRepository.existsByValidateCode(validateCode.getValidateCode())) {
            validateCode.setValidateCode(randomKey());
        }
        validateCode.setCreatedAt(LocalDateTime.now());
        return validateCodeRepository.save(validateCode);
    }

    private String randomKey() {
        StringBuilder keyBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < KEY_LENGTH; ++i) {
            keyBuilder.append(
                    Math.abs(random.nextInt() % 10)
            );
        }
        return keyBuilder.toString();
    }


}
