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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class PlaceServiceImplTest {

    private static final Long RIGHT_NUMBER_PLACES = 2L;
    private static final Long ID_PLACE = 1L;
    private static final Long ID_OTHER_PLACE = 2L;
    private static final int NUMBER_PLACE = 1;    
    @Autowired
    private PlaceService placeService;
    @Autowired
    private PlaceDao placeDao;

    @Test
    void PlaceServiceImpl_getPlaces() {
        List<Place> places = getTestPlaces();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        
        List<Place> resultPlaces = placeService.getPlaces();
        Assertions.assertNotNull(resultPlaces);
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, resultPlaces.size());
        Assertions.assertFalse(resultPlaces.isEmpty());
        Assertions.assertEquals(places, resultPlaces);
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_getPlaces_placeDao_getAllRecords_emptyList() {
        Mockito.doThrow(DaoException.class).when(placeDao).getAllRecords();
        
        Assertions.assertThrows(DaoException.class, () -> placeService.getPlaces());
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_addPlace() {
        Assertions.assertDoesNotThrow(() -> placeService.addPlace(ArgumentMatchers.anyInt()));
        Mockito.verify(placeDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Place.class));
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_deletePlace() {
        Place place = getTestPlace();
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);

        Assertions.assertDoesNotThrow(() -> placeService.deletePlace(ID_PLACE));
        Assertions.assertTrue(place.getDeleteStatus());
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(placeDao, Mockito.times(1)).updateRecord(place);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_deletePlace_placeDao_findById_wrongId() {
        Place place = getTestPlace();
        Mockito.doThrow(DaoException.class).when(placeDao).findById(ID_PLACE);

        Assertions.assertThrows(DaoException.class, () -> placeService.deletePlace(ID_PLACE));
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_deletePlace_placeIsBusy() {
        Place place = getTestPlace();
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);
        place.setBusy(true);

        Assertions.assertThrows(BusinessException.class, () -> placeService.deletePlace(ID_PLACE));
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_deletePlace_placeDeleted() {
        Place place = getTestPlace();
        place.setDeleteStatus(true);
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);

        Assertions.assertThrows(BusinessException.class, () -> placeService.deletePlace(ID_PLACE));
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_getNumberFreePlaceByDate() {
        Date date = new Date();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(date);

        Long numberFreePlaceByDate = placeService.getNumberFreePlaceByDate(date);
        Assertions.assertNotNull(numberFreePlaceByDate);
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, numberFreePlaceByDate);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(date);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_getFreePlaceByDate() {
        Date date = new Date();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(places).when(placeDao).getFreePlaces(date);

        List<Place> resultPlaces = placeService.getFreePlaceByDate(date);
        Assertions.assertNotNull(resultPlaces);
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, resultPlaces.size());
        Assertions.assertFalse(resultPlaces.isEmpty());
        Assertions.assertEquals(places, resultPlaces);
        Mockito.verify(placeDao, Mockito.times(1)).getFreePlaces(date);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_getFreePlaceByDate_placeDao_getFreePlaces_emptyList() {
        Date date = new Date();
        Mockito.doThrow(DaoException.class).when(placeDao).getFreePlaces(date);

        Assertions.assertThrows(DaoException.class, () -> placeService.getFreePlaceByDate(date));
        Mockito.verify(placeDao, Mockito.times(1)).getFreePlaces(date);
        Mockito.reset(placeDao);
    }

    @Test
    void getNumberPlace() {
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();

        Long resultNumberPlaces = placeService.getNumberPlace();
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, resultNumberPlaces);
        Assertions.assertNotNull(resultNumberPlaces);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.reset(placeDao);
    }

    private Place getTestPlace() {
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        return place;
    }

    private List<Place> getTestPlaces() {
        Place placeOne = getTestPlace();
        Place placeTwo = getTestPlace();
        placeTwo.setId(ID_OTHER_PLACE);
        return Arrays.asList(placeOne, placeTwo);
    }
}