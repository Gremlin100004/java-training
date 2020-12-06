package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.WeatherConditionDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.WeatherCondition;
import com.senla.socialnetwork.dto.WeatherConditionDto;
import com.senla.socialnetwork.dto.WeatherConditionForAdminDto;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.config.WeatherConditionTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class WeatherConditionServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    private WeatherConditionService weatherConditionService;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private WeatherConditionDao weatherConditionDao;

    @Test
    void WeatherConditionServiceImpl_getWeatherConditions() {
        List<WeatherCondition> weatherConditions = WeatherConditionTestData.getTestWeatherConditions();
        Mockito.doReturn(weatherConditions).when(weatherConditionDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<WeatherConditionForAdminDto> resultWeatherConditionsDto = weatherConditionService.getWeatherConditions(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultWeatherConditionsDto);
        Assertions.assertEquals(
            WeatherConditionTestData.getRightNumberWeatherConditions(), resultWeatherConditionsDto.size());
        Assertions.assertFalse(resultWeatherConditionsDto.isEmpty());
        Mockito.verify(weatherConditionDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(weatherConditionDao);
    }

    @Test
    void WeatherConditionServiceImpl_getWeatherCondition() {
        WeatherCondition weatherCondition = WeatherConditionTestData.getTestWeatherCondition();
        Location location = LocationTestData.getTestLocation();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(location).when(locationDao).getLocation(UserTestData.getEmail());
        Mockito.doReturn(weatherCondition).when(weatherConditionDao).findByLocation(location);

        WeatherConditionDto resultWeatherConditionDto = weatherConditionService.getWeatherCondition();
        Assertions.assertNotNull(resultWeatherConditionDto);
        Mockito.verify(locationDao, Mockito.times(1)).getLocation(UserTestData.getEmail());
        Mockito.verify(weatherConditionDao, Mockito.times(1)).findByLocation(location);
        Mockito.verify(weatherConditionDao, Mockito.never()).saveRecord(
            ArgumentMatchers.any(WeatherCondition.class));
        Mockito.reset(weatherConditionDao);
        Mockito.reset(locationDao);
    }

    @Test
    void WeatherConditionServiceImpl_getWeatherCondition_locationDao_getLocation_nullObject() {
        WeatherCondition weatherCondition = WeatherConditionTestData.getTestWeatherCondition();
        Location location = LocationTestData.getTestLocation();
        SecurityContextHolder.getContext().setAuthentication(UserTestData.getUsernamePasswordAuthenticationToken());
        Mockito.doReturn(null).when(locationDao).getLocation(UserTestData.getEmail());
        Mockito.doReturn(weatherCondition).when(weatherConditionDao).findByLocation(location);

        Assertions.assertThrows(BusinessException.class, () -> weatherConditionService.getWeatherCondition());
        Mockito.verify(locationDao, Mockito.times(1)).getLocation(UserTestData.getEmail());
        Mockito.verify(weatherConditionDao, Mockito.never()).findByLocation(location);
        Mockito.verify(weatherConditionDao, Mockito.never()).saveRecord(
            ArgumentMatchers.any(WeatherCondition.class));
        Mockito.reset(weatherConditionDao);
        Mockito.reset(locationDao);
    }

    @Test
    void WeatherConditionServiceImpl_deleteWeatherCondition() {
        WeatherCondition location = WeatherConditionTestData.getTestWeatherCondition();
        Mockito.doReturn(location).when(weatherConditionDao).findById(WeatherConditionTestData.getWeatherId());

        Assertions.assertDoesNotThrow(() -> weatherConditionService.deleteWeatherCondition(
            WeatherConditionTestData.getWeatherId()));
        Mockito.verify(weatherConditionDao, Mockito.times(1)).deleteRecord(location);
        Mockito.verify(weatherConditionDao, Mockito.times(1)).findById(
            WeatherConditionTestData.getWeatherId());
        Mockito.reset(weatherConditionDao);
    }

    @Test
    void WeatherConditionServiceImpl_deleteWeatherCondition_weatherConditionDao_findById_nullObject() {
        WeatherCondition location = WeatherConditionTestData.getTestWeatherCondition();
        Mockito.doReturn(null).when(weatherConditionDao).findById(WeatherConditionTestData.getWeatherId());

        Assertions.assertThrows(BusinessException.class, () -> weatherConditionService.deleteWeatherCondition(
            WeatherConditionTestData.getWeatherId()));
        Mockito.verify(weatherConditionDao, Mockito.times(1)).findById(
            WeatherConditionTestData.getWeatherId());
        Mockito.verify(weatherConditionDao, Mockito.never()).deleteRecord(location);
        Mockito.reset(weatherConditionDao);
    }

}
