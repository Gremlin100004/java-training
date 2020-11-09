package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;

import java.sql.Date;
import java.util.List;

public interface UserProfileDao extends GenericDao<UserProfile, Long> {
    UserProfile findByEmail(String email);

    Location getLocation(String email);

    List<UserProfile> getUserProfilesSortByName(int firstResult, int maxResults);

    List<UserProfile> getUserProfilesSortByRegistrationDate(int firstResult, int maxResults);

    List<UserProfile> getUserProfilesSortByNumberOfFriends(int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByLocation(Location location, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredBySchool(School school, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByUniversity(University university, int firstResult, int maxResults);

    List<UserProfile> getUserProfilesFilteredByAge(
        Date startPeriodDate, Date endPeriodDate, int firstResult, int maxResults);

    UserProfile getNearestBirthdayByCurrentDate();

    UserProfile getNearestBirthdayFromTheBeginningOfTheYear();

    List<UserProfile> getFriends(String email, int firstResult, int maxResults);
}
