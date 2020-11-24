package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.JwtUtil;
import com.senla.socialnetwork.service.util.PublicMessageCommentMapper;
import com.senla.socialnetwork.service.util.PublicMessageMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
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
    @Value("${com.senla.socialnetwork.JwtUtil.secret-key:qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq}")
    private String secretKey;

    @Override
    @Transactional
    public List<PublicMessageDto> getMessages(final int firstResult, final int maxResults) {
        log.debug("[getSchools]");
        log.debug("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageDto addMessage(final HttpServletRequest request, final PublicMessageDto publicMessageDto) {
        log.debug("[addMessage]");
        log.debug("[request: {}, publicMessageDto: {}]", request, publicMessageDto);
        PublicMessage publicMessage = PublicMessageMapper.getPublicMessage(
            publicMessageDto, publicMessageDao, userProfileDao, locationDao, schoolDao, universityDao);
        publicMessage.setAuthor(userProfileDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey)));
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.saveRecord(publicMessage));
    }

    @Override
    @Transactional
    public void updateMessage(final HttpServletRequest request, final PublicMessageDto publicMessageDto) {
        log.debug("[updateMessage]");
        log.debug("[request: {}, publicMessageDto: {}]", request, publicMessageDto);
        UserProfile userProfile = userProfileDao.findByEmail(JwtUtil.extractUsername(
            JwtUtil.getToken(request), secretKey));
        PublicMessage publicMessage = PublicMessageMapper.getPublicMessage(
            publicMessageDto, publicMessageDao, userProfileDao, locationDao, schoolDao, universityDao);
        if (publicMessage.getAuthor() != userProfile) {
            throw new BusinessException("Error, this message does not belong to this profile");
        }
        publicMessageDao.updateRecord(publicMessage);
    }

    @Override
    @Transactional
    public void deleteMessageByUser(final Long messageId, final HttpServletRequest request) {
        log.debug("[deleteMessageByUser]");
        log.debug("[request: {}, messageId: {}]", request, messageId);
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(
            JwtUtil.extractUsername(JwtUtil.getToken(request), secretKey), messageId);
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
