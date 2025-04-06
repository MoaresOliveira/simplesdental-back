package com.simplesdental.product.mapper;

import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.UserRegisterDTO;
import com.simplesdental.product.model.dto.request.UserUpdateDTO;
import com.simplesdental.product.model.dto.response.UserDTO;
import org.springframework.data.domain.Page;

public class UserMapper {

    public User toEntity(UserRegisterDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        return user;
    }

    public User toEntity(UserUpdateDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

    public Page<UserDTO> toDTOPage(Page<User> users) {
        return users.map(UserDTO::new);
    }

}
