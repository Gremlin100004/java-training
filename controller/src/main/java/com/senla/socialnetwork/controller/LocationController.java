package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;
import com.senla.socialnetwork.service.LocationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/locations")
@Api(tags = "Locations")
@NoArgsConstructor
public class LocationController {
    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_LOCATIONS_OK_MESSAGE = "Successfully retrieved list of locations";
    public static final String RETURN_LOCATION_OK_MESSAGE = "Successfully retrieved a location";
    public static final String UPDATE_LOCATION_OK_MESSAGE = "Successfully updated a location";
    public static final String DELETE_LOCATION_OK_MESSAGE = "Successfully deleted a location";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String LOCATION_DTO_DESCRIPTION = "DTO location";
    public static final String LOCATION_ID_DESCRIPTION = "Location id";
    public static final String LOCATION_ID_EXAMPLE = "Location id";
    public static final String GET_LOCATIONS_DESCRIPTION = "This method is used to get locations";
    public static final String ADD_LOCATION_DESCRIPTION = "This method is used to add new location by admin";
    public static final String UPDATE_LOCATION_DESCRIPTION = "This method is used to update location by admin";
    public static final String DELETE_LOCATION_DESCRIPTION = "This method is used to delete location by admin";
    @Autowired
    LocationService locationService;

    @GetMapping
    @ApiOperation(value = GET_LOCATIONS_DESCRIPTION, response = LocationDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_LOCATIONS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<LocationDto> getLocations(@ApiParam(value = FIRST_RESULT_DESCRIPTION, example = FIRST_RESULT_EXAMPLE)
                                          @RequestParam int firstResult,
                                          @ApiParam(value = MAX_RESULTS_DESCRIPTION, example = MAX_RESULTS_EXAMPLE)
                                          @RequestParam int maxResults) {
        return locationService.getLocations(firstResult, maxResults);
    }

    @PostMapping
    @ApiOperation(value = ADD_LOCATION_DESCRIPTION, response = LocationDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = CREATED, message = RETURN_LOCATION_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto addLocation(@ApiParam(value = LOCATION_DTO_DESCRIPTION)
                                   @RequestBody @Valid LocationForCreateDto locationDto) {
        return locationService.addLocation(locationDto);
    }

    @PutMapping
    @ApiOperation(value = UPDATE_LOCATION_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = UPDATE_LOCATION_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto updateLocation(@ApiParam(value = LOCATION_DTO_DESCRIPTION)
                                           @RequestBody @Valid LocationDto locationDto) {
        locationService.updateLocation(locationDto);
        return new ClientMessageDto(UPDATE_LOCATION_OK_MESSAGE);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_LOCATION_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_LOCATION_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteLocation(@ApiParam(value = LOCATION_ID_DESCRIPTION,
                                                     example = LOCATION_ID_EXAMPLE)
                                           @PathVariable("id") Long locationId) {
        locationService.deleteLocation(locationId);
        return new ClientMessageDto(DELETE_LOCATION_OK_MESSAGE);
    }

}
