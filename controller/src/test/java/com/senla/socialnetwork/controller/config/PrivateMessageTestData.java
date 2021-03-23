package com.senla.socialnetwork.controller.config;

import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;
import com.senla.socialnetwork.model.PrivateMessage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrivateMessageTestData {
    private static final Long PRIVATE_MESSAGE_ID = 1L;
    private static final Long PRIVATE_MESSAGE_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_PRIVATE_MESSAGES = 2L;
    private static final Date PRIVATE_MESSAGE_CREATION_DATE = new Date();
    private static final String CONTENT = "Test";

    public static Long getPrivateMessageId() {
        return PRIVATE_MESSAGE_ID;
    }

    public static Long getRightNumberPrivateMessages() {
        return RIGHT_NUMBER_PRIVATE_MESSAGES;
    }

    public static String getContent() {
        return CONTENT;
    }

    public static PrivateMessage getTestPrivateMessage() {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setId(PRIVATE_MESSAGE_ID);
        privateMessage.setSender(UserProfileTestData.getTestUserProfile());
        privateMessage.setRecipient(UserProfileTestData.getTestUserProfile());
        privateMessage.setContent(CONTENT);
        privateMessage.setDepartureDate(PRIVATE_MESSAGE_CREATION_DATE);
        privateMessage.setIsRead(false);
        privateMessage.setIsDeleted(false);
        return privateMessage;
    }

    public static PrivateMessageDto getPrivateMessageDto() {
        PrivateMessageDto privateMessageDto = new PrivateMessageDto();
        privateMessageDto.setId(PRIVATE_MESSAGE_ID);
        privateMessageDto.setSender(UserProfileTestData.getTestUserProfileForIdentificationDto());
        privateMessageDto.setRecipient(UserProfileTestData.getTestUserProfileForIdentificationDto());
        privateMessageDto.setContent(CONTENT);
        privateMessageDto.setDepartureDate(PRIVATE_MESSAGE_CREATION_DATE);
        return privateMessageDto;
    }

    public static PrivateMessageForCreateDto getPrivateMessageForCreationDto() {
        PrivateMessageForCreateDto privateMessageDto = new PrivateMessageForCreateDto();
        privateMessageDto.setRecipient(UserProfileTestData.getTestUserProfileForIdentificationDto());
        privateMessageDto.setContent(CONTENT);
        return privateMessageDto;
    }

    public static List<PrivateMessage> getTestPrivateMessages() {
        PrivateMessage privateMessageOne = getTestPrivateMessage();
        PrivateMessage privateMessageTwo = getTestPrivateMessage();
        privateMessageTwo.setId(PRIVATE_MESSAGE_OTHER_ID);
        List<PrivateMessage> privateMessages = new ArrayList<>();
        privateMessages.add(privateMessageOne);
        privateMessages.add(privateMessageTwo);
        return privateMessages;
    }

    public static List<PrivateMessageDto> getTestPrivateMessagesDto() {
        PrivateMessageDto privateMessageDtoOne = getPrivateMessageDto();
        PrivateMessageDto privateMessageDtoTwo = getPrivateMessageDto();
        privateMessageDtoTwo.setId(PRIVATE_MESSAGE_OTHER_ID);
        List<PrivateMessageDto> privateMessagesDto = new ArrayList<>();
        privateMessagesDto.add(privateMessageDtoOne);
        privateMessagesDto.add(privateMessageDtoTwo);
        return privateMessagesDto;
    }

}
