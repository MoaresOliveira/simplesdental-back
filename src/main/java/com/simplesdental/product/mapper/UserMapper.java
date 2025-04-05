package com.simplesdental.product.mapper;

import com.simplesdental.product.model.User;
import com.simplesdental.product.model.dto.request.UserRegisterDTO;

public class UserMapper {

    public User ToEntity(UserRegisterDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setPassword(dto.getPassword());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        return user;
    }

}
