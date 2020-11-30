package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static List<UserForAdminDto> getUserForAdminDto(final List<SystemUser> users) {
        return users.stream()
            .map(UserMapper::getUserForAdminDto)
            .collect(Collectors.toList());
    }

    public static UserForAdminDto getUserForAdminDto(final SystemUser user) {
        UserForAdminDto userDto = new UserForAdminDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public static UserForSecurityDto getUserForSecurityDto(final SystemUser user) {
        UserForSecurityDto userDto = new UserForSecurityDto();
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static SystemUser getSystemUser(final BCryptPasswordEncoder cryptPasswordEncoder,
                                           final UserForSecurityDto userDto) {
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(userDto.getEmail());
        systemUser.setRole(RoleName.ROLE_USER);
        systemUser.setPassword(cryptPasswordEncoder.encode(userDto.getPassword()));
        return systemUser;
    }

    public static void getCurrentSystemUser(final BCryptPasswordEncoder cryptPasswordEncoder,
                                                  final UserForSecurityDto userDto,
                                                  final SystemUser currentSystemUser) {
        currentSystemUser.setEmail(userDto.getEmail());
        currentSystemUser.setPassword(cryptPasswordEncoder.encode(userDto.getPassword()));
    }

}
