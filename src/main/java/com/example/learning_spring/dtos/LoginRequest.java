package com.example.learning_spring.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotNull(message = "Tên tài khoản không được để trống")
    @NotEmpty(message = "Tên tài khoản không được để trống")
    private String username;
    @NotNull(message = "Mật khẩu không được để trống")
    @NotEmpty(message = "Mật khẩu khoản không được để trống")
    private String password;
}
