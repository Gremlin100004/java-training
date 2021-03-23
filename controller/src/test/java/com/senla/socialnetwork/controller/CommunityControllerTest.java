package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.CommunityTestData;
import com.senla.socialnetwork.controller.config.PostTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.dto.CommunityForCreateDto;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;
import com.senla.socialnetwork.model.enumaration.CommunityType;
import com.senla.socialnetwork.service.CommunityService;
import com.senla.socialnetwork.service.enumaration.CommunitySortParameter;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = CommunityController.class)
public class CommunityControllerTest extends AbstractControllerTest {
    private static final String SORT_PARAMETER_NAME = "sortParameter";
    private static final String TYPE_PARAM_NAME = "communityType";
    private static final String COMMUNITY_ENDPOINT = "/communities";
    private static final String ALL_ENDPOINT = "/all";
    private static final String OWN_ENDPOINT = "/own";
    private static final String POSTS_ENDPOINT = "/posts";
    private static final String SUBSCRIPTIONS_ENDPOINT = "/subscriptions";
    private static final String UNSUBSCRIBE_ENDPOINT = "/subscriptions/changes";
    private static final String DELETE_ENDPOINT = "/changes";
    public static final String SUBSCRIBE_TO_COMMUNITY_OK_MESSAGE = "Subscription to community was successful";
    public static final String UPDATE_COMMUNITY_OK_MESSAGE = "Successful community update";
    public static final String DELETE_COMMUNITY_OK_MESSAGE = "Community deleted successfully";
    public static final String UNSUBSCRIBE_TO_COMMUNITY_OK_MESSAGE = "Community unsubscription was successful";
    @Autowired
    private CommunityController communityController;
    @Autowired
    private CommunityService communityService;

