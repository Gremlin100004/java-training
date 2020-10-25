package com.senla.carservice.service;

import com.senla.carservice.dao.UserDao;
import com.senla.carservice.domain.SystemUser;
import com.senla.carservice.domain.enumaration.RoleName;
import com.senla.carservice.service.config.TestConfig;
import com.senla.carservice.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserDetailsServiceImplTest {
    private static final String TEST_EMAIL = "test@test.com";
    private static final String TEST_PASSWORD = "test";
    private static final Long ID_USER = 1L;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDao userDao;

    @Test
    void UserServiceImplTest_loadUserByUsername() {
        SystemUser systemUser = getTestUser();
        systemUser.setRole(RoleName.ROLE_USER);
        Mockito.doReturn(systemUser).when(userDao).findByEmail(TEST_EMAIL);

        UserDetails resultUserDto = userDetailsService.loadUserByUsername(TEST_EMAIL);
        Assertions.assertNotNull(resultUserDto);
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(TEST_EMAIL);
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_loadUserByUsername_userDao_findByEmail_userNull() {
        Mockito.doReturn(null).when(userDao).findByEmail(TEST_EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userDetailsService.loadUserByUsername(TEST_EMAIL));
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(TEST_EMAIL);
        Mockito.reset(userDao);
    }


    private SystemUser getTestUser() {
        SystemUser systemUser = new SystemUser();
        systemUser.setId(ID_USER);
        systemUser.setPassword(TEST_PASSWORD);
        systemUser.setEmail(TEST_EMAIL);
        return systemUser;
    }

}
