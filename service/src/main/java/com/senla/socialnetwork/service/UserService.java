package com.senla.socialnetwork.service;

import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    List<UserForAdminDto> getUsers(int firstResult, int maxResults);

    UserForSecurityDto getUser(HttpServletRequest request, SecretKey secretKey);

    String getUserLogoutToken(String email);

    String logIn(UserForSecurityDto userDto, SecretKey secretKey);

    void logOut(HttpServletRequest request, SecretKey secretKey);

    void addUser(UserForSecurityDto userDto, RoleName roleName);

    void updateUser(HttpServletRequest request,
                    List<UserForSecurityDto> usersDto,
                    SecretKey secretKey);

    void deleteUser(Long userId);

}
