package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.PublicMessageComment;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.service.exception.BusinessException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class PublicMessageCommentMapper {
    private PublicMessageCommentMapper() {
    }

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
                                                               final String email) {
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findByIdAndEmail(
            email, publicMessageCommentDto.getId());
        if (publicMessageComment == null) {
            throw new BusinessException("Error, this comment does not belong to this profile");
        }
        publicMessageComment.setContent(publicMessageCommentDto.getContent());
        return publicMessageComment;
    }

    public static PublicMessageComment getNewPublicMessageComment(final PublicMessageCommentForCreateDto publicMessageCommentDto,
                                                                  final PublicMessage publicMessage,
                                                                  final UserProfile userProfile) {
        PublicMessageComment publicMessageComment = new PublicMessageComment();
        publicMessageComment.setPublicMessage(publicMessage);
        publicMessageComment.setContent(publicMessageCommentDto.getContent());
        publicMessageComment.setAuthor(userProfile);
        publicMessageComment.setCreationDate(new Date());
        publicMessageComment.setIsDeleted(false);
        return publicMessageComment;
    }

}
