package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.service.config.TestConfig;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.exception.DateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class OrderServiceImplTest {

    private static final Long RIGHT_NUMBER_MASTERS = 2L;
    private static final Long RIGHT_NUMBER_PLACES = 2L;
    private static final Long RIGHT_NUMBER_ORDERS = 2L;
    private static final Long WRONG_NUMBER_MASTERS = 0L;
    private static final Long WRONG_NUMBER_PLACES = 0L;
    private static final Long ID_PLACE = 1L;
    private static final Long ID_MASTER = 1L;
    private static final Long ID_MASTER_OTHER = 2L;
    private static final Long ID_ORDER = 1L;
    private static final Long ID_ORDER_OTHER = 2L;
    private static final int NUMBER_PLACE = 1;
    private static final int NUMBER_ORDERS = 1;
    private static final int UPDATE_NUMBER_ORDERS = 0;
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final String PARAMETER_AUTOMAKER = "test automaker";
    private static final String PARAMETER_MODEL = "test model";
    private static final String PARAMETER_REGISTRATION_NUMBER = "registrationNumber";
    private static final String PARAMETER_NAME = "test name";
    @Autowired
    private OrderService orderService;
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PlaceDao placeDao;

    @Test
    void OrderServiceImpl_getOrders() {
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();

        List<Order> resultOrders = orderService.getOrders();
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getOrders_orderDao_getAllRecords_emptyRecords() {
        Mockito.doThrow(DaoException.class).when(orderDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> orderService.getOrders());
        Mockito.verify(orderDao, Mockito.times(1)).getAllRecords();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrder() {
        Place place = getTestPlace();
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);

        Assertions.assertDoesNotThrow(
            () -> orderService.addOrder(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER));
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Order.class));
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void OrderServiceImpl_addOrder_masterDao_getNumberMasters_zeroNumberOfMasters() {
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberMasters();

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.addOrder(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER));
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.never()).getNumberPlaces();
        Mockito.verify(placeDao, Mockito.never()).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.never()).saveRecord(ArgumentMatchers.any(Order.class));
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_addOrder_placeDao_getNumberPlaces_zeroNumberOfPlaces() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberPlaces();

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.addOrder(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER));
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.verify(placeDao, Mockito.never()).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.never()).saveRecord(ArgumentMatchers.any(Order.class));
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_addOrder_placeDao_findById_wrongId() {
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Mockito.doThrow(DaoException.class).when(placeDao).findById(ID_PLACE);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.addOrder(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER));
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Mockito.verify(placeDao, Mockito.times(1)).getNumberPlaces();
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.never()).saveRecord(ArgumentMatchers.any(Order.class));
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_addOrderDeadlines() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(executionStartTime);

        Assertions.assertDoesNotThrow(() -> orderService.addOrderDeadlines(executionStartTime, leadTime));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Assertions.assertEquals(executionStartTime, order.getExecutionStartTime());
        Assertions.assertEquals(leadTime, order.getLeadTime());
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderDeadlines_wrongDate() {
        Date wrongExecutionStartTime = new Date();
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();

        Assertions.assertThrows(DateException.class,
                                () -> orderService.addOrderDeadlines(wrongExecutionStartTime, leadTime));
        Mockito.verify(orderDao, Mockito.never()).getLastOrder();
        Mockito.verify(masterDao, Mockito.never()).getNumberFreeMasters(wrongExecutionStartTime);
        Mockito.verify(placeDao, Mockito.never()).getNumberFreePlaces(wrongExecutionStartTime);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
    }

    @Test
    void OrderServiceImpl_addOrderDeadlines_orderDao_getLastOrder_orderDoesNotExist() {
        Order order = getTestOrder();
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();

        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderDeadlines(executionStartTime, leadTime));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.never()).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.never()).getNumberFreePlaces(executionStartTime);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderDeadlines_zeroNumberOfMasters() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.addOrderDeadlines(executionStartTime, leadTime));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderDeadlines_businessException_zeroNumberOfPlaces() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(executionStartTime);

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.addOrderDeadlines(executionStartTime, leadTime));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderMasters() {
        Master master = getTestMaster();
        Order order = getTestOrder();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doReturn(order).when(orderDao).getLastOrder();

        Assertions.assertDoesNotThrow(() -> orderService.addOrderMasters(ID_MASTER));
        Assertions.assertEquals(1, master.getNumberOrders());
        Assertions.assertEquals(master, order.getMasters().get(0));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.reset(masterDao);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderMasters_orderDao_getLastOrder_orderDoesNotExist() {
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();

        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderMasters(ID_MASTER));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.never()).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderMasters_orderDao_findById_wrongId() {
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);

        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderMasters(ID_MASTER));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(masterDao);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderMasters_masterDeleted() {
        Order order = getTestOrder();
        Master master = getTestMaster();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        master.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class, () -> orderService.addOrderMasters(ID_MASTER));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(masterDao);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderMasters_theMasterAlreadyExists() {
        Master master = getTestMaster();
        Order order = getTestOrder();
        order.getMasters().add(master);
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.addOrderMasters(ID_MASTER));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(masterDao);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderPlace() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);

        Assertions.assertDoesNotThrow(() -> orderService.addOrderPlace(ID_PLACE));
        Assertions.assertEquals(place, order.getPlace());
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void OrderServiceImpl_addOrderPlace_orderDao_getLastOrder_orderDoesNotExist() {
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();

        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderPlace(ID_PLACE));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(placeDao, Mockito.never()).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderPlace_placeDao_findById_wrongId() {
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doThrow(DaoException.class).when(placeDao).findById(ID_PLACE);

        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderPlace(ID_PLACE));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(placeDao, Mockito.times(1)).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
        Mockito.reset(placeDao);
    }

    @Test
    void OrderServiceImpl_addOrderPrice() {
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).getLastOrder();

        Assertions.assertDoesNotThrow(() -> orderService.addOrderPrice(PRICE));
        Assertions.assertEquals(PRICE, order.getPrice());
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_addOrderPrice_orderDao_getLastOrder_orderDoesNotExist() {
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();

        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderPrice(PRICE));
        Mockito.verify(orderDao, Mockito.times(1)).getLastOrder();
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_completeOrder() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertDoesNotThrow(() -> orderService.completeOrder(ID_PLACE));
        Assertions.assertEquals(StatusOrder.PERFORM, order.getStatus());
        Assertions.assertEquals(true, order.getPlace().getBusy());
        Assertions.assertNotNull(order.getExecutionStartTime());
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(placeDao, Mockito.times(1)).updateRecord(place);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.reset(orderDao);
        Mockito.reset(placeDao);
    }

    @Test
    void OrderServiceImpl_completeOrder_orderDao_findById_wrongId() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(DaoException.class, () -> orderService.completeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_completeOrder_orderDeleted() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_completeOrder_orderStatusCompleted() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setStatus(StatusOrder.COMPLETED);

        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_completeOrder_orderStatusPerform() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setStatus(StatusOrder.PERFORM);

        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_completeOrder_orderStatusCanceled() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setStatus(StatusOrder.CANCELED);

        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_cancelOrder() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        place.setBusy(true);
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);

        Assertions.assertDoesNotThrow(() -> orderService.cancelOrder(ID_PLACE));
        Assertions.assertEquals(StatusOrder.CANCELED, order.getStatus());
        Assertions.assertEquals(false, order.getPlace().getBusy());
        Assertions.assertNotNull(order.getLeadTime());
        Assertions.assertEquals(UPDATE_NUMBER_ORDERS, master.getNumberOrders());
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.times(1)).updateAllRecords(masters);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.verify(placeDao, Mockito.times(1)).updateRecord(place);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void OrderServiceImpl_cancelOrder_orderDao_findById_wrongId() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(DaoException.class, () -> orderService.cancelOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_cancelOrder_orderDeleted() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_cancelOrder_orderStatusCompleted() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setStatus(StatusOrder.COMPLETED);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_cancelOrder_orderStatusPerform() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setStatus(StatusOrder.PERFORM);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_cancelOrder_businessException_orderStatusCanceled() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setStatus(StatusOrder.CANCELED);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_closeOrder() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);

        Assertions.assertDoesNotThrow(() -> orderService.closeOrder(ID_PLACE));
        Assertions.assertEquals(StatusOrder.COMPLETED, order.getStatus());
        Assertions.assertEquals(false, order.getPlace().getBusy());
        Assertions.assertNotNull(order.getLeadTime());
        Assertions.assertEquals(UPDATE_NUMBER_ORDERS, master.getNumberOrders());
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.verify(placeDao, Mockito.times(1)).updateRecord(place);
        Mockito.verify(orderDao, Mockito.times(1)).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.times(1)).updateAllRecords(masters);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
        Mockito.reset(placeDao);
    }

    @Test
    void OrderServiceImpl_closeOrder_orderDao_findById_wrongId() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(DaoException.class, () -> orderService.closeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_closeOrder_orderDeleted() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class, () -> orderService.closeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_closeOrder_orderStatusCompleted() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        order.setStatus(StatusOrder.COMPLETED);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.closeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_closeOrder_orderStatusCanceled() {
        Master master = getTestMaster();
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        order.setStatus(StatusOrder.CANCELED);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.closeOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.never()).updateRecord(place);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.verify(masterDao, Mockito.never()).updateAllRecords(masters);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_deleteOrder() {
        Order order = getTestOrder();
        order.setStatus(StatusOrder.CANCELED);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertDoesNotThrow(() -> orderService.deleteOrder(ID_ORDER));
        Assertions.assertTrue(order.isDeleteStatus());
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_deleteOrder_orderDao_findById_wrongId() {
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(DaoException.class, () -> orderService.deleteOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_deleteOrder_orderDeleted() {
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class, () -> orderService.deleteOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_deleteOrder_orderStatusWait() {
        Order order = getTestOrder();
        order.setDeleteStatus(false);
        order.setStatus(StatusOrder.WAIT);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.deleteOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_deleteOrder_orderStatusPerform() {
        Order order = getTestOrder();
        order.setStatus(StatusOrder.PERFORM);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(BusinessException.class, () -> orderService.deleteOrder(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertDoesNotThrow(() -> orderService.shiftLeadTime(ID_ORDER, executionStartTime, leadTime));
        Assertions.assertEquals(executionStartTime, order.getExecutionStartTime());
        Assertions.assertEquals(leadTime, order.getLeadTime());
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime_wrongDate() {
        Date wrongExecutionStartTime = new Date();
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();

        Assertions.assertThrows(DateException.class,
            () -> orderService.shiftLeadTime(ID_ORDER, wrongExecutionStartTime, rightLeadTime));
        Mockito.verify(orderDao, Mockito.never()).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime_orderDao_findById_wrongId() {
        Date rightExecutionStartTime = DateUtil.addDays(new Date(), 1);
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(DaoException.class, () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime_orderDeleted() {
        Date rightExecutionStartTime = DateUtil.addDays(new Date(), 1);
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime_orderStatusCompleted() {
        Date rightExecutionStartTime = DateUtil.addDays(new Date(), 1);
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setStatus(StatusOrder.COMPLETED);

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime_orderStatusCanceled() {
        Date rightExecutionStartTime = DateUtil.addDays(new Date(), 1);
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setStatus(StatusOrder.CANCELED);

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
        }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByFilingDate() {
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByFilingDate();

        List<Order> resultOrders = orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByFilingDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByFilingDate_emptyList() {
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByFilingDate();

        Assertions.assertThrows(DaoException.class, () -> orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE));
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByFilingDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByExecutionDate() {
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByExecutionDate();

        List<Order> resultOrders = orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByExecutionDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByExecutionDate_emptyList() {
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByExecutionDate();

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE));
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByExecutionDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByPlannedStartDate() {
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByPlannedStartDate();

        List<Order> resultOrders = orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByPlannedStartDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByPlannedStartDate_emptyList() {
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByPlannedStartDate();

        Assertions.assertThrows(DaoException.class,
                                () -> orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE));
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByPlannedStartDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByPrice() {
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByPrice();

        List<Order> resultOrders = orderService.getSortOrders(SortParameter.SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByPrice();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByPrice_emptyList() {
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByPrice();

        Assertions.assertThrows(DaoException.class, () -> orderService.getSortOrders(SortParameter.SORT_BY_PRICE));
        Mockito.verify(orderDao, Mockito.times(1)).getOrdersSortByPrice();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getExecuteOrderSortByFilingDate() {
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getExecuteOrderSortByFilingDate();

        List<Order> resultOrders = orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getExecuteOrderSortByFilingDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getExecuteOrderSortByFilingDate_emptyList() {
        Mockito.doThrow(DaoException.class).when(orderDao).getExecuteOrderSortByFilingDate();

        Assertions.assertThrows(DaoException.class,
           () -> orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE));
        Mockito.verify(orderDao, Mockito.times(1)).getExecuteOrderSortByFilingDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getExecuteOrderSortExecutionDate() {
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getExecuteOrderSortExecutionDate();

        List<Order> resultOrders = orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getExecuteOrderSortExecutionDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getExecuteOrderSortExecutionDate_emptyList() {
        Mockito.doThrow(DaoException.class).when(orderDao).getExecuteOrderSortExecutionDate();

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE));
        Mockito.verify(orderDao, Mockito.times(1)).getExecuteOrderSortExecutionDate();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_wrongDate() {
        Date wrongStartPeriodDate = DateUtil.addDays(new Date(), 3);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);

        Assertions.assertThrows(DateException.class,
            () -> orderService.getSortOrdersByPeriod(wrongStartPeriodDate, rightEndPeriodDate,
                SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(wrongStartPeriodDate,
            rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(wrongStartPeriodDate,
            rightEndPeriodDate);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getCompletedOrdersSortByFilingDate() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao)
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_orderDao_getCompletedOrdersSortByFilingDate_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
        Mockito.verify(orderDao, Mockito.times(1)).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getCompletedOrdersSortByExecutionDate() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao)
            .getCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_orderDao_getCompletedOrdersSortByExecutionDate_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
               SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getCompletedOrdersSortByPrice() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_orderDao_getCompletedOrdersSortByPrice_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getCanceledOrdersSortByFilingDate() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao)
            .getCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_orderDao_getCanceledOrdersSortByFilingDate_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
               SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getCanceledOrdersSortByExecutionDate() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao)
            .getCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_orderDao_getCanceledOrdersSortByExecutionDate_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
               SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getCanceledOrdersSortByPrice() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.CANCELED_ORDERS_SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_orderDao_getCanceledOrdersSortByPrice_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
               SortParameter.CANCELED_ORDERS_SORT_BY_PRICE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getDeletedOrdersSortByFilingDate() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao)
            .getDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_getDeletedOrdersSortByFilingDate_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
               SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getDeletedOrdersSortByExecutionDate() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao)
            .getDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_getDeletedOrdersSortByExecutionDate_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
               SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_orderDao_getDeletedOrdersSortByPrice() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        List<Order> orders = getTestOrders();
        Mockito.doReturn(orders).when(orderDao).getDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.DELETED_ORDERS_SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(orderDao, Mockito.times(1))
            .getDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getSortOrdersByPeriod_dateException_getDeletedOrdersSortByPrice_emptyList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        Assertions.assertThrows(DaoException.class,
            () -> orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
               SortParameter.DELETED_ORDERS_SORT_BY_PRICE));
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCompletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getCanceledOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByFilingDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.never()).getDeletedOrdersSortByExecutionDate(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.verify(orderDao, Mockito.times(1)).getDeletedOrdersSortByPrice(
            rightStartPeriodDate, rightEndPeriodDate);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getMasterOrders() {
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Master master = getTestMaster();
        List<Order> orders = getTestOrders();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doReturn(orders).when(orderDao).getMasterOrders(master);

        List<Order> resultOrders = orderService.getMasterOrders(ID_MASTER);
        Assertions.assertNotNull(resultOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrders.size());
        Assertions.assertFalse(resultOrders.isEmpty());
        Assertions.assertEquals(orders, resultOrders);
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.times(1)).getMasterOrders(master);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_getMasterOrders_masterDao_findById_wrongId() {
        Master master = getTestMaster();
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);

        Assertions.assertThrows(DaoException.class, () -> orderService.getMasterOrders(ID_MASTER));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.never()).getMasterOrders(master);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_getMasterOrders_orderDao_getMasterOrders_emptyMasterListOrders() {
        Master master = getTestMaster();
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doThrow(DaoException.class).when(orderDao).getMasterOrders(master);

        Assertions.assertThrows(DaoException.class, () -> orderService.getMasterOrders(ID_MASTER));
        Mockito.verify(masterDao, Mockito.times(1)).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.times(1)).getMasterOrders(master);
        Mockito.reset(orderDao);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_getOrderMasters() {
        Order order = getTestOrder();
        List<Master> masters = getTestMasters();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);

        List<Master> resultMasters = orderService.getOrderMasters(ID_ORDER);
        Assertions.assertNotNull(resultMasters);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMasters.size());
        Assertions.assertFalse(resultMasters.isEmpty());
        Assertions.assertEquals(masters, resultMasters);
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).getOrderMasters(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getOrderMasters_orderDao_findById_wrongId() {
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_MASTER);

        Assertions.assertThrows(DaoException.class, () -> orderService.getOrderMasters(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getOrderMasters_orderDao_getOrderMasters_emptyOrderListMasters() {
        Order order = getTestOrder();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).getOrderMasters(order);

        Assertions.assertThrows(DaoException.class, () -> orderService.getOrderMasters(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).getOrderMasters(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getNumberOrders() {
        Mockito.doReturn(RIGHT_NUMBER_ORDERS).when(orderDao).getNumberOrders();

        Long resultNumberOrders = orderService.getNumberOrders();
        Assertions.assertNotNull(resultNumberOrders);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultNumberOrders);
        Mockito.verify(orderDao, Mockito.times(1)).getNumberOrders();
        Mockito.reset(orderDao);
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
        Order orderOne = getTestOrder();
        Order orderTwo = getTestOrder();
        orderTwo.setId(ID_ORDER_OTHER);
        return Arrays.asList(orderOne, orderTwo);
    }

    private List<Master> getTestMasters() {
        Master masterOne = getTestMaster();
        Master masterTwo = getTestMaster();
        masterTwo.setId(ID_MASTER_OTHER);
        return Arrays.asList(masterOne, masterTwo);
    }
}