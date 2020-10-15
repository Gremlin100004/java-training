package com.senla.carservice.service;

import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Place;
import com.senla.carservice.dto.PlaceDto;
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
    private static final Long WRONG_NUMBER_PLACES = 0L;
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
        List<PlaceDto> placesDto = getTestPlacesDto();
        Mockito.doReturn(places).when(placeDao).getAllRecords();

        List<PlaceDto> resultPlacesDto = placeService.getPlaces();
        Assertions.assertNotNull(resultPlacesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, resultPlacesDto.size());
        Assertions.assertFalse(resultPlacesDto.isEmpty());
        Assertions.assertEquals(placesDto, resultPlacesDto);
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_addPlace() {
        Place place = getTestPlace();
        PlaceDto placeDto = getTestPlaceDto();
        Mockito.doReturn(place).when(placeDao).saveRecord(ArgumentMatchers.any(Place.class));

        Assertions.assertDoesNotThrow(() -> placeService.addPlace(placeDto));
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
    void PlaceServiceImpl_deletePlace_placeIsBusy() {
        Place place = getTestPlace();
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);
        place.setIsBusy(true);
        PlaceDto placeDto = getTestPlaceDto();
        placeDto.setIsBusy(true);

        Assertions.assertThrows(BusinessException.class, () -> placeService.deletePlace(ID_PLACE));
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_deletePlace_placeDeleted() {
        Place place = getTestPlace();
        place.setDeleteStatus(true);
        PlaceDto placeDto = getTestPlaceDto();
        placeDto.setDeleteStatus(true);
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
        List<PlaceDto> placesDto = getTestPlacesDto();
        Mockito.doReturn(places).when(placeDao).getFreePlaces(date);

        List<PlaceDto> resultPlacesDto = placeService.getFreePlaceByDate(date);
        Assertions.assertNotNull(resultPlacesDto);
        Assertions.assertEquals(RIGHT_NUMBER_PLACES, resultPlacesDto.size());
        Assertions.assertFalse(resultPlacesDto.isEmpty());
        Assertions.assertEquals(placesDto, resultPlacesDto);
        Mockito.verify(placeDao, Mockito.times(1)).getFreePlaces(date);
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_getNumberPlace() {
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();

        Assertions.assertDoesNotThrow(() -> placeService.checkPlaces());
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.reset(placeDao);
    }

    @Test
    void PlaceServiceImpl_getNumberPlace_placeDao_getNumberPlaces_zeroNumberPlaces() {
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberPlaces();

        Assertions.assertThrows(BusinessException.class, () -> placeService.checkPlaces());
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.reset(placeDao);
    }

    private Place getTestPlace() {
        Place place = new Place();
        place.setId(ID_PLACE);
        place.setNumber(NUMBER_PLACE);
        return place;
    }

    private PlaceDto getTestPlaceDto() {
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(ID_PLACE);
        placeDto.setNumber(NUMBER_PLACE);
        placeDto.setIsBusy(false);
        placeDto.setDeleteStatus(false);
        return placeDto;
    }

    private List<Place> getTestPlaces() {
        Place placeOne = getTestPlace();
        Place placeTwo = getTestPlace();
        placeTwo.setId(ID_OTHER_PLACE);
        return Arrays.asList(placeOne, placeTwo);
    }

    private List<PlaceDto> getTestPlacesDto() {
        PlaceDto placeDtoOne = getTestPlaceDto();
        PlaceDto placeDtoTwo = getTestPlaceDto();
        placeDtoTwo.setId(ID_OTHER_PLACE);
        return Arrays.asList(placeDtoOne, placeDtoTwo);
    }
}