package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.WeatherConditionDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface WeatherConditionService {
    List<WeatherConditionDto> getWeatherConditions(int firstResult, int maxResults);

    WeatherConditionDto getWeatherCondition(HttpServletRequest request);

    void deleteWeatherCondition(Long weatherConditionId);

}
