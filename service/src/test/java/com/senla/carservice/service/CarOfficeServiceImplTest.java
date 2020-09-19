package com.senla.carservice.service;

import com.senla.carservice.csv.CsvMaster;
import com.senla.carservice.csv.CsvOrder;
import com.senla.carservice.csv.CsvPlace;
import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
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
class CarOfficeServiceImplTest {

    private static final Long RIGHT_NUMBER_MASTERS = 2L;
    private static final Long RIGHT_NUMBER_PLACES = 2L;
    private static final Long RIGHT_NUMBER_ORDERS = 2L;
    private static final Long WRONG_NUMBER_MASTERS = 0L;
    private static final Long WRONG_NUMBER_PLACES = 0L;
    private static final Long WRONG_NUMBER_ORDERS = 0L;
    private static final Long ID_PLACE = 1L;
    private static final Long ID_MASTER = 1L;
    private static final Long ID_ORDER = 1L;
    private static final String PARAMETER_AUTOMAKER = "test automaker";
    private static final String PARAMETER_MODEL = "test model";
    private static final String PARAMETER_REGISTRATION_NUMBER = "test registrationNumber";
    private static final String PARAMETER_NAME = "test name";
    private static final int NUMBER_PLACE = 1;
    @Autowired
    private CarOfficeService carOfficeService;
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CsvPlace csvPlace;
    @Autowired
    private CsvOrder csvOrder;
    @Autowired
    private CsvMaster csvMaster;

    @Test
    void checkGetNearestFreeDateShouldReturnDate() {
        Date leadTimeOrder = new Date();
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_MODEL);
        order.setId(ID_ORDER);
        order.setLeadTime(leadTimeOrder);
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Mockito.doReturn(RIGHT_NUMBER_ORDERS).when(orderDao).getNumberOrders();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(ArgumentMatchers.any(Date.class));
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(ArgumentMatchers.any(Date.class));
        carOfficeService.getNearestFreeDate();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getNumberMasters();
        Mockito.verify(placeDao, Mockito.atLeastOnce()).getNumberPlaces();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getNumberFreeMasters(ArgumentMatchers.any(Date.class));
        Mockito.verify(placeDao, Mockito.atLeastOnce()).getNumberFreePlaces(ArgumentMatchers.any(Date.class));
    }

    @Test
    void checkGetNearestFreeDateShouldThrowException() {
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Assertions.assertThrows(BusinessException.class, () -> carOfficeService.getNearestFreeDate());
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Assertions.assertThrows(BusinessException.class, () -> carOfficeService.getNearestFreeDate());
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Mockito.doReturn(WRONG_NUMBER_ORDERS).when(orderDao).getNumberOrders();
        Assertions.assertThrows(BusinessException.class, () -> carOfficeService.getNearestFreeDate());
        Mockito.doReturn(RIGHT_NUMBER_ORDERS).when(orderDao).getNumberOrders();
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();
        Assertions.assertThrows(DaoException.class, () -> carOfficeService.getNearestFreeDate());
    }

    @Test
    void checkImportEntitiesShouldGetAllEntities() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        Mockito.doReturn(orders).when(csvOrder).importOrder(masters, places);
        Mockito.doReturn(masters).when(csvMaster).importMasters(orders);
        Mockito.doReturn(places).when(csvPlace).importPlaces();
        carOfficeService.importEntities();
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getAllRecords();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getAllRecords();
        Mockito.verify(placeDao, Mockito.atLeastOnce()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.atLeastOnce()).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.atLeastOnce()).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.atLeastOnce()).importPlaces();
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.atLeastOnce()).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.atLeastOnce()).updateAllRecords(places);
    }

    @Test
    void checkImportEntitiesShouldThrowException() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doThrow(DaoException.class).when(orderDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> carOfficeService.importEntities());
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doThrow(CsvException.class).when(csvMaster).importMasters(orders);
        Assertions.assertThrows(CsvException.class, () -> carOfficeService.importEntities());
        Mockito.doReturn(masters).when(csvMaster).importMasters(orders);
        Mockito.doThrow(CsvException.class).when(csvPlace).importPlaces();
        Assertions.assertThrows(CsvException.class, () -> carOfficeService.importEntities());
        Mockito.doReturn(places).when(csvPlace).importPlaces();
        Mockito.doThrow(DaoException.class).when(masterDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> carOfficeService.importEntities());
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doThrow(DaoException.class).when(placeDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> carOfficeService.importEntities());
        Mockito.doReturn(masters).when(placeDao).getAllRecords();
        Mockito.doThrow(CsvException.class).when(csvOrder).importOrder(masters, places);
//        Assertions.assertThrows(DaoException.class, () -> carOfficeService.importEntities());
        //TODO
    }

    @Test
    void exportEntities() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        carOfficeService.exportEntities();
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getAllRecords();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getAllRecords();
        Mockito.verify(placeDao, Mockito.atLeastOnce()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.atLeastOnce()).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.atLeastOnce()).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.atLeastOnce()).exportPlaces(places);
    }

    private Master getTestMaster() {
        Master master = new Master(PARAMETER_NAME);
        master.setId(ID_MASTER);
        return master;
    }

    private Order getTestOrder() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        return order;
    }

    private Place getTestPlace() {
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        return place;
    }

    private List<Order> getTestOrders() {
        return Collections.singletonList(getTestOrder());
    }

    private List<Master> getTestMasters() {
        return Collections.singletonList(getTestMaster());
    }

    private List<Place> getTestPlaces() {
        return Collections.singletonList(getTestPlace());
    }
}