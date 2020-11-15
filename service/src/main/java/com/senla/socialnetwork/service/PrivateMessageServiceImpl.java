package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PrivateMessage;
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
    PrivateMessageDao privateMessageDao;
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
    public List<PrivateMessageDto> getPrivateMessages(int firstResult, int maxResults) {
        log.debug("[getPrivateMessages]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.getAllRecords(firstResult, maxResults));
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
    public void deleteMessageByUser(String email, Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[email: {}, messageId: {}]", email, messageId);
        PrivateMessage privateMessage = privateMessageDao.findByIdAndEmail(email, messageId);
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
        if (privateMessageDao.findById(messageId) == null) {
            throw new BusinessException("Error, there is no such message");
        }
        privateMessageDao.deleteRecord(messageId);
    }

}
