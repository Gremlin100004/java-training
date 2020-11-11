package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.service.exception.BusinessException;
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
    @Autowired
    PublicMessageDao publicMessageDao;
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
    public List<PublicMessageDto> getFriendsMessages(String email, int firstResult, int maxResults) {
        log.debug("[getUserProfileMessages]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(
            publicMessageDao.getFriendsMessages(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<PublicMessageDto> getUserProfileMessages(String email, int firstResult, int maxResults) {
        log.debug("[getUserProfileMessages]");
        log.debug("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.getByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public PublicMessageDto addMessage(PublicMessageDto publicMessageDto) {
        log.debug("[addMessage]");
        log.debug("[publicMessageDto: {}]", publicMessageDto);
        if (publicMessageDto == null) {
            throw new BusinessException("Error, null message");
        }
        return PublicMessageMapper.getPublicMessageDto(publicMessageDao.saveRecord(
            PublicMessageMapper.getPublicMessage(
                publicMessageDto, publicMessageDao, userProfileDao, locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateMessage(PublicMessageDto publicMessageDto) {
        log.debug("[updateMessage]");
        log.debug("[publicMessageDto: {}]", publicMessageDto);
        if (publicMessageDto == null) {
            throw new BusinessException("Error, null message");
        }
        publicMessageDao.updateRecord(PublicMessageMapper.getPublicMessage(
            publicMessageDto, publicMessageDao, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    // ToDo check comments, need to delete it?
    public void deleteMessageByUser(String email, Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[email: {}, messageId: {}]", email, messageId);
        PublicMessage publicMessage = publicMessageDao.findByIdAndEmail(email, messageId);
        if (publicMessage == null) {
            throw new BusinessException("Error, there is no such message");
        } else if (publicMessage.isDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        publicMessage.setDeleted(true);
        publicMessageDao.updateRecord(publicMessage);
    }

    @Override
    @Transactional
    public void deleteMessage(Long messageId) {
        log.debug("[deleteMessage]");
        log.debug("[messageId: {}]", messageId);
        publicMessageDao.deleteRecord(messageId);
    }

}
