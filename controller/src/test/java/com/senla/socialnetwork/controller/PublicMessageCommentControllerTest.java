package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.PublicMessageCommentTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PublicMessageCommentDto;
import com.senla.socialnetwork.model.PublicMessageComment;
import com.senla.socialnetwork.service.PublicMessageCommentService;
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

@WebMvcTest(controllers = PublicMessageComment.class)
public class PublicMessageCommentControllerTest extends AbstractControllerTest {
    private static final String PUBLIC_MESSAGE_COMMENT_ENDPOINT = "/publicMessages/comments";
    private static final String DELETE_BY_USER_ENDPOINT = "/changes";
    public static final String UPDATE_MESSAGE_OK_MESSAGE = "Successfully updated a public message comment";
    public static final String DELETE_MESSAGE_OK_MESSAGE = "Successfully deleted a public message comment";
    @Autowired
    private PublicMessageCommentController publicMessageCommentController;
    @Autowired
    private PublicMessageCommentService publicMessageCommentService;

    @WithMockUser(roles="ADMIN")
    @Test
    void PublicMessageComment_getComments() throws Exception {
        List<PublicMessageCommentDto> publicMessageCommentsDto = PublicMessageCommentTestData.getPublicMessageCommentsDto();
        Mockito.doReturn(publicMessageCommentsDto).when(publicMessageCommentService).getComments(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_COMMENT_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(publicMessageCommentsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageCommentService, Mockito.times(1)).getComments(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(publicMessageCommentService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageComment_getComments_wrongAccess() throws Exception {
        List<PublicMessageCommentDto> publicMessageCommentsDto = PublicMessageCommentTestData.getPublicMessageCommentsDto();
        Mockito.doReturn(publicMessageCommentsDto).when(publicMessageCommentService).getComments(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                            .get(PUBLIC_MESSAGE_COMMENT_ENDPOINT)
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(publicMessageCommentService, Mockito.never()).getComments(
            FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void PublicMessageComment_getComments_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(PUBLIC_MESSAGE_COMMENT_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageCommentService, Mockito.never()).getComments(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageComment_updateComment() throws Exception {
        PublicMessageCommentDto publicMessageCommentDto = PublicMessageCommentTestData.getPublicMessageCommentDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_MESSAGE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_COMMENT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageCommentDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageCommentService, Mockito.times(1)).updateComment(
                ArgumentMatchers.any(PublicMessageCommentDto.class));
        Mockito.reset(publicMessageCommentService);
    }

    @Test
    void PublicMessageComment_updateComment_withoutUser() throws Exception {
        PublicMessageCommentDto publicMessageCommentDto = PublicMessageCommentTestData.getPublicMessageCommentDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_COMMENT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(publicMessageCommentDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageCommentService, Mockito.never()).updateComment(
                ArgumentMatchers.any(PublicMessageCommentDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageComment_deleteCommentByUser() throws Exception {
        Long publicMessageCommentId = PublicMessageCommentTestData.getPublicMessageCommentId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_COMMENT_ENDPOINT + PATH_SEPARATOR + publicMessageCommentId + DELETE_BY_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageCommentService, Mockito.times(1)).deleteCommentByUser(
                publicMessageCommentId);
        Mockito.reset(publicMessageCommentService);
    }

    @Test
    void PublicMessageComment_deleteCommentByUser_withoutUser() throws Exception {
        Long publicMessageCommentId = PublicMessageCommentTestData.getPublicMessageCommentId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(PUBLIC_MESSAGE_COMMENT_ENDPOINT + PATH_SEPARATOR + publicMessageCommentId + DELETE_BY_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageCommentService, Mockito.never()).deleteCommentByUser(
                publicMessageCommentId);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void PublicMessageComment_deleteComment() throws Exception {
        Long publicMessageCommentId = PublicMessageCommentTestData.getPublicMessageCommentId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_MESSAGE_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(PUBLIC_MESSAGE_COMMENT_ENDPOINT + PATH_SEPARATOR + publicMessageCommentId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(publicMessageCommentService, Mockito.times(1)).deleteComment(
                publicMessageCommentId);
        Mockito.reset(publicMessageCommentService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PublicMessageComment_deleteComment_wrongAccess() throws Exception {
        Long publicMessageCommentId = PublicMessageCommentTestData.getPublicMessageCommentId();

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(PUBLIC_MESSAGE_COMMENT_ENDPOINT + PATH_SEPARATOR + publicMessageCommentId))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(publicMessageCommentService, Mockito.never()).deleteComment(
            publicMessageCommentId);
    }

    @Test
    void PublicMessageComment_deleteComment_withoutUser() throws Exception {
        Long publicMessageCommentId = PublicMessageCommentTestData.getPublicMessageCommentId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(PUBLIC_MESSAGE_COMMENT_ENDPOINT + PATH_SEPARATOR + publicMessageCommentId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(publicMessageCommentService, Mockito.never()).deleteComment(
                publicMessageCommentId);
    }

}
