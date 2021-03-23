package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = TestConfig.class)
public class LocationServiceTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    private LocationService locationService;
    @Autowired
    private LocationSpringDataSpecificationDao locationDao;

    @Test
    void LocationServiceImpl_getLocations() {
        Page<Location> locations = LocationTestData.getTestLocations();
        Page<LocationDto> locationsDto = LocationTestData.getTestLocationsDto();
        List<LocationDto> locationDtoList = new ArrayList<>();
        locationsDto.forEach(locationDtoList::add);
        Mockito.doReturn(locations).when(locationDao).findAll(PageRequest.of(FIRST_RESULT, NORMAL_MAX_RESULTS));

        List<LocationDto> resultLocationsDto = locationService.getLocations(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultLocationsDto);
        Assertions.assertEquals(LocationTestData.getRightNumberLocations(), resultLocationsDto.size());
        Assertions.assertFalse(resultLocationsDto.isEmpty());
        Assertions.assertEquals(resultLocationsDto, locationDtoList);
        Mockito.verify(locationDao, Mockito.times(1)).findAll(
            PageRequest.of(FIRST_RESULT, NORMAL_MAX_RESULTS));
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_addLocation() {
        Location location = LocationTestData.getTestLocation();
        LocationForCreateDto locationDto = LocationTestData.getTestLocationForCreationDto();
        Mockito.doReturn(location).when(locationDao).save(ArgumentMatchers.any(Location.class));

        LocationDto resultLocationDto = locationService.addLocation(locationDto);
        Assertions.assertNotNull(resultLocationDto);
        Mockito.verify(locationDao, Mockito.times(1)).save(
            ArgumentMatchers.any(Location.class));
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_updateLocation() {
        Optional<Location> location = Optional.of(LocationTestData.getTestLocation());
        LocationDto locationDto = LocationTestData.getTestLocationDto();
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertDoesNotThrow(() -> locationService.updateLocation(locationDto));
        Mockito.verify(locationDao, Mockito.times(1)).save(
            ArgumentMatchers.any(Location.class));
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_deleteLocation() {
        Optional<Location> location = Optional.of(LocationTestData.getTestLocation());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertDoesNotThrow(() -> locationService.deleteLocation(LocationTestData.getLocationId()));
        Mockito.verify(locationDao, Mockito.times(1)).delete(location.get());
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.reset(locationDao);
    }

    @Test
    void LocationServiceImpl_deleteLocation_locationDao_findById_nullObject() {
        Optional<Location> location = Optional.of(LocationTestData.getTestLocation());
        Optional<Location> emptyLocation = Optional.empty();
        Mockito.doReturn(emptyLocation).when(locationDao).findById(LocationTestData.getLocationId());

        Assertions.assertThrows(BusinessException.class, () -> locationService.deleteLocation(
            LocationTestData.getLocationId()));
        Mockito.verify(locationDao, Mockito.times(1)).findById(LocationTestData.getLocationId());
        Mockito.verify(locationDao, Mockito.never()).delete(location.get());
        Mockito.reset(locationDao);
    }

}
