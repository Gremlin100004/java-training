package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;

import java.util.Date;
import java.util.List;

public interface UserProfileService {
    UserProfileDto getUserProfile(String email);

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

    UserProfileDto getFriendNearestDateOfBirth(String email);

    UserProfileDto getUserProfileFriend(String email, Long userProfileId);

    List<UserProfileDto> getUserProfileFriends(String email, int firstResult, int maxResults);

    List<UserProfileDto> getSortedFriendsOfUserProfile(String email,
                                                       UserProfileSortParameter sortParameter,
                                                       int firstResult,
                                                       int maxResults);

    List<UserProfileDto> getUserProfileSignedFriends(String email, int firstResult, int maxResults);

    void sendAFriendRequest(String email, Long userProfileId);

    void confirmFriend(String email, Long userProfileId);

    void removeUserFromFriends(String email, Long userProfileId);

    void deleteUserProfile(Long userProfileId);

    List<PrivateMessageDto> getPrivateMessages(String email, int firstResult, int maxResults);

    List<PrivateMessageDto> getDialogue(String email, Long userProfileId, int firstResult, int maxResults);

    List<PrivateMessageDto> getUnreadMessages(String email, int firstResult, int maxResults);

    List<PublicMessageDto> getFriendsPublicMessages(String email, int firstResult, int maxResults);

    List<PublicMessageDto> getPublicMessages(String email, int firstResult, int maxResults);
}
