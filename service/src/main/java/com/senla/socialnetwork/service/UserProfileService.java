package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.dto.UserProfileForIdentificationDto;
import com.senla.socialnetwork.service.enumaration.UserProfileFriendSortParameter;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface UserProfileService {
    UserProfileDto getUserProfile(HttpServletRequest request);

    void updateUserProfile(UserProfileDto userProfileDto, HttpServletRequest request);

    List<UserProfileForIdentificationDto> getUserProfiles(int firstResult, int maxResults);

    List<UserProfileForIdentificationDto> getSortUserProfiles(UserProfileSortParameter sortParameter,
                                                              int firstResult,
                                                              int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesByLocationId(Long locationId,
                                                                      int firstResult,
                                                                      int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesBySchoolId(Long schoolId,
                                                                    int firstResult,
                                                                    int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesByUniversityId(Long universityId,
                                                                        int firstResult,
                                                                        int maxResults);

    List<UserProfileForIdentificationDto> getUserProfilesFilteredByAge(Date startPeriodDate,
                                                                       Date endPeriodDate,
                                                                       int firstResult,
                                                                       int maxResults);

    UserProfileForIdentificationDto getFriendNearestDateOfBirth(HttpServletRequest request);

    UserProfileDto getUserProfileDetails(Long userProfileId);

    List<UserProfileForIdentificationDto> getUserProfileFriends(HttpServletRequest request,
                                                                int firstResult,
                                                                int maxResults);

    List<UserProfileForIdentificationDto> getSortedFriendsOfUserProfile(HttpServletRequest request,
                                                                        UserProfileFriendSortParameter sortParameter,
                                                                        int firstResult,
                                                                        int maxResults);

    List<UserProfileForIdentificationDto> getUserProfileSignedFriends(HttpServletRequest request,
                                                                      int firstResult,
                                                                      int maxResults);

    void sendAFriendRequest(HttpServletRequest request, Long userProfileId);

    void confirmFriend(HttpServletRequest request, Long userProfileId);

    void removeUserFromFriends(HttpServletRequest request, Long userProfileId);

    void deleteUserProfile(Long userProfileId);

    List<PrivateMessageDto> getDialogue(HttpServletRequest request,
                                        Long userProfileId,
                                        int firstResult,
                                        int maxResults);

}
