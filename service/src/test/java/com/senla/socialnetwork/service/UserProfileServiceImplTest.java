package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PrivateMessageTestData;
import com.senla.socialnetwork.service.config.PublicMessageTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UniversityTestData;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserProfileServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final int MAX_RESULTS = 0;
    private static final Date START_PERIOD_DATE = new Date();
    private static final Date END_PERIOD_DATE = new Date();
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private UniversityDao universityDao;
    @Autowired
    private PrivateMessageDao privateMessageDao;
    @Autowired
    private PublicMessageDao publicMessageDao;

    @Test
    void UserProfileServiceImpl_getUserProfiles() {
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfile() {
        UserProfileDto userProfileDto = UserProfileTestData.getTestUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());

        UserProfileDto resultProfilesDto = userProfileService.getUserProfile(UserTestData.getEmail());
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(resultProfilesDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_updateUserProfile() {
        UserProfileDto userProfileDto = UserProfileTestData.getTestUserProfileDto();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> userProfileService.updateUserProfile(userProfileDto));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(3)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void UserProfileServiceImpl_getSortUserProfiles_userProfileDao_getUserProfilesSortBySurname() {
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesSortBySurname(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortUserProfiles(
            UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesSortBySurname(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getUserProfilesSortByRegistrationDate(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortUserProfiles_userProfileDao_getUserProfilesSortByRegistrationDate() {
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesSortByRegistrationDate(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortUserProfiles(
            UserProfileSortParameter.BY_REGISTRATION_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.never()).getUserProfilesSortBySurname(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesSortByRegistrationDate(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortUserProfiles_wrongSortParameter() {
        Assertions.assertThrows(BusinessException.class, () -> userProfileService.getSortUserProfiles(
            UserProfileSortParameter.BY_BIRTHDAY, FIRST_RESULT, NORMAL_MAX_RESULTS));
        Mockito.verify(userProfileDao, Mockito.never()).getUserProfilesSortBySurname(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getUserProfilesSortByRegistrationDate(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
    }

    @Test
    void UserProfileServiceImpl_getUserProfiles_locationDto() {
        LocationDto locationDto = LocationTestData.getTestLocationDto();
        Location location = LocationTestData.getTestLocation();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByLocation(
            location, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(
            locationDto, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredByLocation(
            location, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfiles_school() {
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        SchoolDto schoolDto = SchoolTestData.getTestSchoolDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredBySchool(
            school, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(
            schoolDto, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredBySchool(
            school, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SchoolTestData.getSchoolId());
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfiles_university() {
        Location location = LocationTestData.getTestLocation();
        University university = UniversityTestData.getTestUniversity();
        UniversityDto universityDto = UniversityTestData.getTestUniversityDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByUniversity(
            university, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(
            universityDto, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredByUniversity(
            university, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(universityDao, Mockito.times(1)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(universityDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfilesFilteredByAge() {
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayByCurrentDate() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfileDto userProfileDto = UserProfileTestData.getTestUserProfileDto();
        Mockito.doReturn(userProfile).when(userProfileDao).getNearestBirthdayByCurrentDate(UserTestData.getEmail());

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth(UserTestData.getEmail());
        Assertions.assertEquals(resultProfileDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getNearestBirthdayFromTheBeginningOfTheYear(UserTestData.getEmail());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayFromTheBeginningOfTheYear() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfileDto userProfileDto = UserProfileTestData.getTestUserProfileDto();
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayByCurrentDate(UserTestData.getEmail());
        Mockito.doReturn(userProfile).when(userProfileDao).getNearestBirthdayFromTheBeginningOfTheYear(UserTestData.getEmail());

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth(UserTestData.getEmail());
        Assertions.assertEquals(resultProfileDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1))
            .getNearestBirthdayFromTheBeginningOfTheYear(UserTestData.getEmail());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayFromTheBeginningOfTheYear_null() {
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayByCurrentDate(UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayFromTheBeginningOfTheYear(UserTestData.getEmail());

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth(UserTestData.getEmail());
        Assertions.assertNull(resultProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1))
            .getNearestBirthdayFromTheBeginningOfTheYear(UserTestData.getEmail());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileFriend() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfileDto profileDto = UserProfileTestData.getTestUserProfileDto();
        Mockito.doReturn(userProfile).when(userProfileDao).getFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileId());

        UserProfileDto resultProfileDto = userProfileService.getUserProfileFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileId());
        Assertions.assertNotNull(resultProfileDto);
        Assertions.assertEquals(resultProfileDto, profileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileId());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileFriends() {
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriends(UserTestData.getEmail(),  FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfileFriends(
            UserTestData.getEmail(),  FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriends(
            UserTestData.getEmail(),  FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByAge() {
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByAge(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            UserTestData.getEmail(), UserProfileSortParameter.BY_BIRTHDAY, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByAge(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByName() {
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            UserTestData.getEmail(), UserProfileSortParameter.BY_NAME, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByNumberOfFriends() {
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            UserTestData.getEmail(), UserProfileSortParameter.BY_NUMBER_OF_FRIENDS, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_wrongSortParameter() {
        Assertions.assertThrows(BusinessException.class, () -> userProfileService.getSortedFriendsOfUserProfile(
            UserTestData.getEmail(), UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, NORMAL_MAX_RESULTS));
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileSignedFriends() {
        List<UserProfileDto> profilesDto = UserProfileTestData.getTestUserProfilesDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfileSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfile userAnotherProfile = UserProfileTestData.getTestUserProfile();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        userAnotherProfile.setId(UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getFutureFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.sendAFriendRequest(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFutureFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.sendAFriendRequest(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getFutureFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest_userProfileDao_getFutureFriend_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getFutureFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.sendAFriendRequest(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFutureFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfile userAnotherProfile = UserProfileTestData.getTestUserProfile();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfile> friends = UserProfileTestData.getTestUserProfiles();
        friends.get(0).setId(UserProfileTestData.getFriendId());
        friends.get(1).setId(UserProfileTestData.getFriendOtherId());
        userAnotherProfile.setId(UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getSignedFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.doReturn(friends).when(userProfileDao).getFriends(UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.confirmFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.confirmFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend_userProfileDao_getSignedFriend_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getSignedFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.confirmFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_removeUserFromFriends() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfile userAnotherProfile = UserProfileTestData.getTestUserProfile();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUserProfiles();
        List<UserProfile> friends = UserProfileTestData.getTestUserProfiles();
        friends.get(0).setId(UserProfileTestData.getFriendId());
        friends.get(1).setId(UserProfileTestData.getFriendOtherId());
        userAnotherProfile.setId(UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.doReturn(friends).when(userProfileDao).getFriends(UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.removeUserFromFriends(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_removeUserFromFriends_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.removeUserFromFriends(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_removeUserFromFriends_userProfileDao_getSignedFriend_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.removeUserFromFriends(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_deleteUserProfile() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());

        Assertions.assertDoesNotThrow(() -> userProfileService.deleteUserProfile(UserProfileTestData.getUserProfileId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(userProfileDao, Mockito.times(1)).deleteRecord(UserProfileTestData.getUserProfileId());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_deleteUserProfile_userProfileDao_findByEmail_nullObject() {
        Mockito.doReturn(null).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.deleteUserProfile(UserProfileTestData.getUserProfileId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(userProfileDao, Mockito.never()).deleteRecord(UserProfileTestData.getUserProfileId());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getPrivateMessages() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(privateMessages).when(privateMessageDao).getByEmail(UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = userProfileService.getPrivateMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(privateMessageDao, Mockito.times(1)).getByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getPrivateMessages_userProfileDao_findByEmail_nullObject() {
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.getPrivateMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(privateMessageDao, Mockito.never()).getByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getDialogue() {
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessages).when(privateMessageDao).getDialogue(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileId(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = userProfileService.getDialogue(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getDialogue(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getUnreadMessages() {
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessages).when(privateMessageDao).getUnreadMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = userProfileService.getUnreadMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(PrivateMessageTestData.getRightNumberPrivateMessages(), resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getUnreadMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendsPublicMessages() {
        List<PublicMessage> publicMessages = PublicMessageTestData.getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getTestPublicMessagesDto();
        Mockito.doReturn(publicMessages).when(publicMessageDao).getFriendsMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultProfilesDto = userProfileService.getFriendsPublicMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(PublicMessageTestData.getRightNumberPublicMessages(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getFriendsMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getPublicMessages() {
        List<PublicMessage> publicMessages = PublicMessageTestData.getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getTestPublicMessagesDto();
        Mockito.doReturn(publicMessages).when(publicMessageDao).getByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultProfilesDto = userProfileService.getPublicMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(PublicMessageTestData.getRightNumberPublicMessages(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getByEmail(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

}
