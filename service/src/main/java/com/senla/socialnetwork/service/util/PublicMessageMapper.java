package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.dto.PublicMessageDto;

import java.util.List;
import java.util.stream.Collectors;

public class PublicMessageMapper {
    public static PublicMessageDto getPublicMessageDto(final PublicMessage publicMessage) {
        PublicMessageDto publicMessageDto = new PublicMessageDto();
        publicMessageDto.setId(publicMessage.getId());
        publicMessageDto.setCreationDate(publicMessage.getCreationDate());
        publicMessageDto.setAuthor(UserProfileMapper.getUserProfileDto(publicMessage.getAuthor()));
        publicMessageDto.setTittle(publicMessage.getTittle());
        publicMessageDto.setContent(publicMessage.getContent());
        publicMessageDto.setDeleted(publicMessage.isDeleted());
        return publicMessageDto;
    }

    public static List<PublicMessageDto> getPublicMessageDto(final List<PublicMessage> publicMessages) {
        return publicMessages.stream()
            .map(PublicMessageMapper::getPublicMessageDto)
            .collect(Collectors.toList());
    }

    public static PublicMessage getPublicMessage(final PublicMessageDto publicMessageDto,
                                                 final PublicMessageDao publicMessageDao,
                                                 final UserProfileDao userProfileDao,
                                                 final LocationDao locationDao,
                                                 final SchoolDao schoolDao,
                                                 final UniversityDao universityDao) {
        PublicMessage publicMessage;
        if (publicMessageDto.getId() == null) {
            publicMessage = new PublicMessage();
        } else {
            publicMessage = publicMessageDao.findById(publicMessageDto.getId());
        }
        publicMessage.setAuthor(UserProfileMapper.getUserProfile(
            publicMessageDto.getAuthor(), userProfileDao, locationDao, schoolDao, universityDao));
        publicMessage.setTittle(publicMessageDto.getTittle());
        publicMessage.setContent(publicMessageDto.getContent());
        publicMessage.setDeleted(publicMessageDto.isDeleted());
        return publicMessage;
    }

}
