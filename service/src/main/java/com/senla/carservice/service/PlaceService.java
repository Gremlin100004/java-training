package com.senla.carservice.service;

import com.senla.carservice.dto.PlaceDto;

import java.util.Date;
import java.util.List;

public interface PlaceService {

    List<PlaceDto> getPlaces();

    void addPlace(PlaceDto placeDto);

    void deletePlace(PlaceDto placeDto);

    Long getNumberFreePlaceByDate(Date startDate);

    List<PlaceDto> getFreePlaceByDate(Date executeDate);

    Long getNumberPlace();
}