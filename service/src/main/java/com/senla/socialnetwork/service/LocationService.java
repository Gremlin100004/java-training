package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;

import java.util.List;

public interface LocationService {
    List<LocationDto> getLocations(int firstResult, int maxResults);

    LocationDto addLocation(LocationForCreateDto locationDto);

    void updateLocation(LocationDto locationDto);

    void deleteLocation(Long locationId);

}
