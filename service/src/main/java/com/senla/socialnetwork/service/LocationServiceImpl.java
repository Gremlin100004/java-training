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
    public List<LocationDto> getLocations() {
        log.debug("[getLocations]");
        return LocationMapper.getLocationDto(locationDao.getAllRecords());
    }

    @Override
    @Transactional
    public LocationDto addLocation(LocationDto locationDto) {
        log.debug("[addLocation]");
        log.debug("[locationDto: {}]", locationDto);
        if (locationDto == null) {
            throw new BusinessException("Error, null location");
        }
        return LocationMapper.getLocationDto(locationDao.saveRecord(
            LocationMapper.getLocation(locationDto, locationDao)));
    }

    @Override
    @Transactional
    public void updateLocation(LocationDto locationDto) {
        log.debug("[updateLocation]");
        log.debug("[locationDto: {}]", locationDto);
        if (locationDto == null) {
            throw new BusinessException("Error, null location");
        }
        locationDao.updateRecord(LocationMapper.getLocation(locationDto, locationDao));
    }

    @Override
    @Transactional
    public void deleteLocation(Long locationId) {
        log.debug("[deleteLocation]");
        log.debug("[locationId: {}]", locationId);
        locationDao.deleteRecord(locationId);
    }

}
