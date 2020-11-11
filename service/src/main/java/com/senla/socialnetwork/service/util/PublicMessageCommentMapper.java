package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;

import java.util.List;
import java.util.stream.Collectors;

public class PublicMessageCommentMapper {
    public static PublicMessageCommentDto getPublicMessageCommentDto(PublicMessageComment publicMessageComment) {
        PublicMessageCommentDto publicMessageCommentDto = new PublicMessageCommentDto();
        publicMessageCommentDto.setId(publicMessageComment.getId());
        publicMessageCommentDto.setCreationDate(publicMessageComment.getCreationDate());
        publicMessageCommentDto.setAuthor(UserProfileMapper.getUserProfileDto(publicMessageComment.getAuthor()));
        publicMessageCommentDto.setPublicMessage(
            PublicMessageMapper.getPublicMessageDto(publicMessageComment.getPublicMessage()));
        publicMessageCommentDto.setContent(publicMessageComment.getContent());
        publicMessageCommentDto.setDeleted(publicMessageComment.isDeleted());
        return publicMessageCommentDto;
    }

    public static List<PublicMessageCommentDto> getPublicMessageCommentDto(List<PublicMessageComment> publicMessageComments) {
        return publicMessageComments.stream()
            .map(PublicMessageCommentMapper::getPublicMessageCommentDto)
            .collect(Collectors.toList());
    }

    public static PublicMessageComment getPublicMessageComment(PublicMessageCommentDto publicMessageCommentDto,
                                                               PublicMessageCommentDao publicMessageCommentDao,
                                                               PublicMessageDao publicMessageDao,
                                                               UserProfileDao userProfileDao,
                                                               LocationDao locationDao,
                                                               SchoolDao schoolDao,
                                                               UniversityDao universityDao) {
        PublicMessageComment publicMessageComment;
        if (publicMessageCommentDto.getId() == null) {
            publicMessageComment = new PublicMessageComment();
        } else {
            publicMessageComment = publicMessageCommentDao.findById(publicMessageCommentDto.getId());
        }
        publicMessageComment.setCreationDate(publicMessageCommentDto.getCreationDate());
        publicMessageComment.setAuthor(UserProfileMapper.getUserProfile(
            publicMessageCommentDto.getAuthor(), userProfileDao, locationDao, schoolDao, universityDao));
        publicMessageComment.setPublicMessage(PublicMessageMapper.getPublicMessage(
            publicMessageCommentDto.getPublicMessage(), publicMessageDao, userProfileDao,
            locationDao, schoolDao, universityDao));
        publicMessageComment.setContent(publicMessageCommentDto.getContent());
        publicMessageComment.setDeleted(publicMessageCommentDto.isDeleted());
        return publicMessageComment;
    }

}
