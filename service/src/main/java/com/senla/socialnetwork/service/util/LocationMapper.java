package com.senla.socialnetwork.service.util;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.dto.LocationDto;

import java.util.List;
import java.util.stream.Collectors;

public class LocationMapper {
    public static LocationDto getLocationDto(final Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setCity(location.getCity());
        locationDto.setCountry(location.getCountry());
        return locationDto;
    }

    public static List<LocationDto> getLocationDto(final List<Location> locations) {
       return locations.stream()
           .map(LocationMapper::getLocationDto)
           .collect(Collectors.toList());
    }

    public static Location getLocation(final LocationDto locationDto, final LocationDao locationDao) {
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
