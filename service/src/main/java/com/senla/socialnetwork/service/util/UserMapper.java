package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.dto.UserDto;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static List<UserDto> getUserDto(final List<SystemUser> users) {
        return users.stream()
            .map(UserMapper::getUserDto)
            .collect(Collectors.toList());
    }

    public static UserDto getUserDto(final SystemUser user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole().toString());
        return userDto;
    }

    public static SystemUser getSystemUser(final UserDto userDto) {
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(userDto.getEmail());
        systemUser.setPassword(userDto.getPassword());
        return systemUser;
    }

}
