package com.example.learning_spring.utils;

import com.example.learning_spring.dtos.TopicDto;
import com.example.learning_spring.models.Topic;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper {
    public TopicDto toDto(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());
        topicDto.setCreateAt(topic.getCreateAt());
        topicDto.setUpdateAt(topic.getUpdateAt());
        return topicDto;
    }
}
