package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.SchoolTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.SchoolDto;
import com.senla.socialnetwork.dto.SchoolForCreateDto;
import com.senla.socialnetwork.service.SchoolService;
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

@WebMvcTest(controllers = SchoolController.class)
public class SchoolControllerTest extends AbstractControllerTest {
    public static final String UPDATE_SCHOOL_OK_MESSAGE = "Successfully updated a school";
    public static final String DELETE_SCHOOL_OK_MESSAGE = "Successfully deleted a school";
    private static final String SCHOOL_ENDPOINT = "/schools";
    private static final String PATH_SEPARATOR = "/";
    @Autowired
    private SchoolController schoolController;
    @Autowired
    SchoolService schoolService;

    @WithMockUser(roles="USER")
    @Test
    void SchoolController_getSchools() throws Exception {
        List<SchoolDto> schoolsDto = SchoolTestData.getSchoolsDto();
        Mockito.doReturn(schoolsDto).when(schoolService).getSchools(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(SCHOOL_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(schoolsDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(schoolService, Mockito.times(1)).getSchools(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(schoolService);
    }

    @Test
    void SchoolController_getSchools_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(SCHOOL_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(schoolService, Mockito.never()).getSchools(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void SchoolController_addSchool() throws Exception {
        SchoolDto schoolDto = SchoolTestData.getSchoolDto();
        SchoolForCreateDto schoolForCreateDto = SchoolTestData.getSchoolForCreationDto();
        Mockito.doReturn(schoolDto).when(schoolService).addSchool(ArgumentMatchers.any(SchoolForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(SCHOOL_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolForCreateDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(schoolDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(schoolService, Mockito.times(1)).addSchool(
                ArgumentMatchers.any(SchoolForCreateDto.class));
        Mockito.reset(schoolService);
    }

    @WithMockUser(roles="USER")
    @Test
    void SchoolController_addSchool_wrongAccess() throws Exception {
        SchoolForCreateDto schoolForCreateDto = SchoolTestData.getSchoolForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                            .post(SCHOOL_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(schoolForCreateDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(schoolService, Mockito.never()).addSchool(
            ArgumentMatchers.any(SchoolForCreateDto.class));
    }

    @Test
    void SchoolController_addSchool_withoutUser() throws Exception {
        SchoolForCreateDto schoolForCreateDto = SchoolTestData.getSchoolForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(SCHOOL_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(schoolForCreateDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(schoolService, Mockito.never()).addSchool(
                ArgumentMatchers.any(SchoolForCreateDto.class));
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void SchoolController_updateSchool() throws Exception {
        SchoolDto location = SchoolTestData.getSchoolDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_SCHOOL_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(SCHOOL_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(schoolService, Mockito.times(1)).updateSchool(
                ArgumentMatchers.any(SchoolDto.class));
        Mockito.reset(schoolService);
    }

    @WithMockUser(roles="USER")
    @Test
    void SchoolController_updateSchool_wrongAccess() throws Exception {
        SchoolDto location = SchoolTestData.getSchoolDto();

        mockMvc.perform(MockMvcRequestBuilders
                            .put(SCHOOL_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(location)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(schoolService, Mockito.never()).updateSchool(
            ArgumentMatchers.any(SchoolDto.class));
    }

    @Test
    void SchoolController_updateSchool_withoutUser() throws Exception {
        SchoolDto location = SchoolTestData.getSchoolDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(SCHOOL_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(schoolService, Mockito.never()).updateSchool(
                ArgumentMatchers.any(SchoolDto.class));
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void SchoolController_deleteLocation() throws Exception {
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_SCHOOL_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(SCHOOL_ENDPOINT + PATH_SEPARATOR + SchoolTestData.getSchoolId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(schoolService, Mockito.times(1)).deleteSchool(
                ArgumentMatchers.any(Long.class));
        Mockito.reset(schoolService);
    }

    @WithMockUser(roles="USER")
    @Test
    void SchoolController_deleteSchool_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .delete(SCHOOL_ENDPOINT + PATH_SEPARATOR + SchoolTestData.getSchoolId()))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(schoolService, Mockito.never()).deleteSchool(
            ArgumentMatchers.any(Long.class));
    }

    @Test
    void SchoolController_deleteSchool_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(SCHOOL_ENDPOINT + PATH_SEPARATOR + SchoolTestData.getSchoolId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(schoolService, Mockito.never()).deleteSchool(
                ArgumentMatchers.any(Long.class));
    }

}
