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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/userProfiles")
@Api(tags = "User Profiles")
@NoArgsConstructor
public class UserProfileController {
    public static final int OK = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String START_PERIOD_DATE_DESCRIPTION = "Parameter with start date";
    public static final String END_PERIOD_DATE_DESCRIPTION = "Parameter with end date";
    public static final String UNIVERSITY_DTO_DESCRIPTION = "DTO university object";
    public static final String SCHOOL_DTO_DESCRIPTION = " DTO school object";
    public static final String LOCATION_DTO_DESCRIPTION = " DTO location object";
    public static final String USER_PROFILE_DTO_DESCRIPTION = "DTO user profile object";
    public static final String SORT_PARAMETER_DESCRIPTION = "Parameter to select sorting";
    public static final String USER_PROFILE_ID_DESCRIPTION = "User profile id";
    public static final String RETURN_LIST_OF_USER_PROFILES_OK_MESSAGE = "Successfully retrieved list of "
       + "users profiles";
    public static final String RETURN_USER_PROFILE_OK_MESSAGE = "Successfully retrieved user profile";
    public static final String RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE = "Successfully retrieved list of private "
       + "messages";
    public static final String RETURN_LIST_OF_PUBLIC_MESSAGES_OK_MESSAGE = "Successfully retrieved list of public"
       + " messages";
    public static final String UPDATE_USER_PROFILE_OK_MESSAGE = "Profile updated successfully";
    public static final String SEND_A_FRIEND_REQUEST_OK_MESSAGE = "Friend request sent successfully";
    public static final String CONFIRM_FRIEND_OK_MESSAGE = "User added as friend successfully";
    public static final String REMOVE_USER_FROM_FRIENDS_OK_MESSAGE = "User removed from friends successfully";
    public static final String DELETE_USER_PROFILE_OK_MESSAGE = "User deleted successfully";
    public static final String GET_USER_PROFILE_DESCRIPTION = "This method is used to get the profile of a given user";
    public static final String GET_USER_PROFILES_DESCRIPTION = "This method is used to get users profiles. Users "
       + "profiles can be filtered by age, university, school or location";
    public static final String UPDATE_USER_PROFILE_DESCRIPTION = "This method is used to update the profile of a "
       + "given user";
    public static final String GET_SORT_USER_PROFILES_DESCRIPTION = "This method is used to get users profiles. "
       + "Users profiles can be sorted by surname or registration date";
    public static final String GET_FRIEND_NEAREST_DATE_OF_BIRTH_DESCRIPTION = "This method is used to get a friend "
       + "who will have a birthday soon";
    public static final String GET_USER_PROFILE_FRIEND_DESCRIPTION = "This method is used to get a friend profile";
    public static final String GET_USER_PROFILE_FRIENDS_DESCRIPTION = "This method is used to get friends profiles";
    public static final String GET_SORTED_FRIENDS_OF_USER_PROFILE_DESCRIPTION = "This method is used to get friends "
       + "profiles. Friends profiles can be sorted by birthday, name or number of friends";
    public static final String GET_USER_PROFILE_SIGNED_FRIENDS_DESCRIPTION = "This method is used to get a list of "
       + "friends subscribed to this profile";
    public static final String SEND_A_FRIEND_REQUEST_DESCRIPTION = "This method is used to send a friend request";
    public static final String CONFIRM_FRIEND_DESCRIPTION = "This method used you to confirm the user as a friend";
    public static final String REMOVE_USER_FROM_FRIENDS_DESCRIPTION = "This method is used to remove a user from"
       + " friends";
    public static final String DELETE_USER_PROFILE_DESCRIPTION = "This method is used to delete a record from the "
       + "database by the admin";
    public static final String GET_PRIVATE_MESSAGES_DESCRIPTION = "This method is used to get private messages of "
       + "this user";
    public static final String GET_DIALOGUE_DESCRIPTION = "This method is used to get dialog with user";
    public static final String GET_UNREAD_MESSAGES_DESCRIPTION = "This method is used to get unread messages";
    public static final String GET_FRIENDS_PUBLIC_MESSAGES_DESCRIPTION = "This method is used to get public messages "
       + "of friends";
    public static final String GET_PUBLIC_MESSAGES_DESCRIPTION = "This method is used to get public messages of a "
       + "given user";
    @Autowired
    private UserProfileService userProfileService;

