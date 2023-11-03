package com.example.learning_spring.controllers;


import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.ReactionDto;
import com.example.learning_spring.services.ReactionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reactions")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<ReactionDto>> create(@RequestBody @Valid ReactionDto reactionDto, HttpServletRequest request) {
        BaseResponse<ReactionDto> baseResponse = reactionService.create(reactionDto, request);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @DeleteMapping("delete/byPost/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<BaseResponse<Boolean>> delete(@PathVariable Long id, HttpServletRequest request) {
        BaseResponse<Boolean> baseResponse = reactionService.delete(id, request);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }
}
