package com.example.erp.service;



import java.util.List;

import com.example.erp.dto.UserDto;
import com.example.erp.entity.User;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
