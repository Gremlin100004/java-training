package com.senla.carservice.repository;

import com.senla.carservice.container.annotation.Dependency;
import com.senla.carservice.container.annotation.Property;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class PlaceRepositoryImpl implements PlaceRepository {
    private final List<Place> places;
    @Property
    private Boolean isBlockAddPlace;
    @Property
    private Boolean isBlockDeletePlace;
    @Dependency
    private IdGenerator idGeneratorPlace;

    public PlaceRepositoryImpl() {
        this.places = new ArrayList<>();
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
        if (isBlockAddPlace) {
            throw new BusinessException("Permission denied");
        }
        this.places.stream()
            .filter(place -> place.getNumber().equals(addPlace.getNumber()))
            .forEach(place -> {
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
        if (isBlockDeletePlace) {
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