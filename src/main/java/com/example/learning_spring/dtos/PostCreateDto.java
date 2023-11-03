package com.example.learning_spring.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateDto {
    @NotBlank(message = "title required")
    String title;
    String description;
    String content;
    Boolean published;

    List<Long> topicIds;
}
