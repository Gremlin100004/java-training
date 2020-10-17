package com.senla.carservice.service;

import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Place;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.service.util.PlaceMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class PlaceServiceImpl implements PlaceService {
    @Autowired
    private PlaceDao placeDao;
    @Value("${com.senla.carservice.service.PlaceServiceImpl.isBlockAddPlace:false}")
    private Boolean isBlockAddPlace;
    @Value("${com.senla.carservice.service.PlaceServiceImpl.isBlockDeletePlace:false}")
    private Boolean isBlockDeletePlace;

    @Override
    @Transactional
    public List<PlaceDto> getPlaces() {
        log.debug("[getPlaces]");
        return PlaceMapper.getPlaceDto(placeDao.getAllRecords());
    }

    @Override
    @Transactional
    public PlaceDto addPlace(PlaceDto placeDto) {
        log.debug("[addPlace]");
        log.trace("[placeDto: {}]", placeDto);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        Place place = new Place();
        int number = placeDto.getNumber();
        Place checkPlace = placeDao.findByNumber(number);
        if (checkPlace == null) {
            place.setNumber(number);
            return PlaceMapper.getPlaceDto(placeDao.saveRecord(place));
        } else {
            throw new BusinessException("This number exists");
        }

    }

    @Override
    @Transactional
    public void deletePlace(Long orderId) {
        log.debug("[deletePlace]");
        log.trace("[orderId: {}]", orderId);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        Place place = placeDao.findById(orderId);
        if (place.getIsBusy()) {
            throw new BusinessException("Place is busy");
        }
        if (place.getDeleteStatus()) {
            throw new BusinessException("Error, place has already been deleted");
        }
        place.setDeleteStatus(true);
        placeDao.updateRecord(place);
    }

    @Override
    @Transactional
    public Long getNumberPlace() {
        log.debug("[getNumberPlace]");
        return placeDao.getNumberPlaces();
    }

    @Override
    @Transactional
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        log.debug("[getNumberFreePlaceByDate]");
        log.trace("[startDayDate: {}]", startDayDate);
        return placeDao.getNumberFreePlaces(startDayDate);
    }

    @Override
    @Transactional
    public List<PlaceDto> getFreePlaceByDate(Date executeDate) {
        log.debug("[getFreePlaceByDate]");
        log.trace("[executeDate: {}]", executeDate);
        return PlaceMapper.getPlaceDto(placeDao.getFreePlaces(executeDate));
    }

}
