package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;
import com.senla.socialnetwork.service.config.PrivateMessageTestData;
import com.senla.socialnetwork.service.config.PublicMessageCommentTestData;
import com.senla.socialnetwork.service.config.PublicMessageTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class PublicMessageServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    PublicMessageService publicMessageService;
    @Autowired
    PublicMessageDao publicMessageDao;
    @Autowired
    PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    UserProfileDao userProfileDao;

    @Test
    void PublicMessageServiceImpl_getAllMessages() {
        List<PublicMessage> publicMessages = PublicMessageTestData.getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getTestPublicMessagesDto();
        Mockito.doReturn(publicMessages).when(publicMessageDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultPublicMessagesDto = publicMessageService.getMessages(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPublicMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPublicMessagesDto.size());
        Assertions.assertFalse(resultPublicMessagesDto.isEmpty());
        Assertions.assertEquals(resultPublicMessagesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_getPublicMessages() {
        List<PublicMessage> publicMessages = PublicMessageTestData.getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getTestPublicMessagesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessages).when(publicMessageDao).getByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultProfilesDto = publicMessageService.getPublicMessages(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(PublicMessageTestData.getRightNumberPublicMessages(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_addMessage() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        PublicMessageForCreateDto publicMessageDto = PublicMessageTestData.getTestPublicMessageForCreationDto();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(publicMessage).when(publicMessageDao).saveRecord(ArgumentMatchers.any(PublicMessage.class));

        PublicMessageDto resultPublicMessageDto = publicMessageService.addMessage(publicMessageDto);
        Assertions.assertNotNull(resultPublicMessageDto);
        Mockito.verify(publicMessageDao, Mockito.never()).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(publicMessageDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void PublicMessageServiceImpl_updateMessage() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        PublicMessageDto publicMessageDto = PublicMessageTestData.getTestPublicMessageDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessage).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertDoesNotThrow(() -> publicMessageService.updateMessage(publicMessageDto));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_updateMessage_publicMessageDao_findByIdAndEmail_nullObject() {
        PublicMessageDto publicMessageDto = PublicMessageTestData.getTestPublicMessageDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.updateMessage(publicMessageDto));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessageByUser() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessage).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertDoesNotThrow(() -> publicMessageService.deleteMessageByUser(
            PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessageByUser_publicMessageDao_findByIdAndEmail_nullObject() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.deleteMessageByUser(
            PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessageByUser_publicMessageDao_findByIdAndEmail_deletedObject() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        publicMessage.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.deleteMessageByUser(
            PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessage() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        Mockito.doReturn(publicMessage).when(publicMessageDao).findById(PublicMessageTestData.getPublicMessageId());

        Assertions.assertDoesNotThrow(() -> publicMessageService.deleteMessage(
            PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).deleteRecord(publicMessage);
        Mockito.verify(publicMessageDao, Mockito.times(1)).findById(
            PublicMessageTestData.getPublicMessageId());
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessage_publicMessageDao_findById_nullObject() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        Mockito.doReturn(null).when(publicMessageDao).findById(PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.deleteMessage(
            PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findById(
            PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.never()).deleteRecord(publicMessage);
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_getPublicMessageComments() {
        List<PublicMessageComment> publicMessageComments = PublicMessageCommentTestData.getTestPublicMessageComments();
        List<PublicMessageCommentDto> publicMessageCommentsDto = PublicMessageCommentTestData.getTestPublicMessageCommentsDto();
        Mockito.doReturn(publicMessageComments).when(publicMessageCommentDao).getPublicMessageComments(
            PublicMessageTestData.getPublicMessageId(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageCommentDto> resultPublicMessageCommentsDto = publicMessageService.getPublicMessageComments(
            PublicMessageTestData.getPublicMessageId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPublicMessageCommentsDto);
        Assertions.assertEquals(
            PrivateMessageTestData.getRightNumberPrivateMessages(), resultPublicMessageCommentsDto.size());
        Assertions.assertFalse(resultPublicMessageCommentsDto.isEmpty());
        Assertions.assertEquals(resultPublicMessageCommentsDto, publicMessageCommentsDto);
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).getPublicMessageComments(
            PublicMessageTestData.getPublicMessageId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_addComment() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        PublicMessageCommentForCreateDto publicMessageCommentDto = PublicMessageCommentTestData
            .getTestPublicMessageCommentForCreationDto();
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).saveRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.doReturn(publicMessage).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        PublicMessageCommentDto resultPublicMessageCommentDto = publicMessageService.addComment(
            PublicMessageTestData.getPublicMessageId(), publicMessageCommentDto);
        Assertions.assertNotNull(resultPublicMessageCommentDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.reset(publicMessageCommentDao);
        Mockito.reset(publicMessageDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_addComment_publicMessageDao_findByIdAndEmail_nullObject() {
        PublicMessageCommentForCreateDto publicMessageCommentDto = PublicMessageCommentTestData
            .getTestPublicMessageCommentForCreationDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.addComment(
            PublicMessageTestData.getPublicMessageId(), publicMessageCommentDto));
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).saveRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_addComment_publicMessageDao_findByIdAndEmail_deletedObject() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        publicMessage.setIsDeleted(true);
        PublicMessageCommentForCreateDto publicMessageCommentDto = PublicMessageCommentTestData
            .getTestPublicMessageCommentForCreationDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessage).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.addComment(
            PublicMessageTestData.getPublicMessageId(), publicMessageCommentDto));
        Mockito.verify(userProfileDao, Mockito.never()).findByEmail(UserTestData.getEmail());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).saveRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.reset(publicMessageDao);
    }

}
