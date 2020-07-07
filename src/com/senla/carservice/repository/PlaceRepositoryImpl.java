package com.senla.carservice.repository;

import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.PropertyLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PlaceRepositoryImpl implements PlaceRepository, Serializable {
    private static PlaceRepository instance;
    private static final long serialVersionUID = 1L;
    private final List<Place> places;
    private final IdGenerator idGeneratorPlace;

    private PlaceRepositoryImpl() {
        this.places = new ArrayList<>();
        this.idGeneratorPlace = new IdGenerator();
    }

    public static PlaceRepository getInstance() {
        if (instance == null) {
            instance = new PlaceRepositoryImpl();
        }
        return instance;
    }

    @Override
    public IdGenerator getIdGeneratorPlace() {
        return idGeneratorPlace;
    }

    @Override
    public List<Place> getPlaces() {
        return new ArrayList<>(this.places);
    }

    @Override
    public List<Place> getFreePlaces(Date startDayDate) {
        return this.places.stream()
            .filter(place -> place.getOrders().isEmpty() ||
                             startDayDate.before(place.getOrders().get(place.getOrders().size() - 1).getLeadTime()))
            .collect(Collectors.toList());
    }

    @Override
    public void addPlace(Place addPlace) {
        if (!Boolean.parseBoolean(PropertyLoader.getPropertyValue("placeAdd"))) {
            throw new BusinessException("Permission denied");
        }
        this.places.stream().filter(place -> place.getNumber().equals(addPlace.getNumber())).forEachOrdered(place -> {
            throw new BusinessException("Such a number exists");
        });
        addPlace.setId(this.idGeneratorPlace.getId());
        this.places.add(addPlace);
    }

    @Override
    public void updatePlace(Place place) {
        int index = this.places.indexOf(place);
        if (index == -1) {
            this.places.add(place);
        } else {
            this.places.set(index, place);
        }
    }

    @Override
    public void deletePlace(Place place) {
        if (place.getBusyStatus()) {
            throw new BusinessException("Place is busy");
        }
        if (!Boolean.parseBoolean(PropertyLoader.getPropertyValue("placeDelete"))) {
            throw new BusinessException("Permission denied");
        }
        this.places.remove(place);
    }

    @Override
    public void updateListPlace(List<Place> places) {
        this.places.clear();
        this.places.addAll(places);
    }

    @Override
    public void updateGenerator(IdGenerator idGenerator) {
        this.idGeneratorPlace.setId(idGenerator.getId());
    }
}