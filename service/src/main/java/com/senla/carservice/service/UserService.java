package com.senla.carservice.service;

import com.senla.carservice.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getSystemUsers();

    String logIn(UserDto userDto);

    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);

}
