package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.dto.UserProfileForIdentificationDto;
import com.senla.socialnetwork.service.enumaration.UserProfileFriendSortParameter;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;

import java.util.Date;
import java.util.List;

public interface UserProfileService {
    UserProfileDto getUserProfile();

    void updateUserProfile(UserProfileDto userProfileDto);

    List<UserProfileForIdentificationDto> getUserProfiles(int firstResult, int maxResults);

    List<UserProfileForIdentificationDto> getSortUserProfiles(UserProfileSortParameter sortParameter,
                                                              int firstResult,
                                                              int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesByLocationId(Long locationId, int firstResult, int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesBySchoolId(Long schoolId, int firstResult, int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesByUniversityId(Long universityId,
                                                                        int firstResult,
                                                                        int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesFilteredByAge(Date startPeriodDate,
                                                                       Date endPeriodDate,
                                                                       int firstResult,
                                                                       int maxResults);

    UserProfileDto getFriendNearestDateOfBirth();

    UserProfileDto getUserProfileDetails(Long userProfileId);

    List<UserProfileForIdentificationDto> getUserProfileFriends(int firstResult, int maxResults);

    List<UserProfileForIdentificationDto> getSortedFriendsOfUserProfile(UserProfileFriendSortParameter sortParameter,
                                                                        int firstResult,
                                                                        int maxResults);

    List<UserProfileForIdentificationDto> getUserProfileSignedFriends(int firstResult, int maxResults);

    void sendAFriendRequest(Long userProfileId);

    void confirmFriend(Long userProfileId);

    void removeUserFromFriends(Long userProfileId);

    void deleteUserProfile(Long userProfileId);

    List<PrivateMessageDto> getDialogue(Long userProfileId, int firstResult, int maxResults);

}
