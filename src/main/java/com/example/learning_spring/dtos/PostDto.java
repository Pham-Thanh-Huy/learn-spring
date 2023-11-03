package com.example.learning_spring.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;

    @NotBlank(message = "title required")
    private String title;

    private String description;
    private String content;
    private LocalDate createAt;
    private LocalDate updateAt;
    private boolean published;
    private UserDto author;
    private List<TopicDto> topics;
    private Set<ReadListDto> readLists;
    private Long reactionCount = 0L;
}
