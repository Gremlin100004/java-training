package com.senla.socialnetwork.service;

import com.senla.socialnetwork.aspect.ServiceLog;
import com.senla.socialnetwork.dao.springdata.LocationSpringDataSpecificationDao;
import com.senla.socialnetwork.dto.LocationDto;
import com.senla.socialnetwork.dto.LocationForCreateDto;
import com.senla.socialnetwork.model.Location;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.mapper.LocationMapper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@ServiceLog
@NoArgsConstructor
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationSpringDataSpecificationDao locationDao;

    @Override
    @Transactional
    public List<LocationDto> getLocations(final int firstResult, final int maxResults) {
        return LocationMapper.getLocationDto(locationDao.findAll(PageRequest.of(firstResult, maxResults)));
    }

    @Override
    @Transactional
    public LocationDto addLocation(final LocationForCreateDto locationDto) {
        return LocationMapper.getLocationDto(locationDao.save(
            LocationMapper.getNewLocation(locationDto)));
    }

    @Override
    @Transactional
    public void updateLocation(final LocationDto locationDto) {
        locationDao.save(LocationMapper.getLocation(locationDto, locationDao));
    }

    @Override
    @Transactional
    public void deleteLocation(final Long locationId) {
        Optional<Location> location = locationDao.findById(locationId);
        if (!location.isPresent()) {
            throw new BusinessException("Error, there is no such location");
        }
        locationDao.delete(location.get());
    }

}
