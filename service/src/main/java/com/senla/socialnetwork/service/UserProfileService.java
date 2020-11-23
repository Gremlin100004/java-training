package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

public interface UserProfileService {
    UserProfileDto getUserProfile(HttpServletRequest request);

    void updateUserProfile(UserProfileDto userProfileDto);

    List<UserProfileDto> getUserProfiles(int firstResult, int maxResults);

    List<UserProfileDto> getSortUserProfiles(UserProfileSortParameter sortParameter, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfiles(LocationDto locationDto, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfiles(SchoolDto schoolDto, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfiles(UniversityDto universityDto, int firstResult, int maxResults);

    List<UserProfileDto> getUserProfilesFilteredByAge(Date startPeriodDate,
                                                      Date endPeriodDate,
                                                      int firstResult,
                                                      int maxResults);

    UserProfileDto getFriendNearestDateOfBirth(HttpServletRequest request);

    UserProfileDto getUserProfileFriend(HttpServletRequest request, Long userProfileId);

    List<UserProfileDto> getUserProfileFriends(HttpServletRequest request, int firstResult, int maxResults);

    List<UserProfileDto> getSortedFriendsOfUserProfile(HttpServletRequest request,
                                                       UserProfileSortParameter sortParameter,
                                                       int firstResult,
                                                       int maxResults);

    List<UserProfileDto> getUserProfileSignedFriends(HttpServletRequest request, int firstResult, int maxResults);

    void sendAFriendRequest(HttpServletRequest request, Long userProfileId);

    void confirmFriend(HttpServletRequest request, Long userProfileId);

    void removeUserFromFriends(HttpServletRequest request, Long userProfileId);

    void deleteUserProfile(Long userProfileId);

    List<PrivateMessageDto> getPrivateMessages(HttpServletRequest request, int firstResult, int maxResults);

    List<PrivateMessageDto> getDialogue(HttpServletRequest request, Long userProfileId, int firstResult, int maxResults);

    List<PrivateMessageDto> getUnreadMessages(HttpServletRequest request, int firstResult, int maxResults);

    List<PublicMessageDto> getFriendsPublicMessages(HttpServletRequest request, int firstResult, int maxResults);

    List<PublicMessageDto> getPublicMessages(HttpServletRequest request, int firstResult, int maxResults);

}
