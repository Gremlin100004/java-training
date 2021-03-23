package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.model.PublicMessageComment;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PublicMessageCommentMapper;
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ServiceLog
@NoArgsConstructor
public class PublicMessageCommentServiceImpl implements PublicMessageCommentService {
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private PublicMessageCommentDao publicMessageCommentDao;

    @Override
    @Transactional
    public List<PublicMessageCommentDto> getComments(final int firstResult, final int maxResults) {
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public void updateComment(final PublicMessageCommentDto publicMessageCommentDto) {
        publicMessageCommentDao.updateRecord(PublicMessageCommentMapper.getPublicMessageComment(
            publicMessageCommentDto, publicMessageCommentDao, PrincipalUtil.getUserName()));
    }

    @Override
    @Transactional
    public void deleteCommentByUser(final Long commentId) {
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findByIdAndEmail(
            PrincipalUtil.getUserName(), commentId);
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
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findById(commentId);
        if (publicMessageComment == null) {
            throw new BusinessException("Error, there is no such comment");
        }
        publicMessageCommentDao.deleteRecord(publicMessageComment);
    }

}
