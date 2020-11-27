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
import com.senla.socialnetwork.dto.PostCommentForCreateDto;

import java.util.List;
import java.util.stream.Collectors;

public class PostCommentMapper {
    public static PostCommentDto getPostCommentDto(final PostComment postComment) {
        PostCommentDto postCommentDto = new PostCommentDto();
        postCommentDto.setId(postComment.getId());
        postCommentDto.setCreationDate(postComment.getCreationDate());
        postCommentDto.setAuthor(UserProfileMapper.getUserProfileDto(postComment.getAuthor()));
        postCommentDto.setPost(PostMapper.getPostDto(postComment.getPost()));
        postCommentDto.setContent(postComment.getContent());
        postCommentDto.setDeleted(postComment.isDeleted());
        return postCommentDto;
    }

    public static List<PostCommentDto> getPostCommentDto(final List<PostComment> postComments) {
        return postComments.stream()
                .map(PostCommentMapper::getPostCommentDto)
                .collect(Collectors.toList());
    }

    public static PostComment getPostComment(final PostCommentDto postCommentDto,
                                             final PostCommentDao postCommentDao,
                                             final PostDao postDao,
                                             final CommunityDao communityDao,
                                             final UserProfileDao userProfileDao,
                                             final LocationDao locationDao,
                                             final SchoolDao schoolDao,
                                             final UniversityDao universityDao) {
        PostComment postComment = postCommentDao.findById(postCommentDto.getId());
        postComment.setCreationDate(postCommentDto.getCreationDate());
        postComment.setAuthor(UserProfileMapper.getUserProfile(
                postCommentDto.getAuthor(), userProfileDao, locationDao, schoolDao, universityDao));
        postComment.setPost(PostMapper.getPost(
                postCommentDto.getPost(), postDao, communityDao, userProfileDao, locationDao, schoolDao, universityDao));
        postComment.setContent(postCommentDto.getContent());
        postComment.setDeleted(postCommentDto.isDeleted());
        return postComment;
    }

    public static PostComment getPostNewComment(final PostCommentForCreateDto postCommentDto,
                                             final PostDao postDao,
                                             final CommunityDao communityDao,
                                             final UserProfileDao userProfileDao,
                                             final LocationDao locationDao,
                                             final SchoolDao schoolDao,
                                             final UniversityDao universityDao) {
        PostComment postComment = new PostComment();
        postComment.setAuthor(UserProfileMapper.getUserProfile(
            postCommentDto.getAuthor(), userProfileDao, locationDao, schoolDao, universityDao));
        postComment.setPost(PostMapper.getPost(
            postCommentDto.getPost(), postDao, communityDao, userProfileDao, locationDao, schoolDao, universityDao));
        postComment.setContent(postCommentDto.getContent());
        return postComment;
    }

}
