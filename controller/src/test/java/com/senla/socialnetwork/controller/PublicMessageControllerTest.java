package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.PublicMessageCommentTestData;
import com.senla.socialnetwork.controller.config.PublicMessageTestData;
import com.senla.socialnetwork.dto.*;
import com.senla.socialnetwork.service.PublicMessageService;
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

@WebMvcTest(controllers = PublicMessageControllerTest.class)
public class PublicMessageControllerTest extends AbstractControllerTest {
    private static final String PUBLIC_MESSAGE_ENDPOINT = "/publicMessages";
    private static final String COMMENTS_ENDPOINT = "/comments";
    private static final String PUBLIC_MESSAGE_BY_ADMIN_ENDPOINT = "/admin";
    private static final String DELETE_MESSAGE_BY_USER_ENDPOINT = "/changes";
    private static final String PATH_SEPARATOR = "/";
    public static final String UPDATE_MESSAGE_OK_MESSAGE = "Successfully updated a public message";
    public static final String DELETE_MESSAGE_OK_MESSAGE = "Successfully deleted a public message";
    @Autowired
    private PublicMessageController publicMessageController;
    @Autowired
    private PublicMessageService publicMessageService;

    @WithMockUser(roles="ADMIN")
    @Test
    void PublicMessageController_getMessages() throws Exception {
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getPublicMessagesDto();
        Mockito.doReturn(publicMessagesDto).when(publicMessageService).getMessages(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_ENDPOINT + PUBLIC_MESSAGE_BY_ADMIN_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(publicMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageService, Mockito.times(1)).getMessages(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(publicMessageService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_getMessages_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get(PUBLIC_MESSAGE_ENDPOINT + PUBLIC_MESSAGE_BY_ADMIN_ENDPOINT)
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(publicMessageService, Mockito.never()).getMessages(
            FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void PublicMessageController_getMessages_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_ENDPOINT + PUBLIC_MESSAGE_BY_ADMIN_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).getMessages(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_getPublicMessages() throws Exception {
        List<PublicMessageDto> publicMessagesDto = PublicMessageTestData.getPublicMessagesDto();
        Mockito.doReturn(publicMessagesDto).when(publicMessageService).getPublicMessages(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(publicMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageService, Mockito.times(1)).getPublicMessages(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(publicMessageService);
    }

    @Test
    void PublicMessageController_getPublicMessages_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).getPublicMessages(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_addMessage() throws Exception {
        PublicMessageDto publicMessageDto = PublicMessageTestData.getPublicMessageDto();
        PublicMessageForCreateDto publicMessageForCreationDto = PublicMessageTestData.getPublicMessageForCreationDto();
        Mockito.doReturn(publicMessageDto).when(publicMessageService).addMessage(
                ArgumentMatchers.any(PublicMessageForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(PUBLIC_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(publicMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(publicMessageService, Mockito.times(1)).addMessage(
                ArgumentMatchers.any(PublicMessageForCreateDto.class));
        Mockito.reset(publicMessageService);
    }

    @Test
    void PublicMessageController_addMessage_withoutUser() throws Exception {
        PublicMessageForCreateDto publicMessageForCreationDto = PublicMessageTestData.getPublicMessageForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(PUBLIC_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).addMessage(
                ArgumentMatchers.any(PublicMessageForCreateDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_updateMessage() throws Exception {
        PublicMessageDto publicMessageDto = PublicMessageTestData.getPublicMessageDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_MESSAGE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageService, Mockito.times(1)).updateMessage(
                ArgumentMatchers.any(PublicMessageDto.class));
        Mockito.reset(publicMessageService);
    }

    @Test
    void PublicMessageController_updateMessage_withoutUser() throws Exception {
        PublicMessageDto publicMessageDto = PublicMessageTestData.getPublicMessageDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).updateMessage(
                ArgumentMatchers.any(PublicMessageDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_deleteMessageByUser() throws Exception {
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);
        Long id = PublicMessageTestData.getPublicMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + id + DELETE_MESSAGE_BY_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageService, Mockito.times(1)).deleteMessageByUser(id);
        Mockito.reset(publicMessageService);
    }

    @Test
    void PublicMessageController_deleteMessageByUser_withoutUser() throws Exception {
        Long id = PublicMessageTestData.getPublicMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + id + DELETE_MESSAGE_BY_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).deleteMessageByUser(id);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void PublicMessageController_deleteMessage() throws Exception {
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);
        Long id = PublicMessageTestData.getPublicMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageService, Mockito.times(1)).deleteMessage(id);
        Mockito.reset(publicMessageService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_deleteMessage_wrongAccess() throws Exception {
        Long id = PublicMessageTestData.getPublicMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + id)
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(publicMessageService, Mockito.never()).deleteMessage(id);
    }

    @Test
    void PublicMessageController_deleteMessage_withoutUser() throws Exception {
        Long id = PublicMessageTestData.getPublicMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).deleteMessage(id);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_getPublicMessageComments() throws Exception {
        List<PublicMessageCommentDto> publicMessageCommentsDto = PublicMessageCommentTestData.getPublicMessageCommentsDto();
        Long publicMessageId = PublicMessageTestData.getPublicMessageId();
        Mockito.doReturn(publicMessageCommentsDto).when(publicMessageService).getPublicMessageComments(
                publicMessageId, FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + publicMessageId + COMMENTS_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        publicMessageCommentsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageService, Mockito.times(1)).getPublicMessageComments(
                publicMessageId, FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(publicMessageService);
    }

    @Test
    void PublicMessageController_getPublicMessageComments_withoutUser() throws Exception {
        Long publicMessageId = PublicMessageTestData.getPublicMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + publicMessageId + COMMENTS_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).getPublicMessageComments(
                publicMessageId, FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageController_addComment() throws Exception {
        PublicMessageCommentDto publicMessageCommentDto = PublicMessageCommentTestData.getPublicMessageCommentDto();
        PublicMessageCommentForCreateDto publicMessageForCreationDto = PublicMessageCommentTestData
                .getPublicMessageCommentForCreationDto();
        Long publicMessageId = PublicMessageTestData.getPublicMessageId();
        Mockito.doReturn(publicMessageCommentDto).when(publicMessageService).addComment(
                ArgumentMatchers.any(Long.class), ArgumentMatchers.any(PublicMessageCommentForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + publicMessageId + COMMENTS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(publicMessageCommentDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(publicMessageService, Mockito.times(1)).addComment(
                ArgumentMatchers.any(Long.class), ArgumentMatchers.any(PublicMessageCommentForCreateDto.class));
        Mockito.reset(publicMessageService);
    }

    @Test
    void PublicMessageController_addComment_withoutUser() throws Exception {
        PublicMessageCommentForCreateDto publicMessageForCreationDto = PublicMessageCommentTestData
                .getPublicMessageCommentForCreationDto();
        Long publicMessageId = PublicMessageTestData.getPublicMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .post(PUBLIC_MESSAGE_ENDPOINT + PATH_SEPARATOR + publicMessageId + COMMENTS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageService, Mockito.never()).addComment(
                ArgumentMatchers.any(Long.class), ArgumentMatchers.any(PublicMessageCommentForCreateDto.class));
    }

}
