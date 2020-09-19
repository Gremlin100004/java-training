package com.senla.carservice.service;

import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.config.TestConfig;
import com.senla.carservice.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class PlaceServiceImplTest {

    private static final Long RIGHT_NUMBER_PLACES = 2L;
    private static final Long ID_PLACE = 1L;
    private static final int NUMBER_PLACE = 1;
    @Autowired
    private PlaceService placeService;
    @Autowired
    private PlaceDao placeDao;

    @Test
    void checkGetPlacesShouldReturnList() {
        List<Place> places = getTestPlaces();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        List<Place> resultPlaces = placeService.getPlaces();
        Mockito.verify(placeDao, Mockito.atLeastOnce()).getAllRecords();
        Assertions.assertEquals(places, resultPlaces);
    }

    @Test
    void checkGetPlacesShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(placeDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> placeService.getPlaces());
    }

    @Test
    void checkAddPlaceShouldSavePlace() {
        placeService.addPlace(ArgumentMatchers.anyInt());
        Mockito.verify(placeDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Place.class));
    }

    @Test
    void checkDeletePlaceShouldDeletePlaceById() {
        Place place = getTestPlace();
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);
        placeService.deletePlace(ID_PLACE);
        Mockito.verify(placeDao, Mockito.atLeastOnce()).findById(ID_PLACE);
        Mockito.verify(placeDao, Mockito.times(1)).updateRecord(place);
        Assertions.assertTrue(place.getDeleteStatus());
    }

    @Test
    void checkDeletePlaceShouldThrowException() {
        Place place = getTestPlace();
        Mockito.doThrow(DaoException.class).when(placeDao).findById(ID_PLACE);
        Assertions.assertThrows(DaoException.class, () -> placeService.deletePlace(ID_PLACE));
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);
        place.setBusy(true);
        Assertions.assertThrows(BusinessException.class, () -> placeService.deletePlace(ID_PLACE));
    }

    @Test
    void checkGetNumberFreePlaceByDateShouldReturnNumber() {
        Date date = new Date();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(date);
        Long numberFreePlaceByDate = placeService.getNumberFreePlaceByDate(date);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(date);
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, numberFreePlaceByDate);
    }

    @Test
    void checkGetFreePlaceByDateShouldReturnList() {
        Date date = new Date();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(places).when(placeDao).getFreePlaces(date);
        List<Place> resultPlaces = placeService.getFreePlaceByDate(date);
        Mockito.verify(placeDao, Mockito.times(1)).getFreePlaces(date);
        Assertions.assertEquals(places, resultPlaces);
    }

    @Test
    void checkGetFreePlaceByDate() {
        Date date = new Date();
        Mockito.doThrow(DaoException.class).when(placeDao).getFreePlaces(date);
        Assertions.assertThrows(DaoException.class, () -> placeService.getFreePlaceByDate(date));
    }

    @Test
    void getNumberPlace() {
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Long resultNumberMasters = placeService.getNumberPlace();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, resultNumberMasters);
    }

    private Place getTestPlace() {
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        return place;
    }

    private List<Place> getTestPlaces() {
        return Collections.singletonList(getTestPlace());
    }
}