package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(int firstResult, int maxResults);

    UserDto getUser();

    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);

}
