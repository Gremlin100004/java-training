package com.senla.carservice.service;

import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Place;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.service.exception.BusinessException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        return transferDataFromPlaceToPLaceDto(placeDao.getAllRecords());
    }

    @Override
    @Transactional
    public void addPlace(PlaceDto placeDto) {
        log.debug("Method addPlace");
        log.trace("Parameter placeDto: {}", placeDto);
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        placeDao.saveRecord(transferDataFromPlaceDtoToPlace(placeDto));
    }

    @Override
    @Transactional
    public void deletePlace(PlaceDto placeDto) {
        log.debug("Method deletePlace");
        log.trace("Parameter placeDto: {}", placeDto);
        if (isBlockDeletePlace) {
            throw new BusinessException("Permission denied");
        }
        Place place = transferDataFromPlaceDtoToPlace(placeDto);
        if (place.getIsBusy()) {
            throw new BusinessException("Place is busy");
        }
        if (place.getDeleteStatus()) {
            throw new BusinessException("error, place has already been deleted");
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
        return transferDataFromPlaceToPLaceDto(placeDao.getFreePlaces(executeDate));
    }

    @Override
    @Transactional
    public Long getNumberPlace() {
        log.debug("Method getNumberMasters");
        return placeDao.getNumberPlaces();
    }

    private List<PlaceDto> transferDataFromPlaceToPLaceDto(List<Place> places) {
        List<PlaceDto> placesDto = new ArrayList<>();
        for (Place place: places) {
            PlaceDto placeDto = new PlaceDto();
            placeDto.setId(place.getId());
            placeDto.setNumber(place.getNumber());
            placeDto.setIsBusy(place.getIsBusy());
            placeDto.setDeleteStatus(place.getDeleteStatus());
            placesDto.add(placeDto);
        }
        return placesDto;
    }

    private Place transferDataFromPlaceDtoToPlace(PlaceDto placeDto) {
        Place place;
        if (placeDto.getId() == null){
            place = new Place();
        } else {
            place = placeDao.findById(placeDto.getId());
        }
        place.setNumber(placeDto.getNumber());
        place.setIsBusy(placeDto.getIsBusy());
        place.setDeleteStatus(placeDto.getDeleteStatus());
        return place;
    }
}