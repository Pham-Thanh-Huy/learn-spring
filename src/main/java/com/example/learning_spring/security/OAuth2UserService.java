package com.example.learning_spring.security;

import com.example.learning_spring.handlers.NotFoundException;
import com.example.learning_spring.handlers.OAuthException;
import com.example.learning_spring.models.AuthProvider;
import com.example.learning_spring.models.Role;
import com.example.learning_spring.models.User;
import com.example.learning_spring.repositories.RoleRepository;
import com.example.learning_spring.repositories.UserRepository;
import com.sun.security.auth.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(
            OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        String userEmail = (String) oAuth2User.getAttributes().get("email");
        if(userEmail == null || userEmail.isEmpty()) {
            throw new NotFoundException("Email not found from OAuth2 provider");
        }

        Optional<User> userOptional = userRepository.findByUsername(userEmail);
        if(userOptional.isPresent()) {
            User user = userOptional.get();
            if(!user.getAuthProvider().equals(AuthProvider.valueOf(
                    oAuth2UserRequest.getClientRegistration().getRegistrationId()
            ))) {
                throw new OAuthException(
                        String.format("Looks like you're signed up with %s"
                        + " account. Please use your %s account to login.",
                                user.getAuthProvider().name(),
                                user.getAuthProvider().name()));
            }
            return user;
        } else {
            return registerNewUser(oAuth2UserRequest, oAuth2User);
//            throw new NotFoundException("Not found user");
        }
    }


    private User registerNewUser(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        User user = new User();
        Optional<Role> roleOptional = roleRepository.findByName("User");
        if (!roleOptional.isPresent()) {
            throw new RuntimeException("Wrong database state");
        }
        user.setRole(roleOptional.get());
        AuthProvider authProvider = AuthProvider.valueOf(
                userRequest.getClientRegistration().getRegistrationId()
        );
        if (authProvider == null) {
            throw new OAuthException("Unsupported OAuth provider");
        }
        user.setAuthProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        user.setUsername((String) oAuth2User.getAttributes().get("email"));
        user.setEmail((String) oAuth2User.getAttributes().get("email"));
        return userRepository.save(user);
    }
}
