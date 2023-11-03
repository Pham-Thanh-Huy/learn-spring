package com.example.learning_spring;

import com.example.learning_spring.dtos.PostDto;
import com.example.learning_spring.dtos.TopicDto;
import com.example.learning_spring.dtos.UserDto;
import com.example.learning_spring.models.Role;
import com.example.learning_spring.models.Topic;
import com.example.learning_spring.models.User;
import com.example.learning_spring.repositories.RoleRepository;
import com.example.learning_spring.repositories.TopicRepository;
import com.example.learning_spring.repositories.UserRepository;
import com.example.learning_spring.services.MailService;
import com.example.learning_spring.services.PostService;
import com.example.learning_spring.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MailService mailService;

    @Override
    public void run(String... args) throws Exception {
//        mailService.sendEmail(
//                "test", "test",
//                "nguyendanghoanghuy2002@gmail.com"
//        );
    }
}
