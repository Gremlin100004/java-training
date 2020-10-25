package com.senla.carservice.service;

import com.senla.carservice.dao.UserDao;
import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.domain.enumaration.RoleName;
import com.senla.carservice.dto.UserDto;
import com.senla.carservice.service.config.TestConfig;
import com.senla.carservice.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {
    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_PASSWORD = "test";
    private static final Long ID_USER = 1L;
    private static final Long ID_OTHER_USER = 2L;
    private static final Long RIGHT_NUMBER_USERS = 2L;
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @Test
    void UserServiceImplTest_getSystemUsers() {
        List<SystemUser> systemUsers = getTestUsers();
        List<UserDto> usersDto = getTestUsersDto();
        Mockito.doReturn(systemUsers).when(userDao).getAllRecords();

        List<UserDto> resultUserDto = userService.getSystemUsers();
        Assertions.assertNotNull(resultUserDto);
        Assertions.assertEquals(RIGHT_NUMBER_USERS, resultUserDto.size());
        Assertions.assertFalse(resultUserDto.isEmpty());
        Assertions.assertEquals(usersDto, resultUserDto);
        Mockito.verify(userDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_addUser() {
        SystemUser testUser = getTestUser();
        UserDto userDto = getTestUserDto();
        Mockito.doReturn(testUser).when(userDao).saveRecord(ArgumentMatchers.any(SystemUser.class));
        Mockito.doReturn(null).when(userDao).findByEmail(TEST_EMAIL);

        Assertions.assertDoesNotThrow(() -> userService.addUser(userDto));
        Mockito.verify(userDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(SystemUser.class));
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_addUser_userDao_findByEmail_existUserWithEmail() {
        SystemUser testUser = getTestUser();
        UserDto userDto = getTestUserDto();
        Mockito.doReturn(testUser).when(userDao).findByEmail(TEST_EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userService.addUser(userDto));
        Mockito.verify(userDao, Mockito.never()).saveRecord(ArgumentMatchers.any(SystemUser.class));
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_logIn() {
        SystemUser testUser = getTestUser();
        UserDto userDto = getTestUserDto();
        testUser.setRole(RoleName.ROLE_USER);
        Mockito.doReturn(testUser).when(userDao).findByEmail(TEST_EMAIL);

        Assertions.assertDoesNotThrow(() -> userService.logIn(userDto));
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(TEST_EMAIL);
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_logIn_userDao_findByEmail_userNull() {
        SystemUser testUser = getTestUser();
        UserDto userDto = getTestUserDto();
        testUser.setRole(RoleName.ROLE_USER);
        Mockito.doReturn(null).when(userDao).findByEmail(TEST_EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userService.logIn(userDto));
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(TEST_EMAIL);
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_deleteUser() {
        SystemUser testUser = getTestUser();
        Mockito.doReturn(testUser).when(userDao).findById(ID_USER);

        Assertions.assertDoesNotThrow(() -> userService.deleteUser(ID_USER));
        Mockito.verify(userDao, Mockito.times(1)).findById(ID_USER);
        Mockito.verify(userDao, Mockito.times(1)).deleteRecord(ID_USER);
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_deleteUser_userDao_findById_userExist() {
        Mockito.doReturn(null).when(userDao).findById(ID_USER);

        Assertions.assertThrows(BusinessException.class, () -> userService.deleteUser(ID_USER));
        Mockito.verify(userDao, Mockito.times(1)).findById(ID_USER);
        Mockito.verify(userDao, Mockito.never()).deleteRecord(ID_USER);
        Mockito.reset(userDao);
    }

    private SystemUser getTestUser() {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(ID_USER);
        systemUser.setPassword(TEST_PASSWORD);
        systemUser.setEmail(TEST_EMAIL);
        systemUser.setRole(RoleName.ROLE_USER);
        return systemUser;
    }

    private UserDto getTestUserDto() {
        UserDto userDto = new UserDto();
        userDto.setId(ID_USER);
        userDto.setEmail(TEST_EMAIL);
        userDto.setPassword(TEST_PASSWORD);
        return userDto;
    }

    private List<SystemUser> getTestUsers() {
        SystemUser userOne = getTestUser();
        SystemUser userTwo = getTestUser();
        userTwo.setId(ID_OTHER_USER);
        return Arrays.asList(userOne, userTwo);
    }

    private List<UserDto> getTestUsersDto() {
        UserDto userDtoOne = getTestUserDto();
        UserDto userDtoTwo = getTestUserDto();
        userDtoTwo.setId(ID_OTHER_USER);
        return Arrays.asList(userDtoOne, userDtoTwo);
    }

}
