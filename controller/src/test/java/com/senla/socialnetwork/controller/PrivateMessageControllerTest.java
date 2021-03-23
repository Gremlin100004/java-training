package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.PrivateMessageTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageDto;
import com.senla.socialnetwork.dto.PrivateMessageForCreateDto;
import com.senla.socialnetwork.service.PrivateMessageService;
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

@WebMvcTest(controllers = PrivateMessageController.class)
public class PrivateMessageControllerTest extends AbstractControllerTest {
    private static final SimpleDateFormat TIME_DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static final String PRIVATE_MESSAGE_ENDPOINT = "/privateMessages";
    private static final String GET_PRIVATE_MESSAGE_BY_ADMIN_ENDPOINT = "/admin";
    private static final String UNREAD_MESSAGES_ENDPOINT = "/unreadMessages";
    private static final String DELETED_BY_USER_ENDPOINT = "/changes";
    public static final String START_PERIOD_PARAM_NAME = "startPeriodDate";
    public static final String START_PERIOD_PARAM = "2020-11-01 12:04";
    public static final String END_PERIOD_PARAM_NAME = "endPeriodDate";
    public static final String END_PERIOD_PARAM = "2020-11-11 12:05";
    public static final String UPDATE_PRIVATE_MESSAGE_OK_MESSAGE = "Successfully updated a private message";
    public static final String DELETE_PRIVATE_MESSAGE_OK_MESSAGE = "Successfully deleted a private message";
    @Autowired
    private PrivateMessageController privateMessageController;
    @Autowired
    private PrivateMessageService privateMessageService;

