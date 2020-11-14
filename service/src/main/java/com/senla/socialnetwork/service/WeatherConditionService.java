package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.WeatherConditionDto;

import java.util.List;

public interface WeatherConditionService {
    List<WeatherConditionDto> getWeatherConditions(int firstResult, int maxResults);

    WeatherConditionDto getWeatherCondition(String email);

    void deleteWeatherCondition(Long weatherConditionId);

}
