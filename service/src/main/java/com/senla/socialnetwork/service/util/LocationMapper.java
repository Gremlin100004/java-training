package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.dto.LocationDto;

public class LocationMapper {
    public static LocationDto getLocationDto(Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setCity(location.getCity());
        locationDto.setCountry(location.getCountry());
        return locationDto;
    }

    public static Location getLocation(LocationDto locationDto, LocationDao locationDao) {
        Location location;
        if (locationDto.getId() == null) {
            location = new Location();
        } else {
            location = locationDao.findById(locationDto.getId());
        }
        location.setCity(locationDto.getCity());
        location.setCountry(locationDto.getCountry());
        return location;
    }

}
