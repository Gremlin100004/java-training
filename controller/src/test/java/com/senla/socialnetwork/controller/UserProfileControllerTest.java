package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.*;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.UserProfileDto;
import com.senla.socialnetwork.dto.UserProfileForIdentificationDto;
import com.senla.socialnetwork.service.PublicMessageService;
import com.senla.socialnetwork.service.UserProfileService;
import com.senla.socialnetwork.service.enumaration.UserProfileFriendSortParameter;
import com.senla.socialnetwork.service.enumaration.UserProfileSortParameter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebMvcTest(controllers = UserProfileController.class)
public class UserProfileControllerTest extends AbstractControllerTest {
    private static final String USER_PROFILE_ENDPOINT = "/userProfiles";
    private static final String NEAREST_BIRTHDAY_ENDPOINT = "/friends/birthday";
    private static final String SIGNED_FRIENDS_ENDPOINT = "/friends/requests";
    private static final String FRIENDS_OF_USER_PROFILE_ENDPOINT = "/friends";
    private static final String FRIEND_REQUEST_ENDPOINT = "/friends/requests/";
    private static final String CONFIRM_FRIEND_ENDPOINT = "/friends/positiveRequests/";
    private static final String REMOVE_USER_FROM_FRIENDS_ENDPOINT = "/friends/negativeRequests/";
    private static final String DIALOG_ENDPOINT = "/privateMessages";
    private static final String FRIENDS_PUBLIC_MESSAGES_ENDPOINT = "/friends/publicMessages";
    private static final String PATH_SEPARATOR = "/";
    private static final String START_PERIOD_DATE_PARAM = "1990-07-27";
    private static final String START_PERIOD_PARAM_NAME = "startPeriodDate";
    private static final String END_PERIOD_DATE_PARAM = "2004-11-13";
    private static final String END_PERIOD_DATE_PARAM_NAME = "endPeriodDate";
    private static final String UNIVERSITY_ID_PARAM_NAME = "universityId";
    private static final String SCHOOL_ID_PARAM_NAME = "schoolId";
    private static final String LOCATION_ID_PARAM_NAME = "locationId";
    private static final String SORT_PARAMETER_NAME = "sortParameter";
    private static final String UPDATE_USER_PROFILE_OK_MESSAGE = "Profile updated successfully";
    public static final String SEND_A_FRIEND_REQUEST_OK_MESSAGE = "Friend request sent successfully";
    public static final String CONFIRM_FRIEND_OK_MESSAGE = "User added as friend successfully";
    public static final String REMOVE_USER_FROM_FRIENDS_OK_MESSAGE = "User removed from friends successfully";
    public static final String DELETE_USER_PROFILE_OK_MESSAGE = "User deleted successfully";
    public static final String WRONG_PARAMETERS_ERROR_MESSAGE = "Wrong request parameters";
    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private UserProfileController userProfileController;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private PublicMessageService publicMessageService;

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfiles() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getUserProfiles(
                FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfiles(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfiles_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfiles(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfiles_filteredByAge() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getUserProfilesFilteredByAge(
                getDate(START_PERIOD_DATE_PARAM), getDate(END_PERIOD_DATE_PARAM), FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(START_PERIOD_PARAM_NAME, START_PERIOD_DATE_PARAM)
                .param(END_PERIOD_DATE_PARAM_NAME, END_PERIOD_DATE_PARAM)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfilesFilteredByAge(
                getDate(START_PERIOD_DATE_PARAM), getDate(END_PERIOD_DATE_PARAM), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfiles_filteredByAge_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(START_PERIOD_PARAM_NAME, START_PERIOD_DATE_PARAM)
                .param(END_PERIOD_DATE_PARAM_NAME, END_PERIOD_DATE_PARAM)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfilesFilteredByAge(
                getDate(START_PERIOD_DATE_PARAM), getDate(END_PERIOD_DATE_PARAM), FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfiles_byUniversityId() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getUserProfilesByUniversityId(
                UniversityTestData.getUniversityId(), FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(UNIVERSITY_ID_PARAM_NAME, String.valueOf(UniversityTestData.getUniversityId()))
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfilesByUniversityId(
                UniversityTestData.getUniversityId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfiles_byUniversityId_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(UNIVERSITY_ID_PARAM_NAME, String.valueOf(UniversityTestData.getUniversityId()))
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfilesByUniversityId(
                UniversityTestData.getUniversityId(), FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfiles_bySchoolId() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getUserProfilesBySchoolId(
                SchoolTestData.getSchoolId(), FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(SCHOOL_ID_PARAM_NAME, String.valueOf(SchoolTestData.getSchoolId()))
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfilesBySchoolId(
                SchoolTestData.getSchoolId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfiles_bySchoolId_withoutUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(SCHOOL_ID_PARAM_NAME, String.valueOf(SchoolTestData.getSchoolId()))
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfilesBySchoolId(
                SchoolTestData.getSchoolId(), FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfiles_sort() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getSortUserProfiles(
                UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(SORT_PARAMETER_NAME, UserProfileSortParameter.BY_SURNAME.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getSortUserProfiles(
                UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfiles_sort_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(SORT_PARAMETER_NAME, UserProfileSortParameter.BY_SURNAME.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getSortUserProfiles(
                UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfiles_byLocationId() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getUserProfilesByLocationId(
                LocationTestData.getLocationId(), FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(LOCATION_ID_PARAM_NAME, String.valueOf(LocationTestData.getLocationId()))
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfilesByLocationId(
                LocationTestData.getLocationId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfiles_byLocationId_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT)
                .param(LOCATION_ID_PARAM_NAME, String.valueOf(LocationTestData.getLocationId()))
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfilesByLocationId(
                LocationTestData.getLocationId(), FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfiles_wrongParameters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get(USER_PROFILE_ENDPOINT)
                            .param(LOCATION_ID_PARAM_NAME, String.valueOf(LocationTestData.getLocationId()))
                            .param(SORT_PARAMETER_NAME, UserProfileSortParameter.BY_SURNAME.toString())
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(WRONG_PARAMETERS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(userProfileService, Mockito.never()).getSortUserProfiles(
            UserProfileSortParameter.BY_SURNAME, FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_updateUserProfile() throws Exception {
        UserProfileDto userProfileDto = UserProfileTestData.getUserProfileDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_USER_PROFILE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).updateUserProfile(
                ArgumentMatchers.any(UserProfileDto.class));
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_updateUserProfile_withoutUser() throws Exception {
        UserProfileDto userProfileDto = UserProfileTestData.getUserProfileDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfileDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).updateUserProfile(
                ArgumentMatchers.any(UserProfileDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getFriendNearestDateOfBirth() throws Exception {
        UserProfileDto userProfileDto = UserProfileTestData.getUserProfileDto();
        Mockito.doReturn(userProfileDto).when(userProfileService).getFriendNearestDateOfBirth();

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + NEAREST_BIRTHDAY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        userProfileDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getFriendNearestDateOfBirth();
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getFriendNearestDateOfBirth_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + NEAREST_BIRTHDAY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getFriendNearestDateOfBirth();
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfileDetails() throws Exception {
        UserProfileDto userProfileDto = UserProfileTestData.getUserProfileDto();
        Mockito.doReturn(userProfileDto).when(userProfileService).getUserProfileDetails(userProfileDto.getId());

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + PATH_SEPARATOR + userProfileDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        userProfileDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfileDetails(
                userProfileDto.getId());
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfileDetails_withoutUser() throws Exception {
        UserProfileDto userProfileDto = UserProfileTestData.getUserProfileDto();

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + PATH_SEPARATOR + userProfileDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfileDetails(
                userProfileDto.getId());
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getSortedFriendsOfUserProfile() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getUserProfileFriends(
                FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + FRIENDS_OF_USER_PROFILE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfileFriends(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getSortedFriendsOfUserProfile_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + FRIENDS_OF_USER_PROFILE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfileFriends(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getSortedFriendsOfUserProfile_withSortParameter() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getSortedFriendsOfUserProfile(
                UserProfileFriendSortParameter.BY_BIRTHDAY, FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + FRIENDS_OF_USER_PROFILE_ENDPOINT)
                .param(SORT_PARAMETER_NAME, UserProfileFriendSortParameter.BY_BIRTHDAY.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getSortedFriendsOfUserProfile(
                UserProfileFriendSortParameter.BY_BIRTHDAY, FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getUserProfileSignedFriends() throws Exception {
        List<UserProfileForIdentificationDto> usersProfilesForIdentificationDto = UserProfileTestData
                .getTestUsersProfilesForIdentificationDto();
        Mockito.doReturn(usersProfilesForIdentificationDto).when(userProfileService).getUserProfileSignedFriends(
                FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + SIGNED_FRIENDS_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        usersProfilesForIdentificationDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getUserProfileSignedFriends(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getUserProfileSignedFriends_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + SIGNED_FRIENDS_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getUserProfileSignedFriends(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_sendAFriendRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT + FRIEND_REQUEST_ENDPOINT + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SEND_A_FRIEND_REQUEST_OK_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).sendAFriendRequest(
                UserProfileTestData.getFriendId());
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_sendAFriendRequest_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT + FRIEND_REQUEST_ENDPOINT + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).sendAFriendRequest(
                UserProfileTestData.getFriendId());
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_confirmFriend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT + CONFIRM_FRIEND_ENDPOINT + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(CONFIRM_FRIEND_OK_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).confirmFriend(
                UserProfileTestData.getFriendId());
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_confirmFriend_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT + CONFIRM_FRIEND_ENDPOINT + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).confirmFriend(
                UserProfileTestData.getFriendId());
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_removeUserFromFriends() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT + REMOVE_USER_FROM_FRIENDS_ENDPOINT + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(REMOVE_USER_FROM_FRIENDS_OK_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).removeUserFromFriends(
                UserProfileTestData.getFriendId());
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_removeUserFromFriends_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_PROFILE_ENDPOINT + REMOVE_USER_FROM_FRIENDS_ENDPOINT + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).removeUserFromFriends(
                UserProfileTestData.getFriendId());
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void UserProfileController_deleteUserProfile() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(USER_PROFILE_ENDPOINT + PATH_SEPARATOR + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(DELETE_USER_PROFILE_OK_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).deleteUserProfile(
                UserProfileTestData.getFriendId());
        Mockito.reset(userProfileService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_deleteUserProfile_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(USER_PROFILE_ENDPOINT + PATH_SEPARATOR + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(userProfileService, Mockito.never()).deleteUserProfile(
                UserProfileTestData.getFriendId());
    }

    @Test
    void UserProfileController_deleteUserProfile_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(USER_PROFILE_ENDPOINT + PATH_SEPARATOR + UserProfileTestData.getFriendId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).deleteUserProfile(
                UserProfileTestData.getFriendId());
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getDialogue() throws Exception {
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessagesDto).when(userProfileService).getDialogue(
                UserProfileTestData.getUserProfileId(), FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + PATH_SEPARATOR + UserProfileTestData.getUserProfileId()
                    + DIALOG_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        privateMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userProfileService, Mockito.times(1)).getDialogue(
                UserProfileTestData.getUserProfileId(), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userProfileService);
    }

    @Test
    void UserProfileController_getDialogue_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + PATH_SEPARATOR + UserProfileTestData.getUserProfileId()
                        + DIALOG_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userProfileService, Mockito.never()).getDialogue(
                UserProfileTestData.getUserProfileId(), FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserProfileController_getFriendsPublicMessages() throws Exception {
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessagesDto).when(publicMessageService).getFriendsPublicMessages(
                FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + FRIENDS_PUBLIC_MESSAGES_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        privateMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageService, Mockito.times(1)).getFriendsPublicMessages(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(publicMessageService);
    }

    @Test
    void UserProfileController_getFriendsPublicMessages_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_PROFILE_ENDPOINT + FRIENDS_PUBLIC_MESSAGES_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).getFriendsPublicMessages(
                FIRST_RESULT, MAX_RESULTS);
    }

    private Date getDate(String date){
        try {
            return DATE_FORMATTER.parse(date);
        } catch (ParseException exception) {
            return null;
        }
    }

}
