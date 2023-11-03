package com.example.learning_spring.utils;

import com.example.learning_spring.dtos.PostDto;
import com.example.learning_spring.dtos.TopicDto;
import com.example.learning_spring.dtos.UserDto;
import com.example.learning_spring.models.Post;
import com.example.learning_spring.models.Topic;
import com.example.learning_spring.models.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ModelMapper {

    public UserDto map(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public PostDto map(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setCreateAt(post.getCreateAt());
        postDto.setUpdateAt(post.getUpdateAt());
        postDto.setPublished(post.isPublished());
        postDto.setAuthor(map(post.getAuthor()));
        postDto.setTopics(post.getTopics().stream().map((element) -> map(element)).collect(Collectors.toList()));
        return postDto;
    }

    public TopicDto map(Topic topic) {
        TopicDto topicDto = new TopicDto();
        topicDto.setId(topic.getId());
        topicDto.setName(topic.getName());
        return topicDto;
    }

}
