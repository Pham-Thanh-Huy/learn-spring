package com.example.learning_spring.controllers;

import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.dtos.QueryParams;
import com.example.learning_spring.dtos.UserDto;
import com.example.learning_spring.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("all")
    @PreAuthorize("isAuthenticated() and hasAuthority('Admin')")
    public ResponseEntity<BaseResponse<List<UserDto>>> getAll(
            QueryParams queryParams
    ) {
        BaseResponse<List<UserDto>> baseResponse = userService.getAll(queryParams);
        return new ResponseEntity<>(
                baseResponse,
                HttpStatus.valueOf(baseResponse.getCode())
        );
    }


//    @DeleteMapping(path = "delete/{id}")
//    public void delete(@PathVariable Long id) throws Exception {
//        userService.delete(id);
//    }
//
//    @PutMapping(path = "edit/{id}")
//    public UserDto update(@PathVariable Long id, @RequestBody @Valid UserUpdateDto userDto) {
//        return userService.update(id, userDto);
//    }


}
