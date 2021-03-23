package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.PostCommentTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.service.PostCommentService;
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

@WebMvcTest(controllers = PostCommentController.class)
public class PostCommentControllerTest extends AbstractControllerTest {
    private static final String POST_COMMENT_ENDPOINT = "/communities/posts/comments";
    private static final String DELETE_ENDPOINT = "/changes";
    public static final String UPDATE_COMMENT_OK_MESSAGE = "Successfully updated a comment";
    public static final String DELETE_COMMENT_OK_MESSAGE = "Successfully deleted a comment";
    @Autowired
    private PostCommentController postCommentController;
    @Autowired
    private PostCommentService postCommentService;

    @WithMockUser(roles="ADMIN")
    @Test
    void PostCommentController_getComments() throws Exception {
        List<PostCommentDto> locations = PostCommentTestData.getPostCommentsDto();
        Mockito.doReturn(locations).when(postCommentService).getComments(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_COMMENT_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(locations)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postCommentService, Mockito.times(1)).getComments(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postCommentService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostCommentController_getComments_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get(POST_COMMENT_ENDPOINT)
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(postCommentService, Mockito.never()).getComments(
            FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void PostCommentController_getComments_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_COMMENT_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postCommentService, Mockito.never()).getComments(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostCommentController_updateComment() throws Exception {
        PostCommentDto postCommentDto = PostCommentTestData.getPostCommentDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_COMMENT_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_COMMENT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCommentDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postCommentService, Mockito.times(1)).updateComment(
                ArgumentMatchers.any(PostCommentDto.class));
        Mockito.reset(postCommentService);
    }

    @Test
    void PostCommentController_updateComment_withoutUser() throws Exception {
        PostCommentDto postCommentDto = PostCommentTestData.getPostCommentDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_COMMENT_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCommentDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postCommentService, Mockito.never()).updateComment(
                ArgumentMatchers.any(PostCommentDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void PostCommentController_deleteCommentByUser() throws Exception {
        Long postCommentId = PostCommentTestData.getPostCommentId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_COMMENT_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_COMMENT_ENDPOINT + PATH_SEPARATOR + postCommentId + DELETE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postCommentService, Mockito.times(1)).deleteCommentByUser(
                postCommentId);
        Mockito.reset(postCommentService);
    }

    @Test
    void PostCommentController_deleteCommentByUser_withoutUser() throws Exception {
        Long postCommentId = PostCommentTestData.getPostCommentId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_COMMENT_ENDPOINT + PATH_SEPARATOR + postCommentId + DELETE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postCommentService, Mockito.never()).deleteCommentByUser(
                postCommentId);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void PostCommentController_deleteComment() throws Exception {
        Long postCommentId = PostCommentTestData.getPostCommentId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_COMMENT_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(POST_COMMENT_ENDPOINT + PATH_SEPARATOR + postCommentId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postCommentService, Mockito.times(1)).deleteComment(
                postCommentId);
        Mockito.reset(postCommentService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostCommentController_deleteComment_wrongAccess() throws Exception {
        Long postCommentId = PostCommentTestData.getPostCommentId();

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(POST_COMMENT_ENDPOINT + PATH_SEPARATOR + postCommentId))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(postCommentService, Mockito.never()).deleteComment(
            postCommentId);
    }

    @Test
    void PostCommentController_deleteComment_withoutUser() throws Exception {
        Long postCommentId = PostCommentTestData.getPostCommentId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(POST_COMMENT_ENDPOINT + PATH_SEPARATOR + postCommentId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postCommentService, Mockito.never()).deleteComment(
                postCommentId);
    }

}
