package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageCommentForCreateDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.PublicMessageForCreateDto;
import com.senla.socialnetwork.model.PublicMessage;
import com.senla.socialnetwork.model.PublicMessageComment;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PublicMessageCommentMapper;
import com.senla.socialnetwork.service.mapper.PublicMessageMapper;
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@ServiceLog
@NoArgsConstructor
public class PublicMessageServiceImpl implements PublicMessageService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    private PublicMessageDao publicMessageDao;
    @Autowired
    private PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    private UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<PublicMessageDto> getMessages(final int firstResult, final int maxResults) {
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PublicMessageDto> getFriendsPublicMessages(final int firstResult, final int maxResults) {
        return PublicMessageMapper.getPublicMessageDto(
            publicMessageDao.getFriendsMessages(PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PublicMessageDto> getPublicMessages(final int firstResult, final int maxResults) {
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.findByEmail(
            PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageDto addMessage(final PublicMessageForCreateDto publicMessageDto) {
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.save(
            PublicMessageMapper.getNewPublicMessage(
                publicMessageDto, userProfileDao.findByEmail(PrincipalUtil.getUserName()))));
    }

    @Override
    @Transactional
    public void updateMessage(final PublicMessageDto publicMessageDto) {
        publicMessageDao.updateRecord(PublicMessageMapper.getPublicMessage(
            publicMessageDto, publicMessageDao, PrincipalUtil.getUserName()));
    }

    @Override
    @Transactional
    public void deleteMessageByUser(final Long messageId) {
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(PrincipalUtil.getUserName(), messageId);
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
        return PublicMessageCommentMapper.getPublicMessageCommentDto(
            publicMessageCommentDao.getPublicMessageComments(publicMessageId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageCommentDto addComment(final Long publicMessageId,
                                              final PublicMessageCommentForCreateDto publicMessageCommentDto) {
        String email = PrincipalUtil.getUserName();
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(email, publicMessageId);
        if (publicMessage == null) {
            throw new BusinessException("Error, there is no such public message");
        } else if (publicMessage.getIsDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        return PublicMessageCommentMapper.getPublicMessageCommentDto(publicMessageCommentDao.save(
            PublicMessageCommentMapper.getNewPublicMessageComment(
                publicMessageCommentDto, publicMessage, userProfileDao.findByEmail(email))));
    }

}
