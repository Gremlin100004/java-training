package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.SystemUser;
import com.senla.socialnetwork.model.enumaration.RoleName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String EMAIL = "test";
    private static final String PASSWORD = "test";
    @Autowired
    private UserDao userDao;

    @Test
    void UserDao_getAllRecord() {
        List<SystemUser> resultUsers = userDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUsers);
        Assertions.assertFalse(resultUsers.isEmpty());
        Assertions.assertEquals(resultUsers.size(), testDataUtil.getUsers().size());
        Assertions.assertEquals(resultUsers, testDataUtil.getUsers());
    }

    @Test
    void UserDao_findById() {
        SystemUser resultUser = userDao.findById(testDataUtil.getUsers().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultUser);
        Assertions.assertEquals(testDataUtil.getUsers().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultUser);
    }

    @Test
    void UserDao_findById_ErrorId() {
        SystemUser resultUser = userDao.findById((long) ArrayIndex.THIRTY_FIRST_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultUser);
    }

    @Test
    void UserDao_saveRecord() {
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(EMAIL);
        systemUser.setPassword(PASSWORD);
        systemUser.setRole(RoleName.ROLE_USER);

        userDao.save(systemUser);
        Assertions.assertNotNull(systemUser.getId());
    }

    @Test
    void UserDao_updateRecord() {
        SystemUser systemUser = testDataUtil.getUsers().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        systemUser.setEmail(EMAIL);

        userDao.updateRecord(systemUser);
        SystemUser resultUser = userDao.findById(systemUser.getId());
        Assertions.assertNotNull(resultUser);
        Assertions.assertEquals(systemUser, resultUser);
    }

    @Test
    void UserDao_deleteRecord() {
        SystemUser systemUser = testDataUtil.getUsers().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        userDao.deleteRecord(systemUser);
        SystemUser resultUser = userDao.findById(systemUser.getId());
        Assertions.assertNull(resultUser);
    }

    @Test
    void UserDao_findByEmail() {
        SystemUser resultUser = userDao.findByEmail(testDataUtil.getUsers().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail());
        Assertions.assertNotNull(resultUser);
        Assertions.assertEquals(testDataUtil.getUsers().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultUser);
    }

    @Test
    void UserDao_findByEmail_errorEmail() {
        SystemUser resultUser = userDao.findByEmail(EMAIL);
        Assertions.assertNull(resultUser);
    }

}