    @WithMockUser(roles="ADMIN")
    @Test
    void PrivateMessageController_getPrivateMessages_byAdmin() throws Exception {
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessagesDto).when(privateMessageService).getPrivateMessages(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT + GET_PRIVATE_MESSAGE_BY_ADMIN_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(privateMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(privateMessageService, Mockito.times(1)).getPrivateMessages(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(privateMessageService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_getPrivateMessages_byAdmin_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get(PRIVATE_MESSAGE_ENDPOINT + GET_PRIVATE_MESSAGE_BY_ADMIN_ENDPOINT)
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(privateMessageService, Mockito.never()).getPrivateMessages(
            FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void PrivateMessageController_getPrivateMessages_byAdmin_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT + GET_PRIVATE_MESSAGE_BY_ADMIN_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).getPrivateMessages(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_getUnreadMessages() throws Exception {
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessagesDto).when(privateMessageService).getUnreadMessages(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT + UNREAD_MESSAGES_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(privateMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(privateMessageService, Mockito.times(1)).getUnreadMessages(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(privateMessageService);
    }

    @Test
    void PrivateMessageController_getUnreadMessages_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT + UNREAD_MESSAGES_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).getUnreadMessages(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_getPrivateMessages() throws Exception {
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessagesDto).when(privateMessageService).getPrivateMessagesByUser(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(privateMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(privateMessageService, Mockito.times(1)).getPrivateMessagesByUser(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(privateMessageService);
    }

    @Test
    void PrivateMessageController_getPrivateMessages_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).getPrivateMessagesByUser(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_getPrivateMessages_filteredByPeriod() throws Exception {
        List<PrivateMessageDto> privateMessagesDto = PrivateMessageTestData.getTestPrivateMessagesDto();
        Mockito.doReturn(privateMessagesDto).when(privateMessageService).getMessageFilteredByPeriod(
                getDateTime(START_PERIOD_PARAM), getDateTime(END_PERIOD_PARAM), FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT)
                .param(START_PERIOD_PARAM_NAME, START_PERIOD_PARAM)
                .param(END_PERIOD_PARAM_NAME, END_PERIOD_PARAM)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(privateMessagesDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(privateMessageService, Mockito.times(1)).getMessageFilteredByPeriod(
                getDateTime(START_PERIOD_PARAM), getDateTime(END_PERIOD_PARAM), FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(privateMessageService);
    }

    @Test
    void PrivateMessageController_getPrivateMessages_filteredByPeriod_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PRIVATE_MESSAGE_ENDPOINT)
                .param(START_PERIOD_PARAM_NAME, START_PERIOD_PARAM)
                .param(END_PERIOD_PARAM_NAME, END_PERIOD_PARAM)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).getMessageFilteredByPeriod(
                getDateTime(START_PERIOD_PARAM), getDateTime(END_PERIOD_PARAM), FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_addMessage() throws Exception {
        PrivateMessageDto privateMessageDto = PrivateMessageTestData.getPrivateMessageDto();
        PrivateMessageForCreateDto privateMessageForCreationDto = PrivateMessageTestData.getPrivateMessageForCreationDto();
        Mockito.doReturn(privateMessageDto).when(privateMessageService).addMessage(
                ArgumentMatchers.any(PrivateMessageForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(PRIVATE_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(privateMessageForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(privateMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(privateMessageService, Mockito.times(1)).addMessage(
                ArgumentMatchers.any(PrivateMessageForCreateDto.class));
        Mockito.reset(privateMessageService);
    }

    @Test
    void PrivateMessageController_addMessage_withoutUser() throws Exception {
        PrivateMessageForCreateDto privateMessageForCreationDto = PrivateMessageTestData.getPrivateMessageForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(PRIVATE_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(privateMessageForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).addMessage(
                ArgumentMatchers.any(PrivateMessageForCreateDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_updateMessage() throws Exception {
        PrivateMessageDto privateMessageDto = PrivateMessageTestData.getPrivateMessageDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_PRIVATE_MESSAGE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(PRIVATE_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(privateMessageDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(privateMessageService, Mockito.times(1)).updateMessage(
                ArgumentMatchers.any(PrivateMessageDto.class));
        Mockito.reset(privateMessageService);
    }

    @Test
    void PrivateMessageController_updateMessage_withoutUser() throws Exception {
        PrivateMessageDto privateMessageDto = PrivateMessageTestData.getPrivateMessageDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(PRIVATE_MESSAGE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(privateMessageDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).updateMessage(
                ArgumentMatchers.any(PrivateMessageDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_deleteMessageByUser() throws Exception {
        Long privateMessageId = PrivateMessageTestData.getPrivateMessageId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_PRIVATE_MESSAGE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(PRIVATE_MESSAGE_ENDPOINT + PATH_SEPARATOR + privateMessageId + DELETED_BY_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(privateMessageService, Mockito.times(1)).deleteMessageByUser(
                privateMessageId);
        Mockito.reset(privateMessageService);
    }

    @Test
    void PrivateMessageController_deleteMessageByUser_withoutUser() throws Exception {
        Long privateMessageId = PrivateMessageTestData.getPrivateMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(PRIVATE_MESSAGE_ENDPOINT + PATH_SEPARATOR + privateMessageId + DELETED_BY_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).deleteMessageByUser(
                privateMessageId);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void PrivateMessageController_deleteMessage() throws Exception {
        Long privateMessageId = PrivateMessageTestData.getPrivateMessageId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_PRIVATE_MESSAGE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(PRIVATE_MESSAGE_ENDPOINT + PATH_SEPARATOR + privateMessageId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(privateMessageService, Mockito.times(1)).deleteMessage(
                privateMessageId);
        Mockito.reset(privateMessageService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PrivateMessageController_deleteMessage_wrongAccess() throws Exception {
        Long privateMessageId = PrivateMessageTestData.getPrivateMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(PRIVATE_MESSAGE_ENDPOINT + PATH_SEPARATOR + privateMessageId))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(privateMessageService, Mockito.never()).deleteMessage(
            privateMessageId);
    }

    @Test
    void PrivateMessageController_deleteMessage_withoutUser() throws Exception {
        Long privateMessageId = PrivateMessageTestData.getPrivateMessageId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(PRIVATE_MESSAGE_ENDPOINT + PATH_SEPARATOR + privateMessageId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(privateMessageService, Mockito.never()).deleteMessage(
                privateMessageId);
    }

    public static Date getDateTime(String date){
        try {
            return TIME_DATE_FORMATTER.parse(date);
        } catch (ParseException exception) {
            return null;
        }
    }

}
