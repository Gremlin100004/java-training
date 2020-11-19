package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.exception.ControllerException;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PublicMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.service.UserProfileService;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/userProfiles")
@NoArgsConstructor
public class UserProfileController {
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    public List<UserProfileDto> getUserProfiles(@RequestParam(required = false) Date startPeriodDate,
                                                @RequestParam(required = false) Date endPeriodDate,
                                                @RequestBody(required = false) UniversityDto universityDto,
                                                @RequestBody(required = false) SchoolDto schoolDto,
                                                @RequestBody(required = false) LocationDto locationDto,
                                                @RequestParam int firstResult,
                                                @RequestParam int maxResults) {
        if (startPeriodDate == null && endPeriodDate == null && universityDto == null && schoolDto == null
            && locationDto == null) {
            return userProfileService.getUserProfiles(firstResult, maxResults);
        } else if (startPeriodDate != null && endPeriodDate != null && universityDto == null && schoolDto == null
                   && locationDto == null) {
            return userProfileService.getUserProfilesFilteredByAge(
                startPeriodDate, endPeriodDate, firstResult, maxResults);
        } else if (startPeriodDate == null && endPeriodDate == null && universityDto != null && schoolDto == null
                   && locationDto == null) {
            return userProfileService.getUserProfiles(universityDto, firstResult, maxResults);
        } else if (startPeriodDate == null && endPeriodDate == null && universityDto == null && schoolDto != null
                   && locationDto == null) {
            return userProfileService.getUserProfiles(schoolDto, firstResult, maxResults);
        } else if (startPeriodDate == null && endPeriodDate == null && universityDto == null && schoolDto == null) {
            return userProfileService.getUserProfiles(locationDto, firstResult, maxResults);
        } else {
            throw new ControllerException("Wrong request parameters");
        }
    }

    @GetMapping("/own")
    public UserProfileDto getUserProfile(Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getUserProfile(email);
    }

    @PutMapping
    public ClientMessageDto updateUserProfile(@RequestBody UserProfileDto userProfileDto) {
        userProfileService.updateUserProfile(userProfileDto);
        return new ClientMessageDto("Profile updated successfully");
    }

    @GetMapping("/sorting")
    public List<UserProfileDto> getSortUserProfiles(@RequestParam UserProfileSortParameter sortParameter,
                                                    @RequestParam int firstResult,
                                                    @RequestParam int maxResults) {
        return userProfileService.getSortUserProfiles(sortParameter, firstResult, maxResults);
    }

    @GetMapping("/friends/birthday")
    public UserProfileDto getFriendNearestDateOfBirth(Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getFriendNearestDateOfBirth(email);
    }

    @GetMapping("/friends/{id}")
    public UserProfileDto getUserProfileFriend(@PathVariable("id") Long userProfileId, Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getUserProfileFriend(email, userProfileId);
    }

    @GetMapping("/friends")
    public List<UserProfileDto> getUserProfileFriends(@RequestParam int firstResult,
                                                      @RequestParam int maxResults,
                                                      Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getUserProfileFriends(email, firstResult, maxResults);
    }

    @GetMapping("/friends/sorting")
    public List<UserProfileDto> getSortedFriendsOfUserProfile(@RequestParam UserProfileSortParameter sortParameter,
                                                              @RequestParam int firstResult,
                                                              @RequestParam int maxResults,
                                                              Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getSortedFriendsOfUserProfile(email, sortParameter, firstResult, maxResults);
    }

    @GetMapping("/friends/requests")
    public List<UserProfileDto> getUserProfileSignedFriends(@RequestParam int firstResult,
                                                            @RequestParam int maxResults,
                                                            Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getUserProfileSignedFriends(email, firstResult, maxResults);
    }

    @PutMapping("/friends/requests/{id}")
    public ClientMessageDto sendAFriendRequest(@PathVariable("id") Long userProfileId,
                                               Authentication authentication) {
        String email = authentication.getName();
        userProfileService.sendAFriendRequest(email, userProfileId);
        return new ClientMessageDto("Friend request sent successfully");
    }

    @PutMapping("/friends/positiveRequests/{id}")
    public ClientMessageDto confirmFriend(@PathVariable("id") Long userProfileId,
                                          Authentication authentication) {
        String email = authentication.getName();
        userProfileService.sendAFriendRequest(email, userProfileId);
        return new ClientMessageDto("User added as friend successfully");
    }

    @PutMapping("/friends/negativeRequests/{id}")
    public ClientMessageDto removeUserFromFriends(@PathVariable("id") Long userProfileId,
                                                  Authentication authentication) {
        String email = authentication.getName();
        userProfileService.removeUserFromFriends(email, userProfileId);
        return new ClientMessageDto("User removed from friends successfully");
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteUserProfile(@PathVariable("id") Long userProfileId) {
        userProfileService.deleteUserProfile(userProfileId);
        return new ClientMessageDto("User deleted successfully");
    }

    @GetMapping("/privateMessages")
    public List<PrivateMessageDto> getPrivateMessages(@RequestParam int firstResult,
                                                      @RequestParam int maxResults,
                                                      Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getPrivateMessages(email, firstResult, maxResults);
    }

    @GetMapping("/{id}/privateMessages")
    public List<PrivateMessageDto> getDialogue(@PathVariable("id") Long userProfileId,
                                               @RequestParam int firstResult,
                                               @RequestParam int maxResults,
                                               Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getDialogue(email, userProfileId, firstResult, maxResults);
    }
    @GetMapping("/privateMessages/unreadMessages")
    public List<PrivateMessageDto> getUnreadMessages(@RequestParam int firstResult,
                                                     @RequestParam int maxResults,
                                                     Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getUnreadMessages(email, firstResult, maxResults);
    }

    @GetMapping("/friends/publicMessages")
    public List<PublicMessageDto> getFriendsPublicMessages(@RequestParam int firstResult,
                                                           @RequestParam int maxResults,
                                                           Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getFriendsPublicMessages(email, firstResult, maxResults);
    }

    @GetMapping("/publicMessages")
    public List<PublicMessageDto> getPublicMessages(@RequestParam int firstResult,
                                                    @RequestParam int maxResults,
                                                    Authentication authentication) {
        String email = authentication.getName();
        return userProfileService.getPublicMessages(email, firstResult, maxResults);
    }

}
