package com.simplesdental.product.config;


import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.response.ErrorResponseDTO;
import com.simplesdental.product.service.JwtTokenService;
import com.simplesdental.product.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;

    private final UserService userService;

    public UserAuthenticationFilter(JwtTokenService jwtTokenService, UserService userService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = recoveryToken(request);
            String uri = request.getRequestURI();
            boolean isOpenRoute = Arrays.stream(SecurityConfig.OPEN_ROUTES_REGEX).anyMatch(uri::matches);
            if (token != null && !isOpenRoute) {
                String subject = jwtTokenService.getSubjectFromToken(token);
                User user = userService.findByEmail(subject);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
                request.setAttribute("user", user);
            }
            filterChain.doFilter(request, response);
        } catch (JWTVerificationException e) {
            ObjectMapper objectMapper = new ObjectMapper();
            ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO(HttpStatus.UNAUTHORIZED, request.getRequestURI(), e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(errorResponseDTO));
            response.getWriter().flush();
        }
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        return null;
    }

}
