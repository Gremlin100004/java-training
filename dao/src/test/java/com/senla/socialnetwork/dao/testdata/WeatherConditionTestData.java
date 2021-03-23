package com.senla.socialnetwork.dao.testdata;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.WeatherCondition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public final class WeatherConditionTestData {
    private static final String[] STATUS_ARRAY = {
            "Clear sky",
            "Few clouds",
            "Scattered Clouds",
            "Broken clouds",
            "Shower rain",
            "Rain",
            "Thunderstorm"
    };
    private static final List<Date> REGISTRATION_DATES = Arrays.asList(
            UserProfileTestData.getDateTime("2020-11-11 12:00"),
            UserProfileTestData.getDateTime("2020-11-11 12:01"),
            UserProfileTestData.getDateTime("2020-11-11 12:03"),
            UserProfileTestData.getDateTime("2020-11-11 12:04"),
            UserProfileTestData.getDateTime("2020-11-11 12:05"),
            UserProfileTestData.getDateTime("2020-11-01 10:01"),
            UserProfileTestData.getDateTime("2020-11-01 12:02"));

    private WeatherConditionTestData() {
    }

    public static List<WeatherCondition> getWeatherConditions(List<Location> locations) {
        List<WeatherCondition> weatherConditions = new ArrayList<>();
        weatherConditions.add(getWeatherCondition(REGISTRATION_DATES.get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), locations.get(
                        ArrayIndex.FIRST_INDEX_OF_ARRAY.index), STATUS_ARRAY[ArrayIndex.FIRST_INDEX_OF_ARRAY.index]));
        weatherConditions.add(getWeatherCondition(REGISTRATION_DATES.get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index), locations.get(
                        ArrayIndex.SECOND_INDEX_OF_ARRAY.index), STATUS_ARRAY[ArrayIndex.SECOND_INDEX_OF_ARRAY.index]));
        weatherConditions.add(getWeatherCondition(REGISTRATION_DATES.get(
                ArrayIndex.THIRD_INDEX_OF_ARRAY.index), locations.get(
                        ArrayIndex.THIRD_INDEX_OF_ARRAY.index), STATUS_ARRAY[ArrayIndex.THIRD_INDEX_OF_ARRAY.index]));
        weatherConditions.add(getWeatherCondition(REGISTRATION_DATES.get(
                ArrayIndex.FOURTH_INDEX_OF_ARRAY.index), locations.get(
                        ArrayIndex.FOURTH_INDEX_OF_ARRAY.index), STATUS_ARRAY[ArrayIndex.FOURTH_INDEX_OF_ARRAY.index]));
        weatherConditions.add(getWeatherCondition(REGISTRATION_DATES.get(
                ArrayIndex.FIFTH_INDEX_OF_ARRAY.index), locations.get(
                        ArrayIndex.FIFTH_INDEX_OF_ARRAY.index), STATUS_ARRAY[ArrayIndex.FIFTH_INDEX_OF_ARRAY.index]));
        weatherConditions.add(getWeatherCondition(REGISTRATION_DATES.get(
                ArrayIndex.SIXTH_INDEX_OF_ARRAY.index), locations.get(
                        ArrayIndex.SIXTH_INDEX_OF_ARRAY.index), STATUS_ARRAY[ArrayIndex.SIXTH_INDEX_OF_ARRAY.index]));
        weatherConditions.add(getWeatherCondition(REGISTRATION_DATES.get(
                ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index), locations.get(
                        ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index), STATUS_ARRAY[ArrayIndex.SEVENTH_INDEX_OF_ARRAY.index]));
        return weatherConditions;
    }

    private static WeatherCondition getWeatherCondition(Date registrationDate, Location location, String status) {
        WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setLocation(location);
        weatherCondition.setStatus(status);
        weatherCondition.setRegistrationDate(registrationDate);
        return weatherCondition;
    }

}
