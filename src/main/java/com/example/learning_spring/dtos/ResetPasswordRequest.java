package com.example.learning_spring.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ResetPasswordRequest {

    @NotEmpty(message = "key is required")
    private String key;

    @NotBlank(message = "password required")
    @Length(min = 8, message = "Length password must longer than 8 characters")
    private String newPassword;

}
