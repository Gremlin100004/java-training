package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.dto.PublicMessageDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicMessageTestData {
    private static final Long PUBLIC_MESSAGE_ID = 1L;
    private static final Long PUBLIC_MESSAGE_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_PUBLIC_MESSAGES = 2L;
    private static final Date PUBLIC_MESSAGE_CREATION_DATE = new Date();

    public static Long getPublicMessageId() {
        return PUBLIC_MESSAGE_ID;
    }

    public static Long getRightNumberPublicMessages() {
        return RIGHT_NUMBER_PUBLIC_MESSAGES;
    }

    public static PublicMessage getTestPublicMessage() {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setId(PUBLIC_MESSAGE_ID);
        publicMessage.setCreationDate(PUBLIC_MESSAGE_CREATION_DATE);
        publicMessage.setAuthor(UserProfileTestData.getTestUserProfile());
        return publicMessage;
    }

    public static PublicMessageDto getTestPublicMessageDto() {
        PublicMessageDto publicMessageDto = new PublicMessageDto();
        publicMessageDto.setId(PUBLIC_MESSAGE_ID);
        publicMessageDto.setCreationDate(PUBLIC_MESSAGE_CREATION_DATE);
        publicMessageDto.setAuthor(UserProfileTestData.getTestUserProfileDto());
        return publicMessageDto;
    }

    public static List<PublicMessage> getTestPublicMessages() {
        PublicMessage publicMessageOne = getTestPublicMessage();
        PublicMessage publicMessageTwo = getTestPublicMessage();
        publicMessageTwo.setId(PUBLIC_MESSAGE_OTHER_ID);
        List<PublicMessage> publicMessages = new ArrayList<>();
        publicMessages.add(publicMessageOne);
        publicMessages.add(publicMessageTwo);
        return publicMessages;
    }

    public static List<PublicMessageDto> getTestPublicMessagesDto() {
        PublicMessageDto publicMessageDtoOne = getTestPublicMessageDto();
        PublicMessageDto publicMessageDtoTwo = getTestPublicMessageDto();
        publicMessageDtoTwo.setId(PUBLIC_MESSAGE_OTHER_ID);
        List<PublicMessageDto> publicMessagesDto = new ArrayList<>();
        publicMessagesDto.add(publicMessageDtoOne);
        publicMessagesDto.add(publicMessageDtoTwo);
        return publicMessagesDto;
    }

}