    @GetMapping
    @ApiOperation(value = GET_USER_PROFILES_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_USER_PROFILES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<UserProfileDto> getUserProfiles(@ApiParam(value = START_PERIOD_DATE_DESCRIPTION)
                                                @RequestParam(required = false) Date startPeriodDate,
                                                @ApiParam(value = END_PERIOD_DATE_DESCRIPTION)
                                                @RequestParam(required = false) Date endPeriodDate,
                                                @ApiParam(value = UNIVERSITY_DTO_DESCRIPTION)
                                                @RequestBody (required = false) @Valid UniversityDto universityDto,
                                                @ApiParam(value = SCHOOL_DTO_DESCRIPTION)
                                                @RequestBody (required = false) @Valid SchoolDto schoolDto,
                                                @ApiParam(value = LOCATION_DTO_DESCRIPTION)
                                                @RequestBody (required = false) @Valid LocationDto locationDto,
                                                @ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                                @RequestParam int firstResult,
                                                @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
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
    @ApiOperation(value = GET_USER_PROFILE_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_USER_PROFILE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public UserProfileDto getUserProfile(HttpServletRequest request) {
        return userProfileService.getUserProfile(request);
    }

    @PutMapping
    @ApiOperation(value = UPDATE_USER_PROFILE_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_USER_PROFILE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateUserProfile(@ApiParam(value = USER_PROFILE_DTO_DESCRIPTION)
                                              @RequestBody @Valid UserProfileDto userProfileDto) {
        userProfileService.updateUserProfile(userProfileDto);
        return new ClientMessageDto(UPDATE_USER_PROFILE_OK_MESSAGE);
    }

    @GetMapping("/sorting")
    @ApiOperation(value = GET_SORT_USER_PROFILES_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_USER_PROFILE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<UserProfileDto> getSortUserProfiles(@ApiParam(value = SORT_PARAMETER_DESCRIPTION)
                                                    @RequestParam UserProfileSortParameter sortParameter,
                                                    @ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                              example = FIRST_RESULT_EXAMPLE)
                                                    @RequestParam int firstResult,
                                                    @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                              example = MAX_RESULTS_EXAMPLE)
                                                    @RequestParam int maxResults) {
        return userProfileService.getSortUserProfiles(sortParameter, firstResult, maxResults);
    }

    @GetMapping("/friends/birthday")
    @ApiOperation(value = GET_FRIEND_NEAREST_DATE_OF_BIRTH_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_USER_PROFILE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public UserProfileDto getFriendNearestDateOfBirth(HttpServletRequest request) {
        return userProfileService.getFriendNearestDateOfBirth(request);
    }

    @GetMapping("/friends/{id}")
    @ApiOperation(value = GET_USER_PROFILE_FRIEND_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_USER_PROFILE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public UserProfileDto getUserProfileFriend(@ApiParam(value = USER_PROFILE_ID_DESCRIPTION)
                                               @PathVariable("id") Long userProfileId,
                                               HttpServletRequest request) {
        return userProfileService.getUserProfileFriend(request, userProfileId);
    }

    @GetMapping("/friends")
    @ApiOperation(value = GET_USER_PROFILE_FRIENDS_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_USER_PROFILES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<UserProfileDto> getUserProfileFriends(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                example = FIRST_RESULT_EXAMPLE)
                                                      @RequestParam int firstResult,
                                                      @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                example = MAX_RESULTS_EXAMPLE)
                                                      @RequestParam int maxResults,
                                                      HttpServletRequest request) {
        return userProfileService.getUserProfileFriends(request, firstResult, maxResults);
    }

    @GetMapping("/friends/sorting")
    @ApiOperation(value = GET_SORTED_FRIENDS_OF_USER_PROFILE_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_USER_PROFILES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<UserProfileDto> getSortedFriendsOfUserProfile(@ApiParam(value = SORT_PARAMETER_DESCRIPTION)
                                                              @RequestParam UserProfileSortParameter sortParameter,
                                                              @ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                        example = FIRST_RESULT_EXAMPLE)
                                                              @RequestParam int firstResult,
                                                              @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                        example = MAX_RESULTS_EXAMPLE)
                                                              @RequestParam int maxResults,
                                                              HttpServletRequest request) {
        return userProfileService.getSortedFriendsOfUserProfile(request, sortParameter, firstResult, maxResults);
    }

    @GetMapping("/friends/requests")
    @ApiOperation(value = GET_USER_PROFILE_SIGNED_FRIENDS_DESCRIPTION, response = UserProfileDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_USER_PROFILES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<UserProfileDto> getUserProfileSignedFriends(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                      example = FIRST_RESULT_EXAMPLE)
                                                            @RequestParam int firstResult,
                                                            @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                      example = MAX_RESULTS_EXAMPLE)
                                                            @RequestParam int maxResults,
                                                            HttpServletRequest request) {
        return userProfileService.getUserProfileSignedFriends(request, firstResult, maxResults);
    }

    @PutMapping("/friends/requests/{id}")
    @ApiOperation(value = SEND_A_FRIEND_REQUEST_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = SEND_A_FRIEND_REQUEST_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto sendAFriendRequest(@ApiParam(value = USER_PROFILE_ID_DESCRIPTION)
                                               @PathVariable("id") Long userProfileId,
                                               HttpServletRequest request) {
        userProfileService.sendAFriendRequest(request, userProfileId);
        return new ClientMessageDto(SEND_A_FRIEND_REQUEST_OK_MESSAGE);
    }

    @PutMapping("/friends/positiveRequests/{id}")
    @ApiOperation(value = CONFIRM_FRIEND_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = CONFIRM_FRIEND_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto confirmFriend(@ApiParam(value = USER_PROFILE_ID_DESCRIPTION)
                                          @PathVariable("id") Long userProfileId,
                                          HttpServletRequest request) {
        userProfileService.sendAFriendRequest(request, userProfileId);
        return new ClientMessageDto(CONFIRM_FRIEND_OK_MESSAGE);
    }

    @PutMapping("/friends/negativeRequests/{id}")
    @ApiOperation(value = REMOVE_USER_FROM_FRIENDS_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = REMOVE_USER_FROM_FRIENDS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto removeUserFromFriends(@ApiParam(value = USER_PROFILE_ID_DESCRIPTION)
                                                  @PathVariable("id") Long userProfileId,
                                                  HttpServletRequest request) {
        userProfileService.removeUserFromFriends(request, userProfileId);
        return new ClientMessageDto(REMOVE_USER_FROM_FRIENDS_OK_MESSAGE);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_USER_PROFILE_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_USER_PROFILE_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteUserProfile(@ApiParam(value = USER_PROFILE_ID_DESCRIPTION)
                                              @PathVariable("id") Long userProfileId) {
        userProfileService.deleteUserProfile(userProfileId);
        return new ClientMessageDto(DELETE_USER_PROFILE_OK_MESSAGE);
    }

    @GetMapping("/privateMessages")
    @ApiOperation(value = GET_PRIVATE_MESSAGES_DESCRIPTION, response = PrivateMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PrivateMessageDto> getPrivateMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                example = FIRST_RESULT_EXAMPLE)
                                                      @RequestParam int firstResult,
                                                      @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                example = MAX_RESULTS_EXAMPLE)
                                                      @RequestParam int maxResults,
                                                      HttpServletRequest request) {
        return userProfileService.getPrivateMessages(request, firstResult, maxResults);
    }

    @GetMapping("/{id}/privateMessages")
    @ApiOperation(value = GET_DIALOGUE_DESCRIPTION, response = PrivateMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PrivateMessageDto> getDialogue(@ApiParam(value = USER_PROFILE_ID_DESCRIPTION)
                                               @PathVariable("id") Long userProfileId,
                                               @ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                         example = FIRST_RESULT_EXAMPLE)
                                               @RequestParam int firstResult,
                                               @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                         example = MAX_RESULTS_EXAMPLE)
                                               @RequestParam int maxResults,
                                               HttpServletRequest request) {
        return userProfileService.getDialogue(request, userProfileId, firstResult, maxResults);
    }
    @GetMapping("/privateMessages/unreadMessages")
    @ApiOperation(value = GET_UNREAD_MESSAGES_DESCRIPTION, response = PrivateMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PRIVATE_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PrivateMessageDto> getUnreadMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                               example = FIRST_RESULT_EXAMPLE)
                                                     @RequestParam int firstResult,
                                                     @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                               example = MAX_RESULTS_EXAMPLE)
                                                     @RequestParam int maxResults,
                                                     HttpServletRequest request) {
        return userProfileService.getUnreadMessages(request, firstResult, maxResults);
    }

    @GetMapping("/friends/publicMessages")
    @ApiOperation(value = GET_FRIENDS_PUBLIC_MESSAGES_DESCRIPTION, response = PublicMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PUBLIC_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PublicMessageDto> getFriendsPublicMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                     example = FIRST_RESULT_EXAMPLE)
                                                           @RequestParam int firstResult,
                                                           @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                     example = MAX_RESULTS_EXAMPLE)
                                                           @RequestParam int maxResults,
                                                           HttpServletRequest request) {
        return userProfileService.getFriendsPublicMessages(request, firstResult, maxResults);
    }

    @GetMapping("/publicMessages")
    @ApiOperation(value = GET_PUBLIC_MESSAGES_DESCRIPTION, response = PublicMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_PUBLIC_MESSAGES_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<PublicMessageDto> getPublicMessages(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                              example = FIRST_RESULT_EXAMPLE)
                                                    @RequestParam int firstResult,
                                                    @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                              example = MAX_RESULTS_EXAMPLE)
                                                    @RequestParam int maxResults,
                                                    HttpServletRequest request) {
        return userProfileService.getPublicMessages(request, firstResult, maxResults);
    }

}
