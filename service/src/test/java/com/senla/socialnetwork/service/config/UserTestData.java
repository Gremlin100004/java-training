package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;
import com.senla.socialnetwork.service.security.UserPrincipal;
import com.senla.socialnetwork.service.util.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserTestData {
    private static final Long ID_USER = 1L;
    private static final Long ID_OTHER_USER = 2L;
    private static final Long RIGHT_NUMBER_USERS = 2L;
    private static final String EMAIL = "test@test.com";
    private static final String WRONG_EMAIL = "test";
    private static final String PASSWORD = "test";
    private static final String TOKEN = "test";
    private static final String EMPTY_TOKEN = "";
    private static final String TOKEN_TYPE = "Bearer ";
    private static final Integer EXPIRATION = 3600000;

    public static Long getIdUser() {
        return ID_USER;
    }

    public static Long getRightNumberUsers() {
        return RIGHT_NUMBER_USERS;
    }

    public static String getEmail() {
        return EMAIL;
    }

    public static String getWrongEmail() {
        return WRONG_EMAIL;
    }

    public static String getToken() {
        return TOKEN;
    }

    public static String getEmptyToken() {
        return EMPTY_TOKEN;
    }

    public static String getAuthorizationHeader(SecretKey secretKey){
        User user = new User(EMAIL, WRONG_EMAIL, new ArrayList<>());
        return TOKEN_TYPE + JwtUtil.generateToken(user, secretKey, EXPIRATION);
    }

    public static SystemUser getTestUser() {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(ID_USER);
        systemUser.setPassword(PASSWORD);
        systemUser.setEmail(EMAIL);
        systemUser.setRole(RoleName.ROLE_USER);
        return systemUser;
    }

    public static UserForAdminDto getTestUserForClientDto() {
        UserForAdminDto userDto = new UserForAdminDto();
        userDto.setId(ID_USER);
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);
        return userDto;
    }

    public static UserForSecurityDto getTestUserForSecurityDto() {
        UserForSecurityDto userDto = new UserForSecurityDto();
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);
        return userDto;
    }

    public static List<SystemUser> getTestUsers() {
        SystemUser userOne = getTestUser();
        SystemUser userTwo = getTestUser();
        userTwo.setId(ID_OTHER_USER);
        return Arrays.asList(userOne, userTwo);
    }

    public static List<UserForAdminDto> getTestUsersForClientDto() {
        UserForAdminDto userDtoOne = getTestUserForClientDto();
        UserForAdminDto userDtoTwo = getTestUserForClientDto();
        userDtoTwo.setId(ID_OTHER_USER);
        return Arrays.asList(userDtoOne, userDtoTwo);
    }

    public static List<UserForSecurityDto> getTestUsersForLoginDto() {
        UserForSecurityDto userDtoOne = getTestUserForSecurityDto();
        UserForSecurityDto userDtoTwo = getTestUserForSecurityDto();
        return Arrays.asList(userDtoOne, userDtoTwo);
    }

    public static UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken() {
        return new UsernamePasswordAuthenticationToken(
            new UserPrincipal(UserTestData.getTestUser()), null, null);
    }

}
