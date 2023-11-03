package com.example.learning_spring;

import com.example.learning_spring.models.Role;
import com.example.learning_spring.models.User;
import com.example.learning_spring.repositories.RoleRepository;
import com.example.learning_spring.repositories.UserRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class ApplicationReadyEventListener implements
        ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationReadyEventListener(
            UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initializeRolesAndAdminAccount();
    }

    private void initializeRolesAndAdminAccount() {
        if (!roleRepository.existsByName("Admin")) {
            Role role = roleRepository.save(
                    new Role("Admin")
            );
            User adminUser = new User(
                    "admin",
                    passwordEncoder.encode("appadmin"),
                    "admin@admin.com");
            adminUser.setRole(role);
            userRepository.save(adminUser);
        }
        if (!roleRepository.existsByName("User")) {
            roleRepository.save(
                    new Role("User")
            );
        }
    }

}
