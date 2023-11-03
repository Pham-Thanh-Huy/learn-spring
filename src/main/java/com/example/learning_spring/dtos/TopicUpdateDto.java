package com.example.learning_spring.dtos;

import com.example.learning_spring.models.Topic;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TopicUpdateDto {
    String name;
   

    public void updateEntity(Topic topic) {
        if (name != null) {
            topic.setName(name);
        }
    }
}
