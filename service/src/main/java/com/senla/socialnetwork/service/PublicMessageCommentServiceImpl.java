package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.PostCommentMapper;
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
    public List<PublicMessageCommentDto> Comments() {
        log.debug("[Comments]");
        return PublicMessageCommentMapper.getPublicMessageCommentDto(publicMessageCommentDao.getAllRecords());
    }

    @Override
    @Transactional
    public List<PublicMessageCommentDto> getPublicMessageComments(Long publicMessageId) {
        log.debug("[getPublicMessageComments]");
        log.trace("[publicMessageId: {}]", publicMessageId);
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.getPublicMessageComments(publicMessageId));
    }

    @Override
    @Transactional
    public PublicMessageCommentDto addComment(PublicMessageCommentDto publicMessageCommentDto) {
        log.debug("[addComment]");
        log.debug("[publicMessageCommentDto: {}]", publicMessageCommentDto);
        if (publicMessageCommentDto == null) {
            throw new BusinessException("Error, null comment");
        }
        return PublicMessageCommentMapper.getPublicMessageCommentDto(publicMessageCommentDao.saveRecord(
            PublicMessageCommentMapper.getPublicMessageComment(
                publicMessageCommentDto, publicMessageCommentDao, publicMessageDao,  userProfileDao,
                locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateComment(PublicMessageCommentDto publicMessageCommentDto) {
        log.debug("[updateComment]");
        log.debug("[publicMessageCommentDto: {}]", publicMessageCommentDto);
        if (publicMessageCommentDto == null) {
            throw new BusinessException("Error, null comment");
        }
        publicMessageCommentDao.updateRecord(PublicMessageCommentMapper.getPublicMessageComment(
            publicMessageCommentDto, publicMessageCommentDao, publicMessageDao,  userProfileDao,
            locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deleteCommentByUser(String email, Long commentId) {
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
    public void deleteComment(Long commentId) {
        log.debug("[deleteComment]");
        log.debug("[commentId: {}]", commentId);
        publicMessageCommentDao.deleteRecord(commentId);
    }
}
