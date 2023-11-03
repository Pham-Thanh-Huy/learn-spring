package com.example.learning_spring.services;

import com.example.learning_spring.configuration.Constants;
import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.QueryParams;
import com.example.learning_spring.dtos.TopicDto;
import com.example.learning_spring.dtos.TopicUpdateDto;
import com.example.learning_spring.models.Topic;
import com.example.learning_spring.repositories.TopicRepository;
import com.example.learning_spring.utils.TopicMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    private TopicMapper topicMapper;

    @Autowired
    private PostService postService;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }


    public BaseResponse<List<TopicDto>> getAll(QueryParams queryParams) {
        List<TopicDto> topicDtos = topicRepository.findAll().stream().map(entity -> topicMapper.toDto(entity)).toList();
        BaseResponse<List<TopicDto>> baseResponse = new BaseResponse<>();
        baseResponse.setData(topicDtos);
        baseResponse.setCode(HttpStatus.OK.value());
        return baseResponse;
    }

    public BaseResponse<TopicDto> create(@Valid TopicDto topicDto) {
        BaseResponse<TopicDto> baseResponse = new BaseResponse<>();
        if (topicRepository.existsByName(topicDto.getName())) {
            baseResponse.setMessage("Topic already exists");
            baseResponse.setCode(400);
        } else {
            Topic topic = new Topic();
            topic.setName(topicDto.getName());

            Topic entity = topicRepository.save(topic);
            baseResponse.setData(topicMapper.toDto(entity));
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        }

        return baseResponse;
    }

    public BaseResponse<Boolean> delete(Long id) {
        postService.removeTopicFromAllPosts(id);
        topicRepository.deleteById(id);
        BaseResponse<Boolean> baseResponse = new BaseResponse<>();
        baseResponse.setData(true);
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        return baseResponse;
    }


    public BaseResponse<TopicDto> update(Long id, TopicUpdateDto topicUpdateDto) {
        BaseResponse<TopicDto> baseResponse = new BaseResponse<>();
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (topicOptional.isEmpty()) {
            baseResponse.setMessage("Topic not found");
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
        } else {
            Topic topic = topicOptional.get();
            topicUpdateDto.updateEntity(topic);
            Topic entity = topicRepository.save(topic);
            baseResponse.setData(topicMapper.toDto(entity));
            baseResponse.setCode(HttpStatus.OK.value());
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        }

        return baseResponse;
    }
}
