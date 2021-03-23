package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;
import com.senla.socialnetwork.model.PrivateMessage;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.PrivateMessageMapper;
import com.senla.socialnetwork.service.util.PrincipalUtil;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@ServiceLog
@NoArgsConstructor
public class PrivateMessageServiceImpl implements PrivateMessageService {
    @Autowired
    private PrivateMessageDao privateMessageDao;
    @Autowired
    private UserProfileDao userProfileDao;

    @Override
    @Transactional
    public List<PrivateMessageDto> getPrivateMessages(final int firstResult, final int maxResults) {
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getPrivateMessagesByUser(final int firstResult, final int maxResults) {
        String email = PrincipalUtil.getUserName();
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        if (ownProfile == null) {
            throw new BusinessException("Error, user with this email does not exist");
        }
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.findByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getUnreadMessages(final int firstResult, final int maxResults) {
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getUnreadMessages(PrincipalUtil.getUserName(), firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getMessageFilteredByPeriod(final Date startPeriodDate,
                                                              final Date endPeriodDate,
                                                              final int firstResult,
                                                              final int maxResults) {
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getMessageFilteredByPeriod(
                PrincipalUtil.getUserName(), startPeriodDate, endPeriodDate, firstResult, maxResults));
    }

    @Override
    @Transactional
    public PrivateMessageDto addMessage(final PrivateMessageForCreateDto privateMessageDto) {
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.save(
            PrivateMessageMapper.getNewPrivateMessage(privateMessageDto, userProfileDao.findByEmail(
                PrincipalUtil.getUserName()), userProfileDao)));
    }

    @Override
    @Transactional
    public void updateMessage(final PrivateMessageDto privateMessageDto) {
        privateMessageDao.updateRecord(PrivateMessageMapper.getPrivateMessage(
            privateMessageDto, privateMessageDao, PrincipalUtil.getUserName()));
    }

    @Override
    @Transactional
    public void deleteMessageByUser(final Long messageId) {
        PrivateMessage privateMessage = privateMessageDao.findByIdAndEmail(PrincipalUtil.getUserName(), messageId);
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
        PrivateMessage privateMessage = privateMessageDao.findById(messageId);
        if (privateMessage == null) {
            throw new BusinessException("Error, there is no such message");
        }
        privateMessageDao.deleteRecord(privateMessage);
    }

}
