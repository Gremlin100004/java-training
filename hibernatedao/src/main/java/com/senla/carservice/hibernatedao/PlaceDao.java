package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Place;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

public interface PlaceDao extends GenericDao<Place> {

    List<Place> getFreePlaces(Date startDayDate, Session session);

    int getNumberPlaces(Session session);

    Place getPlaceById(Long index, Session session);
}