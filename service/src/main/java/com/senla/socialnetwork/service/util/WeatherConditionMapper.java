package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.domain.WeatherCondition;
import com.senla.socialnetwork.dto.WeatherConditionDto;

public class WeatherConditionMapper {

    public static WeatherConditionDto getWeatherConditionDto(WeatherCondition weatherCondition) {
        WeatherConditionDto weatherConditionDto = new WeatherConditionDto();
        weatherConditionDto.setStatus(weatherCondition.getStatus());
        return weatherConditionDto;
    }

}
