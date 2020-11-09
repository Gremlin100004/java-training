package com.senla.socialnetwork.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.dao.WeatherConditionDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.WeatherCondition;
import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.WeatherConditionMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
@NoArgsConstructor
@Slf4j
public class WeatherConditionServiceImpl implements WeatherConditionService {
    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String LOCATION_PARAMETER = "?q=";
    private static final String PARAMETERS_SEPARATOR = ",";
    private static final String KEY_PARAMETER = "&appid=";
    private static final String RESPONSE_ERROR = "Error getting weather status";
    private static final String JSON_WEATHER_KEY = "weather";
    private static final String JSON_WEATHER_CONDITION_KEY = "main";
    private static final int MILLISECONDS_IN_SECONDS = 1000;
    @Value("${com.senla.socialnetwork.service.WeatherConditionServiceImpl.weatherKey:key}")
    private String weatherKey;
    @Value("${com.senla.socialnetwork.service.WeatherConditionServiceImpl.updateTime:2000}")
    private Long updateTime;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private WeatherConditionDao weatherConditionDao;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    @Transactional
    public WeatherConditionDto getWeatherCondition(String email) {
        log.debug("[getWeatherCondition]");
        log.debug("[email: {}]", email);
        Location location = userProfileDao.getLocation(email);
        if (location == null) {
            throw new BusinessException("There is no location");
        }
        WeatherCondition weatherCondition = weatherConditionDao.findByLocation(location);
        if (weatherCondition == null) {
            weatherCondition = getWeatherCondition(location);
            weatherConditionDao.saveRecord(weatherCondition);
        } else if (getTimeWithoutUpdate(weatherCondition.getRegistrationDate().getTime()) > updateTime) {
            updateWeatherCondition(weatherCondition, location);
        }
        return WeatherConditionMapper.getWeatherConditionDto(weatherCondition);
    }

    private long getTimeWithoutUpdate(long registrationDate) {
        return (new Date().getTime() - registrationDate) / MILLISECONDS_IN_SECONDS;
    }

    private WeatherCondition getWeatherCondition(Location location) {
        String status = getStatus(location);
        if (status == null) {
            throw new BusinessException("Error getting weather conditions");
        }
        WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setRegistrationDate(new Date());
        weatherCondition.setLocation(location);
        weatherCondition.setStatus(status);
        return weatherCondition;
    }

    private void updateWeatherCondition(WeatherCondition weatherCondition, Location location) {
        String condition = getStatus(location);
        if (condition != null) {
            weatherCondition.setStatus(condition);
            weatherCondition.setRegistrationDate(new Date());
        }
        weatherConditionDao.saveRecord(weatherCondition);
    }

    private String getStatus(Location location) {
        try {
            String url = WEATHER_API_URL + LOCATION_PARAMETER + location.getCity() + PARAMETERS_SEPARATOR +
                location.getCountry() + KEY_PARAMETER + weatherKey;
            System.out.println(url);
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String stringJson = response.getBody();
            if (stringJson == null) {
                throw new BusinessException(RESPONSE_ERROR);
            }
            return objectMapper
                .readTree(stringJson)
                .get(JSON_WEATHER_KEY)
                .get(0)
                .get(JSON_WEATHER_CONDITION_KEY)
                .asText();
        } catch (JsonProcessingException exception) {
            log.error(exception.getMessage());
            return null;
        } catch (HttpClientErrorException exception) {
            log.error(exception.getResponseBodyAsString());
            return null;
        }
    }

}
