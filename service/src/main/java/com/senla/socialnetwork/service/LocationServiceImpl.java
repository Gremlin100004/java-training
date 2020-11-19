package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.LocationMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {
    @Autowired
    LocationDao locationDao;

    @Override
    @Transactional
    public List<LocationDto> getLocations(final int firstResult, final int maxResults) {
        log.debug("[getLocations]");
        return LocationMapper.getLocationDto(locationDao.getAllRecords(firstResult, maxResults));
    }

    @Override
    @Transactional
    public LocationDto addLocation(final LocationDto locationDto) {
        log.debug("[addLocation]");
        log.debug("[locationDto: {}]", locationDto);
        return LocationMapper.getLocationDto(locationDao.saveRecord(
            LocationMapper.getLocation(locationDto, locationDao)));
    }

    @Override
    @Transactional
    public void updateLocation(final LocationDto locationDto) {
        log.debug("[updateLocation]");
        log.debug("[locationDto: {}]", locationDto);
        locationDao.updateRecord(LocationMapper.getLocation(locationDto, locationDao));
    }

    @Override
    @Transactional
    public void deleteLocation(final Long locationId) {
        log.debug("[deleteLocation]");
        log.debug("[locationId: {}]", locationId);
        if (locationDao.findById(locationId) == null) {
            throw new BusinessException("Error, there is no such location");
        }
        locationDao.deleteRecord(locationId);
    }

}
