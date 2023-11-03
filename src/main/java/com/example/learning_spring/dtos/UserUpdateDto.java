package com.example.learning_spring.dtos;

import com.example.learning_spring.models.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateDto {
    private final PasswordEncoder passwordEncoder;

    @Length(min = 8, message = "Length password must longer than 8 characters")
    String password;

    public UserUpdateDto(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void updateEntity(User user) {
        if (this.password != null) user.setPassword(passwordEncoder.encode(this.password));

    }
}
