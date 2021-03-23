package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;
import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.service.exception.BusinessException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class PublicMessageMapper {
    private PublicMessageMapper() {
    }

    public static PublicMessageDto getPublicMessageDto(final PublicMessage publicMessage) {
        PublicMessageDto publicMessageDto = new PublicMessageDto();
        publicMessageDto.setId(publicMessage.getId());
        publicMessageDto.setCreationDate(publicMessage.getCreationDate());
        publicMessageDto.setAuthor(UserProfileMapper.getUserProfileForIdentificationDto(publicMessage.getAuthor()));
        publicMessageDto.setTitle(publicMessage.getTitle());
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
                                                 final String email) {
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(email, publicMessageDto.getId());
        if (publicMessage == null) {
            throw new BusinessException("Error, this message does not belong to this profile");
        }
        publicMessage.setTitle(publicMessageDto.getTitle());
        publicMessage.setContent(publicMessageDto.getContent());
        return publicMessage;
    }

    public static PublicMessage getNewPublicMessage(final PublicMessageForCreateDto publicMessageDto,
                                                    final UserProfile userProfile) {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setTitle(publicMessageDto.getTittle());
        publicMessage.setContent(publicMessageDto.getContent());
        publicMessage.setAuthor(userProfile);
        publicMessage.setCreationDate(new Date());
        publicMessage.setIsDeleted(false);
        return publicMessage;
    }

}
