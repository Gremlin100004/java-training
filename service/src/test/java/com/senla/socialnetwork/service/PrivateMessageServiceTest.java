package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;
import com.senla.socialnetwork.model.PrivateMessage;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PrivateMessageTestData;
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

import java.util.Date;
import java.util.List;

@SpringBootTest(classes = TestConfig.class)
public class PrivateMessageServiceTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final Date START_PERIOD_DATE = new Date();
    private static final Date END_PERIOD_DATE = new Date();
    @Autowired
    private PrivateMessageService privateMessageService;
    @Autowired
    private PrivateMessageDao privateMessageDao;
    @Autowired
    private UserProfileDao userProfileDao;

    @Test
    void PrivateMessageServiceImpl_getPrivateMessages() {
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessages).when(privateMessageDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = privateMessageService.getPrivateMessages(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(
            PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_getPrivateMessagesByUser() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(privateMessages).when(privateMessageDao).findByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = privateMessageService.getPrivateMessagesByUser(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_getPrivateMessagesByUser_userProfileDao_findByEmail_nullObject() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.getPrivateMessagesByUser(
            FIRST_RESULT, NORMAL_MAX_RESULTS));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(privateMessageDao, Mockito.never()).findByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUnreadMessages() {
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(privateMessages).when(privateMessageDao).getUnreadMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = privateMessageService.getUnreadMessages(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getUnreadMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_getMessageFilteredByPeriod() {
        List<PrivateMessage> locations = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> locationsDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(locations).when(privateMessageDao).getMessageFilteredByPeriod(
            UserTestData.getEmail(), START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = privateMessageService.getMessageFilteredByPeriod(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(
            PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, locationsDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getMessageFilteredByPeriod(
            UserTestData.getEmail(), START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_addMessage() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        PrivateMessageForCreateDto privateMessageDto = PrivateMessageTestData.getTestPrivateMessageForCreationDto();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(privateMessage).when(privateMessageDao).save(ArgumentMatchers.any(PrivateMessage.class));

        PrivateMessageDto resultPrivateMessageDto = privateMessageService.addMessage(privateMessageDto);
        Assertions.assertNotNull(resultPrivateMessageDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).save(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.verify(privateMessageDao, Mockito.never()).findById(PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(privateMessageDao);
        Mockito.reset(userProfileDao);
    }

    @Test
    void PrivateMessageServiceImpl_updateMessage() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        PrivateMessageDto privateMessageDto = PrivateMessageTestData.getTestPrivateMessageDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(privateMessage).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertDoesNotThrow(() -> privateMessageService.updateMessage(privateMessageDto));
        Mockito.verify(privateMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_updateMessage_privateMessageDao_findByIdAndEmail_nullObject() {
        PrivateMessageDto privateMessageDto = PrivateMessageTestData.getTestPrivateMessageDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.updateMessage(privateMessageDto));
        Mockito.verify(privateMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessageByUser() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(privateMessage).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertDoesNotThrow(() -> privateMessageService.deleteMessageByUser(
            PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(privateMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessageByUser_privateMessageDao_findByIdAndEmail_nullObject() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.deleteMessageByUser(
            PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(privateMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessageByUser_privateMessageDao_findByIdAndEmail_deletedObject() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        privateMessage.setIsDeleted(true);
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(privateMessage).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.deleteMessageByUser(
            PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(privateMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessage() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        Mockito.doReturn(privateMessage).when(privateMessageDao).findById(PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertDoesNotThrow(() -> privateMessageService.deleteMessage(
            PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).deleteRecord(privateMessage);
        Mockito.verify(privateMessageDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessage_privateMessageDao_findById_nullObject() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        Mockito.doReturn(null).when(
            privateMessageDao).findById(PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.deleteMessage(
            PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(privateMessageDao, Mockito.never()).deleteRecord(privateMessage);
        Mockito.reset(privateMessageDao);
    }

}
