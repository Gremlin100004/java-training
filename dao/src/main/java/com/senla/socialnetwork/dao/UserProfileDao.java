package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;

import java.sql.Date;
import java.util.List;

public interface UserProfileDao extends GenericDao<UserProfile, Long> {
    UserProfile findByEmail(String email);

    List<UserProfile> getCommunityUsers(Long communityId);

    List<UserProfile> getUserProfilesSortBySurname(int firstResult, int maxResults);

    List<UserProfile> getUserProfilesSortByRegistrationDate(int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByLocation(Location location, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredBySchool(School school, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByUniversity(University university, int firstResult, int maxResults);

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
