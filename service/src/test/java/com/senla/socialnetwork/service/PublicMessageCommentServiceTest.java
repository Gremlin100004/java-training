package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.model.PublicMessageComment;
import com.senla.socialnetwork.service.config.PrivateMessageTestData;
import com.senla.socialnetwork.service.config.PublicMessageCommentTestData;
import com.senla.socialnetwork.service.config.TestConfig;
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
public class PublicMessageCommentServiceTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    private PublicMessageCommentService publicMessageCommentService;
    @Autowired
    private PublicMessageCommentDao publicMessageCommentDao;

    @Test
    void PublicMessageCommentServiceImpl_getComments() {
        List<PublicMessageComment> publicMessageComments = PublicMessageCommentTestData.getTestPublicMessageComments();
        List<PublicMessageCommentDto> publicMessageCommentsDto = PublicMessageCommentTestData
            .getTestPublicMessageCommentsDto();
        Mockito.doReturn(publicMessageComments).when(publicMessageCommentDao).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageCommentDto> resultPublicMessageCommentsDto = publicMessageCommentService.getComments(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPublicMessageCommentsDto);
        Assertions.assertEquals(
            PublicMessageCommentTestData.getRightNumberPublicMessageComments(), resultPublicMessageCommentsDto.size());
        Assertions.assertFalse(resultPublicMessageCommentsDto.isEmpty());
        Assertions.assertEquals(resultPublicMessageCommentsDto, publicMessageCommentsDto);
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_updateComment() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        PublicMessageCommentDto publicMessageCommentDto = PublicMessageCommentTestData.getTestPublicMessageCommentDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertDoesNotThrow(() -> publicMessageCommentService.updateComment(publicMessageCommentDto));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_updateComment_publicMessageCommentDao_findByIdAndEmail_nullObject() {
        PublicMessageCommentDto publicMessageCommentDto = PublicMessageCommentTestData.getTestPublicMessageCommentDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageCommentService.updateComment(
            publicMessageCommentDto));
        Mockito.verify(publicMessageCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteCommentByUser() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertDoesNotThrow(() -> publicMessageCommentService.deleteCommentByUser(
            PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteCommentByUser_publicMessageCommentDao_findByIdAndEmail_nullObject() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageCommentService.deleteCommentByUser(
            PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteCommentByUser_publicMessageCommentDao_findByIdAndEmail_deletedObject() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        publicMessageComment.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageCommentService.deleteCommentByUser(
            PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteComment() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertDoesNotThrow(() -> publicMessageCommentService.deleteComment(
            PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).deleteRecord(
            publicMessageComment);
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteComment_publicMessageCommentDao_findById_nullObject() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        Mockito.doReturn(null).when(publicMessageCommentDao).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageCommentService.deleteComment(
            PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).deleteRecord(publicMessageComment);
        Mockito.reset(publicMessageCommentDao);
    }

}
