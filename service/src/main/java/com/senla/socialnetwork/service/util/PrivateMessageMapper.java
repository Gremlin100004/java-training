package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.dto.PrivateMessageDto;

import java.util.List;
import java.util.stream.Collectors;

public class PrivateMessageMapper {
    public static PrivateMessageDto getPrivateMessageDto(PrivateMessage privateMessage) {
        PrivateMessageDto privateMessageDto = new PrivateMessageDto();
        privateMessageDto.setId(privateMessage.getId());
        privateMessageDto.setDepartureDate(privateMessage.getDepartureDate());
        privateMessageDto.setSender(UserProfileMapper.getUserProfileDto(privateMessage.getSender()));
        privateMessageDto.setRecipient(UserProfileMapper.getUserProfileDto(privateMessage.getRecipient()));
        privateMessageDto.setContent(privateMessage.getContent());
        privateMessageDto.setRead(privateMessage.isRead());
        privateMessageDto.setDeleted(privateMessage.isDeleted());
        return privateMessageDto;
    }

    public static List<PrivateMessageDto> getPrivateMessageDto(List<PrivateMessage> privateMessages) {
        return privateMessages.stream()
            .map(PrivateMessageMapper::getPrivateMessageDto)
            .collect(Collectors.toList());
    }

    public static PrivateMessage getPrivateMessage(PrivateMessageDto privateMessageDto,
                                                   PrivateMessageDao privateMessageDao,
                                                   UserProfileDao userProfileDao,
                                                   LocationDao locationDao,
                                                   SchoolDao schoolDao,
                                                   UniversityDao universityDao) {
        PrivateMessage privateMessage;
        if (privateMessageDto.getId() == null) {
            privateMessage = new PrivateMessage();
        } else {
            privateMessage = privateMessageDao.findById(privateMessageDto.getId());
        }
        privateMessage.setId(privateMessageDto.getId());
        privateMessage.setDepartureDate(privateMessageDto.getDepartureDate());
        privateMessage.setSender(UserProfileMapper.getUserProfile(
            privateMessageDto.getSender(), userProfileDao, locationDao, schoolDao, universityDao));
        privateMessage.setRecipient(UserProfileMapper.getUserProfile(
            privateMessageDto.getRecipient(), userProfileDao, locationDao, schoolDao, universityDao));
        privateMessage.setContent(privateMessageDto.getContent());
        privateMessage.setRead(privateMessageDto.isRead());
        privateMessage.setDeleted(privateMessageDto.isDeleted());
        return privateMessage;
    }

}
