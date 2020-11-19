package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.dto.UserDto;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @Test
    void UserServiceImpl_getUsers() {
        List<SystemUser> systemUsers = UserTestData.getTestUsers();
        List<UserDto> usersDto = UserTestData.getTestUsersDto();
        Mockito.doReturn(systemUsers).when(userDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserDto> resultUserDto = userService.getUsers(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultUserDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultUserDto.size());
        Assertions.assertFalse(resultUserDto.isEmpty());
        Assertions.assertEquals(usersDto, resultUserDto);
        Mockito.verify(userDao, Mockito.times(1)).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImpl_getUser() {
        SystemUser systemUser = UserTestData.getTestUser();
        UserDto userDto = UserTestData.getTestUserDto();
        Mockito.doReturn(systemUser).when(userDao).findByEmail(UserTestData.getEmail());

        UserDto resultUserDto = userService.getUser(UserTestData.getEmail());
        Assertions.assertNotNull(resultUserDto);
        Assertions.assertEquals(userDto, resultUserDto);
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImpl_getUserLogoutToken() {
        Mockito.doReturn(UserTestData.getTOKEN()).when(userDao).getLogoutToken(UserTestData.getEmail());

        String resultToken = userService.getUserLogoutToken(UserTestData.getEmail());
        Assertions.assertNotNull(resultToken);
        Assertions.assertEquals(UserTestData.getTOKEN(), resultToken);
        Mockito.verify(userDao, Mockito.times(1)).getLogoutToken(UserTestData.getEmail());
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImpl_getUserLogoutToken_userDao_getLogoutToken_nullObject() {
        Mockito.doReturn(null).when(userDao).getLogoutToken(UserTestData.getEmail());

        String resultToken = userService.getUserLogoutToken(UserTestData.getEmail());
        Assertions.assertNotNull(resultToken);
        Assertions.assertEquals(UserTestData.getEmptyToken(), resultToken);
        Mockito.verify(userDao, Mockito.times(1)).getLogoutToken(UserTestData.getEmail());
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImpl_logIn() {
        SystemUser testUser = UserTestData.getTestUser();
        UserDto userDto = UserTestData.getTestUserDto();
        testUser.setRole(RoleName.ROLE_USER);
        Mockito.doReturn(testUser).when(userDao).findByEmail(UserTestData.getEmail());

        Assertions.assertDoesNotThrow(() -> userService.logIn(userDto));
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_logIn_userDao_findByEmail_userNull() {
        SystemUser testUser = UserTestData.getTestUser();
        UserDto userDto = UserTestData.getTestUserDto();
        testUser.setRole(RoleName.ROLE_USER);
        Mockito.doReturn(null).when(userDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userService.logIn(userDto));
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImpl_addUser() {
        SystemUser testUser = UserTestData.getTestUser();
        UserDto userDto = UserTestData.getTestUserDto();
        Mockito.doReturn(testUser).when(userDao).saveRecord(ArgumentMatchers.any(SystemUser.class));
        Mockito.doReturn(null).when(userDao).findByEmail(UserTestData.getEmail());

        Assertions.assertDoesNotThrow(() -> userService.addUser(userDto));
        Mockito.verify(userDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(SystemUser.class));
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_addUser_userDao_findByEmail_nullObject() {
        SystemUser testUser = UserTestData.getTestUser();
        UserDto userDto = UserTestData.getTestUserDto();
        Mockito.doReturn(testUser).when(userDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userService.addUser(userDto));
        Mockito.verify(userDao, Mockito.never()).saveRecord(ArgumentMatchers.any(SystemUser.class));
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImpl_deleteUser() {
        SystemUser testUser = UserTestData.getTestUser();
        Mockito.doReturn(testUser).when(userDao).findById(UserTestData.getIdUser());

        Assertions.assertDoesNotThrow(() -> userService.deleteUser(UserTestData.getIdUser()));
        Mockito.verify(userDao, Mockito.times(1)).findById(UserTestData.getIdUser());
        Mockito.verify(userDao, Mockito.times(1)).deleteRecord(UserTestData.getIdUser());
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_deleteUser_userDao_findById_nullObject() {
        Mockito.doReturn(null).when(userDao).findById(UserTestData.getIdUser());

        Assertions.assertThrows(BusinessException.class, () -> userService.deleteUser(UserTestData.getIdUser()));
        Mockito.verify(userDao, Mockito.times(1)).findById(UserTestData.getIdUser());
        Mockito.verify(userDao, Mockito.never()).deleteRecord(UserTestData.getIdUser());
        Mockito.reset(userDao);
    }

}
