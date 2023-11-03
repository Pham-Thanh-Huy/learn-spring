package com.example.learning_spring.controllers;

import com.example.learning_spring.dtos.*;
import com.example.learning_spring.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity<BaseResponse<JsonWebToken>> login(
            @RequestBody @Valid LoginRequest loginRequest) {
        BaseResponse<JsonWebToken> baseResponse = userService.login(loginRequest);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @PostMapping("forgotPassword")
    public ResponseEntity<BaseResponse<String>> notifyForgotPassword(@RequestParam Long userId) {
        BaseResponse<String> baseResponse = userService.forgotPasswordRequest(userId);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @PostMapping("resetPassword")
    public ResponseEntity<BaseResponse<String>> resetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        BaseResponse<String> baseResponse = userService.resetPassword(resetPasswordRequest);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @PostMapping("signup")
    public ResponseEntity<BaseResponse<UserDto>> signup(@RequestBody @Valid UserCreateDto userDto) {
        BaseResponse<UserDto> baseResponse = userService.create(userDto);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }
}
