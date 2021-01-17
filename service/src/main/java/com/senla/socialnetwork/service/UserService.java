package com.senla.socialnetwork.service;

import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;

import javax.crypto.SecretKey;
import java.util.List;

public interface UserService {
    List<UserForAdminDto> getUsers(int firstResult, int maxResults);

    UserForSecurityDto getUser();

    String logIn(UserForSecurityDto userDto, SecretKey secretKey);

    void addUser(UserForSecurityDto userDto, RoleName roleName);

    void updateUser(List<UserForSecurityDto> usersDto);

    void deleteUser(Long userId);

}
