package com.senla.carservice.service;

import com.senla.carservice.dto.PlaceDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface PlaceService {

    List<PlaceDto> getPlaces();

    PlaceDto addPlace(PlaceDto placeDto);

    void deletePlace(Long orderId);

    Long getNumberPlace();

    Long getNumberFreePlaceByDate(Date startDate);

    List<PlaceDto> getFreePlaceByDate(Date executeDate);

}
