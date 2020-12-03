package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.dto.UserProfileForIdentificationDto;
import com.senla.socialnetwork.service.enumaration.UserProfileFriendSortParameter;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface UserProfileService {
    UserProfileDto getUserProfile(HttpServletRequest request, SecretKey secretKey);

    void updateUserProfile(UserProfileDto userProfileDto,
                           HttpServletRequest request,
                           SecretKey secretKey);

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

    UserProfileForIdentificationDto getFriendNearestDateOfBirth(HttpServletRequest request,
                                                                SecretKey secretKey);

    UserProfileDto getUserProfileDetails(Long userProfileId);

    List<UserProfileForIdentificationDto> getUserProfileFriends(HttpServletRequest request,
                                                                int firstResult,
                                                                int maxResults,
                                                                SecretKey secretKey);

    List<UserProfileForIdentificationDto> getSortedFriendsOfUserProfile(HttpServletRequest request,
                                                                        UserProfileFriendSortParameter sortParameter,
                                                                        int firstResult,
                                                                        int maxResults,
                                                                        SecretKey secretKey);

    List<UserProfileForIdentificationDto> getUserProfileSignedFriends(HttpServletRequest request,
                                                                      int firstResult,
                                                                      int maxResults,
                                                                      SecretKey secretKey);

    void sendAFriendRequest(HttpServletRequest request, Long userProfileId, SecretKey secretKey);

    void confirmFriend(HttpServletRequest request, Long userProfileId, SecretKey secretKey);

    void removeUserFromFriends(HttpServletRequest request, Long userProfileId, SecretKey secretKey);

    void deleteUserProfile(Long userProfileId);

    List<PrivateMessageDto> getDialogue(HttpServletRequest request,
                                        Long userProfileId,
                                        int firstResult,
                                        int maxResults,
                                        SecretKey secretKey);

}
