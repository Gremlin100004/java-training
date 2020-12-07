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
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.dto.UserProfileForIdentificationDto;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PrivateMessageTestData;
import com.senla.socialnetwork.service.config.PublicMessageTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UniversityTestData;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.enumaration.UserProfileFriendSortParameter;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    PublicMessageService publicMessageService;
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
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData.getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getUserProfiles(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
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
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());

        UserProfileDto resultProfilesDto = userProfileService.getUserProfile();
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
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> userProfileService.updateUserProfile(userProfileDto));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
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
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesSortBySurname(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getSortUserProfiles(
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
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesSortByRegistrationDate(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getSortUserProfiles(
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
    void UserProfileServiceImpl_getUserProfilesByLocationId() {
        Long locationId = LocationTestData.getLocationId();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByLocation(
            locationId, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getUserProfilesByLocationId(
            locationId, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredByLocation(
            locationId, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfilesBySchoolId() {
        Long schoolId = SchoolTestData.getSchoolId();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredBySchool(
            schoolId, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getUserProfilesBySchoolId(
            schoolId, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredBySchool(
            schoolId, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfilesByUniversityId() {
        Long universityId = UniversityTestData.getUniversityId();
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByUniversity(
            universityId, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getUserProfilesByUniversityId(
            universityId, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfilesFilteredByAge() {
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getUserProfilesFilteredByAge(
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
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).getNearestBirthdayByCurrentDate(UserTestData.getEmail());

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth();
        Assertions.assertEquals(resultProfileDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(
            UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getNearestBirthdayFromTheBeginningOfTheYear(
            UserTestData.getEmail());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayFromTheBeginningOfTheYear() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfileDto userProfileDto = UserProfileTestData.getTestUserProfileDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayByCurrentDate(
            UserTestData.getEmail());
        Mockito.doReturn(userProfile).when(userProfileDao).getNearestBirthdayFromTheBeginningOfTheYear(
            UserTestData.getEmail());

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth();
        Assertions.assertEquals(resultProfileDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(
            UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1))
            .getNearestBirthdayFromTheBeginningOfTheYear(UserTestData.getEmail());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayFromTheBeginningOfTheYear_null() {
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayByCurrentDate(
            UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayFromTheBeginningOfTheYear(
            UserTestData.getEmail());

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth();
        Assertions.assertNull(resultProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(
            UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1))
            .getNearestBirthdayFromTheBeginningOfTheYear(UserTestData.getEmail());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileDetails() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfileDto profileDto = UserProfileTestData.getTestUserProfileDto();
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());

        UserProfileDto resultProfileDto = userProfileService.getUserProfileDetails(
            UserProfileTestData.getUserProfileId());
        Assertions.assertNotNull(resultProfileDto);
        Assertions.assertEquals(resultProfileDto, profileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(
            UserProfileTestData.getUserProfileId());
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileFriends() {
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriends(
            UserTestData.getEmail(),  FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getUserProfileFriends(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
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
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByAge(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            UserProfileFriendSortParameter.BY_BIRTHDAY, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByAge(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByName() {
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData.getTestUsersProfilesForIdentificationDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            UserProfileFriendSortParameter.BY_NAME, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByNumberOfFriends() {
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            UserProfileFriendSortParameter.BY_NUMBER_OF_FRIENDS, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(UserTestData.getRightNumberUsers(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByNumberOfFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileSignedFriends() {
        List<UserProfileForIdentificationDto> profilesDto = UserProfileTestData
            .getTestUsersProfilesForIdentificationDto();
        List<UserProfile> userProfiles = UserProfileTestData.getTestUsersProfiles();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileForIdentificationDto> resultProfilesDto = userProfileService.getUserProfileSignedFriends(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
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
        userAnotherProfile.setId(UserProfileTestData.getUserProfileOtherId());
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getFutureFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());

        Assertions.assertDoesNotThrow(() -> userProfileService.sendAFriendRequest(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFutureFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userAnotherProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.sendAFriendRequest(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getFutureFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest_userProfileDao_getFutureFriend_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getFutureFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.sendAFriendRequest(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFutureFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        UserProfile userAnotherProfile = UserProfileTestData.getTestUserProfile();
        List<UserProfile> friends = UserProfileTestData.getTestUsersProfiles();
        userAnotherProfile.setId(UserProfileTestData.getUserProfileOtherId());
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getSignedFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(friends).when(userProfileDao).getFriends(UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.confirmFriend(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.confirmFriend(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
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
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getSignedFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.confirmFriend(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
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
        List<UserProfile> friends = UserProfileTestData.getTestUsersProfiles();
        friends.get(0).setId(UserProfileTestData.getFriendId());
        friends.get(1).setId(UserProfileTestData.getFriendOtherId());
        userAnotherProfile.setId(UserProfileTestData.getUserProfileOtherId());
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.doReturn(friends).when(userProfileDao).getFriends(UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.removeUserFromFriends(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriends(
            UserTestData.getEmail(), FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_removeUserFromFriends_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(userProfileDao).findByEmail(UserTestData.getEmail());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.removeUserFromFriends(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.never()).getFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
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
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(UserTestData.getEmail());
        Mockito.doReturn(null).when(userProfileDao).getFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.removeUserFromFriends(
            UserProfileTestData.getUserProfileOtherId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(UserTestData.getEmail());
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileOtherId());
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

        Assertions.assertDoesNotThrow(() -> userProfileService.deleteUserProfile(
            UserProfileTestData.getUserProfileId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(
            UserProfileTestData.getUserProfileId());
        Mockito.verify(userProfileDao, Mockito.times(1)).deleteRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_deleteUserProfile_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Mockito.doReturn(null).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.deleteUserProfile(
            UserProfileTestData.getUserProfileId()));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(
            UserProfileTestData.getUserProfileId());
        Mockito.verify(userProfileDao, Mockito.never()).deleteRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getDialogue() {
        List<PrivateMessage> privateMessages = PrivateMessageTestData.getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(privateMessages).when(privateMessageDao).getDialogue(
            UserTestData.getEmail(), UserProfileTestData.getUserProfileId(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = userProfileService.getDialogue(
            UserProfileTestData.getUserProfileId(), FIRST_RESULT, NORMAL_MAX_RESULTS);
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
    void UserProfileServiceImpl_getFriendsPublicMessages() {
        List<PublicMessage> publicMessages = PublicMessageTestData.getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getTestPublicMessagesDto();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(publicMessages).when(publicMessageDao).getFriendsMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultProfilesDto = publicMessageService.getFriendsPublicMessages(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(PublicMessageTestData.getRightNumberPublicMessages(), resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getFriendsMessages(
            UserTestData.getEmail(), FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

}
