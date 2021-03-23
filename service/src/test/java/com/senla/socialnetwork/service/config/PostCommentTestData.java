package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostCommentForCreateDto;
import com.senla.socialnetwork.model.PostComment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostCommentTestData {
    private static final Long POST_COMMENT_ID = 1L;
    private static final Long POST_COMMENT_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_POST_COMMENTS = 2L;
    private static final Date POST_COMMENT_CREATION_DATE = new Date();

    public static Long getPostCommentId() {
        return POST_COMMENT_ID;
    }

    public static Long getRightNumberPostComments() {
        return RIGHT_NUMBER_POST_COMMENTS;
    }

    public static PostComment getTestPostComment() {
        PostComment postComment = new PostComment();
        postComment.setId(POST_COMMENT_ID);
        postComment.setCreationDate(POST_COMMENT_CREATION_DATE);
        postComment.setAuthor(UserProfileTestData.getTestUserProfile());
        postComment.setPost(PostTestData.getTestPost());
        postComment.setIsDeleted(false);
        return postComment;
    }

    public static PostCommentDto getTestPostCommentDto() {
        PostCommentDto postCommentDto = new PostCommentDto();
        postCommentDto.setId(POST_COMMENT_ID);
        postCommentDto.setCreationDate(POST_COMMENT_CREATION_DATE);
        postCommentDto.setAuthor(UserProfileTestData.getTestUserProfileForIdentificationDto());
        postCommentDto.setPost(PostTestData.getTestPostDto());
        return postCommentDto;
    }

    public static PostCommentForCreateDto getTestPostCommentForCreationDto() {
        return new PostCommentForCreateDto();
    }

    public static List<PostComment> getTestPostComments() {
        PostComment postCommentOne = getTestPostComment();
        PostComment postCommentTwo = getTestPostComment();
        postCommentTwo.setId(POST_COMMENT_OTHER_ID);
        List<PostComment> postComments = new ArrayList<>();
        postComments.add(postCommentOne);
        postComments.add(postCommentTwo);
        return postComments;
    }

    public static List<PostCommentDto> getTestPostCommentsDto() {
        PostCommentDto postCommentDtoOne = getTestPostCommentDto();
        PostCommentDto postCommentDtoTwo = getTestPostCommentDto();
        postCommentDtoTwo.setId(POST_COMMENT_OTHER_ID);
        List<PostCommentDto> postCommentsDto = new ArrayList<>();
        postCommentsDto.add(postCommentDtoOne);
        postCommentsDto.add(postCommentDtoTwo);
        return postCommentsDto;
    }

}
