package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;

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

    public static Location getTestLocation() {
        Location location = new Location();
        location.setId(LOCATION_ID);
        location.setCountry(COUNTRY);
        location.setCity(CITY);
        return location;
    }

    public static LocationDto getTestLocationDto() {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(LOCATION_ID);
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

    public static List<Location> getTestLocations() {
        Location locationOne = getTestLocation();
        Location locationTwo = getTestLocation();
        locationTwo.setId(LOCATION_OTHER_ID);
        List<Location> privateMessages = new ArrayList<>();
        privateMessages.add(locationOne);
        privateMessages.add(locationTwo);
        return privateMessages;
    }

    public static List<LocationDto> getTestLocationsDto() {
        LocationDto locationDtoOne = getTestLocationDto();
        LocationDto locationDtoTwo = getTestLocationDto();
        locationDtoTwo.setId(LOCATION_OTHER_ID);
        List<LocationDto> locationsDto = new ArrayList<>();
        locationsDto.add(locationDtoOne);
        locationsDto.add(locationDtoTwo);
        return locationsDto;
    }

}
