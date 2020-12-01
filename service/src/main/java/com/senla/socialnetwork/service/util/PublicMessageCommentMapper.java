package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PublicMessageCommentMapper {
        public static PublicMessageCommentDto getPublicMessageCommentDto(final PublicMessageComment publicMessageComment) {
        PublicMessageCommentDto publicMessageCommentDto = new PublicMessageCommentDto();
        publicMessageCommentDto.setId(publicMessageComment.getId());
        publicMessageCommentDto.setCreationDate(publicMessageComment.getCreationDate());
        publicMessageCommentDto.setAuthor(UserProfileMapper.getUserProfileForIdentificationDto(
            publicMessageComment.getAuthor()));
        publicMessageCommentDto.setPublicMessage(
            PublicMessageMapper.getPublicMessageDto(publicMessageComment.getPublicMessage()));
        publicMessageCommentDto.setContent(publicMessageComment.getContent());
        publicMessageCommentDto.setDeleted(publicMessageComment.getIsDeleted());
        return publicMessageCommentDto;
    }

    public static List<PublicMessageCommentDto> getPublicMessageCommentDto(final List<PublicMessageComment> publicMessageComments) {
        return publicMessageComments.stream()
            .map(PublicMessageCommentMapper::getPublicMessageCommentDto)
            .collect(Collectors.toList());
    }

    public static PublicMessageComment getPublicMessageComment(final PublicMessageCommentDto publicMessageCommentDto,
                                                               final PublicMessageCommentDao publicMessageCommentDao,
                                                               final PublicMessageDao publicMessageDao,
                                                               final UserProfileDao userProfileDao) {
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findById(publicMessageCommentDto.getId());
        publicMessageComment.setCreationDate(publicMessageCommentDto.getCreationDate());
        publicMessageComment.setAuthor(UserProfileMapper.getUserProfileFromUserProfileForIdentificationDto(
            publicMessageCommentDto.getAuthor(), userProfileDao));
        publicMessageComment.setPublicMessage(PublicMessageMapper.getPublicMessage(
            publicMessageCommentDto.getPublicMessage(), publicMessageDao, userProfileDao));
        publicMessageComment.setContent(publicMessageCommentDto.getContent());
        publicMessageComment.setIsDeleted(publicMessageCommentDto.getDeleted());
        return publicMessageComment;
    }

    public static PublicMessageComment getNewPublicMessageComment(final PublicMessageCommentForCreateDto publicMessageCommentDto,
                                                                  final PublicMessage publicMessage,
                                                                  final UserProfile userProfile) {
        PublicMessageComment publicMessageComment = new PublicMessageComment();
        publicMessageComment.setPublicMessage(publicMessage);
        publicMessageComment.setContent(publicMessageCommentDto.getContent());
        publicMessageComment.setAuthor(userProfile);
        publicMessage.setCreationDate(new Date());
        return publicMessageComment;
    }

}
