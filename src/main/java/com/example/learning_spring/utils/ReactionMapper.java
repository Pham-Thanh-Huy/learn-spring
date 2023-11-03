package com.example.learning_spring.utils;

import com.example.learning_spring.dtos.ReactionDto;
import com.example.learning_spring.models.Reaction;
import org.springframework.stereotype.Component;

@Component
public class ReactionMapper {
    public ReactionDto toDto(Reaction reaction) {
        ReactionDto reactionDto = new ReactionDto();
        reactionDto.setId(reaction.getId());
        reactionDto.setUserId(reaction.getUser().getId());
        reactionDto.setPostId(reaction.getPost().getId());

        return reactionDto;
    }
}
