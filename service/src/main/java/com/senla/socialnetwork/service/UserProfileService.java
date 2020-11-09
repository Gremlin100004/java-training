package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;

import java.sql.Date;
import java.util.List;

public interface UserProfileService {
    UserProfileDto getUserProfileFiltered(String email);

    UserProfileDto addUserProfile(UserProfileDto userProfileDto);

    void updateUserProfile(UserProfileDto userProfileDto);

    List<UserProfileDto> getUserProfiles();

    List<UserProfileDto> getSortUserProfiles(UserProfileSortParameter sortParameter, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfileFiltered(LocationDto locationDto, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfileFiltered(SchoolDto schoolDto, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfileFiltered(UniversityDto universityDto, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfilesFilteredByAge(Date startPeriodDate,
                                                      Date endPeriodDate,
                                                      int firstResult,
                                                      int maxResults);

    UserProfileDto getNearestDateOfBirth();

    List<UserProfileDto> getUserProfileFriends(String email, int firstResult, int maxResults);

}
