package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PrivateMessageDao;
import com.senla.socialnetwork.dao.PublicMessageDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PrivateMessage;
import com.senla.socialnetwork.domain.PublicMessage;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class UserProfileServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final int MAX_RESULTS = 0;
    private static final Long COMMUNITY_ID = 1L;
    private static final Long USER_PROFILE_ID = 1L;
    private static final Long POST_ID = 1L;
    private static final Long LOCATION_ID = 1L;
    private static final Long SCHOOL_ID = 1L;
    private static final Long UNIVERSITY_ID = 1L;
    private static final Long FRIEND_ID = 1L;
    private static final Long PRIVATE_MESSAGE_ID = 1L;
    private static final Long PUBLIC_MESSAGE_ID = 1L;
    private static final Long COMMUNITY_OTHER_ID = 2L;
    private static final Long USER_PROFILE_OTHER_ID = 2L;
    private static final Long POST_OTHER_ID = 2L;
    private static final Long FRIEND_OTHER_ID = 2L;
    private static final Long PRIVATE_MESSAGE_OTHER_ID = 2L;
    private static final Long PUBLIC_MESSAGE_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_PROFILES = 2L;
    private static final Long RIGHT_NUMBER_PRIVATE_MESSAGES = 2L;
    private static final Long RIGHT_NUMBER_PUBLIC_MESSAGES = 2L;
    private static final Date USER_PROFILE_CREATION_DATE = new Date();
    private static final Date PRIVATE_MESSAGE_CREATION_DATE = new Date();
    private static final Date PUBLIC_MESSAGE_CREATION_DATE = new Date();
    private static final Date POST_CREATION_DATE = new Date();
    private static final Date START_PERIOD_DATE = new Date();
    private static final Date END_PERIOD_DATE = new Date();
    private static final String COMMUNITY_TITTLE = "Test";
    private static final String POST_TITTLE = "Test";
    private static final String COUNTRY = "Test";
    private static final String CITY = "Test";
    private static final String SCHOOL_NAME = "Test";
    private static final String UNIVERSITY_NAME = "Test";
    private static final String CONTENT = "Test";
    private static final String EMAIL = "test@test";
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
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfile() {
        UserProfileDto userProfileDto = getTestUserProfileDto();
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);

        UserProfileDto resultProfilesDto = userProfileService.getUserProfile(EMAIL);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(resultProfilesDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_updateUserProfile() {
        UserProfileDto userProfileDto = getTestUserProfileDto();
        UserProfile userProfile = getTestUserProfile();
        Location location = getTestLocation();
        School school = getTestSchool();
        University university = getTestUniversity();
        Mockito.doReturn(userProfile).when(userProfileDao).findById(USER_PROFILE_ID);
        Mockito.doReturn(location).when(locationDao).findById(LOCATION_ID);
        Mockito.doReturn(school).when(schoolDao).findById(SCHOOL_ID);
        Mockito.doReturn(university).when(universityDao).findById(UNIVERSITY_ID);

        Assertions.assertDoesNotThrow(() -> userProfileService.updateUserProfile(userProfileDto));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(USER_PROFILE_ID);
        Mockito.verify(locationDao, Mockito.times(3)).findById(LOCATION_ID);
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SCHOOL_ID);
        Mockito.verify(universityDao, Mockito.times(1)).findById(UNIVERSITY_ID);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void UserProfileServiceImpl_getSortUserProfiles_userProfileDao_getUserProfilesSortBySurname() {
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesSortBySurname(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortUserProfiles(
            UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
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
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesSortByRegistrationDate(
            FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortUserProfiles(
            UserProfileSortParameter.BY_REGISTRATION_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
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
        LocationDto locationDto = getTestLocationDto();
        Location location = getTestLocation();
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        Mockito.doReturn(location).when(locationDao).findById(LOCATION_ID);
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByLocation(
            location, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(
            locationDto, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredByLocation(
            location, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(locationDao, Mockito.times(1)).findById(LOCATION_ID);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfiles_school() {
        Location location = getTestLocation();
        School school = getTestSchool();
        SchoolDto schoolDto = getTestSchoolDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        Mockito.doReturn(location).when(locationDao).findById(LOCATION_ID);
        Mockito.doReturn(school).when(schoolDao).findById(SCHOOL_ID);
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredBySchool(
            school, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(
            schoolDto, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredBySchool(
            school, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(locationDao, Mockito.times(1)).findById(LOCATION_ID);
        Mockito.verify(schoolDao, Mockito.times(1)).findById(SCHOOL_ID);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfiles_university() {
        Location location = getTestLocation();
        University university = getTestUniversity();
        UniversityDto universityDto = getTestUniversityDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        Mockito.doReturn(location).when(locationDao).findById(LOCATION_ID);
        Mockito.doReturn(university).when(universityDao).findById(UNIVERSITY_ID);
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByUniversity(
            university, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfiles(
            universityDto, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredByUniversity(
            university, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(locationDao, Mockito.times(1)).findById(LOCATION_ID);
        Mockito.verify(universityDao, Mockito.times(1)).findById(UNIVERSITY_ID);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(universityDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfilesFilteredByAge() {
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayByCurrentDate() {
        UserProfile userProfile = getTestUserProfile();
        UserProfileDto userProfileDto = getTestUserProfileDto();
        Mockito.doReturn(userProfile).when(userProfileDao).getNearestBirthdayByCurrentDate(EMAIL);

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth(EMAIL);
        Assertions.assertEquals(resultProfileDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(EMAIL);
        Mockito.verify(userProfileDao, Mockito.never()).getNearestBirthdayFromTheBeginningOfTheYear(EMAIL);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayFromTheBeginningOfTheYear() {
        UserProfile userProfile = getTestUserProfile();
        UserProfileDto userProfileDto = getTestUserProfileDto();
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayByCurrentDate(EMAIL);
        Mockito.doReturn(userProfile).when(userProfileDao).getNearestBirthdayFromTheBeginningOfTheYear(EMAIL);

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth(EMAIL);
        Assertions.assertEquals(resultProfileDto, userProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1))
            .getNearestBirthdayFromTheBeginningOfTheYear(EMAIL);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendNearestDateOfBirth_userProfileDao_getNearestBirthdayFromTheBeginningOfTheYear_null() {
        UserProfileDto userProfileDto = getTestUserProfileDto();
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayByCurrentDate(EMAIL);
        Mockito.doReturn(null).when(userProfileDao).getNearestBirthdayFromTheBeginningOfTheYear(EMAIL);

        UserProfileDto resultProfileDto = userProfileService.getFriendNearestDateOfBirth(EMAIL);
        Assertions.assertNull(resultProfileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getNearestBirthdayByCurrentDate(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1))
            .getNearestBirthdayFromTheBeginningOfTheYear(EMAIL);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileFriend() {
        UserProfile userProfile = getTestUserProfile();
        UserProfileDto profileDto = getTestUserProfileDto();
        Mockito.doReturn(userProfile).when(userProfileDao).getFriend(EMAIL, USER_PROFILE_ID);

        UserProfileDto resultProfileDto = userProfileService.getUserProfileFriend(EMAIL, USER_PROFILE_ID);
        Assertions.assertNotNull(resultProfileDto);
        Assertions.assertEquals(resultProfileDto, profileDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(EMAIL, USER_PROFILE_ID);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileFriends() {
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriends(EMAIL,  FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfileFriends(
            EMAIL,  FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriends(
            EMAIL,  FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByAge() {
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByAge(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
           EMAIL, UserProfileSortParameter.BY_BIRTHDAY, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByAge(EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByName() {
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByName(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            EMAIL, UserProfileSortParameter.BY_NAME, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByName(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_userProfileDao_getFriendsSortByNumberOfFriends() {
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getFriendsSortByNumberOfFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getSortedFriendsOfUserProfile(
            EMAIL, UserProfileSortParameter.BY_NUMBER_OF_FRIENDS, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriendsSortByNumberOfFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getSortedFriendsOfUserProfile_wrongSortParameter() {
        Assertions.assertThrows(BusinessException.class, () -> userProfileService.getSortedFriendsOfUserProfile(
            EMAIL, UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, NORMAL_MAX_RESULTS));
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByAge(EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByName(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriendsSortByNumberOfFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getUserProfileSignedFriends() {
        List<UserProfileDto> profilesDto = getTestUserProfilesDto();
        List<UserProfile> userProfiles = getTestUserProfiles();
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<UserProfileDto> resultProfilesDto = userProfileService.getUserProfileSignedFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PROFILES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, profilesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest() {
        UserProfile userProfile = getTestUserProfile();
        UserProfile userAnotherProfile = getTestUserProfile();
        List<UserProfile> userProfiles = getTestUserProfiles();
        userAnotherProfile.setId(USER_PROFILE_OTHER_ID);
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getFutureFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(EMAIL, FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.sendAFriendRequest(EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFutureFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(null).when(userProfileDao).findByEmail(EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.sendAFriendRequest(
            EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.never()).getFutureFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_sendAFriendRequest_userProfileDao_getFutureFriend_nullObject() {
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(null).when(userProfileDao).getFutureFriend(EMAIL, USER_PROFILE_OTHER_ID);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.sendAFriendRequest(
            EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFutureFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend() {
        UserProfile userProfile = getTestUserProfile();
        UserProfile userAnotherProfile = getTestUserProfile();
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfile> friends = getTestUserProfiles();
        friends.get(0).setId(FRIEND_ID);
        friends.get(1).setId(FRIEND_OTHER_ID);
        userAnotherProfile.setId(USER_PROFILE_OTHER_ID);
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getSignedFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.doReturn(friends).when(userProfileDao).getFriends(EMAIL, FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.confirmFriend(EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(null).when(userProfileDao).findByEmail(EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.confirmFriend(
            EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_confirmFriend_userProfileDao_getSignedFriend_nullObject() {
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(null).when(userProfileDao).getSignedFriend(EMAIL, USER_PROFILE_OTHER_ID);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.confirmFriend(
            EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_removeUserFromFriends() {
        UserProfile userProfile = getTestUserProfile();
        UserProfile userAnotherProfile = getTestUserProfile();
        List<UserProfile> userProfiles = getTestUserProfiles();
        List<UserProfile> friends = getTestUserProfiles();
        friends.get(0).setId(FRIEND_ID);
        friends.get(1).setId(FRIEND_OTHER_ID);
        userAnotherProfile.setId(USER_PROFILE_OTHER_ID);
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(userAnotherProfile).when(userProfileDao).getFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.doReturn(userProfiles).when(userProfileDao).getSignedFriends(EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.doReturn(friends).when(userProfileDao).getFriends(EMAIL, FIRST_RESULT, MAX_RESULTS);

        Assertions.assertDoesNotThrow(() -> userProfileService.removeUserFromFriends(EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.times(1)).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_removeUserFromFriends_userProfileDao_findByEmail_nullObject() {
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(null).when(userProfileDao).findByEmail(EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.removeUserFromFriends(
            EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.never()).getFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_removeUserFromFriends_userProfileDao_getSignedFriend_nullObject() {
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(null).when(userProfileDao).getFriend(EMAIL, USER_PROFILE_OTHER_ID);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.removeUserFromFriends(
            EMAIL, USER_PROFILE_OTHER_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(userProfileDao, Mockito.times(1)).getFriend(EMAIL, USER_PROFILE_OTHER_ID);
        Mockito.verify(userProfileDao, Mockito.never()).getSignedFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).getFriends(
            EMAIL, FIRST_RESULT, MAX_RESULTS);
        Mockito.verify(userProfileDao, Mockito.never()).updateRecord(userProfile);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_deleteUserProfile() {
        UserProfile userProfile = getTestUserProfile();
        Mockito.doReturn(userProfile).when(userProfileDao).findById(USER_PROFILE_ID);

        Assertions.assertDoesNotThrow(() -> userProfileService.deleteUserProfile(USER_PROFILE_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(USER_PROFILE_ID);
        Mockito.verify(userProfileDao, Mockito.times(1)).deleteRecord(USER_PROFILE_ID);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_deleteUserProfile_userProfileDao_findByEmail_nullObject() {
        Mockito.doReturn(null).when(userProfileDao).findById(USER_PROFILE_ID);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.deleteUserProfile(USER_PROFILE_ID));
        Mockito.verify(userProfileDao, Mockito.times(1)).findById(USER_PROFILE_ID);
        Mockito.verify(userProfileDao, Mockito.never()).deleteRecord(USER_PROFILE_ID);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getPrivateMessages() {
        UserProfile userProfile = getTestUserProfile();
        List<PrivateMessage> privateMessages = getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = getTestPrivateMessagesDto();
        Mockito.doReturn(userProfile).when(userProfileDao).findByEmail(EMAIL);
        Mockito.doReturn(privateMessages).when(privateMessageDao).getByEmail(EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = userProfileService.getPrivateMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PRIVATE_MESSAGES, resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getByEmail(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getPrivateMessages_userProfileDao_findByEmail_nullObject() {
        Mockito.doReturn(null).when(userProfileDao).findByEmail(EMAIL);

        Assertions.assertThrows(BusinessException.class, () -> userProfileService.getPrivateMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS));
        Mockito.verify(userProfileDao, Mockito.times(1)).findByEmail(EMAIL);
        Mockito.verify(privateMessageDao, Mockito.never()).getByEmail(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
    }

    @Test
    void UserProfileServiceImpl_getDialogue() {
        List<PrivateMessage> privateMessages = getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessages).when(privateMessageDao).getDialogue(
            EMAIL, USER_PROFILE_ID, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = userProfileService.getDialogue(
            EMAIL, USER_PROFILE_ID, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PRIVATE_MESSAGES, resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getDialogue(
            EMAIL, USER_PROFILE_ID, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getUnreadMessages() {
        List<PrivateMessage> privateMessages = getTestPrivateMessages();
        List<PrivateMessageDto> privateMessagesDto = getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessages).when(privateMessageDao).getUnreadMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PrivateMessageDto> resultPrivateMessagesDto = userProfileService.getUnreadMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPrivateMessagesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PRIVATE_MESSAGES, resultPrivateMessagesDto.size());
        Assertions.assertFalse(resultPrivateMessagesDto.isEmpty());
        Assertions.assertEquals(resultPrivateMessagesDto, privateMessagesDto);
        Mockito.verify(privateMessageDao, Mockito.times(1)).getUnreadMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(userProfileDao);
        Mockito.reset(privateMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getFriendsPublicMessages() {
        List<PublicMessage> publicMessages = getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = getTestPublicMessagesDto();
        Mockito.doReturn(publicMessages).when(publicMessageDao).getFriendsMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultProfilesDto = userProfileService.getFriendsPublicMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PUBLIC_MESSAGES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getFriendsMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

    @Test
    void UserProfileServiceImpl_getPublicMessages() {
        List<PublicMessage> publicMessages = getTestPublicMessages();
        List<PublicMessageDto> publicMessagesDto = getTestPublicMessagesDto();
        Mockito.doReturn(publicMessages).when(publicMessageDao).getByEmail(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PublicMessageDto> resultProfilesDto = userProfileService.getPublicMessages(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultProfilesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PUBLIC_MESSAGES, resultProfilesDto.size());
        Assertions.assertFalse(resultProfilesDto.isEmpty());
        Assertions.assertEquals(resultProfilesDto, publicMessagesDto);
        Mockito.verify(publicMessageDao, Mockito.times(1)).getByEmail(
            EMAIL, FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(publicMessageDao);
    }

    private Community getTestCommunity() {
        Community community = new Community();
        community.setId(COMMUNITY_ID);
        community.setAuthor(getTestUserProfile());
        community.setTittle(COMMUNITY_TITTLE);
        community.setAuthor(getTestUserProfile());
        return community;
    }

    private CommunityDto getTestCommunityDto() {
        CommunityDto communityDto = new CommunityDto();
        communityDto.setId(COMMUNITY_ID);
        communityDto.setAuthor(getTestUserProfileDto());
        communityDto.setTittle(COMMUNITY_TITTLE);
        communityDto.setAuthor(getTestUserProfileDto());
        return communityDto;
    }

    private List<Community> getTestCommunities() {
        Community communityOne = getTestCommunity();
        Community communityTwo = getTestCommunity();
        communityTwo.setId(COMMUNITY_OTHER_ID);
        return Arrays.asList(communityOne, communityTwo);
    }

    private List<CommunityDto> getTestCommunitiesDto() {
        CommunityDto communityDtoOne = getTestCommunityDto();
        CommunityDto communityDtoTwo = getTestCommunityDto();
        communityDtoTwo.setId(COMMUNITY_OTHER_ID);
        List<CommunityDto> communitiesDto = new ArrayList<>();
        communitiesDto.add(communityDtoOne);
        communitiesDto.add(communityDtoTwo);
        return communitiesDto;
    }

    private UserProfile getTestUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(USER_PROFILE_ID);
        userProfile.setRegistrationDate(USER_PROFILE_CREATION_DATE);
        userProfile.setLocation(getTestLocation());
        userProfile.setSchool(getTestSchool());
        userProfile.setUniversity(getTestUniversity());
        return userProfile;
    }

    private UserProfileDto getTestUserProfileDto() {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(USER_PROFILE_ID);
        userProfileDto.setRegistrationDate(USER_PROFILE_CREATION_DATE);
        userProfileDto.setLocation(getTestLocationDto());
        userProfileDto.setSchool(getTestSchoolDto());
        userProfileDto.setUniversity(getTestUniversityDto());
        return userProfileDto;
    }

    private List<UserProfile> getTestUserProfiles() {
        UserProfile userProfileOne = getTestUserProfile();
        UserProfile userProfileTwo = getTestUserProfile();
        userProfileTwo.setId(USER_PROFILE_OTHER_ID);
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfileOne);
        userProfiles.add(userProfileTwo);
        return userProfiles;
    }

    private List<UserProfileDto> getTestUserProfilesDto() {
        UserProfileDto userProfileDtoOne = getTestUserProfileDto();
        UserProfileDto UserProfileDtoTwo = getTestUserProfileDto();
        UserProfileDtoTwo.setId(USER_PROFILE_OTHER_ID);
        List<UserProfileDto> userProfilesDto = new ArrayList<>();
        userProfilesDto.add(userProfileDtoOne);
        userProfilesDto.add(UserProfileDtoTwo);
        return userProfilesDto;
    }

    private Post getTestPost() {
        Post post = new Post();
        post.setId(POST_ID);
        post.setTittle(POST_TITTLE);
        post.setCreationDate(POST_CREATION_DATE);
        post.setCommunity(getTestCommunity());
        return post;
    }

    private PostDto getTestPostDto() {
        PostDto postDto = new PostDto();
        postDto.setId(POST_ID);
        postDto.setTittle(POST_TITTLE);
        postDto.setCreationDate(POST_CREATION_DATE);
        postDto.setCommunity(getTestCommunityDto());
        return postDto;
    }

    private List<Post> getTestPosts() {
        Post postOne = getTestPost();
        Post postTwo = getTestPost();
        postTwo.setId(POST_OTHER_ID);
        List<Post> posts = new ArrayList<>();
        posts.add(postOne);
        posts.add(postTwo);
        return posts;
    }

    private List<PostDto> getTestPostsDto() {
        PostDto postDtoOne = getTestPostDto();
        PostDto postDtoTwo = getTestPostDto();
        postDtoTwo.setId(POST_OTHER_ID);
        List<PostDto> postsDto = new ArrayList<>();
        postsDto.add(postDtoOne);
        postsDto.add(postDtoTwo);
        return postsDto;
    }

    private Location getTestLocation() {
        Location location = new Location();
        location.setId(LOCATION_ID);
        location.setCountry(COUNTRY);
        location.setCity(CITY);
        return location;
    }

    private LocationDto getTestLocationDto() {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(LOCATION_ID);
        locationDto.setCountry(COUNTRY);
        locationDto.setCity(CITY);
        return locationDto;
    }

    private School getTestSchool() {
        School school = new School();
        school.setId(SCHOOL_ID);
        school.setName(SCHOOL_NAME);
        school.setLocation(getTestLocation());
        return school;
    }

    private SchoolDto getTestSchoolDto() {
        SchoolDto schoolDto = new SchoolDto();
        schoolDto.setId(SCHOOL_ID);
        schoolDto.setName(SCHOOL_NAME);
        schoolDto.setLocation(getTestLocationDto());
        return schoolDto;
    }

    private University getTestUniversity() {
        University university = new University();
        university.setId(UNIVERSITY_ID);
        university.setName(UNIVERSITY_NAME);
        university.setLocation(getTestLocation());
        return university;
    }

    private UniversityDto getTestUniversityDto() {
        UniversityDto universityDto = new UniversityDto();
        universityDto.setId(UNIVERSITY_ID);
        universityDto.setName(SCHOOL_NAME);
        universityDto.setLocation(getTestLocationDto());
        return universityDto;
    }

    private PrivateMessage getTestPrivateMessage() {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setId(PRIVATE_MESSAGE_ID);
        privateMessage.setSender(getTestUserProfile());
        privateMessage.setRecipient(getTestUserProfile());
        privateMessage.setContent(CONTENT);
        privateMessage.setDepartureDate(PRIVATE_MESSAGE_CREATION_DATE);
        return privateMessage;
    }

    private PrivateMessageDto getTestPrivateMessageDto() {
        PrivateMessageDto privateMessageDto = new PrivateMessageDto();
        privateMessageDto.setId(PRIVATE_MESSAGE_ID);
        privateMessageDto.setSender(getTestUserProfileDto());
        privateMessageDto.setRecipient(getTestUserProfileDto());
        privateMessageDto.setContent(CONTENT);
        privateMessageDto.setDepartureDate(PRIVATE_MESSAGE_CREATION_DATE);
        return privateMessageDto;
    }

    private List<PrivateMessage> getTestPrivateMessages() {
        PrivateMessage privateMessageOne = getTestPrivateMessage();
        PrivateMessage privateMessageTwo = getTestPrivateMessage();
        privateMessageTwo.setId(PRIVATE_MESSAGE_OTHER_ID);
        List<PrivateMessage> privateMessages = new ArrayList<>();
        privateMessages.add(privateMessageOne);
        privateMessages.add(privateMessageTwo);
        return privateMessages;
    }

    private List<PrivateMessageDto> getTestPrivateMessagesDto() {
        PrivateMessageDto privateMessageDtoOne = getTestPrivateMessageDto();
        PrivateMessageDto privateMessageDtoTwo = getTestPrivateMessageDto();
        privateMessageDtoTwo.setId(PRIVATE_MESSAGE_OTHER_ID);
        List<PrivateMessageDto> privateMessagesDto = new ArrayList<>();
        privateMessagesDto.add(privateMessageDtoOne);
        privateMessagesDto.add(privateMessageDtoTwo);
        return privateMessagesDto;
    }

    private PublicMessage getTestPublicMessage() {
        PublicMessage publicMessage = new PublicMessage();
        publicMessage.setId(PUBLIC_MESSAGE_ID);
        publicMessage.setCreationDate(PUBLIC_MESSAGE_CREATION_DATE);
        publicMessage.setAuthor(getTestUserProfile());
        return publicMessage;
    }

    private PublicMessageDto getTestPublicMessageDto() {
        PublicMessageDto publicMessageDto = new PublicMessageDto();
        publicMessageDto.setId(PUBLIC_MESSAGE_ID);
        publicMessageDto.setCreationDate(PUBLIC_MESSAGE_CREATION_DATE);
        publicMessageDto.setAuthor(getTestUserProfileDto());
        return publicMessageDto;
    }

    private List<PublicMessage> getTestPublicMessages() {
        PublicMessage publicMessageOne = getTestPublicMessage();
        PublicMessage publicMessageTwo = getTestPublicMessage();
        publicMessageTwo.setId(PUBLIC_MESSAGE_OTHER_ID);
        List<PublicMessage> publicMessages = new ArrayList<>();
        publicMessages.add(publicMessageOne);
        publicMessages.add(publicMessageTwo);
        return publicMessages;
    }

    private List<PublicMessageDto> getTestPublicMessagesDto() {
        PublicMessageDto publicMessageDtoOne = getTestPublicMessageDto();
        PublicMessageDto publicMessageDtoTwo = getTestPublicMessageDto();
        publicMessageDtoTwo.setId(PRIVATE_MESSAGE_OTHER_ID);
        List<PublicMessageDto> publicMessagesDto = new ArrayList<>();
        publicMessagesDto.add(publicMessageDtoOne);
        publicMessagesDto.add(publicMessageDtoTwo);
        return publicMessagesDto;
    }

}
