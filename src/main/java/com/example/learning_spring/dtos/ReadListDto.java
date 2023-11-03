package com.example.learning_spring.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ReadListDto {
    Long id;

    @NotBlank(message = "name is required")
    String name;

    LocalDate createAt;

    LocalDate updateAt;
    UserDto user;
}
