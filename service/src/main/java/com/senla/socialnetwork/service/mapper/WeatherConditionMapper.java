package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.domain.WeatherCondition;
import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.dto.WeatherConditionForAdminDto;

import java.util.List;
import java.util.stream.Collectors;

public class WeatherConditionMapper {

    public static WeatherConditionDto getWeatherConditionDto(final WeatherCondition weatherCondition) {
        WeatherConditionDto weatherConditionDto = new WeatherConditionDto();
        weatherConditionDto.setStatus(weatherCondition.getStatus());
        return weatherConditionDto;
    }

    public static WeatherConditionForAdminDto getWeatherConditionForAdminDto(final WeatherCondition weatherCondition) {
        WeatherConditionForAdminDto weatherConditionDto = new WeatherConditionForAdminDto();
        weatherConditionDto.setId(weatherCondition.getId());
        weatherConditionDto.setRegistrationDate(weatherCondition.getRegistrationDate());
        weatherConditionDto.setLocation(LocationMapper.getLocationDto(weatherCondition.getLocation()));
        weatherConditionDto.setStatus(weatherCondition.getStatus());
        return weatherConditionDto;
    }

    public static List<WeatherConditionForAdminDto> getWeatherConditionDto(final List<WeatherCondition> weatherConditions) {
        return weatherConditions.stream()
            .map(WeatherConditionMapper::getWeatherConditionForAdminDto)
            .collect(Collectors.toList());
    }

}
