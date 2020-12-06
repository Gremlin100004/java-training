package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PrivateMessageMapper;
import com.senla.socialnetwork.service.security.UserPrincipal;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PrivateMessageServiceImpl implements PrivateMessageService {
    @Autowired
    PrivateMessageDao privateMessageDao;
    @Autowired
    UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<PrivateMessageDto> getPrivateMessages(final int firstResult, final int maxResults) {
        log.debug("[getPrivateMessages]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getPrivateMessagesByUser(final int firstResult, final int maxResults) {
        log.debug("[getPrivateMessages]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        String email = getUserName();
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        if (ownProfile == null) {
            throw new BusinessException("Error, user with this email does not exist");
        }
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getUnreadMessages(final int firstResult, final int maxResults) {
        log.debug("[getUnreadMessages]");
        log.debug("[ firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getUnreadMessages(getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getMessageFilteredByPeriod(final Date startPeriodDate,
                                                              final Date endPeriodDate,
                                                              final int firstResult,
                                                              final int maxResults) {
        log.debug("[getMessageFilteredByPeriod]");
        log.debug("[startPeriodDate: {}, endPeriodDate: {}, firstResult: {}, maxResults: {}]",
                  startPeriodDate, endPeriodDate, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getMessageFilteredByPeriod(
                getUserName(), startPeriodDate, endPeriodDate, firstResult, maxResults));
    }

    @Override
    @Transactional
    public PrivateMessageDto addMessage(final PrivateMessageForCreateDto privateMessageDto) {
        log.debug("[addMessage]");
        log.debug("[privateMessageDto: {}]", privateMessageDto);
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.saveRecord(
            PrivateMessageMapper.getNewPrivateMessage(privateMessageDto, userProfileDao.findByEmail(getUserName()))));
    }

    @Override
    @Transactional
    public void updateMessage(final PrivateMessageDto privateMessageDto) {
        log.debug("[updateMessage]");
        log.debug("[privateMessage: {}]", privateMessageDto);
        UserProfile userProfile = userProfileDao.findByEmail(getUserName());
        PrivateMessage privateMessage = PrivateMessageMapper.getPrivateMessage(
            privateMessageDto, privateMessageDao, userProfileDao);
        if (privateMessage.getSender() != userProfile) {
            throw new BusinessException("Error, this message does not belong to this profile");
        }
        privateMessageDao.updateRecord(privateMessage);
    }

    @Override
    @Transactional
    public void deleteMessageByUser(final Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[messageId: {}]", messageId);
        PrivateMessage privateMessage = privateMessageDao.findByIdAndEmail(getUserName(), messageId);
        if (privateMessage == null) {
            throw new BusinessException("Error, there is no such message");
        } else if (privateMessage.getIsDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        privateMessage.setIsDeleted(true);
        privateMessageDao.updateRecord(privateMessage);
    }

    @Override
    @Transactional
    public void deleteMessage(final Long messageId) {
        log.debug("[deleteMessage]");
        log.debug("[messageId: {}]", messageId);
        PrivateMessage privateMessage = privateMessageDao.findById(messageId);
        if (privateMessage == null) {
            throw new BusinessException("Error, there is no such message");
        }
        privateMessageDao.deleteRecord(privateMessage);
    }

    private String getUserName() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        return userPrincipal.getUsername();
    }

}
