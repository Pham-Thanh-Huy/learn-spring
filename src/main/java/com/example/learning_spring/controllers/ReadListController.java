package com.example.learning_spring.controllers;

import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.QueryParams;
import com.example.learning_spring.dtos.ReadListDto;
import com.example.learning_spring.dtos.ReadListUpdateDto;
import com.example.learning_spring.services.ReadListService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "read-lists")
public class ReadListController {

    @Autowired
    private ReadListService readListService;

    @GetMapping(path = "/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<List<ReadListDto>>> getByUserId(QueryParams params, HttpServletRequest request) {
        BaseResponse<List<ReadListDto>> baseResponse = readListService.getByUserId(params, request);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));

    }

    @PostMapping(path = "/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<ReadListDto>> create(@RequestBody @Valid ReadListDto readListDto, HttpServletRequest request) {
        BaseResponse<ReadListDto> baseResponse = readListService.create(readListDto, request);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));
    }

    @PutMapping(path = "/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<ReadListDto>> update(@PathVariable Long id, @RequestBody @Valid ReadListUpdateDto readListUpdateDto) {
        BaseResponse<ReadListDto> baseResponse = readListService.update(id, readListUpdateDto);
        return new ResponseEntity<>(baseResponse, HttpStatus.valueOf(baseResponse.getCode()));

    }
}
