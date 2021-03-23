package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.WeatherConditionTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.dto.WeatherConditionForAdminDto;
import com.senla.socialnetwork.service.WeatherConditionService;
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

@WebMvcTest(controllers = WeatherConditionController.class)
public class WeatherConditionControllerTest extends AbstractControllerTest {
    private static final String WEATHER_CONDITION_ENDPOINT = "/weatherConditions";
    private static final String GET_CONDITION_ENDPOINT = "/location";
    private static final String PATH_SEPARATOR = "/";
    public static final String DELETE_WEATHER_CONDITION_OK_MESSAGE = "Successfully deleted a weather condition";

    @Autowired
    private WeatherConditionController weatherConditionController;
    @Autowired
    private WeatherConditionService weatherConditionService;

    @WithMockUser(roles="ADMIN")
    @Test
    void WeatherConditionController_getWeatherConditions() throws Exception {
        List<WeatherConditionForAdminDto> weatherConditionsForAdminDto = WeatherConditionTestData.getWeatherConditionsForAdminDto();
        Mockito.doReturn(weatherConditionsForAdminDto).when(weatherConditionService).getWeatherConditions(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                .get(WEATHER_CONDITION_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(weatherConditionsForAdminDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(weatherConditionService, Mockito.times(1)).getWeatherConditions(
                FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(weatherConditionService);
    }

    @WithMockUser(roles="USER")
    @Test
    void WeatherConditionController_getWeatherConditions_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(WEATHER_CONDITION_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(weatherConditionService, Mockito.never()).getWeatherConditions(
                FIRST_RESULT, MAX_RESULTS);
    }

    @Test
    void WeatherConditionController_getWeatherConditions_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(WEATHER_CONDITION_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(weatherConditionService, Mockito.never()).getWeatherConditions(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="USER")
    @Test
    void WeatherConditionController_getWeatherCondition() throws Exception {
        WeatherConditionDto weatherConditionDto = WeatherConditionTestData.getWeatherConditionDto();
        Mockito.doReturn(weatherConditionDto).when(weatherConditionService).getWeatherCondition();

        mockMvc.perform(MockMvcRequestBuilders
                .get(WEATHER_CONDITION_ENDPOINT + GET_CONDITION_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(weatherConditionDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(weatherConditionService, Mockito.times(1)).getWeatherCondition();
        Mockito.reset(weatherConditionService);
    }

    @Test
    void WeatherConditionController_getWeatherCondition_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(WEATHER_CONDITION_ENDPOINT + GET_CONDITION_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(weatherConditionService, Mockito.never()).getWeatherCondition();
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void WeatherConditionController_deleteWeatherCondition() throws Exception {
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_WEATHER_CONDITION_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(WEATHER_CONDITION_ENDPOINT + PATH_SEPARATOR + WeatherConditionTestData.getWeatherId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(weatherConditionService, Mockito.times(1)).deleteWeatherCondition(
                ArgumentMatchers.any(Long.class));
        Mockito.reset(weatherConditionService);
    }

    @WithMockUser(roles="USER")
    @Test
    void WeatherConditionController_deleteWeatherCondition_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(WEATHER_CONDITION_ENDPOINT + PATH_SEPARATOR + WeatherConditionTestData.getWeatherId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(weatherConditionService, Mockito.never()).deleteWeatherCondition(
                ArgumentMatchers.any(Long.class));
    }

    @Test
    void WeatherConditionController_deleteWeatherCondition_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(WEATHER_CONDITION_ENDPOINT + PATH_SEPARATOR + WeatherConditionTestData.getWeatherId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(weatherConditionService, Mockito.never()).deleteWeatherCondition(
                ArgumentMatchers.any(Long.class));
    }

}
