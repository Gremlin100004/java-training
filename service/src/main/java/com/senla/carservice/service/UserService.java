package com.senla.carservice.service;

import com.senla.carservice.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getSystemUsers();

    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);

}
