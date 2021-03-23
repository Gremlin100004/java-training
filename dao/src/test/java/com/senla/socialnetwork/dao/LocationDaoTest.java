package com.senla.socialnetwork.dao;

import com.senla.socialnetwork.dao.enumaration.ArrayIndex;
import com.senla.socialnetwork.dao.testdata.UserTestData;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.model.School;
import com.senla.socialnetwork.model.University;
import com.senla.socialnetwork.model.UserProfile;
import com.senla.socialnetwork.model.WeatherCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LocationDaoTest extends AbstractDaoTest {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULT = 0;
    private static final String UNEXPECTED_EMAIL = "email";
    private static final String CITY = "test";
    private static final String COUNTRY = "test";
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private UserProfileDao userProfileDao;
    @Autowired
    private UniversityDao universityDao;
    @Autowired
    private WeatherConditionDao weatherConditionDao;

    @Test
    void LocationDao_getLocation() {
        String email = UserTestData.getSystemUsers().get(ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getEmail();

        Location location = locationDao.getLocation(email);
        Assertions.assertNotNull(location);
        Assertions.assertEquals(location, testDataUtil.getLocationByEmail(email));
    }

    @Test
    void LocationDao_getLocation_nullObject() {
        Location location = locationDao.getLocation(UNEXPECTED_EMAIL);
        Assertions.assertNull(location);
    }

    @Test
    void LocationDao_saveRecord() {
        Location location = new Location();
        location.setCity(CITY);
        location.setCountry(COUNTRY);

        locationDao.save(location);
        Assertions.assertNotNull(location.getId());
    }

    @Test
    void LocationDao_findById() {
        Location resultLocation = locationDao.findById(testDataUtil.getLocations().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index).getId());
        Assertions.assertNotNull(resultLocation);
        Assertions.assertEquals(testDataUtil.getLocations().get(
                ArrayIndex.FIRST_INDEX_OF_ARRAY.index), resultLocation);
    }

    @Test
    void LocationDao_findById_ErrorId() {
        Location resultLocation = locationDao.findById((long) ArrayIndex.NINTH_INDEX_OF_ARRAY.index);
        Assertions.assertNull(resultLocation);
    }

    @Test
    void LocationDao_getAllRecords() {
        List<Location> resultLocations = locationDao.getAllRecords(FIRST_RESULT, MAX_RESULT);
        Assertions.assertNotNull(resultLocations);
        Assertions.assertFalse(resultLocations.isEmpty());
        Assertions.assertEquals(resultLocations.size(), testDataUtil.getLocations().size());
        Assertions.assertEquals(resultLocations, testDataUtil.getLocations());
    }

    @Test
    void LocationDao_updateRecord() {
        Location location = testDataUtil.getLocations().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        location.setCity(CITY);

        locationDao.updateRecord(location);
        Location resultLocation = locationDao.findById(location.getId());
        Assertions.assertNotNull(resultLocation);
        Assertions.assertEquals(location, resultLocation);
    }

    @Test
    void LocationDao_deleteRecord() {
        Location location = testDataUtil.getLocations().get(ArrayIndex.SECOND_INDEX_OF_ARRAY.index);
        location.getSchools().forEach(school -> {
            school.getUserProfiles().forEach(userProfile -> userProfile.setSchool(null));
            schoolDao.deleteRecord(school);
        });
        location.getUniversities().forEach(university -> {
            university.getUserProfiles().forEach(userProfile -> userProfile.setUniversity(null));
            universityDao.deleteRecord(university);
        });
        location.getWeatherConditions().forEach(weatherCondition -> weatherConditionDao.deleteRecord(weatherCondition));
        location.getUserProfiles().forEach(userProfile -> userProfile.setLocation(null));

        locationDao.deleteRecord(location);
        Location resultLocation = locationDao.findById(location.getId());
        Assertions.assertNull(resultLocation);
    }

}
