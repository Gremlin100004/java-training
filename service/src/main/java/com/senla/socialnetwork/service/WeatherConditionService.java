package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.dto.WeatherConditionForAdminDto;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface WeatherConditionService {
    List<WeatherConditionForAdminDto> getWeatherConditions(int firstResult, int maxResults);

    WeatherConditionDto getWeatherCondition();

    void deleteWeatherCondition(Long weatherConditionId);

}
