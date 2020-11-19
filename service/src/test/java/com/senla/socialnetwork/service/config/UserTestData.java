package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserDto;

import java.util.Arrays;
import java.util.List;

public class UserTestData {
    private static final Long ID_USER = 1L;
    private static final Long ID_OTHER_USER = 2L;
    private static final Long RIGHT_NUMBER_USERS = 2L;
    private static final String EMAIL = "test@test.com";
    private static final String PASSWORD = "test";
    private static final String TOKEN = "test";
    private static final String EMPTY_TOKEN = "";

    public static Long getIdUser() {
        return ID_USER;
    }

    public static Long getRightNumberUsers() {
        return RIGHT_NUMBER_USERS;
    }

    public static String getEmail() {
        return EMAIL;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getTOKEN() {
        return TOKEN;
    }

    public static String getEmptyToken() {
        return EMPTY_TOKEN;
    }

    public static SystemUser getTestUser() {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(ID_USER);
        systemUser.setPassword(PASSWORD);
        systemUser.setEmail(EMAIL);
        systemUser.setRole(RoleName.ROLE_USER);
        return systemUser;
    }

    public static UserDto getTestUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(ID_USER);
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

    public static List<UserDto> getTestUsersDto() {
        UserDto userDtoOne = getTestUserDto();
        UserDto userDtoTwo = getTestUserDto();
        userDtoTwo.setId(ID_OTHER_USER);
        return Arrays.asList(userDtoOne, userDtoTwo);
    }

}
