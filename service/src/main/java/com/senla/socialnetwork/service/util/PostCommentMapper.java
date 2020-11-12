package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.dto.PostCommentDto;

import java.util.List;
import java.util.stream.Collectors;

public class PostCommentMapper {
    public static PostCommentDto getPostCommentDto(PostComment postComment) {
        PostCommentDto postCommentDto = new PostCommentDto();
        postCommentDto.setId(postComment.getId());
        postCommentDto.setCreationDate(postComment.getCreationDate());
        postCommentDto.setAuthor(UserProfileMapper.getUserProfileDto(postComment.getAuthor()));
        postCommentDto.setPost(PostMapper.getPostDto(postComment.getPost()));
        postCommentDto.setContent(postComment.getContent());
        postCommentDto.setDeleted(postComment.isDeleted());
        return postCommentDto;
    }

    public static List<PostCommentDto> getPostCommentDto(List<PostComment> postComments) {
        return postComments.stream()
                .map(PostCommentMapper::getPostCommentDto)
                .collect(Collectors.toList());
    }

    public static PostComment getPostComment(PostCommentDto postCommentDto,
                                             PostCommentDao postCommentDao,
                                             PostDao postDao,
                                             CommunityDao communityDao,
                                             UserProfileDao userProfileDao,
                                             LocationDao locationDao,
                                             SchoolDao schoolDao,
                                             UniversityDao universityDao) {
        PostComment postComment;
        if (postCommentDto.getId() == null) {
            postComment = new PostComment();
        } else {
            postComment = postCommentDao.findById(postCommentDto.getId());
        }
        postComment.setCreationDate(postCommentDto.getCreationDate());
        postComment.setAuthor(UserProfileMapper.getUserProfile(
                postCommentDto.getAuthor(), userProfileDao, locationDao, schoolDao, universityDao));
        postComment.setPost(PostMapper.getPost(
                postCommentDto.getPost(), postDao, communityDao, userProfileDao, locationDao, schoolDao, universityDao));
        postComment.setContent(postCommentDto.getContent());
        postComment.setDeleted(postCommentDto.isDeleted());
        return postComment;
    }

}
