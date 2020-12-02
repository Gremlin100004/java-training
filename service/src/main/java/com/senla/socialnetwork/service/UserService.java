package com.senla.socialnetwork.service;

import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    List<UserForAdminDto> getUsers(int firstResult, int maxResults);

    UserForSecurityDto getUser(HttpServletRequest request);

    String getUserLogoutToken(String email);

    String logIn(UserForSecurityDto userDto);

    void logOut(HttpServletRequest request);

    void addUser(UserForSecurityDto userDto, RoleName roleName);

    void updateUser(HttpServletRequest request, List<UserForSecurityDto> usersDto);

    void deleteUser(Long userId);

}
