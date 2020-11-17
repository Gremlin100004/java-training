package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class LocationServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    private static final Long LOCATION_ID = 1L;
    private static final Long LOCATION_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_LOCATIONS = 2L;
    private static final String COUNTRY = "Test";
    private static final String CITY = "Test";
    @Autowired
    LocationService locationService;
    @Autowired
    LocationDao locationDao;

    @Test
    void LocationServiceImpl_getLocations() {
        List<Location> locations = getTestLocations();
        List<LocationDto> locationsDto = getTestLocationsDto();
        Mockito.doReturn(locations).when(locationDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<LocationDto> resultLocationsDto = locationService.getLocations(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultLocationsDto);
        Assertions.assertEquals(RIGHT_NUMBER_LOCATIONS, resultLocationsDto.size());
        Assertions.assertFalse(resultLocationsDto.isEmpty());
        Assertions.assertEquals(resultLocationsDto, locationsDto);
        Mockito.verify(locationDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_addLocation() {
        Location location = getTestLocation();
        LocationDto locationDto = getTestLocationDto();
        locationDto.setId(null);
        Mockito.doReturn(location).when(locationDao).saveRecord(ArgumentMatchers.any(Location.class));

        LocationDto resultLocationDto = locationService.addLocation(locationDto);
        Assertions.assertNotNull(resultLocationDto);
        Mockito.verify(locationDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(Location.class));
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_updateLocation() {
        Location location = getTestLocation();
        LocationDto locationDto = getTestLocationDto();
        Mockito.doReturn(location).when(locationDao).findById(LOCATION_ID);

        Assertions.assertDoesNotThrow(() -> locationService.updateLocation(locationDto));
        Mockito.verify(locationDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Location.class));
        Mockito.verify(locationDao, Mockito.times(1)).findById(LOCATION_ID);
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_deleteLocation() {
        Location location = getTestLocation();
        Mockito.doReturn(location).when(locationDao).findById(LOCATION_ID);

        Assertions.assertDoesNotThrow(() -> locationService.deleteLocation(LOCATION_ID));
        Mockito.verify(locationDao, Mockito.times(1)).deleteRecord(LOCATION_ID);
        Mockito.verify(locationDao, Mockito.times(1)).findById(LOCATION_ID);
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_deleteLocation_locationDao_findById_nullObject() {
        Mockito.doReturn(null).when(locationDao).findById(LOCATION_ID);

        Assertions.assertThrows(BusinessException.class, () -> locationService.deleteLocation(LOCATION_ID));
        Mockito.verify(locationDao, Mockito.times(1)).findById(LOCATION_ID);
        Mockito.verify(locationDao, Mockito.never()).deleteRecord(LOCATION_ID);
        Mockito.reset(locationDao);
    }

    private Location getTestLocation() {
        Location location = new Location();
        location.setId(LOCATION_ID);
        location.setCountry(COUNTRY);
        location.setCity(CITY);
        return location;
    }

    private LocationDto getTestLocationDto() {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(LOCATION_ID);
        locationDto.setCountry(COUNTRY);
        locationDto.setCity(CITY);
        return locationDto;
    }

    private List<Location> getTestLocations() {
        Location locationOne = getTestLocation();
        Location locationTwo = getTestLocation();
        locationTwo.setId(LOCATION_OTHER_ID);
        List<Location> privateMessages = new ArrayList<>();
        privateMessages.add(locationOne);
        privateMessages.add(locationTwo);
        return privateMessages;
    }

    private List<LocationDto> getTestLocationsDto() {
        LocationDto locationDtoOne = getTestLocationDto();
        LocationDto locationDtoTwo = getTestLocationDto();
        locationDtoTwo.setId(LOCATION_OTHER_ID);
        List<LocationDto> locationsDto = new ArrayList<>();
        locationsDto.add(locationDtoOne);
        locationsDto.add(locationDtoTwo);
        return locationsDto;
    }

}
