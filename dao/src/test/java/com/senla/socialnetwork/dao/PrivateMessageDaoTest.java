package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.dao.testdata.UserProfileTestData;
import com.senla.socialnetwork.model.PrivateMessage;
import com.senla.socialnetwork.model.SystemUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class PrivateMessageDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String CONTENT = "test";
    private static final Date START_PERIOD = UserProfileTestData.getDateTime("2020-11-01 12:04");
    private static final Date END_PERIOD = UserProfileTestData.getDateTime("2020-11-11 12:05");
    @Autowired
    private PrivateMessageDao privateMessageDao;

    @Test
    void PrivateMessageDao_getAllRecord() {
        List<PrivateMessage> resultPrivateMessages = privateMessageDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPrivateMessages);
        Assertions.assertFalse(resultPrivateMessages.isEmpty());
        Assertions.assertEquals(resultPrivateMessages.size(), testDataUtil.getPrivateMessages().size());
        Assertions.assertEquals(resultPrivateMessages, testDataUtil.getPrivateMessages());
    }

    @Test
    void PrivateMessageDao_findById() {
        PrivateMessage resultPrivateMessage = privateMessageDao.findById(testDataUtil.getPrivateMessages().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultPrivateMessage);
        Assertions.assertEquals(testDataUtil.getPrivateMessages().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultPrivateMessage);
    }

    @Test
    void PrivateMessageDao_findById_ErrorId() {
        PrivateMessage resultUser = privateMessageDao.findById((long) ArrayIndex.THIRTY_FIRST_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultUser);
    }

    @Test
    void PrivateMessageDao_saveRecord() {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setContent(CONTENT);
        privateMessage.setDepartureDate(new Date());
        privateMessage.setIsDeleted(false);
        privateMessage.setIsRead(false);
        privateMessage.setSender(testDataUtil.getUserProfiles().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));
        privateMessage.setRecipient(testDataUtil.getUserProfiles().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index));

        privateMessageDao.save(privateMessage);
        Assertions.assertNotNull(privateMessage.getId());
    }

    @Test
    void PrivateMessageDao_updateRecord() {
        PrivateMessage privateMessage = testDataUtil.getPrivateMessages().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        privateMessage.setContent(CONTENT);

        privateMessageDao.updateRecord(privateMessage);
        PrivateMessage resultPrivateMessage = privateMessageDao.findById(privateMessage.getId());
        Assertions.assertNotNull(resultPrivateMessage);
        Assertions.assertEquals(privateMessage, resultPrivateMessage);
    }

    @Test
    void PrivateMessageDao_deleteRecord() {
        PrivateMessage privateMessage = testDataUtil.getPrivateMessages().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        privateMessageDao.deleteRecord(privateMessage);
        PrivateMessage resultPrivateMessage = privateMessageDao.findById(privateMessage.getId());
        Assertions.assertNull(resultPrivateMessage);
    }

    @Test
    void PrivateMessageDao_findByEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<PrivateMessage> resultPrivateMessages = privateMessageDao.findByEmail(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPrivateMessages);
        Assertions.assertFalse(resultPrivateMessages.isEmpty());
        Assertions.assertEquals(resultPrivateMessages.size(), testDataUtil.getPrivateMessagesByEmail(email).size());
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                        ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
    }

    @Test
    void PrivateMessageDao_getDialogue() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        SystemUser user = testDataUtil.getUsers().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        List<PrivateMessage> resultPrivateMessages = privateMessageDao.getDialogue(
            email, user.getId(), FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPrivateMessages);
        Assertions.assertFalse(resultPrivateMessages.isEmpty());
        Assertions.assertEquals(resultPrivateMessages.size(), testDataUtil.getPrivateMessagesByEmailAndUser(
            user, email).size());
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
    }

    @Test
    void PrivateMessageDao_getUnreadMessages() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<PrivateMessage> resultPrivateMessages = privateMessageDao.getUnreadMessages(
            email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPrivateMessages);
        Assertions.assertFalse(resultPrivateMessages.isEmpty());
        Assertions.assertEquals(testDataUtil.getUnreadPrivateMessages(
            email).size(), resultPrivateMessages.size());
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                ArrayIndex.SIXTH_INDEX_OF_ARRAY.index)));
    }

    @Test
    void PrivateMessageDao_getMessageFilteredByPeriod() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<PrivateMessage> resultPrivateMessages = privateMessageDao.getMessageFilteredByPeriod(email,
            START_PERIOD, END_PERIOD, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPrivateMessages);
        Assertions.assertFalse(resultPrivateMessages.isEmpty());
        Assertions.assertEquals(resultPrivateMessages.size(), testDataUtil.getPrivateMessagesByPeriod(
            email, START_PERIOD, END_PERIOD).size());
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultPrivateMessages.contains(testDataUtil.getPrivateMessagesByEmail(email).get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
    }

    @Test
    void PrivateMessageDao_findByIdAndEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        PrivateMessage privateMessage = testDataUtil.getPrivateMessages().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        PrivateMessage resultPrivateMessage = privateMessageDao.findByIdAndEmail(email, privateMessage.getId());
        Assertions.assertNotNull(resultPrivateMessage);
        Assertions.assertEquals(privateMessage, resultPrivateMessage);
    }

}
