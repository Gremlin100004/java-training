package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.PublicMessageCommentMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;

    @Override
    @Transactional
    public List<PublicMessageCommentDto> getComments(final int firstResult, final int maxResults) {
        log.debug("[Comments]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageCommentDto addComment(final PublicMessageCommentDto publicMessageCommentDto) {
        log.debug("[addComment]");
        log.debug("[publicMessageCommentDto: {}]", publicMessageCommentDto);
        return PublicMessageCommentMapper.getPublicMessageCommentDto(publicMessageCommentDao.saveRecord(
            PublicMessageCommentMapper.getPublicMessageComment(
                publicMessageCommentDto, publicMessageCommentDao, publicMessageDao,  userProfileDao,
                locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateComment(final PublicMessageCommentDto publicMessageCommentDto) {
        log.debug("[updateComment]");
        log.debug("[publicMessageCommentDto: {}]", publicMessageCommentDto);
        publicMessageCommentDao.updateRecord(PublicMessageCommentMapper.getPublicMessageComment(
            publicMessageCommentDto, publicMessageCommentDao, publicMessageDao,  userProfileDao,
            locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deleteCommentByUser(final String email, final Long commentId) {
        log.debug("[deleteCommentByUser]");
        log.debug("[email: {}, commentId: {}]", email, commentId);
        PublicMessageComment publicMessageComment = publicMessageCommentDao.findByIdAndEmail(email, commentId);
        if (publicMessageComment == null) {
            throw new BusinessException("Error, there is no such comment");
        } else if (publicMessageComment.isDeleted()) {
            throw new BusinessException("Error, the comment has already been deleted");
        }
        publicMessageComment.setDeleted(true);
        publicMessageCommentDao.updateRecord(publicMessageComment);
    }

    @Override
    @Transactional
    public void deleteComment(final Long commentId) {
        log.debug("[deleteComment]");
        log.debug("[commentId: {}]", commentId);
        if (publicMessageCommentDao.findById(commentId) == null) {
            throw new BusinessException("Error, there is no such comment");
        }
        publicMessageCommentDao.deleteRecord(commentId);
    }
}
