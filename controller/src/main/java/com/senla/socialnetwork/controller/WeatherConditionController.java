package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.controller.util.SecretKeyUtil;
import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.dto.WeatherConditionForAdminDto;
import com.senla.socialnetwork.service.WeatherConditionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/weatherConditions")
@Api(tags = "Weather Conditions")
@NoArgsConstructor
public class WeatherConditionController {
    public static final int OK = 200;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    public static final String RETURN_LIST_OF_WEATHER_CONDITIONS_OK_MESSAGE = "Successfully retrieved list of weather"
       + " conditions";
    public static final String RETURN_WEATHER_CONDITION_OK_MESSAGE = "Successfully retrieved a weather condition";
    public static final String DELETE_WEATHER_CONDITION_OK_MESSAGE = "Successfully deleted a weather condition";
    public static final String FIRST_RESULT_DESCRIPTION = "The number of the first element of the expected list";
    public static final String MAX_RESULTS_DESCRIPTION = "Maximum number of list elements";
    public static final String FIRST_RESULT_EXAMPLE = "1";
    public static final String MAX_RESULTS_EXAMPLE = "10";
    public static final String WEATHER_CONDITION_ID_DESCRIPTION = "Weather condition id";
    public static final String WEATHER_CONDITION_ID_EXAMPLE = "1";
    public static final String GET_WEATHER_CONDITIONS_DESCRIPTION = "This method is used to get weather conditions"
       + " by admin";
    public static final String GET_WEATHER_CONDITION_DESCRIPTION = "This method is used to get weather condition "
       + "by this user";
    public static final String DELETE_WEATHER_CONDITION_DESCRIPTION = "This method is used to delete weather "
       + "condition by admin";
    @Autowired
    private WeatherConditionService weatherConditionService;

    @Secured({"ROLE_ADMIN"})
    @GetMapping
    @ApiOperation(value = GET_WEATHER_CONDITIONS_DESCRIPTION, response = WeatherConditionForAdminDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_LIST_OF_WEATHER_CONDITIONS_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public List<WeatherConditionForAdminDto> getWeatherConditions(@ApiParam(value = FIRST_RESULT_DESCRIPTION,
                                                                            example = FIRST_RESULT_EXAMPLE)
                                                                  @RequestParam final int firstResult,
                                                                  @ApiParam(value = MAX_RESULTS_DESCRIPTION,
                                                                            example = MAX_RESULTS_EXAMPLE)
                                                                  @RequestParam final int maxResults) {
        return weatherConditionService.getWeatherConditions(firstResult, maxResults);
    }


    @GetMapping("/location")
    @ApiOperation(value = GET_WEATHER_CONDITION_DESCRIPTION, response = WeatherConditionDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = RETURN_WEATHER_CONDITION_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public WeatherConditionDto getWeatherCondition(final HttpServletRequest request) {
        return weatherConditionService.getWeatherCondition(request, SecretKeyUtil.getSecretKey());
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ApiOperation(value = DELETE_WEATHER_CONDITION_DESCRIPTION, response = ClientMessageDto.class)
    @ApiResponses(value = {
        @ApiResponse(code = OK, message = DELETE_WEATHER_CONDITION_OK_MESSAGE),
        @ApiResponse(code = UNAUTHORIZED, message = UNAUTHORIZED_MESSAGE),
        @ApiResponse(code = FORBIDDEN, message = FORBIDDEN_MESSAGE),
        @ApiResponse(code = NOT_FOUND, message = NOT_FOUND_MESSAGE)
    })
    public ClientMessageDto deleteWeatherCondition(@ApiParam(value = WEATHER_CONDITION_ID_DESCRIPTION,
                                                             example = WEATHER_CONDITION_ID_EXAMPLE)
                                                   @PathVariable("id") final Long weatherConditionId) {
        weatherConditionService.deleteWeatherCondition(weatherConditionId);
        return new ClientMessageDto(DELETE_WEATHER_CONDITION_OK_MESSAGE);
    }

}
