package com.senla.socialnetwork.controller.config;

import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;
import com.senla.socialnetwork.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class LocationTestData {
    private static final Long LOCATION_ID = 1L;
    private static final Long LOCATION_OTHER_ID = 2L;
    private static final Long RIGHT_NUMBER_LOCATIONS = 2L;
    private static final String COUNTRY = "Test";
    private static final String CITY = "Test";

    public static Long getLocationId() {
        return LOCATION_ID;
    }

    public static Long getRightNumberLocations() {
        return RIGHT_NUMBER_LOCATIONS;
    }

    public static Location getLocation() {
        Location location = new Location();
        location.setId(LOCATION_ID);
        location.setCountry(COUNTRY);
        location.setCity(CITY);
        return location;
    }

    public static LocationDto getLocationDto() {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(LOCATION_ID);
        locationDto.setCountry(COUNTRY);
        locationDto.setCity(CITY);
        return locationDto;
    }

    public static LocationForCreateDto getLocationForCreateDto() {
        LocationForCreateDto locationDto = new LocationForCreateDto();
        locationDto.setCountry(COUNTRY);
        locationDto.setCity(CITY);
        return locationDto;
    }

    public static LocationForCreateDto getTestLocationForCreationDto() {
        LocationForCreateDto locationDto = new LocationForCreateDto();
        locationDto.setCountry(COUNTRY);
        locationDto.setCity(CITY);
        return locationDto;
    }

    public static Page<Location> getTestLocations() {
        Location locationOne = getLocation();
        Location locationTwo = getLocation();
        locationTwo.setId(LOCATION_OTHER_ID);
        List<Location> locations = new ArrayList<>();
        locations.add(locationOne);
        locations.add(locationTwo);
        return new PageImpl<>(locations, Pageable.unpaged(), 2L);
    }

    public static List<LocationDto> getTestLocationsDto() {
        LocationDto locationDtoOne = getLocationDto();
        LocationDto locationDtoTwo = getLocationDto();
        locationDtoTwo.setId(LOCATION_OTHER_ID);
        List<LocationDto> locationsDto = new ArrayList<>();
        locationsDto.add(locationDtoOne);
        locationsDto.add(locationDtoTwo);
        return locationsDto;
    }

}
