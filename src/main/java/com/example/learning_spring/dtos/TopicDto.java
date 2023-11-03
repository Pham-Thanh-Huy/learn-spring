package com.example.learning_spring.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TopicDto {

    private Long id;

    @NotBlank(message = "topic's name is required")
    private String name;

    private LocalDate createAt;

    private LocalDate updateAt;


}
