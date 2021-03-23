package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.PostComment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class PostCommentDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String CONTENT = "test";
    @Autowired
    private PostCommentDao postCommentDao;

    @Test
    void PostCommentDao_getAllRecords() {
        List<PostComment> resultPostComments = postCommentDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPostComments);
        Assertions.assertFalse(resultPostComments.isEmpty());
        Assertions.assertEquals(testDataUtil.getPostComments().size(), resultPostComments.size());
        Assertions.assertEquals(testDataUtil.getPostComments(), resultPostComments);
    }

    @Test
    void PostCommentDao_findById() {
        PostComment resultPostComment = postCommentDao.findById(testDataUtil.getPostComments().get(
            ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultPostComment);
        Assertions.assertEquals(testDataUtil.getPostComments().get(
            ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultPostComment);
    }

    @Test
    void PostCommentDao_findById_ErrorId() {
        PostComment resultPostComment = postCommentDao.findById((long) ArrayIndex.THIRTY_THIRD_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultPostComment);
    }

    @Test
    void PostCommentDao_saveRecord() {
        PostComment postComment = new PostComment();
        postComment.setIsDeleted(false);
        postComment.setPost(testDataUtil.getPosts().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index));
        postComment.setContent(CONTENT);
        postComment.setAuthor(testDataUtil.getUserProfiles().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));
        postComment.setCreationDate(new Date());

        postCommentDao.save(postComment);
        Assertions.assertNotNull(postComment.getId());
    }

    @Test
    void PostCommentDao_updateRecord() {
        PostComment comment = testDataUtil.getPostComments().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        comment.setContent(CONTENT);

        postCommentDao.updateRecord(comment);
        PostComment resultPostComment = postCommentDao.findById(comment.getId());
        Assertions.assertNotNull(resultPostComment);
        Assertions.assertEquals(comment, resultPostComment);
    }

    @Test
    void PostCommentDao_deleteRecord() {
        PostComment postComment = testDataUtil.getPostComments().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        postCommentDao.deleteRecord(postComment);
        PostComment resultPostComment = postCommentDao.findById(postComment.getId());
        Assertions.assertNull(resultPostComment);
    }

    @Test
    void PostCommentDao_findByIdAndEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        PostComment postComment = testDataUtil.getPostComments().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        PostComment resultPostComment = postCommentDao.findByIdAndEmail(email, postComment.getId());
        Assertions.assertNotNull(resultPostComment);
        Assertions.assertEquals(postComment, resultPostComment);
    }

    @Test
    void PostCommentDao_getPostComments() {
        Post post = testDataUtil.getPosts().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        List<PostComment> resultPostComments = postCommentDao.getPostComments(post.getId(), FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPostComments);
        Assertions.assertFalse(resultPostComments.isEmpty());
        Assertions.assertEquals(post.getPostComments().size(), resultPostComments.size());
        Assertions.assertEquals(post.getPostComments().get(
            ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultPostComments.get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));
        Assertions.assertEquals(post.getPostComments().get(
            ArrayIndex.SECOND_INDEX_OF_ARRAY.index), resultPostComments.get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index));
    }

}
