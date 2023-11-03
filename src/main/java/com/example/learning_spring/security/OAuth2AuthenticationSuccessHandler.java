package com.example.learning_spring.security;

import com.example.learning_spring.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private JsonWebTokenProvider tokenProvider;
    @Value("${application.oauth2.successRedirect}")
    private String clientRedirectUrl;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        clearAuthenticationAttributes(request);
        response.addCookie(
                new Cookie("tokenType", "Bearer")
        );
        response.addCookie(
                new Cookie("token", tokenProvider.generateToken((User) authentication.getPrincipal()))
        );
        getRedirectStrategy().sendRedirect(
                request, response, clientRedirectUrl
        );
//        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

//    protected String determineTargetUrl(
//            HttpServletRequest request, HttpServletResponse response,
//            Authentication authentication) {
//        Optional<String> redirectUri = CookieUtils.getCookie(request, HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
//                .map(Cookie::getValue);
//
//        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
//            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
//        }
//
//        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());
//
//        String token = tokenProvider.createToken(authentication);
//
//        return UriComponentsBuilder.fromUriString(targetUrl)
//                .queryParam("token", token)
//                .build().toUriString();
//    }

}
