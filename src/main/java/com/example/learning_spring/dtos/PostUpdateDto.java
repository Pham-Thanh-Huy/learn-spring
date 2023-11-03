package com.example.learning_spring.dtos;

import com.example.learning_spring.models.Post;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostUpdateDto {
    String title;
    String description;
    String content;
    Boolean published;
    List<Long> topicIds;
    List<Long> readListIds;

    public void updateEntity(Post post) {
        if (title != null) {
            post.setTitle(title);
        }
        if (description != null) {
            post.setDescription(description);
        }
        if (content != null) {
            post.setContent(content);
        }
        if (published != null) {
            post.setPublished(published);
        }

    }
}
