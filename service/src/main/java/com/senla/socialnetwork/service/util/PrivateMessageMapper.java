package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PrivateMessageMapper {

    public static PrivateMessageDto getPrivateMessageDto(final PrivateMessage privateMessage) {
        PrivateMessageDto privateMessageDto = new PrivateMessageDto();
        privateMessageDto.setId(privateMessage.getId());
        privateMessageDto.setDepartureDate(privateMessage.getDepartureDate());
        privateMessageDto.setSender(UserProfileMapper.getUserProfileForIdentificationDto(privateMessage.getSender()));
        privateMessageDto.setRecipient(UserProfileMapper.getUserProfileForIdentificationDto(
            privateMessage.getRecipient()));
        privateMessageDto.setContent(privateMessage.getContent());
        privateMessageDto.setRead(privateMessage.getIsRead());
        privateMessageDto.setDeleted(privateMessage.getIsDeleted());
        return privateMessageDto;
    }

    public static List<PrivateMessageDto> getPrivateMessageDto(final List<PrivateMessage> privateMessages) {
        return privateMessages.stream()
            .map(PrivateMessageMapper::getPrivateMessageDto)
            .collect(Collectors.toList());
    }

    public static PrivateMessage getPrivateMessage(final PrivateMessageDto privateMessageDto,
                                                   final PrivateMessageDao privateMessageDao,
                                                   final UserProfileDao userProfileDao) {
        PrivateMessage privateMessage = privateMessageDao.findById(privateMessageDto.getId());
        privateMessage.setId(privateMessageDto.getId());
        privateMessage.setDepartureDate(privateMessageDto.getDepartureDate());
        privateMessage.setSender(UserProfileMapper.getUserProfileFromUserProfileForIdentificationDto(
            privateMessageDto.getSender(), userProfileDao));
        privateMessage.setRecipient(UserProfileMapper.getUserProfileFromUserProfileForIdentificationDto(
            privateMessageDto.getRecipient(), userProfileDao));
        privateMessage.setContent(privateMessageDto.getContent());
        privateMessage.setIsRead(privateMessageDto.getRead());
        privateMessage.setIsDeleted(privateMessageDto.getDeleted());
        return privateMessage;
    }

    public static PrivateMessage getNewPrivateMessage(final PrivateMessageForCreateDto privateMessageDto,
                                                      final UserProfile userProfile) {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setRecipient(userProfile);
        privateMessage.setDepartureDate(new Date());
        privateMessage.setContent(privateMessageDto.getContent());
        return privateMessage;
    }

}
