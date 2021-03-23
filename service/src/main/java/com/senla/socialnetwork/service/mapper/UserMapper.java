package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.enumaration.RoleName;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMapper {
    private UserMapper() {
    }

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

    public static SystemUser getSystemUser(final PasswordEncoder cryptPasswordEncoder,
                                           final UserForSecurityDto userDto,
                                           final RoleName roleName) {
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(userDto.getEmail());
        systemUser.setRole(roleName);
        systemUser.setPassword(cryptPasswordEncoder.encode(userDto.getPassword()));
        return systemUser;
    }

    public static void getCurrentSystemUser(final PasswordEncoder passwordEncoder,
                                            final UserForSecurityDto userDto,
                                            final SystemUser currentSystemUser) {
        currentSystemUser.setEmail(userDto.getEmail());
        currentSystemUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    }

}
