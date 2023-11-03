package com.example.learning_spring.utils;

import com.example.learning_spring.dtos.PostDto;
import com.example.learning_spring.models.Post;
import com.example.learning_spring.models.Reaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private ReadListMapper readListMapper;

    public PostDto toDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        postDto.setCreateAt(post.getCreateAt());
        postDto.setUpdateAt(post.getUpdateAt());
        postDto.setPublished(post.isPublished());
        postDto.setAuthor(userMapper.toDto(post.getAuthor()));
        postDto.setTopics(post.getTopics().stream().map((element) -> topicMapper.toDto(element)).collect(Collectors.toList()));
        postDto.setReadLists(post.getReadLists().stream().map((element) -> readListMapper.toDto(element)).collect(Collectors.toSet()));

        List<Reaction> reactions = post.getReactions();
        if (reactions != null) {
            postDto.setReactionCount((long) reactions.size());
        } else {
            postDto.setReactionCount(0L);
        }

        return postDto;
    }

    public List<PostDto> toDtos(List<Post> posts) {
        return posts.stream().map(this::toDto).collect(Collectors.toList());
    }
}
