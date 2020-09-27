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
    void CarOfficeServiceImpl_getNearestFreeDate() {
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

        Date resultDate = carOfficeService.getNearestFreeDate();
        Assertions.assertNotNull(resultDate);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.verify(orderDao, Mockito.times(1)).getNumberOrders();
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(ArgumentMatchers.any(Date.class));
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(ArgumentMatchers.any(Date.class));
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void CarOfficeServiceImpl_getNearestFreeDate_zeroNumberMasters() {
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        
        Assertions.assertThrows(BusinessException.class, () -> carOfficeService.getNearestFreeDate());
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.never()).getNumberPlaces();
        Mockito.verify(orderDao, Mockito.never()).getNumberOrders();
        Mockito.verify(orderDao, Mockito.never()).getLastOrder();
        Mockito.verify(masterDao, Mockito.never()).getNumberFreeMasters(ArgumentMatchers.any(Date.class));
        Mockito.verify(placeDao, Mockito.never()).getNumberFreePlaces(ArgumentMatchers.any(Date.class));
        Mockito.reset(masterDao);
    }
    @Test
    void CarOfficeServiceImpl_getNearestFreeDate_zeroNumberPlaces() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        
        Assertions.assertThrows(BusinessException.class, () -> carOfficeService.getNearestFreeDate());
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.verify(orderDao, Mockito.never()).getNumberOrders();
        Mockito.verify(orderDao, Mockito.never()).getLastOrder();
        Mockito.verify(masterDao, Mockito.never()).getNumberFreeMasters(ArgumentMatchers.any(Date.class));
        Mockito.verify(placeDao, Mockito.never()).getNumberFreePlaces(ArgumentMatchers.any(Date.class));
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void CarOfficeServiceImpl_getNearestFreeDate_zeroNumberOrders() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Mockito.doReturn(WRONG_NUMBER_ORDERS).when(orderDao).getNumberOrders();
        
        Assertions.assertThrows(BusinessException.class, () -> carOfficeService.getNearestFreeDate());
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.verify(orderDao, Mockito.times(1)).getNumberOrders();
        Mockito.verify(orderDao, Mockito.never()).getLastOrder();
        Mockito.verify(masterDao, Mockito.never()).getNumberFreeMasters(ArgumentMatchers.any(Date.class));
        Mockito.verify(placeDao, Mockito.never()).getNumberFreePlaces(ArgumentMatchers.any(Date.class));
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }
    
    @Test
    void CarOfficeServiceImpl_getNearestFreeDate_orderDao_getLastOrder_nullOrder() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Mockito.doReturn(RIGHT_NUMBER_ORDERS).when(orderDao).getNumberOrders();
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();
        
        Assertions.assertThrows(DaoException.class, () -> carOfficeService.getNearestFreeDate());
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.verify(orderDao, Mockito.times(1)).getNumberOrders();
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.never()).getNumberFreeMasters(ArgumentMatchers.any(Date.class));
        Mockito.verify(placeDao, Mockito.never()).getNumberFreePlaces(ArgumentMatchers.any(Date.class));
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void CarOfficeServiceImpl_importEntities() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        Mockito.doReturn(orders).when(csvOrder).importOrder(masters, places);
        Mockito.doReturn(masters).when(csvMaster).importMasters(orders);
        Mockito.doReturn(places).when(csvPlace).importPlaces();

        Assertions.assertDoesNotThrow(() -> carOfficeService.importEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.times(1)).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.times(1)).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.times(1)).importPlaces();
        Mockito.verify(orderDao, Mockito.times(1)).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.times(1)).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.times(1)).updateAllRecords(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvOrder);
        Mockito.reset(csvMaster);
        Mockito.reset(csvPlace);
    }

    @Test
    void CarOfficeServiceImpl_importEntities_orderDao_getAllRecords_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doThrow(DaoException.class).when(orderDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> carOfficeService.importEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.never()).getAllRecords();
        Mockito.verify(placeDao, Mockito.never()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.never()).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.never()).importPlaces();
        Mockito.verify(orderDao, Mockito.never()).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.never()).updateAllRecords(places);
        Mockito.reset(orderDao);
    }

    @Test
    void CarOfficeServiceImpl_importEntities_csvMaster_wrongStructureCsvFile() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doThrow(CsvException.class).when(csvMaster).importMasters(orders);

        Assertions.assertThrows(CsvException.class, () -> carOfficeService.importEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.never()).getAllRecords();
        Mockito.verify(placeDao, Mockito.never()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.times(1)).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.never()).importPlaces();
        Mockito.verify(orderDao, Mockito.never()).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.never()).updateAllRecords(places);
        Mockito.reset(orderDao);
        Mockito.reset(csvMaster);
    }

    @Test
    void CarOfficeServiceImpl_importEntities_csvPlace_importPlaces_wrongStructureCsvFile() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(csvMaster).importMasters(orders);
        Mockito.doThrow(CsvException.class).when(csvPlace).importPlaces();

        Assertions.assertThrows(CsvException.class, () -> carOfficeService.importEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.never()).getAllRecords();
        Mockito.verify(placeDao, Mockito.never()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.times(1)).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.times(1)).importPlaces();
        Mockito.verify(orderDao, Mockito.never()).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.times(1)).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.never()).updateAllRecords(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(csvMaster);
        Mockito.reset(csvPlace);
    }

    @Test
    void CarOfficeServiceImpl_importEntities_masterDao_getAllRecords_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(csvMaster).importMasters(orders);
        Mockito.doReturn(places).when(csvPlace).importPlaces();
        Mockito.doThrow(DaoException.class).when(masterDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> carOfficeService.importEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.never()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.times(1)).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.times(1)).importPlaces();
        Mockito.verify(orderDao, Mockito.never()).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.times(1)).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.times(1)).updateAllRecords(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvMaster);
        Mockito.reset(csvPlace);
    }

    @Test
    void CarOfficeServiceImpl_importEntities_placeDao_getAllRecords_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(csvMaster).importMasters(orders);
        Mockito.doReturn(places).when(csvPlace).importPlaces();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doThrow(DaoException.class).when(placeDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> carOfficeService.importEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.times(1)).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.times(1)).importPlaces();
        Mockito.verify(orderDao, Mockito.never()).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.times(1)).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.times(1)).updateAllRecords(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvMaster);
        Mockito.reset(csvPlace);
    }

    @Test
    void CarOfficeServiceImpl_importEntities_csvOrder_importOrder_wrongStructureCsvFile() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(csvMaster).importMasters(orders);
        Mockito.doReturn(places).when(csvPlace).importPlaces();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        Mockito.doThrow(CsvException.class).when(csvOrder).importOrder(masters, places);

        Assertions.assertThrows(CsvException.class, () -> carOfficeService.importEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.times(1)).importOrder(masters, places);
        Mockito.verify(csvMaster, Mockito.times(1)).importMasters(orders);
        Mockito.verify(csvPlace, Mockito.times(1)).importPlaces();
        Mockito.verify(orderDao, Mockito.never()).updateAllRecords(orders);
        Mockito.verify(masterDao, Mockito.times(1)).updateAllRecords(masters);
        Mockito.verify(placeDao, Mockito.times(1)).updateAllRecords(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvOrder);
        Mockito.reset(csvMaster);
        Mockito.reset(csvPlace);
    }

    @Test
    void CarOfficeServiceImpl_exportEntities() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();

        Assertions.assertDoesNotThrow(() -> carOfficeService.exportEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.times(1)).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.times(1)).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.times(1)).exportPlaces(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvOrder);
        Mockito.reset(csvMaster);
        Mockito.reset(csvPlace);
    }

    @Test
    void CarOfficeServiceImpl_exportEntities_orderDao_getAllRecords_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doThrow(DaoException.class).when(orderDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> carOfficeService.exportEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.never()).getAllRecords();
        Mockito.verify(placeDao, Mockito.never()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.never()).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.never()).exportPlaces(places);
        Mockito.reset(orderDao);
    }

    @Test
    void CarOfficeServiceImpl_exportEntities_masterDao_getAllRecords_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doThrow(DaoException.class).when(masterDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> carOfficeService.exportEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.never()).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.never()).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.never()).exportPlaces(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
    }

    @Test
    void CarOfficeServiceImpl_exportEntities_placeDao_getAllRecords_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doThrow(DaoException.class).when(placeDao).getAllRecords();

        Assertions.assertThrows(DaoException.class, () -> carOfficeService.exportEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.never()).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.never()).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.never()).exportPlaces(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void CarOfficeServiceImpl_exportEntities_csvOrder_exportOrder_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        Mockito.doThrow(CsvException.class).when(csvOrder).exportOrder(orders);

        Assertions.assertThrows(CsvException.class, () -> carOfficeService.exportEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.times(1)).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.never()).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.never()).exportPlaces(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvOrder);
    }

    @Test
    void CarOfficeServiceImpl_exportEntities_csvMaster_exportMasters_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        Mockito.doThrow(CsvException.class).when(csvMaster).exportMasters(masters);

        Assertions.assertThrows(CsvException.class, () -> carOfficeService.exportEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.times(1)).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.times(1)).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.never()).exportPlaces(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvOrder);
        Mockito.reset(csvMaster);
    }

    @Test
    void CarOfficeServiceImpl_exportEntities_csvPlace_exportPlaces_emptyRecords() {
        List<Order> orders = getTestOrders();
        List<Master> masters = getTestMasters();
        List<Place> places = getTestPlaces();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        Mockito.doReturn(places).when(placeDao).getAllRecords();
        Mockito.doThrow(CsvException.class).when(csvPlace).exportPlaces(places);

        Assertions.assertThrows(CsvException.class, () -> carOfficeService.exportEntities());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(placeDao, Mockito.times(1)).getAllRecords();
        Mockito.verify(csvOrder, Mockito.times(1)).exportOrder(orders);
        Mockito.verify(csvMaster, Mockito.times(1)).exportMasters(masters);
        Mockito.verify(csvPlace, Mockito.times(1)).exportPlaces(places);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
        Mockito.reset(csvOrder);
        Mockito.reset(csvMaster);
        Mockito.reset(csvPlace);
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