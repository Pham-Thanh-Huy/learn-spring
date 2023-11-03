package com.example.learning_spring.services;

import com.example.learning_spring.configuration.Constants;
import com.example.learning_spring.dtos.*;
import com.example.learning_spring.models.Role;
import com.example.learning_spring.models.User;
import com.example.learning_spring.repositories.RoleRepository;
import com.example.learning_spring.repositories.UserRepository;
import com.example.learning_spring.security.JsonWebTokenProvider;
import com.example.learning_spring.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JsonWebTokenProvider tokenProvider;
    private final ModelMapper modelMapper;

    public UserService(
            RoleRepository roleRepository, AuthenticationManager authenticationManager,
            JsonWebTokenProvider tokenProvider, ModelMapper modelMapper,
            UserRepository userRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.modelMapper = modelMapper;
    }

    public BaseResponse<UserDto> create(UserCreateDto userDto) {
        BaseResponse<UserDto> baseResponse = new BaseResponse<>();
        Optional<Role> roleOptional = roleRepository.findByName(userDto.getRole());
        if (roleOptional.isEmpty()) {
            baseResponse.setMessage(
                    String.format("Role %s not exists.", userDto.getRole())
            );
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
        }
        else if (userRepository.existsByUsername(userDto.getUsername())) {
            baseResponse.setMessage(
                    String.format("User %s exists.", userDto.getUsername())
            );
            baseResponse.setCode(HttpStatus.NOT_FOUND.value());
        }
        else {
            User user = new User();
            user.setUsername(user.getUsername());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setRole(roleOptional.get());
            baseResponse.setData(modelMapper.map(user));
            baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
            baseResponse.setCode(HttpStatus.OK.value());
        }
        return baseResponse;
    }

    public BaseResponse<JsonWebToken> login(LoginRequest loginRequest) {
        BaseResponse<JsonWebToken> baseResponse = new BaseResponse<>();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về jwt cho người dùng.
        String jwt = tokenProvider.generateToken((User) authentication.getPrincipal());
        baseResponse.setData(
                new JsonWebToken(jwt, "Bearer")
        );
        baseResponse.setCode(HttpStatus.OK.value());
        baseResponse.setMessage(Constants.ResponseMessage.SUCCESS);
        return baseResponse;
    }

    public BaseResponse<List<UserDto>> getAll(int page, int size) {
        BaseResponse<List<UserDto>> baseResponse = new BaseResponse<>();
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);
        baseResponse.success();
        baseResponse.setData(userPage.getContent()
                .stream().map(modelMapper::map)
                .collect(Collectors.toList()));
        baseResponse.setPage(userPage.getNumber());
        baseResponse.setSize(userPage.getSize());
        baseResponse.setTotal(userPage.getTotalElements());
        return baseResponse;
    }

}
