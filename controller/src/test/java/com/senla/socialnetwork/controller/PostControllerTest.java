package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.PostCommentTestData;
import com.senla.socialnetwork.controller.config.PostTestData;
import com.senla.socialnetwork.dto.*;
import com.senla.socialnetwork.service.PostService;
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

@WebMvcTest(controllers = PostController.class)
public class PostControllerTest extends AbstractControllerTest {
    private static final String POST_ENDPOINT = "/communities/posts";
    private static final String HISTORY_ENDPOINT = "/history";
    private static final String DELETE_ENDPOINT = "/changes";
    private static final String COMMENTS_ENDPOINT = "/comments";
    public static final String UPDATE_POST_OK_MESSAGE = "Successfully updated a post";
    public static final String DELETE_POST_OK_MESSAGE = "Successfully deleted a post";
    @Autowired
    private PostController postController;
    @Autowired
    private PostService postService;

    @WithMockUser(roles="ADMIN")
    @Test
    void PostController_getPosts() throws Exception {
        List<PostDto> postsDto = PostTestData.getPostsDto();
        Mockito.doReturn(postsDto).when(postService).getPosts(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(postsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).getPosts(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostController_getPosts_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get(POST_ENDPOINT)
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(postService, Mockito.never()).getPosts(
            FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void PostController_getPosts_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postService, Mockito.never()).getPosts(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostController_getPostsHistory() throws Exception {
        List<PostDto> postsDto = PostTestData.getPostsDto();
        Mockito.doReturn(postsDto).when(postService).getPostsFromSubscribedCommunities(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_ENDPOINT + HISTORY_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(postsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).getPostsFromSubscribedCommunities(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postService);
    }

    @Test
    void PostController_getPostsHistory_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_ENDPOINT + HISTORY_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postService, Mockito.never()).getPostsFromSubscribedCommunities(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostController_updatePost() throws Exception {
        PostDto postDto = PostTestData.getPostDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_POST_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).updatePost(
                ArgumentMatchers.any(PostDto.class));
        Mockito.reset(postService);
    }

    @Test
    void PostController_updatePost_withoutUser() throws Exception {
        PostDto postDto = PostTestData.getPostDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postService, Mockito.never()).updatePost(
                ArgumentMatchers.any(PostDto.class));
    }

    @WithMockUser(roles="USER")
    @Test
    void PostController_deletePostByUser() throws Exception {
        Long postId = PostTestData.getPostId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_POST_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_ENDPOINT + PATH_SEPARATOR + postId + DELETE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).deletePostByUser(postId);
        Mockito.reset(postService);
    }

    @Test
    void PostController_deletePostByUser_withoutUser() throws Exception {
        Long postId = PostTestData.getPostId();

        mockMvc.perform(MockMvcRequestBuilders
                .put(POST_ENDPOINT + PATH_SEPARATOR + postId + DELETE_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postService, Mockito.never()).deletePostByUser(postId);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void PostController_deletePost() throws Exception {
        Long postId = PostTestData.getPostId();
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_POST_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(POST_ENDPOINT + PATH_SEPARATOR + postId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).deletePost(
                postId);
        Mockito.reset(postService);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostController_deletePost_wrongAccess() throws Exception {
        Long postId = PostTestData.getPostId();

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(POST_ENDPOINT + PATH_SEPARATOR + postId))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(postService, Mockito.never()).deletePost(
            postId);
    }

    @Test
    void PostController_deletePost_withoutUser() throws Exception {
        Long postId = PostTestData.getPostId();

        mockMvc.perform(MockMvcRequestBuilders
                .delete(POST_ENDPOINT + PATH_SEPARATOR + postId))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postService, Mockito.never()).deletePost(
                postId);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostController_getPostComments() throws Exception {
        Long postId = PostTestData.getPostId();
        List<PostCommentDto> postCommentsDto = PostCommentTestData.getPostCommentsDto();
        Mockito.doReturn(postCommentsDto).when(postService).getPostComments(postId, FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_ENDPOINT + PATH_SEPARATOR + postId + COMMENTS_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(postCommentsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(postService, Mockito.times(1)).getPostComments(
                postId, FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(postService);
    }

    @Test
    void PostController_getPostComments_withoutUser() throws Exception {
        Long postId = PostTestData.getPostId();

        mockMvc.perform(MockMvcRequestBuilders
                .get(POST_ENDPOINT + PATH_SEPARATOR + postId + COMMENTS_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postService, Mockito.never()).getPostComments(
                postId, FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void PostController_addComment() throws Exception {
        Long postId = PostTestData.getPostId();
        PostCommentDto postCommentDto = PostCommentTestData.getPostCommentDto();
        PostCommentForCreateDto postCommentForCreationDto = PostCommentTestData.getPostCommentForCreationDto();
        Mockito.doReturn(postCommentDto).when(postService).addComment(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(
                PostCommentForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(POST_ENDPOINT + PATH_SEPARATOR + postId + COMMENTS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCommentForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(postCommentDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(postService, Mockito.times(1)).addComment(
                ArgumentMatchers.any(Long.class), ArgumentMatchers.any(PostCommentForCreateDto.class));
        Mockito.reset(postService);
    }

    @Test
    void PostController_addComment_withoutUser() throws Exception {
        Long postId = PostTestData.getPostId();
        PostCommentDto postCommentDto = PostCommentTestData.getPostCommentDto();
        PostCommentForCreateDto postCommentForCreationDto = PostCommentTestData.getPostCommentForCreationDto();
        Mockito.doReturn(postCommentDto).when(postService).addComment(ArgumentMatchers.any(Long.class), ArgumentMatchers.any(
                PostCommentForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(POST_ENDPOINT + PATH_SEPARATOR + postId + COMMENTS_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(postCommentForCreationDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(postService, Mockito.never()).addComment(
                ArgumentMatchers.any(Long.class), ArgumentMatchers.any(PostCommentForCreateDto.class));
    }

}
