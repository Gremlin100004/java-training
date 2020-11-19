package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PublicMessageCommentDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.PublicMessageComment;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PrivateMessageTestData;
import com.senla.socialnetwork.service.config.PublicMessageCommentTestData;
import com.senla.socialnetwork.service.config.PublicMessageTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UniversityTestData;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class PublicMessageServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    PublicMessageService publicMessageService;
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

    @Test
    void PublicMessageServiceImpl_getMessages() {
        List<PublicMessage> publicMessages = PublicMessageTestData.getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getTestPublicMessagesDto();
        Mockito.doReturn(publicMessages).when(publicMessageDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultPublicMessagesDto = publicMessageService.getMessages(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPublicMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPublicMessagesDto.size());
        Assertions.assertFalse(resultPublicMessagesDto.isEmpty());
        Assertions.assertEquals(resultPublicMessagesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_addMessage() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        PublicMessageDto publicMessageDto = PublicMessageTestData.getTestPublicMessageDto();
        publicMessageDto.setId(null);
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(publicMessage).when(publicMessageDao).saveRecord(ArgumentMatchers.any(PublicMessage.class));
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        PublicMessageDto resultPublicMessageDto = publicMessageService.addMessage(publicMessageDto);
        Assertions.assertNotNull(resultPublicMessageDto);
        Mockito.verify(publicMessageDao, Mockito.never()).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(3)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(UniversityTestData.getUniversityId());
        Mockito.verify(publicMessageDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PublicMessageServiceImpl_updateMessage() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        PublicMessageDto locationDto = PublicMessageTestData.getTestPublicMessageDto();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(publicMessage).when(publicMessageDao).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> publicMessageService.updateMessage(locationDto));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(3)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(UniversityTestData.getUniversityId());
        Mockito.verify(publicMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessageByUser() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        Mockito.doReturn(publicMessage).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertDoesNotThrow(() -> publicMessageService.deleteMessageByUser(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessageByUser_publicMessageDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.deleteMessageByUser(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessageByUser_publicMessageDao_findByIdAndEmail_deletedObject() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        publicMessage.setDeleted(true);
        Mockito.doReturn(null).when(publicMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.deleteMessageByUser(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessage.class));
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessage() {
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        Mockito.doReturn(publicMessage).when(publicMessageDao).findById(PublicMessageTestData.getPublicMessageId());

        Assertions.assertDoesNotThrow(() -> publicMessageService.deleteMessage(PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).deleteRecord(PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.times(1)).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_deleteMessage_publicMessageDao_findById_nullObject() {
        Mockito.doReturn(null).when(publicMessageDao).findById(PublicMessageTestData.getPublicMessageId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageService.deleteMessage(PublicMessageTestData.getPublicMessageId()));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.verify(publicMessageDao, Mockito.never()).deleteRecord(PublicMessageTestData.getPublicMessageId());
        Mockito.reset(publicMessageDao);
    }

    @Test
    void PublicMessageServiceImpl_getPublicMessageComments() {
        List<PublicMessageComment> publicMessageComments = PublicMessageCommentTestData.getTestPublicMessageComments();
        List<PublicMessageCommentDto> publicMessageCommentsDto = PublicMessageCommentTestData.getTestPublicMessageCommentsDto();
        Mockito.doReturn(publicMessageComments).when(publicMessageCommentDao).getPublicMessageComments(
            PublicMessageTestData.getPublicMessageId(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageCommentDto> resultPublicMessageCommentsDto = publicMessageService.getPublicMessageComments(
            PublicMessageTestData.getPublicMessageId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPublicMessageCommentsDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPublicMessageCommentsDto.size());
        Assertions.assertFalse(resultPublicMessageCommentsDto.isEmpty());
        Assertions.assertEquals(resultPublicMessageCommentsDto, publicMessageCommentsDto);
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).getPublicMessageComments(
            PublicMessageTestData.getPublicMessageId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageCommentDao);
    }

}
