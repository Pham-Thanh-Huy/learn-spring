package com.example.learning_spring.handlers;

import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.JsonWebToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<String> list = ex.getBindingResult()
                .getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        BaseResponse<String> baseResponse = new BaseResponse<>();
        baseResponse.setMessage(list.stream().collect(Collectors.joining(", ")));
        baseResponse.setCode(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(
                baseResponse, HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<BaseResponse<String>> handleAccessDeniedException(
            AccessDeniedException e
    ) {
        BaseResponse<String> baseResponse = new BaseResponse<>(
                "You do not have permission!",
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @ExceptionHandler({BadCredentialsException.class})
    public ResponseEntity<BaseResponse<String>> handleBadCredentialException(
            BadCredentialsException e
    ) {
        BaseResponse<String> baseResponse = new BaseResponse<>(
                "Incorrect username or password!",
                HttpStatus.UNAUTHORIZED.value()
        );
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<BaseResponse<String>> handleSystemException(Exception e) {
        //  Logging
        log.error("[handleSystemException]", e);
        BaseResponse<String> baseResponse = new BaseResponse<>(
                "Internal server exception!",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }
}
