package com.senla.carservice.ui.client;

import com.senla.carservice.dto.PlaceDto;

import java.util.List;

public interface PlaceClient {
    String addPlace(int numberPlace);

    String checkPlaces();

    List<PlaceDto> getPlaces();

    String deletePlace(Long idPlace);

    List<PlaceDto> getFreePlacesByDate(String stringExecuteDate);

}
