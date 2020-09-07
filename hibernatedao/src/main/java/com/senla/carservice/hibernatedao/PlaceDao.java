package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Place;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public interface PlaceDao extends GenericDao<Place, Long> {

    List<Place> getBusyPlaces(Date startDayDate);

    Long getNumberPlaces();

    Long getNumberBusyPlaces(Date executeDate);

    Place getPlaceById(Long index);
}