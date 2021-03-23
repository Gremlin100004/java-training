package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.model.WeatherCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class WeatherConditionDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final Date REGISTRATION_DATE = new Date();
    private static final String STATUS = "test";
    @Autowired
    private WeatherConditionDao weatherConditionDao;

    @Test
    void WeatherConditionDao_getAllRecord() {
        List<WeatherCondition> weatherConditions = weatherConditionDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(weatherConditions);
        Assertions.assertFalse(weatherConditions.isEmpty());
        Assertions.assertEquals(weatherConditions.size(), testDataUtil.getWeatherConditions().size());
        Assertions.assertEquals(weatherConditions, testDataUtil.getWeatherConditions());
    }

    @Test
    void WeatherConditionDao_findById() {
        WeatherCondition resultCondition = weatherConditionDao.findById(testDataUtil.getWeatherConditions().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultCondition);
        Assertions.assertEquals(testDataUtil.getWeatherConditions().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultCondition);
    }

    @Test
    void WeatherConditionDao_findById_ErrorId() {
        WeatherCondition resultCondition = weatherConditionDao.findById((long) ArrayIndex.NINTH_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultCondition);
    }

    @Test
    void WeatherConditionDao_saveRecord() {
        WeatherCondition weatherCondition = new WeatherCondition();
        weatherCondition.setRegistrationDate(REGISTRATION_DATE);
        weatherCondition.setStatus(STATUS);
        weatherCondition.setLocation(testDataUtil.getLocations().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index));

        weatherConditionDao.save(weatherCondition);
        Assertions.assertNotNull(weatherCondition.getId());
    }

    @Test
    void WeatherConditionDao_updateRecord() {
        WeatherCondition weatherCondition = testDataUtil.getWeatherConditions().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        weatherCondition.setStatus(STATUS);

        weatherConditionDao.updateRecord(weatherCondition);
        WeatherCondition resultUser = weatherConditionDao.findById(weatherCondition.getId());
        Assertions.assertNotNull(resultUser);
        Assertions.assertEquals(weatherCondition, resultUser);
    }

    @Test
    void WeatherConditionDao_deleteRecord() {
        WeatherCondition weatherCondition = testDataUtil.getWeatherConditions().get(
                ArrayIndex.SECOND_INDEX_OF_ARRAY.index);

        weatherConditionDao.deleteRecord(weatherCondition);
        WeatherCondition resultCondition = weatherConditionDao.findById(weatherCondition.getId());
        Assertions.assertNull(resultCondition);
    }

}
