package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.model.UserProfile;

import java.util.Date;
import java.util.List;

public interface UserProfileDao extends GenericDao<UserProfile, Long> {
    UserProfile findByEmail(String email);

    List<UserProfile> getUserProfilesSortBySurname(int firstResult, int maxResults);

    List<UserProfile> getUserProfilesSortByRegistrationDate(int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByLocation(Long locationId, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredBySchool(Long schoolId, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByUniversity(Long universityId, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByAge(Date startPeriodDate,
                                                   Date endPeriodDate,
                                                   int firstResult,
                                                   int maxResults);

    UserProfile getNearestBirthdayByCurrentDate(String email);

    UserProfile getNearestBirthdayFromTheBeginningOfTheYear(String email);

    List<UserProfile> getFriendsSortByAge(String email, int firstResult, int maxResults);

    List<UserProfile> getFriendsSortByName(String email, int firstResult, int maxResults);

    List<UserProfile> getFriendsSortByNumberOfFriends(String email, int firstResult, int maxResults);

    List<UserProfile> getFriends(String email, int firstResult, int maxResults);

    List<UserProfile> getSignedFriends(String email, int firstResult, int maxResults);

    UserProfile getSignedFriend(String email, Long userProfileId);

    UserProfile getFutureFriend(String email, Long userProfileId);

    UserProfile getFriend(String email, Long userProfileId);

}
