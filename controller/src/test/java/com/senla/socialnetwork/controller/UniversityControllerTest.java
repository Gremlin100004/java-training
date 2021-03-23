package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.UniversityTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.UniversityDto;
import com.senla.socialnetwork.dto.UniversityForCreateDto;
import com.senla.socialnetwork.service.UniversityService;
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

@WebMvcTest(controllers = UniversityController.class)
public class UniversityControllerTest extends AbstractControllerTest {
    private static final String UNIVERSITY_ENDPOINT = "/universities";
    private static final String PATH_SEPARATOR = "/";
    private static final String UPDATE_UNIVERSITY_OK_MESSAGE = "Successfully updated a university";
    private static final String DELETE_UNIVERSITY_OK_MESSAGE = "Successfully deleted a university";
    @Autowired
    private UniversityController universityController;
    @Autowired
    private UniversityService universityService;

    @WithMockUser(roles="USER")
    @Test
    void UniversityController_getUniversities() throws Exception {
        List<UniversityDto> universityDtoList = UniversityTestData.getTestUniversitiesDto();
        Mockito.doReturn(universityDtoList).when(universityService).getUniversities(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(UNIVERSITY_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(universityDtoList)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(universityService, Mockito.times(1)).getUniversities(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(universityService);
    }

    @Test
    void UniversityController_getUniversities_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(UNIVERSITY_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(universityService, Mockito.never()).getUniversities(
                FIRST_RESULT, MAX_RESULTS);
    }


    @WithMockUser(roles="ADMIN")
    @Test
    void UniversityController_addLocation() throws Exception {
        UniversityDto universityDto = UniversityTestData.getUniversityDto();
        UniversityForCreateDto universityForCreateDto = UniversityTestData.getUniversityForCreationDto();
        Mockito.doReturn(universityDto).when(universityService).addUniversity(ArgumentMatchers.any(
                UniversityForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(UNIVERSITY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universityForCreateDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(universityDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(universityService, Mockito.times(1)).addUniversity(
                ArgumentMatchers.any(UniversityForCreateDto.class));
        Mockito.reset(universityService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UniversityController_addLocation_wrongAccess() throws Exception {
        UniversityForCreateDto universityForCreateDto = UniversityTestData.getUniversityForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                            .post(UNIVERSITY_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(universityForCreateDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(universityService, Mockito.never()).addUniversity(
            ArgumentMatchers.any(UniversityForCreateDto.class));
    }

    @Test
    void UniversityController_addLocation_withoutUser() throws Exception {
        UniversityForCreateDto universityForCreateDto = UniversityTestData.getUniversityForCreationDto();

        mockMvc.perform(MockMvcRequestBuilders
                .post(UNIVERSITY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universityForCreateDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(universityService, Mockito.never()).addUniversity(
                ArgumentMatchers.any(UniversityForCreateDto.class));
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void UniversityController_updateUniversity() throws Exception {
        UniversityDto universityDto = UniversityTestData.getUniversityDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_UNIVERSITY_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(UNIVERSITY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universityDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(universityService, Mockito.times(1)).updateUniversity(
                ArgumentMatchers.any(UniversityDto.class));
        Mockito.reset(universityService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UniversityController_updateUniversity_wrongAccess() throws Exception {
        UniversityDto universityDto = UniversityTestData.getUniversityDto();

        mockMvc.perform(MockMvcRequestBuilders
                            .put(UNIVERSITY_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(universityDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(universityService, Mockito.never()).updateUniversity(
            ArgumentMatchers.any(UniversityDto.class));
    }

    @Test
    void UniversityController_updateUniversity_withoutUser() throws Exception {
        UniversityDto universityDto = UniversityTestData.getUniversityDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(UNIVERSITY_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universityDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(universityService, Mockito.never()).updateUniversity(
                ArgumentMatchers.any(UniversityDto.class));
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void UniversityController_deleteUniversity() throws Exception {
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_UNIVERSITY_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(UNIVERSITY_ENDPOINT + PATH_SEPARATOR + UniversityTestData.getUniversityId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(universityService, Mockito.times(1)).deleteUniversity(
                ArgumentMatchers.any(Long.class));
        Mockito.reset(universityService);
    }

    @WithMockUser(roles="USER")
    @Test
    void UniversityController_deleteUniversity_wrongAccess() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                            .delete(UNIVERSITY_ENDPOINT + PATH_SEPARATOR + UniversityTestData.getUniversityId()))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(universityService, Mockito.never()).deleteUniversity(
            ArgumentMatchers.any(Long.class));
    }

    @Test
    void UniversityController_deleteUniversity_withoutUser() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .delete(UNIVERSITY_ENDPOINT + PATH_SEPARATOR + UniversityTestData.getUniversityId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(universityService, Mockito.never()).deleteUniversity(
                ArgumentMatchers.any(Long.class));
    }

}
