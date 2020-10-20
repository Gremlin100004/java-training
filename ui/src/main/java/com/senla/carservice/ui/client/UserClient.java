package com.senla.carservice.ui.client;

import com.senla.carservice.dto.UserDto;

import java.util.List;

public interface UserClient {
    List<UserDto> getUsers();

    String addUser(UserDto userDto);

    String logIn(UserDto userDto);

    String deletePlace(Long idUser);
}
