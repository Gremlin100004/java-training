package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.model.PostComment;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.service.config.PostCommentTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@SpringBootTest(classes = TestConfig.class)
public class PostCommentServiceTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    private PostCommentService postCommentService;
    @Autowired
    private PostCommentDao postCommentDao;

    @Test
    void PostCommentServiceImpl_getComments() {
        List<PostComment> postComments = PostCommentTestData.getTestPostComments();
        List<PostCommentDto> postCommentsDto = PostCommentTestData.getTestPostCommentsDto();
        Mockito.doReturn(postComments).when(postCommentDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostCommentDto> resultPostCommentsDto = postCommentService.getComments(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostCommentsDto);
        Assertions.assertEquals(PostCommentTestData.getRightNumberPostComments(), resultPostCommentsDto.size());
        Assertions.assertFalse(resultPostCommentsDto.isEmpty());
        Assertions.assertEquals(resultPostCommentsDto, postCommentsDto);
        Mockito.verify(postCommentDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_updateComment() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        PostCommentDto postCommentDto = PostCommentTestData.getTestPostCommentDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(postComment).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertDoesNotThrow(() -> postCommentService.updateComment(postCommentDto));
        Mockito.verify(postCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_updateComment_postCommentDao_findByIdAndEmail_nullObject() {
        PostCommentDto postCommentDto = PostCommentTestData.getTestPostCommentDto();
        UserProfile wrongUserProfile = UserProfileTestData.getTestUserProfile();
        wrongUserProfile.setId(UserProfileTestData.getUserProfileOtherId());
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.updateComment(postCommentDto));
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(postComment).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertDoesNotThrow(() -> postCommentService.deleteCommentByUser(
            PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser_postCommentDao_findByIdAndEmail_nullObject() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteCommentByUser(
            PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser_postCommentDao_findByIdAndEmail_deletedObject() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        postComment.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(postComment).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteCommentByUser(
            PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteComment() {
        PostComment comment = PostCommentTestData.getTestPostComment();
        Mockito.doReturn(comment).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());

        Assertions.assertDoesNotThrow(() -> postCommentService.deleteComment(PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).deleteRecord(comment);
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(PostCommentTestData.getPostCommentId());
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteComment_postCommentDao_findById_nullObject() {
        PostComment comment = PostCommentTestData.getTestPostComment();
        Mockito.doReturn(null).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteComment(
            PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(
            PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).deleteRecord(comment);
        Mockito.reset(postCommentDao);
    }

}
