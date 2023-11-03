package com.example.learning_spring.controllers;

import com.example.learning_spring.dtos.*;
import com.example.learning_spring.services.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<PostDto>> create(@RequestBody @Valid PostCreateDto postCreateDto, HttpServletRequest request) throws Exception {
        BaseResponse<PostDto> baseResponse = postService.create(postCreateDto, request);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @GetMapping("/all")
    public ResponseEntity<BaseResponse<List<PostDto>>> getAll(QueryParams params) {
        System.out.println(params);
        BaseResponse<List<PostDto>> baseResponse = postService.getAll(params);

        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));
    }


    @GetMapping(path = "byAuthor/{id}")
    public ResponseEntity<BaseResponse<List<PostDto>>> getByAuthorId(@PathVariable Long id, QueryParams params) {
        BaseResponse<List<PostDto>> baseResponse = postService.getByTopicId(id, params);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));
    }

    @GetMapping(path = "byTopic/{id}")
    public ResponseEntity<BaseResponse<List<PostDto>>> getByTopicId(@PathVariable Long id, QueryParams params) {
        BaseResponse<List<PostDto>> baseResponse = postService.getByTopicId(id, params);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));
    }


    @GetMapping(path = "byReadList/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<List<PostDto>>> getByReadListId(@PathVariable Long id, QueryParams params) {
        BaseResponse<List<PostDto>> baseResponse = postService.getByReadListId(id, params);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));

    }

    @GetMapping(path = "{id}")
    public ResponseEntity<BaseResponse<PostDto>> findById(@PathVariable Long id) throws Exception {
        BaseResponse<PostDto> baseResponse = postService.findById(id);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));

    }

    @GetMapping(path = "byId/{id}/reactions")
    public ResponseEntity<BaseResponse<List<UserDto>>> getUserReactions(@PathVariable Long id, QueryParams params) {
        BaseResponse<List<UserDto>> baseResponse = postService.getListUserReactionsByPost(id, params);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));
    }


    //
//    @GetMapping(path = "/published/byAuthor/{id}")
//    public List<PostDto> getPublishedByAuthorId(@PathVariable Long id, QueryParams params) {
//        return postService.getPublishedByAuthorId(id, params);
//    }
//
//    @GetMapping(path = "/drafts/byAuthor/{id}")
//    public List<PostDto> getDraftByAuthorId(@PathVariable Long id, QueryParams params) {
//        return postService.getDraftByAuthorId(id, params);
//    }
//
    @PutMapping(path = "edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<PostDto>> update(@PathVariable Long id, @RequestBody @Valid PostUpdateDto postDto, HttpServletRequest request) throws Exception {
        BaseResponse<PostDto> baseResponse = postService.update(id, postDto, request);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));
    }

    @DeleteMapping(path = "delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<Boolean>> delete(@PathVariable Long id, HttpServletRequest request) {
        BaseResponse<Boolean> baseResponse = postService.delete(id, request);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));

    }
}
