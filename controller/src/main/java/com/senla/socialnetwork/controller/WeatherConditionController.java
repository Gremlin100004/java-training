package com.senla.socialnetwork.controller;

import com.senla.socialnetwork.dto.ClientMessageDto;
import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.service.WeatherConditionService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/weatherCondition")
@NoArgsConstructor
public class WeatherConditionController {
    @Autowired
    private WeatherConditionService weatherConditionService;

    @GetMapping
    public List<WeatherConditionDto> getWeatherConditions(int firstResult, int maxResults) {
        return weatherConditionService.getWeatherConditions(firstResult, maxResults);
    }

    @GetMapping("/location")
    public WeatherConditionDto getWeatherCondition(Authentication authentication) {
        String email = authentication.getName();
        return weatherConditionService.getWeatherCondition(email);
    }

    @DeleteMapping("/{id}")
    public ClientMessageDto deleteWeatherCondition(Long weatherConditionId) {
        weatherConditionService.deleteWeatherCondition(weatherConditionId);
        return new ClientMessageDto("Weather condition deleted successfully");
    }

}