    @WithMockUser(roles="ADMIN")
    @Test
    void CommunityController_getAllCommunities() throws Exception {
        List<CommunityDto> communitiesDto = CommunityTestData.getCommunitiesDto();
        Mockito.doReturn(communitiesDto).when(communityService).getAllCommunities(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT + ALL_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communitiesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).getAllCommunities(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(communityService);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_getAllCommunities_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get(COMMUNITY_ENDPOINT + ALL_ENDPOINT)
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(communityService, Mockito.never()).getAllCommunities(
            FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void CommunityController_getAllCommunities_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT + ALL_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).getAllCommunities(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_getCommunities() throws Exception {
        List<CommunityDto> communitiesDto = CommunityTestData.getCommunitiesDto();
        Mockito.doReturn(communitiesDto).when(communityService).getCommunities(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communitiesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).getCommunities(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_getCommunities_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).getCommunities(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_getCommunities_sortByNumberOfSubscribers() throws Exception {
        List<CommunityDto> communitiesDto = CommunityTestData.getCommunitiesDto();
        Mockito.doReturn(communitiesDto).when(communityService).getCommunitiesSortiedByNumberOfSubscribers(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT)
                .param(SORT_PARAMETER_NAME, CommunitySortParameter.NUMBER_OF_SUBSCRIBERS.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communitiesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).getCommunitiesSortiedByNumberOfSubscribers(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_getCommunities_sortByNumberOfSubscribers_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT)
                .param(SORT_PARAMETER_NAME, CommunitySortParameter.NUMBER_OF_SUBSCRIBERS.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).getCommunitiesSortiedByNumberOfSubscribers(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_getCommunities_filteredByType() throws Exception {
        List<CommunityDto> communitiesDto = CommunityTestData.getCommunitiesDto();
        Mockito.doReturn(communitiesDto).when(communityService).getCommunitiesFilteredByType(
                CommunityType.BUSINESS, FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT)
                .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communitiesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).getCommunitiesFilteredByType(
                CommunityType.BUSINESS, FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_getCommunities_filteredByType_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT)
                .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).getCommunitiesFilteredByType(
                CommunityType.BUSINESS, FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_getOwnCommunities() throws Exception {
        List<CommunityDto> communitiesDto = CommunityTestData.getCommunitiesDto();
        Mockito.doReturn(communitiesDto).when(communityService).getOwnCommunities(
            FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                            .get(COMMUNITY_ENDPOINT + OWN_ENDPOINT)
                            .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communitiesDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).getOwnCommunities(
            FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_getOwnCommunities_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT + OWN_ENDPOINT)
                .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).getOwnCommunities(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_getCommunityPosts() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();
        List<CommunityDto> communitiesDto = CommunityTestData.getCommunitiesDto();
        Mockito.doReturn(communitiesDto).when(communityService).getCommunityPosts(
            communityId, FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                            .get(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + POSTS_ENDPOINT)
                            .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communitiesDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).getCommunityPosts(
            communityId, FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_getCommunityPosts_withoutUser() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();

        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + POSTS_ENDPOINT)
                .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).getCommunityPosts(
                communityId, FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_getSubscribedCommunities() throws Exception {
        List<CommunityDto> communitiesDto = CommunityTestData.getCommunitiesDto();
        Mockito.doReturn(communitiesDto).when(communityService).getSubscribedCommunities(
            FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                            .get(COMMUNITY_ENDPOINT + SUBSCRIPTIONS_ENDPOINT)
                            .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communitiesDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).getSubscribedCommunities(
           FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_getSubscribedCommunities_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(COMMUNITY_ENDPOINT + SUBSCRIPTIONS_ENDPOINT)
                .param(TYPE_PARAM_NAME, CommunityType.BUSINESS.toString())
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).getSubscribedCommunities(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_subscribeToCommunity() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();

        mockMvc.perform(MockMvcRequestBuilders
                            .put(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + SUBSCRIPTIONS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(SUBSCRIBE_TO_COMMUNITY_OK_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).subscribeToCommunity(communityId);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_subscribeToCommunity_withoutUser() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + SUBSCRIPTIONS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).subscribeToCommunity(communityId);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_unsubscribeFromCommunity() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UNSUBSCRIBE_TO_COMMUNITY_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                            .put(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + UNSUBSCRIBE_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).unsubscribeFromCommunity(
            communityId);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_unsubscribeFromCommunity_withoutUser() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + UNSUBSCRIBE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).unsubscribeFromCommunity(
                communityId);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_addCommunity() throws Exception {
        CommunityDto communityDto = CommunityTestData.getCommunityDto();
        CommunityForCreateDto communityForCreationDto = CommunityTestData.getCommunityForCreationDto();
        Mockito.doReturn(communityDto).when(communityService).addCommunity(ArgumentMatchers.any(
            CommunityForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                            .post(COMMUNITY_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(communityForCreationDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(communityDto)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(communityService, Mockito.times(1)).addCommunity(
            ArgumentMatchers.any(CommunityForCreateDto.class));
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_addCommunity_withoutUser() throws Exception {
        CommunityForCreateDto communityForCreationDto = CommunityTestData.getCommunityForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(COMMUNITY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(communityForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).addCommunity(
                ArgumentMatchers.any(CommunityForCreateDto.class));
    }


    @WithMockUser(roles="USER")
    @Test
    void CommunityController_updateCommunity() throws Exception {
        CommunityDto communityDto = CommunityTestData.getCommunityDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_COMMUNITY_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                            .put(COMMUNITY_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(communityDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).updateCommunity(
            ArgumentMatchers.any(CommunityDto.class));
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_updateCommunity_withoutUser() throws Exception {
        CommunityDto communityDto = CommunityTestData.getCommunityDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(COMMUNITY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(communityDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).updateCommunity(
                ArgumentMatchers.any(CommunityDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_deleteCommunityByUser() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_COMMUNITY_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                            .put(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + DELETE_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).deleteCommunityByUser(
            communityId);
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_deleteCommunityByUser_withoutUser() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + DELETE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).deleteCommunityByUser(
                communityId);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void CommunityController_deleteCommunity() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_COMMUNITY_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(communityService, Mockito.times(1)).deleteCommunity(
            communityId);
        Mockito.reset(communityService);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_deleteCommunity_wrongAccess() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(communityService, Mockito.never()).deleteCommunity(
            communityId);
    }

    @Test
    void CommunityController_deleteCommunity_withoutUser() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).deleteCommunity(
                communityId);
    }

    @WithMockUser(roles="USER")
    @Test
    void CommunityController_addPostToCommunity() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();
        PostForCreationDto postForCreationDto = PostTestData.getPostForCreationDto();
        PostDto postDto = PostTestData.getPostDto();
        Mockito.doReturn(postDto).when(communityService).addPostToCommunity(
            ArgumentMatchers.any(PostForCreationDto.class), ArgumentMatchers.any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders
                            .post(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + POSTS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(postForCreationDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(postDto)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(communityService, Mockito.times(1)).addPostToCommunity(
            ArgumentMatchers.any(PostForCreationDto.class), ArgumentMatchers.any(Long.class));
        Mockito.reset(communityService);
    }

    @Test
    void CommunityController_addPostToCommunity_withoutUser() throws Exception {
        Long communityId = CommunityTestData.getCommunityId();
        PostForCreationDto postForCreationDto = PostTestData.getPostForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(COMMUNITY_ENDPOINT + PATH_SEPARATOR + communityId + POSTS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(communityService, Mockito.never()).addPostToCommunity(
                ArgumentMatchers.any(PostForCreationDto.class), ArgumentMatchers.any(Long.class));
    }

}
