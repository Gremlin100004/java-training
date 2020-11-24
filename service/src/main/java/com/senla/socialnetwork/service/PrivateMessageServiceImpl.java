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
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.util.PrivateMessageMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;
    @Value("${com.senla.socialnetwork.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;

    @Override
    @Transactional
    public List<PrivateMessageDto> getPrivateMessages(final int firstResult, final int maxResults) {
        log.debug("[getPrivateMessages]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PrivateMessageDto> getMessageFilteredByPeriod(final HttpServletRequest request,
                                                              final Date startPeriodDate,
                                                              final Date endPeriodDate,
                                                              final int firstResult,
                                                              final int maxResults) {
        log.debug("[getMessageFilteredByPeriod]");
        log.debug("[request: {}, startPeriodDate: {}, endPeriodDate: {}, firstResult: {}, maxResults: {}]",
                  request, startPeriodDate, endPeriodDate, firstResult, maxResults);
        return PrivateMessageMapper.getPrivateMessageDto(
            privateMessageDao.getMessageFilteredByPeriod(JwtUtil.extractUsername(JwtUtil.getToken(
                request), secretKey), startPeriodDate, endPeriodDate, firstResult, maxResults));
    }

    @Override
    @Transactional
    public PrivateMessageDto addMessage(final HttpServletRequest request, final PrivateMessageDto privateMessageDto) {
        log.debug("[addMessage]");
        log.debug("[request: {}, privateMessageDto: {}]", request, privateMessageDto);
        PrivateMessage privateMessage = PrivateMessageMapper.getPrivateMessage(
            privateMessageDto, privateMessageDao, userProfileDao, locationDao, schoolDao, universityDao);
        privateMessage.setSender(userProfileDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey)));
        return PrivateMessageMapper.getPrivateMessageDto(privateMessageDao.saveRecord(privateMessage));
    }

    @Override
    @Transactional
    public void updateMessage(final HttpServletRequest request, final PrivateMessageDto privateMessageDto) {
        log.debug("[updateMessage]");
        log.debug("[request: {}, privateMessage: {}]", request, privateMessageDto);
        UserProfile userProfile = userProfileDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey));
        PrivateMessage privateMessage = PrivateMessageMapper.getPrivateMessage(
            privateMessageDto, privateMessageDao, userProfileDao, locationDao, schoolDao, universityDao);
        if (privateMessage.getSender() != userProfile) {
            throw new BusinessException("Error, this message does not belong to this profile");
        }
        privateMessageDao.updateRecord(privateMessage);
    }

    @Override
    @Transactional
    public void deleteMessageByUser(final HttpServletRequest request, final Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[request: {}, messageId: {}]", request, messageId);
        PrivateMessage privateMessage = privateMessageDao.findByIdAndEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey), messageId);
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
    public void deleteMessage(final Long messageId) {
        log.debug("[deleteMessage]");
        log.debug("[messageId: {}]", messageId);
        if (privateMessageDao.findById(messageId) == null) {
            throw new BusinessException("Error, there is no such message");
        }
        privateMessageDao.deleteRecord(messageId);
    }

}
