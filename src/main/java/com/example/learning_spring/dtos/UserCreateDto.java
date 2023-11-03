package com.example.learning_spring.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCreateDto {
    private Long id;

    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "password required")
    @Length(min = 8, message = "Length password must longer than 8 characters")
    private String password;

    @NotBlank(message = "email required")
    @Email(message = "email not valid")
    private String email;

    @NotBlank(message = "role required")
    private String role = "User";

}
