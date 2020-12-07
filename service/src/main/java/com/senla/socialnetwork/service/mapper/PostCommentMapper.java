package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PostCommentForCreateDto;
import com.senla.socialnetwork.service.exception.BusinessException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostCommentMapper {
    public static PostCommentDto getPostCommentDto(final PostComment postComment) {
        PostCommentDto postCommentDto = new PostCommentDto();
        postCommentDto.setId(postComment.getId());
        postCommentDto.setCreationDate(postComment.getCreationDate());
        postCommentDto.setAuthor(UserProfileMapper.getUserProfileForIdentificationDto(postComment.getAuthor()));
        postCommentDto.setPost(PostMapper.getPostDto(postComment.getPost()));
        postCommentDto.setContent(postComment.getContent());
        postCommentDto.setDeleted(postComment.getIsDeleted());
        return postCommentDto;
    }

    public static List<PostCommentDto> getPostCommentDto(final List<PostComment> postComments) {
        return postComments.stream()
                .map(PostCommentMapper::getPostCommentDto)
                .collect(Collectors.toList());
    }

    public static PostComment getPostComment(final PostCommentDto postCommentDto,
                                             final PostCommentDao postCommentDao,
                                             final String email) {
        PostComment postComment = postCommentDao.findByIdAndEmail(email, postCommentDto.getId());
        if (postComment == null) {
            throw new BusinessException("Error, this comment does not belong to this profile");
        }
        postComment.setContent(postCommentDto.getContent());
        return postComment;
    }

    public static PostComment getPostNewComment(final PostCommentForCreateDto postCommentDto,
                                                final Post post,
                                                final UserProfile userProfile) {
        PostComment postComment = new PostComment();
        postComment.setPost(post);
        postComment.setContent(postCommentDto.getContent());
        postComment.setAuthor(userProfile);
        postComment.setCreationDate(new Date());
        postComment.setIsDeleted(false);
        return postComment;
    }

}
