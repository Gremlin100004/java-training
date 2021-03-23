package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.PublicMessageComment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class PublicMessageCommentDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    @Autowired
    private PublicMessageCommentDao publicMessageCommentDao;

    @Test
    void PublicMessageCommentDao_getAllRecord() {
        List<PublicMessageComment> resultComments = publicMessageCommentDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultComments);
        Assertions.assertFalse(resultComments.isEmpty());
        Assertions.assertEquals(resultComments.size(), testDataUtil.getPublicMessageComments().size());
        Assertions.assertEquals(resultComments, testDataUtil.getPublicMessageComments());
    }

    @Test
    void PublicMessageCommentDao_findById() {
        PublicMessageComment resultComments = publicMessageCommentDao.findById(testDataUtil.getPublicMessageComments().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultComments);
        Assertions.assertEquals(testDataUtil.getPublicMessageComments().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultComments);
    }

    @Test
    void PublicMessageCommentDao_findById_ErrorId() {
        PublicMessageComment resultComment = publicMessageCommentDao.findById((long) ArrayIndex.NINTH_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultComment);
    }

    @Test
    void PublicMessageCommentDao_saveRecord() {
        PublicMessageComment comment = new PublicMessageComment();
        comment.setIsDeleted(false);
        comment.setContent(null);
        comment.setPublicMessage(testDataUtil.getPublicMessages().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));
        comment.setCreationDate(new Date());
        comment.setAuthor(testDataUtil.getUserProfiles().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));

        publicMessageCommentDao.save(comment);
        Assertions.assertNotNull(comment.getId());
    }

    @Test
    void PublicMessageCommentDao_updateRecord() {
        PublicMessageComment comment = testDataUtil.getPublicMessageComments().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        comment.setCreationDate(new Date());

        publicMessageCommentDao.updateRecord(comment);
        PublicMessageComment resultComment = publicMessageCommentDao.findById(comment.getId());
        Assertions.assertNotNull(resultComment);
        Assertions.assertEquals(comment, resultComment);
    }

    @Test
    void PublicMessageCommentDao_deleteRecord() {
        PublicMessageComment comment = testDataUtil.getPublicMessageComments().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        publicMessageCommentDao.deleteRecord(comment);
        PublicMessageComment resultComment = publicMessageCommentDao.findById(comment.getId());
        Assertions.assertNull(resultComment);
    }

    @Test
    void PublicMessageCommentDao_findByIdAndEmail() {
        PublicMessageComment comment = testDataUtil.getPublicMessageComments().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index);
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        PublicMessageComment resultComment = publicMessageCommentDao.findByIdAndEmail(email, comment.getId());
        Assertions.assertNotNull(resultComment);
        Assertions.assertEquals(comment, resultComment);
    }

    @Test
    void PublicMessageCommentDao_getPublicMessageComments() {
        PublicMessage publicMessage = testDataUtil.getPublicMessages().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        List<PublicMessageComment> resultComments = publicMessageCommentDao.getPublicMessageComments(
                publicMessage.getId(), FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultComments);
        Assertions.assertFalse(resultComments.isEmpty());
        Assertions.assertEquals(resultComments.size(), publicMessage.getPublicMessageComments().size());
        Assertions.assertEquals(resultComments, publicMessage.getPublicMessageComments());

    }

}
