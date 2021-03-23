package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class LocationMapper {
    private LocationMapper() {
    }

    public static LocationDto getLocationDto(final Location location) {
        LocationDto locationDto = new LocationDto();
        locationDto.setId(location.getId());
        locationDto.setCity(location.getCity());
        locationDto.setCountry(location.getCountry());
        return locationDto;
    }

    public static List<LocationDto> getLocationDto(final Page<Location> locations) {
       return locations.stream()
           .map(LocationMapper::getLocationDto)
           .collect(Collectors.toList());
    }

    public static Location getLocation(final LocationDto locationDto, final LocationSpringDataSpecificationDao locationDao) {
        Optional<Location> location = locationDao.findById(locationDto.getId());
        if (!location.isPresent()) {
            throw new BusinessException("Error, there is no such location");
        }
        location.get().setCity(locationDto.getCity());
        location.get().setCountry(locationDto.getCountry());
        return location.get();
    }

    public static Location getNewLocation(final LocationForCreateDto locationDto) {
        Location location = new Location();
        location.setCity(locationDto.getCity());
        location.setCountry(locationDto.getCountry());
        return location;
    }

}
