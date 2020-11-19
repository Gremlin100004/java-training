package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PrivateMessageTestData;
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

import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class PrivateMessageServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final Date START_PERIOD_DATE = new Date();
    private static final Date END_PERIOD_DATE = new Date();
    @Autowired
    PrivateMessageService privateMessageService;
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

    @Test
    void PrivateMessageServiceImpl_getPrivateMessages() {
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessages).when(privateMessageDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = privateMessageService.getPrivateMessages(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_getMessageFilteredByPeriod() {
        List<PrivateMessage> locations = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> locationsDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(locations).when(privateMessageDao).getMessageFilteredByPeriod(
            UserTestData.getEmail(), START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = privateMessageService.getMessageFilteredByPeriod(
            UserTestData.getEmail(), START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, locationsDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getMessageFilteredByPeriod(
            UserTestData.getEmail(), START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_addMessage() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        PrivateMessageDto privateMessageDto = PrivateMessageTestData.getTestPrivateMessageDto();
        privateMessageDto.setId(null);
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(privateMessage).when(privateMessageDao).saveRecord(ArgumentMatchers.any(PrivateMessage.class));
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        PrivateMessageDto resultPrivateMessageDto = privateMessageService.addMessage(privateMessageDto);
        Assertions.assertNotNull(resultPrivateMessageDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.verify(privateMessageDao, Mockito.never()).findById(PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(6)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(2)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(2)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(privateMessageDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PrivateMessageServiceImpl_updateMessage() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        PrivateMessageDto privateMessageDto = PrivateMessageTestData.getTestPrivateMessageDto();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(privateMessage).when(privateMessageDao).findById(PrivateMessageTestData.getPrivateMessageId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> privateMessageService.updateMessage(privateMessageDto));
        Mockito.verify(privateMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findById(PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(6)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(2)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(2)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(privateMessageDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessageByUser() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        Mockito.doReturn(privateMessage).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertDoesNotThrow(() -> privateMessageService.deleteMessageByUser(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(privateMessageDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessageByUser_privateMessageDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.deleteMessageByUser(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(privateMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessageByUser_privateMessageDao_findByIdAndEmail_deletedObject() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        privateMessage.setDeleted(true);
        Mockito.doReturn(privateMessage).when(privateMessageDao).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.deleteMessageByUser(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(privateMessageDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PrivateMessage.class));
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessage() {
        PrivateMessage privateMessage = PrivateMessageTestData.getTestPrivateMessage();
        Mockito.doReturn(privateMessage).when(privateMessageDao).findById(PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertDoesNotThrow(() -> privateMessageService.deleteMessage(PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).deleteRecord(LocationTestData.getLocationId());
        Mockito.verify(privateMessageDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.reset(privateMessageDao);
    }

    @Test
    void PrivateMessageServiceImpl_deleteMessage_privateMessageDao_findById_nullObject() {
        Mockito.doReturn(null).when(privateMessageDao).findById(PrivateMessageTestData.getPrivateMessageId());

        Assertions.assertThrows(BusinessException.class, () -> privateMessageService.deleteMessage(PrivateMessageTestData.getPrivateMessageId()));
        Mockito.verify(privateMessageDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(privateMessageDao, Mockito.never()).deleteRecord(PrivateMessageTestData.getPrivateMessageId());
        Mockito.reset(privateMessageDao);
    }

}
