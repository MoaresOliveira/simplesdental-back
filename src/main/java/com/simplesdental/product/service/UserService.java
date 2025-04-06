package com.simplesdental.product.service;

import com.simplesdental.product.config.SecurityConfig;
import com.simplesdental.product.exception.ResourceNotFoundException;
import com.simplesdental.product.mapper.UserMapper;
import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.UserUpdateDTO;
import com.simplesdental.product.repository.UserRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final SecurityConfig securityConfig;
    private final UserMapper userMapper;


    public UserService(UserRepository userRepository, SecurityConfig securityConfig) {
        this.userRepository = userRepository;
        this.securityConfig = securityConfig;
        userMapper = new UserMapper();
    }

    public User save(User user) {
        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Cacheable(value = "userByEmail", key = "#email")
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("user"));
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @CacheEvict(value = "userByEmail", key = "#user.email")
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public User updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        return findById(id)
                .map(existingUser -> {
                    User user = userMapper.toEntity(userUpdateDTO);
                    user.setId(id);
                    user.setPassword(existingUser.getPassword());
                    return save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User"));
    }
}
