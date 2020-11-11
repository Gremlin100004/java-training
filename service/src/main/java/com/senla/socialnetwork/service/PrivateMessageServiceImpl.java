package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.PrivateMessageMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PrivateMessageServiceImpl implements PrivateMessageService {
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    PrivateMessageDao privateMessageDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;

    @Override
    @Transactional
    public List<PrivateMessageDto> getUserProfileMessages(String email, int firstResult, int maxResults) {
        log.debug("[getUserProfileMessages]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        UserProfile ownProfile = userProfileDao.findByEmail(email);
        if (ownProfile == null) {
            throw new BusinessException("Error, user with this email does not exist");
        }
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getDialogue(String email, Long userProfileId, int firstResult, int maxResults) {
        log.debug("[getDialogue]");
        log.debug("[email: {}, userProfileId: {}, firstResult: {}, maxResults: {}]",
                  email, userProfileId, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getDialogue(email, userProfileId, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getUnreadMessages(String email, int firstResult, int maxResults) {
        log.debug("[getUnreadMessages]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getUnreadMessages(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getMessageFilteredByPeriod(String email,
                                                              Date startPeriodDate,
                                                              Date endPeriodDate,
                                                              int firstResult,
                                                              int maxResults) {
        log.debug("[getMessageFilteredByPeriod]");
        log.debug("[email: {}, startPeriodDate: {}, endPeriodDate: {}, firstResult: {}, maxResults: {}]",
                  email, startPeriodDate, endPeriodDate, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getMessageFilteredByPeriod(
                email, startPeriodDate, endPeriodDate, firstResult, maxResults));
    }

    @Override
    @Transactional
    public PrivateMessageDto addMessage(PrivateMessageDto privateMessageDto) {
        log.debug("[addMessage]");
        log.debug("[privateMessageDto: {}]", privateMessageDto);
        if (privateMessageDto == null) {
            throw new BusinessException("Error, null message");
        }
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.saveRecord(
            PrivateMessageMapper.getPrivateMessage(
                privateMessageDto, privateMessageDao, userProfileDao, locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateMessage(PrivateMessageDto privateMessageDto) {
        log.debug("[deleteMessageByUser]");
        log.debug("[privateMessage: {}]", privateMessageDto);
        if (privateMessageDto == null) {
            throw new BusinessException("Error, null message");
        }
        privateMessageDao.updateRecord(PrivateMessageMapper.getPrivateMessage(
            privateMessageDto, privateMessageDao, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deleteMessageByUser(Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[messageId: {}]", messageId);
        PrivateMessage privateMessage = privateMessageDao.findById(messageId);
        if (privateMessage == null) {
            throw new BusinessException("Error, there is no such message");
        } else if (privateMessage.isDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        privateMessage.setDeleted(true);
        privateMessageDao.updateRecord(privateMessage);
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[messageId: {}]", messageId);
        privateMessageDao.deleteRecord(messageId);
    }

}
