package com.senla.carservice.service.util;

import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static List<UserDto> getUserDto(List<SystemUser> users) {
        return users.stream()
            .map(UserMapper::getUserDto)
            .collect(Collectors.toList());
    }

    public static UserDto getUserDto(SystemUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static SystemUser getSystemUser(UserDto userDto) {
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(userDto.getEmail());
        systemUser.setPassword(userDto.getPassword());
        return systemUser;
    }

}
