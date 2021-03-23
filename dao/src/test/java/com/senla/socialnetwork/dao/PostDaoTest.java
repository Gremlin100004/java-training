package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Community;
import com.senla.socialnetwork.model.Post;
import com.senla.socialnetwork.model.PostComment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class PostDaoTest extends AbstractDaoTest {
    private static final String TITTLE = "test";
    private static final String CONTENT = "test";
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostCommentDao postCommentDao;

    @Test
    void PostDao_getAllRecords() {
        List<Post> resultPosts = postDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultPosts);
        Assertions.assertFalse(resultPosts.isEmpty());
        Assertions.assertEquals(resultPosts.size(), testDataUtil.getPosts().size());
        Assertions.assertEquals(resultPosts, testDataUtil.getPosts());
    }

    @Test
    void PostDao_findById() {
        Post resultPost = postDao.findById(testDataUtil.getPosts().get(
            ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultPost);
        Assertions.assertEquals(testDataUtil.getPosts().get(
            ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultPost);
    }

    @Test
    void PostDao_findById_ErrorId() {
        Post resultPost = postDao.findById((long) ArrayIndex.THIRTY_FIRST_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultPost);
    }

    @Test
    void PostDao_saveRecord() {
        Post post = new Post();
        post.setIsDeleted(false);
        post.setCommunity(testDataUtil.getCommunities().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));
        post.setTitle(TITTLE);
        post.setContent(CONTENT);
        post.setCreationDate(new Date());

        postDao.save(post);
        Assertions.assertNotNull(post.getId());
    }

    @Test
    void PostDao_updateRecord() {
        Post post = testDataUtil.getPosts().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);
        post.setContent(CONTENT);

        postDao.updateRecord(post);
        Post resultPost = postDao.findById(post.getId());
        Assertions.assertNotNull(resultPost);
        Assertions.assertEquals(post, resultPost);
    }

    @Test
    void PostDao_deleteRecord() {
        Post post = testDataUtil.getPosts().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        post.getPostComments().forEach(postComment -> postCommentDao.deleteRecord(postComment));

        postDao.deleteRecord(post);
        Post resultLocation = postDao.findById(post.getId());
        Assertions.assertNull(resultLocation);
    }

    @Test
    void PostDao_findByIdAndEmail() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        Post post = testDataUtil.getPosts().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        Post resultPost = postDao.findByIdAndEmail(email, post.getId());
        Assertions.assertNotNull(resultPost);
        Assertions.assertEquals(post, resultPost);
    }

    @Test
    void PostDao_getByCommunityId() {
        Community community = testDataUtil.getCommunities().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        List<Post> resultPosts = postDao.getByCommunityId(community.getId(), FIRST_RESULT, MAX_RESULT);
        Assertions.assertFalse(resultPosts.isEmpty());
        Assertions.assertEquals(resultPosts.size(), community.getPosts().size());
        Assertions.assertEquals(resultPosts, community.getPosts());
    }

}
