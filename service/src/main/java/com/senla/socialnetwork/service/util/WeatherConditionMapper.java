package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.domain.WeatherCondition;
import com.senla.socialnetwork.dto.WeatherConditionDto;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherConditionMapper {

    public static WeatherConditionDto getWeatherConditionDto(final WeatherCondition weatherCondition) {
        WeatherConditionDto weatherConditionDto = new WeatherConditionDto();
        weatherConditionDto.setStatus(weatherCondition.getStatus());
        return weatherConditionDto;
    }

    public static List<WeatherConditionDto> getWeatherConditionDto(final List<WeatherCondition> weatherConditions) {
        return weatherConditions.stream()
            .map(WeatherConditionMapper::getWeatherConditionDto)
            .collect(Collectors.toList());
    }

}
