package com.example.learning_spring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReactionDto {
    private Long id;

    private Long userId;

    private Long postId;
}
