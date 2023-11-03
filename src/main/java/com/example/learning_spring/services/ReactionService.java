package com.example.learning_spring.services;


import com.example.learning_spring.configuration.Constants;
import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.ReactionDto;
import com.example.learning_spring.models.Post;
import com.example.learning_spring.models.Reaction;
import com.example.learning_spring.models.User;
import com.example.learning_spring.repositories.PostRepository;
import com.example.learning_spring.repositories.ReactionRepository;
import com.example.learning_spring.repositories.UserRepository;
import com.example.learning_spring.security.JsonWebTokenProvider;
import com.example.learning_spring.utils.ReactionMapper;
import com.example.learning_spring.utils.UserMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReactionService {

    private final ReactionRepository reactionRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JsonWebTokenProvider jsonWebTokenProvider;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    private ReactionMapper reactionMapper;

    public ReactionService(ReactionRepository reactionRepository,
                           UserRepository userRepository,
                           PostRepository postRepository) {
        this.reactionRepository = reactionRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public BaseResponse<ReactionDto> create(ReactionDto reactionDto, HttpServletRequest request) {
        BaseResponse<ReactionDto> baseResponse = new BaseResponse<>();
        Reaction reaction = new Reaction();

        Long userId = jsonWebTokenProvider.getUserIdFromRequest(request);
        Optional<User> user = userRepository.findById(userId);

        Long postId = reactionDto.getPostId();
        Optional<Post> post = postRepository.findById(postId);

        user.ifPresent(reaction::setUser);

        post.ifPresent(reaction::setPost);

        Reaction entity = reactionRepository.save(reaction);
        baseResponse.setData(reactionMapper.toDto(entity));
        baseResponse.setCode(HttpStatus.CREATED.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        return baseResponse;
    }


    public BaseResponse<Boolean> delete(Long postId, HttpServletRequest request) {
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();

        Long userId = jsonWebTokenProvider.getUserIdFromRequest(request);
        reactionRepository.deleteByPostIdAndUserId(postId, userId);

        baseResponse.setData(true);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);

        return baseResponse;
    }
}
