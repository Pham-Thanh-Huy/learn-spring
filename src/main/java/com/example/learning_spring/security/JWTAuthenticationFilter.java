package com.example.learning_spring.security;

import com.example.learning_spring.dtos.BaseResponse;
import com.example.learning_spring.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JsonWebTokenProvider jwtProvider;

    private CustomUserDetailService customUserDetailService;
    private ObjectMapper objectMapper;

    @Autowired
    public void setJwtProvider(JsonWebTokenProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Autowired
    public void setCustomUserDetailService(CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws IOException {
        try {
            String jwt = getJWTFromRequest(request);
            if (Objects.nonNull(jwt) && !jwt.isEmpty() && jwtProvider.validateToken(jwt)) {
                Long userId = jwtProvider.getUserIdFromJWT(jwt);
                User user = customUserDetailService.loadUserById(userId);
                if (user != null) {
                    UsernamePasswordAuthenticationToken
                            authenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities()
                    );
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            BaseResponse<String> baseResponse = new BaseResponse<>();
            baseResponse.setCode(HttpStatus.UNAUTHORIZED.value());
            baseResponse.setMessage("Unautorized request!");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(objectMapper.writeValueAsString(baseResponse));
        }
    }

    private String getJWTFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty() && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
