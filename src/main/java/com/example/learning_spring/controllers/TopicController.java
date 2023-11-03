package com.example.learning_spring.controllers;

import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.QueryParams;
import com.example.learning_spring.dtos.TopicDto;
import com.example.learning_spring.dtos.TopicUpdateDto;
import com.example.learning_spring.services.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicService topicService;

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<List<TopicDto>>> getAll(QueryParams queryParams) {
        BaseResponse<List<TopicDto>> baseResponse = topicService.getAll(queryParams);
        return new ResponseEntity<>(baseResponse, HttpStatusCode.valueOf(baseResponse.getCode()));
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<TopicDto>> create(@RequestBody @Valid TopicDto topicDto) {
        BaseResponse<TopicDto> baseResponse = topicService.create(topicDto);
        return new ResponseEntity<>(baseResponse, HttpStatusCode.valueOf(baseResponse.getCode()));
    }

    @DeleteMapping(path = "delete/{id}")
    @PreAuthorize("isAuthenticated() and hasAuthority('Admin')")
    public ResponseEntity<BaseResponse<Boolean>> delete(@PathVariable Long id) {
        BaseResponse<Boolean> baseResponse = topicService.delete(id);
        return new ResponseEntity<>(baseResponse, HttpStatusCode.valueOf(baseResponse.getCode()));
    }


    @PutMapping(path = "edit/{id}")
    @PreAuthorize("isAuthenticated() and hasAuthority('Admin')")
    public ResponseEntity<BaseResponse<TopicDto>> update(@PathVariable Long id, @RequestBody @Valid TopicUpdateDto topicUpdateDto) {
        BaseResponse<TopicDto> baseResponse = topicService.update(id, topicUpdateDto);
        return new ResponseEntity<>(baseResponse, HttpStatusCode.valueOf(baseResponse.getCode()));
    }
}
