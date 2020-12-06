package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PublicMessageCommentMapper;
import com.senla.socialnetwork.service.mapper.PublicMessageMapper;
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
public class PublicMessageServiceImpl implements PublicMessageService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    PublicMessageDao publicMessageDao;
    @Autowired
    PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<PublicMessageDto> getMessages(final int firstResult, final int maxResults) {
        log.debug("[getSchools]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PublicMessageDto> getFriendsPublicMessages(final int firstResult, final int maxResults) {
        log.debug("[getFriendsPublicMessages]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(
            publicMessageDao.getFriendsMessages(getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PublicMessageDto> getPublicMessages(final int firstResult, final int maxResults) {
        log.debug("[getPublicMessages]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.getByEmail(
            getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageDto addMessage(final PublicMessageForCreateDto publicMessageDto) {
        log.debug("[addMessage]");
        log.debug("[publicMessageDto: {}]", publicMessageDto);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.saveRecord(
            PublicMessageMapper.getNewPublicMessage(
                publicMessageDto, userProfileDao.findByEmail(getUserName()))));
    }

    @Override
    @Transactional
    public void updateMessage(final PublicMessageDto publicMessageDto) {
        log.debug("[updateMessage]");
        log.debug("[publicMessageDto: {}]", publicMessageDto);
        UserProfile userProfile = userProfileDao.findByEmail(getUserName());
        PublicMessage publicMessage = PublicMessageMapper.getPublicMessage(
            publicMessageDto, publicMessageDao, userProfileDao);
        if (publicMessage.getAuthor() != userProfile) {
            throw new BusinessException("Error, this message does not belong to this profile");
        }
        publicMessageDao.updateRecord(publicMessage);
    }

    @Override
    @Transactional
    public void deleteMessageByUser(final Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[messageId: {}]", messageId);
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(getUserName(), messageId);
        if (publicMessage == null) {
            throw new BusinessException("Error, there is no such message");
        } else if (publicMessage.getIsDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        List<PublicMessageComment> comments = publicMessageCommentDao.getPublicMessageComments(
            messageId, FIRST_RESULT, MAX_RESULTS);
        comments.forEach(publicMessageComment -> publicMessageComment.setIsDeleted(true));
        publicMessage.setPublicMessageComments(comments);
        publicMessage.setIsDeleted(true);
        publicMessageDao.updateRecord(publicMessage);
    }

    @Override
    @Transactional
    public void deleteMessage(final Long messageId) {
        log.debug("[deleteMessage]");
        log.debug("[messageId: {}]", messageId);
        PublicMessage publicMessage = publicMessageDao.findById(messageId);
        if (publicMessage == null) {
            throw new BusinessException("Error, there is no such message");
        }
        publicMessageDao.deleteRecord(publicMessage);
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

    @Override
    @Transactional
    public PublicMessageCommentDto addComment(final Long publicMessageId,
                                              final PublicMessageCommentForCreateDto publicMessageCommentDto) {
        log.debug("[addComment]");
        log.debug("[publicMessageCommentDto: {}]", publicMessageCommentDto);
        String email = getUserName();
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(email, publicMessageId);
        if (publicMessage == null) {
            throw new BusinessException("Error, there is no such public message");
        } else if (publicMessage.getIsDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        return PublicMessageCommentMapper.getPublicMessageCommentDto(publicMessageCommentDao.saveRecord(
            PublicMessageCommentMapper.getNewPublicMessageComment(
                publicMessageCommentDto, publicMessage, userProfileDao.findByEmail(email))));
    }

    private String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

}
