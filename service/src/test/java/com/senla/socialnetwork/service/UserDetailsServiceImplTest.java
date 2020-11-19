package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.UserDao;
import com.senla.socialnetwork.domain.SystemUser;
import com.senla.socialnetwork.domain.enumaration.RoleName;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.exception.BusinessException;
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
    private static final String EMAIL = "Test";
    private static final String PASSWORD = "Test";
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDao userDao;

    @Test
    void UserDetailsServiceImpl_loadUserByUsername() {
        SystemUser systemUser = new SystemUser();
        systemUser.setPassword(PASSWORD);
        systemUser.setRole(RoleName.ROLE_USER);
        Mockito.doReturn(systemUser).when(userDao).findByEmail(EMAIL);

        UserDetails resultUser = userDetailsService.loadUserByUsername(EMAIL);
        Assertions.assertNotNull(resultUser);
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.reset(userDao);
    }

    @Test
    void UserServiceImplTest_loadUserByUsername_userDao_findByEmail_userNull() {
        Mockito.doReturn(null).when(userDao).findByEmail(EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userDetailsService.loadUserByUsername(EMAIL));
        Mockito.verify(userDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.reset(userDao);
    }

}
