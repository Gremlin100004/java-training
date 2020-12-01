package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PublicMessageMapper {
    public static PublicMessageDto getPublicMessageDto(final PublicMessage publicMessage) {
        PublicMessageDto publicMessageDto = new PublicMessageDto();
        publicMessageDto.setId(publicMessage.getId());
        publicMessageDto.setCreationDate(publicMessage.getCreationDate());
        publicMessageDto.setAuthor(UserProfileMapper.getUserProfileForIdentificationDto(publicMessage.getAuthor()));
        publicMessageDto.setTittle(publicMessage.getTittle());
        publicMessageDto.setContent(publicMessage.getContent());
        publicMessageDto.setDeleted(publicMessage.getIsDeleted());
        return publicMessageDto;
    }

    public static List<PublicMessageDto> getPublicMessageDto(final List<PublicMessage> publicMessages) {
        return publicMessages.stream()
            .map(PublicMessageMapper::getPublicMessageDto)
            .collect(Collectors.toList());
    }

    public static PublicMessage getPublicMessage(final PublicMessageDto publicMessageDto,
                                                 final PublicMessageDao publicMessageDao,
                                                 final UserProfileDao userProfileDao) {
        PublicMessage publicMessage = publicMessageDao.findById(publicMessageDto.getId());
        publicMessage.setAuthor(UserProfileMapper.getUserProfileFromUserProfileForIdentificationDto(
            publicMessageDto.getAuthor(), userProfileDao));
        publicMessage.setTittle(publicMessageDto.getTittle());
        publicMessage.setContent(publicMessageDto.getContent());
        publicMessage.setIsDeleted(publicMessageDto.getDeleted());
        return publicMessage;
    }

    public static PublicMessage getNewPublicMessage(final PublicMessageForCreateDto publicMessageDto,
                                                    final UserProfile userProfile) {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setTittle(publicMessageDto.getTittle());
        publicMessage.setContent(publicMessageDto.getContent());
        publicMessage.setAuthor(userProfile);
        publicMessage.setCreationDate(new Date());
        return publicMessage;
    }

}
