package com.simplesdental.product.service;

import com.simplesdental.product.config.SecurityConfig;
import com.simplesdental.product.mapper.UserMapper;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.LoginRequestDTO;
import com.simplesdental.product.model.dto.request.UserRegisterDTO;
import com.simplesdental.product.model.dto.response.TokenResponseDTO;
import jakarta.validation.Valid;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final SecurityConfig securityConfig;
    private final JwtTokenService jwtTokenService;

    public AuthService(UserService userService, SecurityConfig securityConfig, JwtTokenService jwtTokenService) {
        this.userService = userService;
        this.securityConfig = securityConfig;
        this.jwtTokenService = jwtTokenService;
        this.userMapper = new UserMapper();
    }

    public TokenResponseDTO login(LoginRequestDTO request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(request.email(), request.password());

        Authentication authenticate = securityConfig.getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

        String token = jwtTokenService.generateToken((User) authenticate.getPrincipal());

        return new TokenResponseDTO(token);
    }

    public User register(@Valid UserRegisterDTO request) {
        return userService.save(userMapper.ToEntity(request));
    }

    public void context() {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByEmail(username);
    }
}
