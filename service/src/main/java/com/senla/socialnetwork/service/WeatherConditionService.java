package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.WeatherConditionDto;

public interface WeatherConditionService {
    WeatherConditionDto getWeatherCondition(String email);

}
