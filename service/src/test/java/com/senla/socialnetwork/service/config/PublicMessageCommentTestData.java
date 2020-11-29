package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PublicMessageCommentTestData {
    private static final Long PUBLIC_MESSAGE_COMMENT_ID = 1L;
    private static final Long PUBLIC_MESSAGE_COMMENT_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_PUBLIC_MESSAGE_COMMENTS = 2L;
    private static final Date PUBLIC_MESSAGE_COMMENT_CREATION_DATE = new Date();

    public static Long getPublicMessageCommentId() {
        return PUBLIC_MESSAGE_COMMENT_ID;
    }

    public static Long getRightNumberPublicMessageComments() {
        return RIGHT_NUMBER_PUBLIC_MESSAGE_COMMENTS;
    }

    public static PublicMessageComment getTestPublicMessageComment() {
        PublicMessageComment publicMessageComment = new PublicMessageComment();
        publicMessageComment.setId(PUBLIC_MESSAGE_COMMENT_ID);
        publicMessageComment.setCreationDate(PUBLIC_MESSAGE_COMMENT_CREATION_DATE);
        publicMessageComment.setAuthor(UserProfileTestData.getTestUserProfile());
        publicMessageComment.setPublicMessage(PublicMessageTestData.getTestPublicMessage());
        return publicMessageComment;
    }

    public static PublicMessageCommentDto getTestPublicMessageCommentDto() {
        PublicMessageCommentDto publicMessageCommentDto = new PublicMessageCommentDto();
        publicMessageCommentDto.setId(PUBLIC_MESSAGE_COMMENT_ID);
        publicMessageCommentDto.setCreationDate(PUBLIC_MESSAGE_COMMENT_CREATION_DATE);
        publicMessageCommentDto.setAuthor(UserProfileTestData.getTestUserProfileForIdentificationDto());
        publicMessageCommentDto.setPublicMessage(PublicMessageTestData.getTestPublicMessageDto());
        return publicMessageCommentDto;
    }

    public static PublicMessageCommentForCreateDto getTestPublicMessageCommentForCreationDto() {
        return new PublicMessageCommentForCreateDto();
    }

    public static List<PublicMessageComment> getTestPublicMessageComments() {
        PublicMessageComment publicMessageCommentOne = getTestPublicMessageComment();
        PublicMessageComment publicMessageCommentTwo = getTestPublicMessageComment();
        publicMessageCommentTwo.setId(PUBLIC_MESSAGE_COMMENT_OTHER_ID);
        List<PublicMessageComment> publicMessages = new ArrayList<>();
        publicMessages.add(publicMessageCommentOne);
        publicMessages.add(publicMessageCommentTwo);
        return publicMessages;
    }

    public static List<PublicMessageCommentDto> getTestPublicMessageCommentsDto() {
        PublicMessageCommentDto publicMessageCommentDtoOne = getTestPublicMessageCommentDto();
        PublicMessageCommentDto publicMessageCommentDtoTwo = getTestPublicMessageCommentDto();
        publicMessageCommentDtoTwo.setId(PUBLIC_MESSAGE_COMMENT_OTHER_ID);
        List<PublicMessageCommentDto> publicMessagesDto = new ArrayList<>();
        publicMessagesDto.add(publicMessageCommentDtoOne);
        publicMessagesDto.add(publicMessageCommentDtoTwo);
        return publicMessagesDto;
    }

}
