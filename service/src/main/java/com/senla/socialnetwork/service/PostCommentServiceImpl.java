package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PostCommentMapper;
import com.senla.socialnetwork.service.security.UserPrincipal;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PostCommentServiceImpl implements PostCommentService {
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    PostCommentDao postCommentDao;
    @Autowired
    UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<PostCommentDto> getComments(final int firstResult, final int maxResults) {
        log.debug("[getComments]");
        return PostCommentMapper.getPostCommentDto(postCommentDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public void updateComment(final PostCommentDto postCommentDto) {
        log.debug("[updateComment]");
        log.debug("[postCommentDto: {}]", postCommentDto);
        UserProfile userProfile = userProfileDao.findByEmail(getUserName());
        PostComment postComment = PostCommentMapper.getPostComment(
            postCommentDto, postCommentDao, postDao, communityDao, userProfileDao);
        if (postComment.getAuthor() != userProfile) {
            throw new BusinessException("Error, this comment does not belong to this profile");
        }
        postCommentDao.updateRecord(postComment);
    }

    @Override
    @Transactional
    public void deleteCommentByUser(final Long commentId) {
        log.debug("[deleteCommentByUser]");
        log.debug("[commentId: {}]", commentId);
        PostComment postComment = postCommentDao.findByIdAndEmail(getUserName(), commentId);
        if (postComment == null) {
            throw new BusinessException("Error, there is no such comment");
        } else if (postComment.getIsDeleted()) {
            throw new BusinessException("Error, the comment has already been deleted");
        }
        postComment.setIsDeleted(true);
        postCommentDao.updateRecord(postComment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        log.debug("[deleteComment]");
        log.debug("[commentId: {}]", commentId);
        PostComment postComment = postCommentDao.findById(commentId);
        if (postComment == null) {
            throw new BusinessException("Error, there is no such comment");
        }
        postCommentDao.deleteRecord(postComment);
    }

    private String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

}
