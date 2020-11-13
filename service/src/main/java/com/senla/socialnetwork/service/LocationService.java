package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dto.LocationDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocationService {
    @Transactional
    List<LocationDto> getLocations();

    @Transactional
    LocationDto addLocation(LocationDto locationDto);

    @Transactional
    void updateLocation(LocationDto locationDto);

    @Transactional
    void deleteLocation(Long locationId);
}
