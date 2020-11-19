package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.service.config.LocationTestData;
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

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class LocationServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    LocationService locationService;
    @Autowired
    LocationDao locationDao;

    @Test
    void LocationServiceImpl_getLocations() {
        List<Location> locations = LocationTestData.getTestLocations();
        List<LocationDto> locationsDto = LocationTestData.getTestLocationsDto();
        Mockito.doReturn(locations).when(locationDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<LocationDto> resultLocationsDto = locationService.getLocations(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultLocationsDto);
        Assertions.assertEquals(LocationTestData.getRightNumberLocations(), resultLocationsDto.size());
        Assertions.assertFalse(resultLocationsDto.isEmpty());
        Assertions.assertEquals(resultLocationsDto, locationsDto);
        Mockito.verify(locationDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_addLocation() {
        Location location = LocationTestData.getTestLocation();
        LocationDto locationDto = LocationTestData.getTestLocationDto();
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
        Location location = LocationTestData.getTestLocation();
        LocationDto locationDto = LocationTestData.getTestLocationDto();
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertDoesNotThrow(() -> locationService.updateLocation(locationDto));
        Mockito.verify(locationDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(Location.class));
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_deleteLocation() {
        Location location = LocationTestData.getTestLocation();
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertDoesNotThrow(() -> locationService.deleteLocation(LocationTestData.getLocationId()));
        Mockito.verify(locationDao, Mockito.times(1)).deleteRecord(LocationTestData.getLocationId());
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_deleteLocation_locationDao_findById_nullObject() {
        Mockito.doReturn(null).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertThrows(BusinessException.class, () -> locationService.deleteLocation(
            LocationTestData.getLocationId()));
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(locationDao, Mockito.never()).deleteRecord(LocationTestData.getLocationId());
        Mockito.reset(locationDao);
    }

}
