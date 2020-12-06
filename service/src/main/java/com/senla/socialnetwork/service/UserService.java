package com.senla.socialnetwork.service;

import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;

import javax.crypto.SecretKey;
import java.util.List;

public interface UserService {
    List<UserForAdminDto> getUsers(int firstResult, int maxResults);

    UserForSecurityDto getUser();

    String getUserLogoutToken(String email);

    String logIn(UserForSecurityDto userDto, SecretKey secretKey);

    void logOut(String token);

    void addUser(UserForSecurityDto userDto, RoleName roleName);

    void updateUser(List<UserForSecurityDto> usersDto, String token);

    void deleteUser(Long userId);

}
