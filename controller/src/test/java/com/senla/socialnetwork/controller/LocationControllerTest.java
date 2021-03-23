package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.config.LocationTestData;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;
import com.senla.socialnetwork.service.LocationService;
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

@WebMvcTest(controllers = LocationController.class)
public class LocationControllerTest extends AbstractControllerTest {
    private static final String LOCATION_ENDPOINT = "/locations";
    private static final String PATH_SEPARATOR = "/";
    private static final String UPDATE_LOCATION_OK_MESSAGE = "Successfully updated a location";
    private static final String DELETE_LOCATION_OK_MESSAGE = "Successfully deleted a location";
    @Autowired
    private LocationController locationController;
    @Autowired
    private LocationService locationService;


    @WithMockUser(roles="USER")
    @Test
    void LocationController_getLocations() throws Exception {
        List<LocationDto> locations = LocationTestData.getTestLocationsDto();
        Mockito.doReturn(locations).when(locationService).getLocations(FIRST_RESULT, MAX_RESULTS);

        mockMvc.perform(MockMvcRequestBuilders
                                .get(LOCATION_ENDPOINT)
                                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                                .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(locations)))
                    .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(locationService, Mockito.times(1)).getLocations(
            FIRST_RESULT, MAX_RESULTS);
        Mockito.reset(locationService);
    }

    @Test
    void LocationController_getLocations_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get(LOCATION_ENDPOINT)
                .param(FIRST_RESULT_PARAM_NAME, String.valueOf(FIRST_RESULT))
                .param(MAX_RESULTS_PARAM_NAME, String.valueOf(MAX_RESULTS))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(locationService, Mockito.never()).getLocations(
                FIRST_RESULT, MAX_RESULTS);
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void LocationController_addLocation() throws Exception {
        LocationDto location = LocationTestData.getLocationDto();
        LocationForCreateDto locationForCreateDto = LocationTestData.getLocationForCreateDto();
        Mockito.doReturn(location).when(locationService).addLocation(ArgumentMatchers.any(LocationForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                            .post(LOCATION_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(locationForCreateDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(location)))
            .andExpect(MockMvcResultMatchers.status().isCreated());
        Mockito.verify(locationService, Mockito.times(1)).addLocation(
            ArgumentMatchers.any(LocationForCreateDto.class));
        Mockito.reset(locationService);
    }

    @WithMockUser(roles="USER")
    @Test
    void LocationController_addLocation_wrongAccess() throws Exception {
        LocationForCreateDto locationForCreateDto = LocationTestData.getLocationForCreateDto();

        mockMvc.perform(MockMvcRequestBuilders
                            .post(LOCATION_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(locationForCreateDto)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(locationService, Mockito.never()).addLocation(
            ArgumentMatchers.any(LocationForCreateDto.class));
    }

    @Test
    void LocationController_addLocation_withoutUser() throws Exception {
        LocationDto location = LocationTestData.getLocationDto();
        LocationForCreateDto locationForCreateDto = LocationTestData.getLocationForCreateDto();
        Mockito.doReturn(location).when(locationService).addLocation(ArgumentMatchers.any(LocationForCreateDto.class));

        mockMvc.perform(MockMvcRequestBuilders
                .post(LOCATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(locationForCreateDto)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(locationService, Mockito.never()).addLocation(
                ArgumentMatchers.any(LocationForCreateDto.class));
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void LocationController_updateLocation() throws Exception {
        LocationDto location = LocationTestData.getLocationDto();
        ClientMessageDto clientMessageDto = new ClientMessageDto(UPDATE_LOCATION_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .put(LOCATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(locationService, Mockito.times(1)).updateLocation(
                ArgumentMatchers.any(LocationDto.class));
        Mockito.reset(locationService);
    }

    @WithMockUser(roles="USER")
    @Test
    void LocationController_updateLocation_wrongAccess() throws Exception {
        LocationDto location = LocationTestData.getLocationDto();

        mockMvc.perform(MockMvcRequestBuilders
                            .put(LOCATION_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(location)))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(locationService, Mockito.never()).updateLocation(
            ArgumentMatchers.any(LocationDto.class));
    }

    @Test
    void LocationController_updateLocation_withoutUser() throws Exception {
        LocationDto location = LocationTestData.getLocationDto();

        mockMvc.perform(MockMvcRequestBuilders
                .put(LOCATION_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(location)))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(locationService, Mockito.never()).updateLocation(
                ArgumentMatchers.any(LocationDto.class));
    }

    @WithMockUser(roles="ADMIN")
    @Test
    void LocationController_deleteLocation() throws Exception {
        ClientMessageDto clientMessageDto = new ClientMessageDto(DELETE_LOCATION_OK_MESSAGE);

        mockMvc.perform(MockMvcRequestBuilders
                .delete(LOCATION_ENDPOINT + PATH_SEPARATOR + LocationTestData.getLocationId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(clientMessageDto)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(locationService, Mockito.times(1)).deleteLocation(
                LocationTestData.getLocationId());
        Mockito.reset(locationService);
    }

    @WithMockUser(roles="USER")
    @Test
    void LocationController_deleteLocation_wrongAccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                            .delete(LOCATION_ENDPOINT + PATH_SEPARATOR + LocationTestData.getLocationId()))
            .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                new ClientMessageDto(ACCESS_ERROR_MESSAGE))))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
        Mockito.verify(locationService, Mockito.never()).deleteLocation(
            LocationTestData.getLocationId());
    }

    @Test
    void LocationController_deleteLocation_withoutUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete(LOCATION_ENDPOINT + PATH_SEPARATOR + LocationTestData.getLocationId()))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(
                        new ClientMessageDto(SECURITY_ERROR_MESSAGE))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        Mockito.verify(locationService, Mockito.never()).deleteLocation(
                LocationTestData.getLocationId());
    }

}
