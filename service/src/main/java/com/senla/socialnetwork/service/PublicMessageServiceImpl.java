package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.PublicMessageCommentMapper;
import com.senla.socialnetwork.service.util.PublicMessageMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PublicMessageServiceImpl implements PublicMessageService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    PublicMessageDao publicMessageDao;
    @Autowired
    PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;

    @Override
    @Transactional
    public List<PublicMessageDto> getMessages(final int firstResult, final int maxResults) {
        log.debug("[getSchools]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageDto addMessage(final PublicMessageDto publicMessageDto) {
        log.debug("[addMessage]");
        log.debug("[publicMessageDto: {}]", publicMessageDto);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.saveRecord(
            PublicMessageMapper.getPublicMessage(
                publicMessageDto, publicMessageDao, userProfileDao, locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateMessage(final PublicMessageDto publicMessageDto) {
        log.debug("[updateMessage]");
        log.debug("[publicMessageDto: {}]", publicMessageDto);
        publicMessageDao.updateRecord(PublicMessageMapper.getPublicMessage(
            publicMessageDto, publicMessageDao, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deleteMessageByUser(final String email, final Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[email: {}, messageId: {}]", email, messageId);
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(email, messageId);
        if (publicMessage == null) {
            throw new BusinessException("Error, there is no such message");
        } else if (publicMessage.isDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        List<PublicMessageComment> comments = publicMessageCommentDao.getPublicMessageComments(
            messageId, FIRST_RESULT, MAX_RESULTS);
        comments.forEach(publicMessageComment -> publicMessageComment.setDeleted(true));
        publicMessage.setPublicMessageComments(comments);
        publicMessage.setDeleted(true);
        publicMessageDao.updateRecord(publicMessage);
    }

    @Override
    @Transactional
    public void deleteMessage(final Long messageId) {
        log.debug("[deleteMessage]");
        log.debug("[messageId: {}]", messageId);
        if (publicMessageDao.findById(messageId) == null) {
            throw new BusinessException("Error, there is no such message");
        }
        publicMessageDao.deleteRecord(messageId);
    }

    @Override
    @Transactional
    public List<PublicMessageCommentDto> getPublicMessageComments(final Long publicMessageId,
                                                                  final int firstResult,
                                                                  final int maxResults) {
        log.debug("[getPublicMessageComments]");
        log.trace("[publicMessageId: {}]", publicMessageId);
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.getPublicMessageComments(publicMessageId, firstResult, maxResults));
    }

}
