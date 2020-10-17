package com.senla.carservice.ui.client;

import com.senla.carservice.dto.PlaceDto;

import java.util.List;

public interface PlaceClient {
    String addPlace(int numberPlace);

    List<PlaceDto> getPlaces();

    String deletePlace(Long idPlace);

    Long getNumberFreePlace(String stringExecuteDate);

    List<PlaceDto> getFreePlacesByDate(String stringExecuteDate);

}
