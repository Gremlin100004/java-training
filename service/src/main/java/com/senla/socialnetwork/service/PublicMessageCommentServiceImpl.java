package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PublicMessageCommentMapper;
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
public class PublicMessageCommentServiceImpl implements PublicMessageCommentService {
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    PublicMessageDao publicMessageDao;

    @Override
    @Transactional
    public List<PublicMessageCommentDto> getComments(final int firstResult, final int maxResults) {
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public void updateComment(final PublicMessageCommentDto publicMessageCommentDto) {
        log.debug("[publicMessageCommentDto: {}]", publicMessageCommentDto);
        UserProfile userProfile = userProfileDao.findByEmail(getUserName());
        PublicMessageComment publicMessageComment = PublicMessageCommentMapper.getPublicMessageComment(
            publicMessageCommentDto, publicMessageCommentDao, publicMessageDao,  userProfileDao);
        if (publicMessageComment.getAuthor() != userProfile) {
            throw new BusinessException("Error, this comment does not belong to this profile");
        }
        publicMessageCommentDao.updateRecord(publicMessageComment);
    }

    @Override
    @Transactional
    public void deleteCommentByUser(final Long commentId) {
        log.debug("[commentId: {}]", commentId);
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findByIdAndEmail(getUserName(), commentId);
        if (publicMessageComment == null) {
            throw new BusinessException("Error, there is no such comment");
        } else if (publicMessageComment.getIsDeleted()) {
            throw new BusinessException("Error, the comment has already been deleted");
        }
        publicMessageComment.setIsDeleted(true);
        publicMessageCommentDao.updateRecord(publicMessageComment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        log.debug("[commentId: {}]", commentId);
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findById(commentId);
        if (publicMessageComment == null) {
            throw new BusinessException("Error, there is no such comment");
        }
        publicMessageCommentDao.deleteRecord(publicMessageComment);
    }

    private String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }
}
