package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.service.WeatherConditionService;
import io.swagger.annotations.Api;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/weatherConditions")
@Api(tags = "Weather Conditions")
@NoArgsConstructor
public class WeatherConditionController {
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final String BAD_REQUEST_MESSAGE = "Successfully retrieved list";
    public static final String UNAUTHORIZED_MESSAGE = "You are not authorized to view the resource";
    public static final String FORBIDDEN_MESSAGE = "Accessing the resource you were trying to reach is forbidden";
    public static final String NOT_FOUND_MESSAGE = "The resource you were trying to reach is not found";
    @Autowired
    private WeatherConditionService weatherConditionService;

    @GetMapping
    public List<WeatherConditionDto> getWeatherConditions(final int firstResult, final int maxResults) {
        return weatherConditionService.getWeatherConditions(firstResult, maxResults);
    }

    @GetMapping("/location")
    public WeatherConditionDto getWeatherCondition(final HttpServletRequest request) {
        return weatherConditionService.getWeatherCondition(request);
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteWeatherCondition(final Long weatherConditionId) {
        weatherConditionService.deleteWeatherCondition(weatherConditionId);
        return new ClientMessageDto("Weather condition deleted successfully");
    }

}
