package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.PublicMessageComment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class PublicMessageDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String EMAIL = "test";
    @Autowired
    private PublicMessageDao publicMessageDao;
    @Autowired
    private PublicMessageCommentDao publicMessageCommentDao;

    @Test
    void PublicMessageDao_getAllRecord() {
        List<PublicMessage> resultPublicMessages = publicMessageDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPublicMessages);
        Assertions.assertFalse(resultPublicMessages.isEmpty());
        Assertions.assertEquals(resultPublicMessages.size(), testDataUtil.getPublicMessages().size());
        Assertions.assertEquals(resultPublicMessages, testDataUtil.getPublicMessages());
    }

    @Test
    void PublicMessageDao_findById() {
        PublicMessage resultPublicMessage = publicMessageDao.findById(testDataUtil.getPublicMessages().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultPublicMessage);
        Assertions.assertEquals(testDataUtil.getPublicMessages().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultPublicMessage);
    }
    @Test
    void PublicMessageDao_findById_ErrorId() {
        PublicMessage resultPublicMessage = publicMessageDao.findById((long) ArrayIndex.THIRTEENTH_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultPublicMessage);
    }

    @Test
    void PublicMessageDao_saveRecord() {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setContent(null);
        publicMessage.setTitle(null);
        publicMessage.setIsDeleted(false);
        publicMessage.setCreationDate(new Date());
        publicMessage.setAuthor(testDataUtil.getUserProfiles().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));

        publicMessageDao.save(publicMessage);
        Assertions.assertNotNull(publicMessage.getId());
    }

    @Test
    void PublicMessageDao_updateRecord() {
        PublicMessage publicMessage = testDataUtil.getPublicMessages().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        publicMessage.setContent(null);

        publicMessageDao.updateRecord(publicMessage);
        PublicMessage resultPublicMessage = publicMessageDao.findById(publicMessage.getId());
        Assertions.assertNotNull(resultPublicMessage);
        Assertions.assertEquals(publicMessage, resultPublicMessage);
    }

    @Test
    void PublicMessageDao_deleteRecord() {
        PublicMessage publicMessage = testDataUtil.getPublicMessages().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        for (PublicMessageComment publicMessageComment:publicMessage.getPublicMessageComments()) {
            publicMessageCommentDao.deleteRecord(publicMessageComment);
        }

        publicMessageDao.deleteRecord(publicMessage);
        PublicMessage resultPublicMessage = publicMessageDao.findById(publicMessage.getId());
        Assertions.assertNull(resultPublicMessage);
    }

    @Test
    void PublicMessageDao_findByEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<PublicMessage> resultPublicMessages = publicMessageDao.findByEmail(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPublicMessages);
        Assertions.assertFalse(resultPublicMessages.isEmpty());
        Assertions.assertEquals(resultPublicMessages.size(), testDataUtil.getPublicMessagesByEmail(email).size());
        Assertions.assertEquals(testDataUtil.getPublicMessagesByEmail(email), resultPublicMessages);
    }

    @Test
    void PublicMessageDao_errorEmail() {
        List<PublicMessage> resultPublicMessages = publicMessageDao.findByEmail(EMAIL, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPublicMessages);
        Assertions.assertTrue(resultPublicMessages.isEmpty());
    }

    @Test
    void PublicMessageDao_findByIdAndEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        PublicMessage publicMessage = testDataUtil.getPublicMessages().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        PublicMessage resultPublicMessage = publicMessageDao.findByIdAndEmail(email, publicMessage.getId());
        Assertions.assertNotNull(resultPublicMessage);
        Assertions.assertEquals(publicMessage, resultPublicMessage);
    }

    @Test
    void PublicMessageDao_getFriendsMessages() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<PublicMessage> resultPublicMessages = publicMessageDao.getFriendsMessages(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPublicMessages);
        Assertions.assertFalse(resultPublicMessages.isEmpty());
        Assertions.assertEquals(testDataUtil.getFriendsPublicMessages(email).size(), resultPublicMessages.size());
        Assertions.assertTrue(resultPublicMessages.contains(testDataUtil.getFriendsPublicMessages(email).get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultPublicMessages.contains(testDataUtil.getFriendsPublicMessages(email).get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultPublicMessages.contains(testDataUtil.getFriendsPublicMessages(email).get(
                ArrayIndex.THIRD_INDEX_OF_ARRAY.index)));
    }

}
