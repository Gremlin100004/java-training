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
public class PublicMessageCommentServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    PublicMessageCommentService publicMessageCommentService;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    PublicMessageCommentDao publicMessageCommentDao;
    @Autowired
    PublicMessageDao publicMessageDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;

    @Test
    void PublicMessageCommentServiceImpl_getComments() {
        List<PublicMessageComment> publicMessageComments = PublicMessageCommentTestData.getTestPublicMessageComments();
        List<PublicMessageCommentDto> publicMessageCommentsDto = PublicMessageCommentTestData.getTestPublicMessageCommentsDto();
        Mockito.doReturn(publicMessageComments).when(publicMessageCommentDao).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageCommentDto> resultPublicMessageCommentsDto = publicMessageCommentService.getComments(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPublicMessageCommentsDto);
        Assertions.assertEquals(
            PublicMessageCommentTestData.getRightNumberPublicMessageComments(), resultPublicMessageCommentsDto.size());
        Assertions.assertFalse(resultPublicMessageCommentsDto.isEmpty());
        Assertions.assertEquals(resultPublicMessageCommentsDto, publicMessageCommentsDto);
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_addComment() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        PublicMessageCommentDto publicMessageCommentDto = PublicMessageCommentTestData.getTestPublicMessageCommentDto();
        publicMessageCommentDto.setId(null);
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).saveRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.doReturn(publicMessage).when(publicMessageDao).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        PublicMessageCommentDto resultPublicMessageCommentDto = publicMessageCommentService.addComment(
            publicMessageCommentDto);
        Assertions.assertNotNull(resultPublicMessageCommentDto);
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.verify(publicMessageDao, Mockito.times(1)).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(6)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(2)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(2)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(publicMessageCommentDao);
        Mockito.reset(publicMessageDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_updateComment() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        PublicMessageCommentDto publicMessageCommentDto = PublicMessageCommentTestData.getTestPublicMessageCommentDto();
        PublicMessage publicMessage = PublicMessageTestData.getTestPublicMessage();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.doReturn(publicMessage).when(publicMessageDao).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> publicMessageCommentService.updateComment(publicMessageCommentDto));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.verify(publicMessageDao, Mockito.times(1)).findById(PublicMessageTestData.getPublicMessageId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(6)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(2)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(2)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(publicMessageCommentDao);
        Mockito.reset(publicMessageDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteCommentByUser() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertDoesNotThrow(() -> publicMessageCommentService.deleteCommentByUser(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteCommentByUser_publicMessageCommentDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageCommentService.deleteCommentByUser(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteCommentByUser_publicMessageCommentDao_findByIdAndEmail_deletedObject() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        publicMessageComment.setDeleted(true);
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageCommentService.deleteCommentByUser(
            UserTestData.getEmail(), PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PrivateMessageTestData.getPrivateMessageId());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PublicMessageComment.class));
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteComment() {
        PublicMessageComment publicMessageComment = PublicMessageCommentTestData.getTestPublicMessageComment();
        Mockito.doReturn(publicMessageComment).when(publicMessageCommentDao).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertDoesNotThrow(() -> publicMessageCommentService.deleteComment(
            PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).deleteRecord(
            PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findById(
            PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.reset(publicMessageCommentDao);
    }

    @Test
    void PublicMessageCommentServiceImpl_deleteComment_publicMessageCommentDao_findById_nullObject() {
        Mockito.doReturn(null).when(publicMessageCommentDao).findById(PublicMessageCommentTestData.getPublicMessageCommentId());

        Assertions.assertThrows(BusinessException.class, () -> publicMessageCommentService.deleteComment(
            PublicMessageCommentTestData.getPublicMessageCommentId()));
        Mockito.verify(publicMessageCommentDao, Mockito.times(1)).findById(PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.verify(publicMessageCommentDao, Mockito.never()).deleteRecord(PublicMessageCommentTestData.getPublicMessageCommentId());
        Mockito.reset(publicMessageCommentDao);
    }

}
