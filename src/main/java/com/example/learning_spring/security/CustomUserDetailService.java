package com.example.learning_spring.security;

import com.example.learning_spring.models.User;
import com.example.learning_spring.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//        CustomUserDetail customUserDetail = new CustomUserDetail(user);
//        customUserDetail.getAuthorities().clear();
//        user.getRoles().forEach(role -> {
//            customUserDetail.getAuthorities().add(new SimpleGrantedAuthority(role.getName()));
//        });
        return user;
    }

    public User loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
