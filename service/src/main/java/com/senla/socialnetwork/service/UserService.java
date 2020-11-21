package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.UserDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    List<UserDto> getUsers(int firstResult, int maxResults);

    UserDto getUser(String email);

    String getUserLogoutToken(String email);

    String logIn(UserDto userDto);

    void logOut(String email, HttpServletRequest request);

    UserDto addUser(UserDto userDto);

    void updateUser(String email, List<UserDto> usersDto);

    void deleteUser(Long userId);

}
