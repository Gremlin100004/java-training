package com.senla.socialnetwork.controller.config;

import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.dto.WeatherConditionForAdminDto;
import com.senla.socialnetwork.model.WeatherCondition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WeatherConditionTestData {
    private static final Long WEATHER_ID = 1L;
    private static final Long WEATHER_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_WEATHER_CONDITIONS = 2L;
    private static final Date REGISTRATION_DATE = new Date();
    private static final String STATUS = "Test";

    public static Long getWeatherId() {
        return WEATHER_ID;
    }

    public static Long getRightNumberWeatherConditions() {
        return RIGHT_NUMBER_WEATHER_CONDITIONS;
    }

    public static WeatherCondition getTestWeatherCondition() {
        WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setId(WEATHER_ID);
        weatherCondition.setStatus(STATUS);
        weatherCondition.setRegistrationDate(REGISTRATION_DATE);
        weatherCondition.setLocation(LocationTestData.getLocation());
        return weatherCondition;
    }

    public static List<WeatherCondition> getTestWeatherConditions() {
        WeatherCondition weatherConditionOne = getTestWeatherCondition();
        WeatherCondition weatherConditionTwo = getTestWeatherCondition();
        weatherConditionTwo.setId(WEATHER_OTHER_ID);
        List<WeatherCondition> weatherConditions = new ArrayList<>();
        weatherConditions.add(weatherConditionOne);
        weatherConditions.add(weatherConditionTwo);
        return weatherConditions;
    }

    public static WeatherConditionForAdminDto getWeatherConditionForAdminDto() {
        WeatherConditionForAdminDto weatherConditionForAdminDto = new WeatherConditionForAdminDto();
        weatherConditionForAdminDto.setLocation(LocationTestData.getLocationDto());
        weatherConditionForAdminDto.setRegistrationDate(REGISTRATION_DATE);
        weatherConditionForAdminDto.setStatus(STATUS);
        weatherConditionForAdminDto.setId(WEATHER_ID);
        return weatherConditionForAdminDto;
    }

    public static List<WeatherConditionForAdminDto> getWeatherConditionsForAdminDto() {
        WeatherConditionForAdminDto weatherConditionForAdminDto = getWeatherConditionForAdminDto();
        WeatherConditionForAdminDto weatherConditionForAdminDtoOther = getWeatherConditionForAdminDto();
        weatherConditionForAdminDtoOther.setId(WEATHER_OTHER_ID);
        List<WeatherConditionForAdminDto> weatherConditionsForAdminDto = new ArrayList<>();
        weatherConditionsForAdminDto.add(weatherConditionForAdminDto);
        weatherConditionsForAdminDto.add(weatherConditionForAdminDtoOther);
        return weatherConditionsForAdminDto;
    }

    public static WeatherConditionDto getWeatherConditionDto() {
        WeatherConditionDto weatherCondition = new WeatherConditionDto();
        weatherCondition.setStatus(STATUS);
        return weatherCondition;
    }

}
