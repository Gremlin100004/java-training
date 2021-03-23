package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.dao.testdata.UserProfileTestData;
import com.senla.socialnetwork.model.*;
import com.senla.socialnetwork.model.enumaration.RoleName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserProfileDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String EMAIL = "test";
    private static final String PASSWORD = "test";
    private static final Date START_PERIOD_DATE = UserProfileTestData.getDate("1990-06-11");
    private static final Date END_PERIOD_DATE = UserProfileTestData.getDate("1996-08-11");
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommunityDao communityDao;


    @Test
    void UserProfile_getAllRecord() {
        List<UserProfile> resultUserProfiles = userProfileDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getUserProfiles().size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getUserProfiles());
}

    @Test
    void UserProfile_findById() {
        UserProfile resultProfiles = userProfileDao.findById(testDataUtil.getUserProfiles().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultProfiles);
        Assertions.assertEquals(testDataUtil.getUserProfiles().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultProfiles);
    }

    @Test
    void UserProfile_findById_ErrorId() {
        UserProfile userProfileDaoById = userProfileDao.findById((long) ArrayIndex.NINTH_INDEX_OF_ARRAY.index);
        Assertions.assertNull(userProfileDaoById);
    }

    @Test
    void UserProfile_saveRecord() {
        SystemUser systemUser = new SystemUser();
        systemUser.setEmail(EMAIL);
        systemUser.setPassword(PASSWORD);
        systemUser.setRole(RoleName.ROLE_USER);
        userDao.save(systemUser);
        UserProfile userProfile = new UserProfile();
        userProfile.setSystemUser(systemUser);
        userProfile.setRegistrationDate(new Date());

        userProfileDao.save(userProfile);
        Assertions.assertNotNull(userProfile.getId());
    }

    @Test
    void UserProfile_updateRecord() {
        UserProfile userProfile = testDataUtil.getUserProfiles().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);
        userProfile.setRegistrationDate(new Date());

        userProfileDao.updateRecord(userProfile);
        UserProfile resultUserProfile = userProfileDao.findById(userProfile.getId());
        Assertions.assertNotNull(resultUserProfile);
        Assertions.assertEquals(userProfile, resultUserProfile);
    }

    @Test
    void UserProfile_deleteRecord() {
        UserProfile userProfile = testDataUtil.getUserProfiles().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        userProfileDao.deleteRecord(userProfile);
        UserProfile resultUserProfile = userProfileDao.findById(userProfile.getId());
        Assertions.assertNull(resultUserProfile);
    }

    @Test
    void UserProfile_findByEmail() {
        UserProfile resultUserProfile = userProfileDao.findByEmail(testDataUtil.getUserProfiles().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getSystemUser().getEmail());
        Assertions.assertNotNull(resultUserProfile);
        Assertions.assertEquals(testDataUtil.getUserProfiles().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultUserProfile);
    }

    @Test
    void UserProfile_findByEmail_errorEmail() {
        UserProfile userProfile = userProfileDao.findByEmail(EMAIL);
        Assertions.assertNull(userProfile);
    }

    @Test
    void UserProfile_getUserProfilesSortBySurname() {
        List<UserProfile> resultUserProfiles = userProfileDao.getUserProfilesSortBySurname(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getUserProfiles().size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getUserProfiles());
    }

    @Test
    void UserProfile_getUserProfilesSortByRegistrationDate() {
        List<UserProfile> resultUserProfiles = userProfileDao.getUserProfilesSortByRegistrationDate(
                FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getUserProfiles().size());
        Assertions.assertTrue(resultUserProfiles.contains(testDataUtil.getUserProfiles().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultUserProfiles.contains(testDataUtil.getUserProfiles().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultUserProfiles.contains(testDataUtil.getUserProfiles().get(
                ArrayIndex.THIRD_INDEX_OF_ARRAY.index)));
        Assertions.assertTrue(resultUserProfiles.contains(testDataUtil.getUserProfiles().get(
                ArrayIndex.FOURTH_INDEX_OF_ARRAY.index)));

    }

    @Test
    void UserProfile_getUserProfilesFilteredByLocation() {
        Location location = testDataUtil.getLocations().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        List<UserProfile> resultUserProfiles = userProfileDao.getUserProfilesFilteredByLocation(
                location.getId(), FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getUserProfilesByLocation(location).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getUserProfilesByLocation(location));
    }

    @Test
    void UserProfile_getUserProfilesFilteredBySchool() {
        School school = testDataUtil.getSchools().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        List<UserProfile> resultUserProfiles = userProfileDao.getUserProfilesFilteredBySchool(
                school.getId(), FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getUserProfilesBySchool(school).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getUserProfilesBySchool(school));
    }

    @Test
    void UserProfile_getUserProfilesFilteredByUniversity() {
        University university = testDataUtil.getUniversities().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index);

        List<UserProfile> resultUserProfiles = userProfileDao.getUserProfilesFilteredByUniversity(
                university.getId(), FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getUserProfilesByUniversity(university).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getUserProfilesByUniversity(university));
    }

    @Test
    void UserProfile_getUserProfilesFilteredByAge() {
        List<UserProfile> resultUserProfiles = userProfileDao.getUserProfilesFilteredByAge(
            START_PERIOD_DATE, END_PERIOD_DATE, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getUserProfilesByAge(
                START_PERIOD_DATE, END_PERIOD_DATE).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getUserProfilesByAge(
                START_PERIOD_DATE, END_PERIOD_DATE));
    }

    @Test
    void UserProfile_getNearestBirthdayByCurrentDate() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        UserProfile resultUserProfile = userProfileDao.getNearestBirthdayByCurrentDate(email);
        Assertions.assertNotNull(resultUserProfile);
    }

    @Test
    void UserProfile_getNearestBirthdayFromTheBeginningOfTheYear() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        UserProfile resultUserProfile = userProfileDao.getNearestBirthdayFromTheBeginningOfTheYear(email);
        Assertions.assertNotNull(resultUserProfile);
    }

    @Test
    void UserProfile_getFriendsSortByAge() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<UserProfile> resultUserProfiles = userProfileDao.getFriendsSortByAge(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getFriend(email).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getFriend(email));
    }

    @Test
    void UserProfile_getFriendsSortByAge_wrongEmail() {
        List<UserProfile> resultUserProfiles = userProfileDao.getFriendsSortByAge(EMAIL, FIRST_RESULT, MAX_RESULT);
        Assertions.assertTrue(resultUserProfiles.isEmpty());
    }

    @Test
    void UserProfile_getFriendsSortByName() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<UserProfile> resultUserProfiles = userProfileDao.getFriendsSortByName(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getFriend(email).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getFriend(email));
    }

    @Test
    void UserProfile_getFriendsSortByName_wrongEmail() {
        List<UserProfile> resultUserProfiles = userProfileDao.getFriendsSortByName(EMAIL, FIRST_RESULT, MAX_RESULT);
        Assertions.assertTrue(resultUserProfiles.isEmpty());
    }

    @Test
    void UserProfile_getFriendsSortByNumberOfFriends() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<UserProfile> resultUserProfiles = userProfileDao.getFriendsSortByNumberOfFriends(
                email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getFriend(email).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getFriend(email));
    }

    @Test
    void UserProfile_getFriendsSortByNumberOfFriends_wrongEmail() {
        List<UserProfile> resultUserProfiles = userProfileDao.getFriendsSortByNumberOfFriends(
                EMAIL, FIRST_RESULT, MAX_RESULT);
        Assertions.assertTrue(resultUserProfiles.isEmpty());
    }

    @Test
    void UserProfile_getFriends() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        List<UserProfile> resultUserProfiles = userProfileDao.getFriends(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), testDataUtil.getFriend(email).size());
        Assertions.assertEquals(resultUserProfiles, testDataUtil.getFriend(email));
    }

    @Test
    void UserProfile_getFriends_wrongEmail() {
        List<UserProfile> resultUserProfiles = userProfileDao.getFriends(EMAIL, FIRST_RESULT, MAX_RESULT);
        Assertions.assertTrue(resultUserProfiles.isEmpty());
    }

    @Test
    void UserProfile_getFriend() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        UserProfile friend = testDataUtil.getUserProfiles().get(ArrayIndex.THIRD_INDEX_OF_ARRAY.index);

        UserProfile resultUserProfile = userProfileDao.getFriend(email, friend.getId());
        Assertions.assertNotNull(resultUserProfile);
        Assertions.assertEquals(resultUserProfile, friend);
    }

    @Test
    void UserProfile_getFriend_wrongEmail() {
        UserProfile resultUserProfile = userProfileDao.getFriend(EMAIL, testDataUtil.getUsers().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNull(resultUserProfile);
    }

    @Test
    void UserProfile_getSignedFriends() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        List<UserProfile> friendsRequest = Collections.singletonList(testDataUtil.getUserProfiles().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index));

        List<UserProfile> resultUserProfiles = userProfileDao.getSignedFriends(email, FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultUserProfiles);
        Assertions.assertFalse(resultUserProfiles.isEmpty());
        Assertions.assertEquals(resultUserProfiles.size(), friendsRequest.size());
        Assertions.assertEquals(resultUserProfiles, friendsRequest);
    }

    @Test
    void UserProfile_getSignedFriend() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        UserProfile friendRequest = testDataUtil.getUserProfiles().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        UserProfile resultUserProfile = userProfileDao.getSignedFriend(email, friendRequest.getId());
        Assertions.assertNotNull(resultUserProfile);
        Assertions.assertEquals(resultUserProfile, friendRequest);
    }

    @Test
    void UserProfile_getFutureFriend() {
        String email = testDataUtil.getUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();
        UserProfile friendRequest = testDataUtil.getUserProfiles().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        UserProfile resultUserProfile = userProfileDao.getFutureFriend(email, friendRequest.getId());
        Assertions.assertNotNull(resultUserProfile);
        Assertions.assertEquals(resultUserProfile, friendRequest);
    }

}
