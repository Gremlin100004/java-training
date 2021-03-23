package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.UserTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.UserForAdminDto;
import com.senla.socialnetwork.dto.UserForSecurityDto;
import com.senla.socialnetwork.model.enumaration.RoleName;
import com.senla.socialnetwork.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest extends AbstractControllerTest {
    private static final String USER_ENDPOINT = "/users";
    private static final String OWN_USER_ENDPOINT = "/own";
    private static final String REGISTRATION_ENDPOINT = "/registration";
    private static final String REGISTRATION_ADMIN_ENDPOINT = "/registration/admin";
    private static final String LOGIN_ENDPOINT = "/login";
    public static final String ADD_USER_OK_MESSAGE = "Successfully registered a user";
    public static final String UPDATE_USER_OK_MESSAGE = "Successfully updated a user";
    public static final String DELETE_USER_OK_MESSAGE = "Successfully deleted a user";
    private static final String PATH_SEPARATOR = "/";
    @Autowired
    private UserController userController;
    @Autowired
    private UserService userService;

    @WithMockUser(roles="ADMIN")
    @Test
    void UserController_getUsers() throws Exception {
        List<UserForAdminDto> users = UserTestData.getUsersForAdminDto();
        Mockito.doReturn(users).when(userService).getUsers(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(users)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService, Mockito.times(1)).getUsers(FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(userService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserController_getUsers_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .get(USER_ENDPOINT)
                            .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                            .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(userService, Mockito.never()).getUsers(FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void UserController_getUsers_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userService, Mockito.never()).getUsers(FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserController_getUser() throws Exception {
        UserForSecurityDto user = UserTestData.getUserForSecurityDto();
        Mockito.doReturn(user).when(userService).getUser();

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_ENDPOINT + OWN_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService, Mockito.times(1)).getUser();
        Mockito.reset(userService);
    }

    @Test
    void UserController_getUser_withoutUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get(USER_ENDPOINT + OWN_USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userService, Mockito.never()).getUser();
        Mockito.reset(userService);
    }

    @Test
    void UserController_addUser() throws Exception {
        UserForSecurityDto user = UserTestData.getUserForSecurityDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(USER_ENDPOINT + REGISTRATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(ADD_USER_OK_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(userService, Mockito.times(1)).addUser(
                ArgumentMatchers.any(UserForSecurityDto.class), ArgumentMatchers.any(RoleName.class));
        Mockito.reset(userService);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void UserController_addAdmin() throws Exception {
        UserForSecurityDto user = UserTestData.getUserForSecurityDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(USER_ENDPOINT + REGISTRATION_ADMIN_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(ADD_USER_OK_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(userService, Mockito.times(1)).addUser(
                ArgumentMatchers.any(UserForSecurityDto.class), ArgumentMatchers.any(RoleName.class));
        Mockito.reset(userService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserController_addAdmin_wrongAccess() throws Exception {
        UserForSecurityDto user = UserTestData.getUserForSecurityDto();

        mockMvc.perform(MockMvcRequestBuilders
                            .post(USER_ENDPOINT + REGISTRATION_ADMIN_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(user)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(userService, Mockito.never()).addUser(
            ArgumentMatchers.any(UserForSecurityDto.class), ArgumentMatchers.any(RoleName.class));
    }

    @Test
    void UserController_addAdmin_withoutUser() throws Exception {
        UserForSecurityDto user = UserTestData.getUserForSecurityDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(USER_ENDPOINT + REGISTRATION_ADMIN_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userService, Mockito.never()).addUser(
                ArgumentMatchers.any(UserForSecurityDto.class), ArgumentMatchers.any(RoleName.class));
    }

    @Test
    void UserController_logIn() throws Exception {
        UserForSecurityDto user = UserTestData.getUserForSecurityDto();
        Mockito.doReturn(ADD_USER_OK_MESSAGE).when(userService).logIn(ArgumentMatchers.any(
                UserForSecurityDto.class), ArgumentMatchers.any(SecretKey.class));

        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_ENDPOINT + LOGIN_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(ADD_USER_OK_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService, Mockito.times(1)).logIn(ArgumentMatchers.any(
                UserForSecurityDto.class), ArgumentMatchers.any(SecretKey.class));
        Mockito.reset(userService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserController_updateUser() throws Exception {
        List<UserForSecurityDto> users = UserTestData.getUsersForSecurityDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_USER_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService, Mockito.times(1)).updateUser(
                ArgumentMatchers.any(ArrayList.class));
        Mockito.reset(userService);
    }

    @Test
    void UserController_updateUser_withoutUser() throws Exception {
        List<UserForSecurityDto> users = UserTestData.getUsersForSecurityDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(USER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(users)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userService, Mockito.never()).updateUser(
                ArgumentMatchers.any(ArrayList.class));
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void UserController_deleteUser() throws Exception {
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_USER_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(USER_ENDPOINT + PATH_SEPARATOR + UserTestData.getIdUser()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(userService, Mockito.times(1)).deleteUser(
                ArgumentMatchers.any(Long.class));
        Mockito.reset(userService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UserController_deleteUser_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .delete(USER_ENDPOINT + PATH_SEPARATOR + UserTestData.getIdUser()))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(userService, Mockito.never()).deleteUser(
            ArgumentMatchers.any(Long.class));
        Mockito.reset(userService);
    }

    @Test
    void UserController_deleteUser_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(USER_ENDPOINT + PATH_SEPARATOR + UserTestData.getIdUser()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(userService, Mockito.never()).deleteUser(
                ArgumentMatchers.any(Long.class));
        Mockito.reset(userService);
    }

}
