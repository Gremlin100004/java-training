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
        log.debug("Method getPlaces");
        return PlaceMapper.transferDataFromPlaceToPLaceDto(placeDao.getAllRecords());
    }

    @Override
    @Transactional
    public PlaceDto addPlace(PlaceDto placeDto) {
        log.debug("Method addPlace");
        log.trace("Parameter placeDto: {}", placeDto);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        return PlaceMapper.transferDataFromPlaceToPLaceDto(placeDao.saveRecord(new Place(placeDto.getNumber())));
    }

    @Override
    @Transactional
    public void deletePlace(Long orderId) {
        log.debug("Method deletePlace");
        log.trace("Parameter orderId: {}", orderId);
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
    public Long getNumberFreePlaceByDate(Date startDayDate) {
        log.debug("Method getNumberFreePlaceByDate");
        log.trace("Parameter startDayDate: {}", startDayDate);
        return placeDao.getNumberFreePlaces(startDayDate);
    }

    @Override
    @Transactional
    public List<PlaceDto> getFreePlaceByDate(Date executeDate) {
        log.debug("Method getFreePlaceByDate");
        log.trace("Parameter executeDate: {}", executeDate);
        return PlaceMapper.transferDataFromPlaceToPLaceDto(placeDao.getFreePlaces(executeDate));
    }

    @Override
    @Transactional
    public Long getNumberPlace() {
        log.debug("Method getNumberMasters");
        return placeDao.getNumberPlaces();
    }
}