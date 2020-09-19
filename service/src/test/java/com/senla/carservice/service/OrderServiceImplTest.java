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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class OrderServiceImplTest {
    private static final Long RIGHT_NUMBER_MASTERS = 2L;
    private static final Long RIGHT_NUMBER_PLACES = 2L;
    private static final Long WRONG_NUMBER_MASTERS = 0L;
    private static final Long WRONG_NUMBER_PLACES = 0L;
    private static final Long ID_PLACE = 1L;
    private static final Long ID_MASTER = 1L;
    private static final Long ID_ORDER = 1L;
    private static final int NUMBER_PLACE = 1;
    private static final int NUMBER_ORDERS = 1;
    private static final int UPDATE_NUMBER_ORDERS = 0;
    private static final BigDecimal PRICE = BigDecimal.valueOf(123);
    private static final String PARAMETER_AUTOMAKER = "test automaker";
    private static final String PARAMETER_MODEL = "test model";
    private static final String PARAMETER_REGISTRATION_NUMBER = "registrationNumber";
    @Autowired
    private OrderService orderService;
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PlaceDao placeDao;

    @Test
    void checkGetOrdersShouldReturnList() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        List<Order> orders = Collections.singletonList(order);
        Mockito.doReturn(orders).when(orderDao).getAllRecords();
        List<Order> resultOrders = orderService.getOrders();
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getAllRecords();
        Assertions.assertEquals(orders, resultOrders);
    }

    @Test
    void checkGetOrdersShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(orderDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> orderService.getOrders());
    }

    @Test
    void checkAddOrderShouldCreateOrder() {
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Mockito.doReturn(place).when(placeDao).findById(ID_PLACE);
        orderService.addOrder(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        Mockito.verify(placeDao, Mockito.atLeastOnce()).findById(ID_PLACE);
        Mockito.verify(orderDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Order.class));
    }

    @Test
    void checkAddOrderShouldThrowException() {
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Assertions.assertThrows(BusinessException.class, () -> orderService
            .addOrder(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER));
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberMasters();
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberPlaces();
        Assertions.assertThrows(BusinessException.class, () -> orderService
            .addOrder(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER));
    }

    @Test
    void checkAddOrderDeadlinesShouldAddDeadlineToOrder() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_MODEL);
        order.setId(ID_ORDER);
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(executionStartTime);
        orderService.addOrderDeadlines(executionStartTime, leadTime);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
    }

    @Test
    void checkAddOrderDeadlinesShouldThrowException() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date wrongExecutionStartTime = new Date();
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();
        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderDeadlines(executionStartTime, leadTime));
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);
        Assertions
            .assertThrows(BusinessException.class, () -> orderService.addOrderDeadlines(executionStartTime, leadTime));
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(executionStartTime);
        Assertions
            .assertThrows(BusinessException.class, () -> orderService.addOrderDeadlines(executionStartTime, leadTime));
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(wrongExecutionStartTime);
        Assertions
            .assertThrows(DateException.class, () -> orderService.addOrderDeadlines(wrongExecutionStartTime, leadTime));
    }

    @Test
    void checkAddOrderMastersShouldAddMasterToOrder() {
        Master master = new Master("test name");
        master.setId(ID_MASTER);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        orderService.addOrderMasters(ID_MASTER);
        Assertions.assertEquals(1, master.getNumberOrders());
        Assertions.assertEquals(master, order.getMasters().get(0));
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
    }

    @Test
    void checkAddOrderMastersShouldThrowException() {
        Master master = new Master("test name");
        master.setId(ID_MASTER);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();
        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderMasters(ID_MASTER));
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);
        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderMasters(ID_MASTER));
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        master.setDeleteStatus(true);
        Assertions.assertThrows(BusinessException.class, () -> orderService.addOrderMasters(ID_MASTER));
        master.setDeleteStatus(false);
        order.getMasters().add(master);
        Assertions.assertThrows(BusinessException.class, () -> orderService.addOrderMasters(ID_MASTER));
    }

    @Test
    void checkAddOrderPlaceShouldAddPlaceToOrder() {
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doReturn(place).when(placeDao).findById(ID_MASTER);
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        orderService.addOrderPlace(ID_PLACE);
        Assertions.assertEquals(place, order.getPlace());
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
    }

    @Test
    void checkAddOrderPlaceShouldThrowException() {
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();
        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderPlace(ID_PLACE));
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doThrow(DaoException.class).when(placeDao).findById(ID_PLACE);
        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderPlace(ID_PLACE));
    }

    @Test
    void checkAddOrderPriceShouldAddPriceToOrder() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        orderService.addOrderPrice(PRICE);
        Assertions.assertEquals(PRICE, order.getPrice());
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
    }

    @Test
    void checkAddOrderPriceShouldThrowException() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).getLastOrder();
        Assertions.assertThrows(DaoException.class, () -> orderService.addOrderPrice(PRICE));
    }

    @Test
    void checkCompleteOrderShouldChangeStatusIntoPerform() {
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        orderService.completeOrder(ID_PLACE);
        Assertions.assertEquals(StatusOrder.PERFORM, order.getStatus());
        Assertions.assertEquals(true, order.getPlace().getBusy());
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.atLeastOnce()).updateRecord(place);
    }

    @Test
    void checkCompleteOrderShouldThrowException() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);
        Assertions.assertThrows(DaoException.class, () -> orderService.completeOrder(ID_ORDER));
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);
        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
        order.setDeleteStatus(false);
        order.setStatus(StatusOrder.COMPLETED);
        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
        order.setStatus(StatusOrder.PERFORM);
        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
        order.setStatus(StatusOrder.CANCELED);
        Assertions.assertThrows(BusinessException.class, () -> orderService.completeOrder(ID_ORDER));
    }

    @Test
    void checkCancelOrderShouldReturn() {
        Master master = new Master("test name");
        master.setId(ID_MASTER);
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);
        orderService.cancelOrder(ID_PLACE);
        Assertions.assertEquals(StatusOrder.CANCELED, order.getStatus());
        Assertions.assertEquals(false, order.getPlace().getBusy());
        Assertions.assertEquals(UPDATE_NUMBER_ORDERS, master.getNumberOrders());
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.atLeastOnce()).updateRecord(place);
        Mockito.verify(masterDao, Mockito.atLeastOnce()).updateAllRecords(masters);
    }

    @Test
    void checkCancelOrderShouldThrowException() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);
        Assertions.assertThrows(DaoException.class, () -> orderService.cancelOrder(ID_ORDER));
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);
        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
        order.setDeleteStatus(false);
        order.setStatus(StatusOrder.COMPLETED);
        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
        order.setStatus(StatusOrder.PERFORM);
        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
        order.setStatus(StatusOrder.CANCELED);
        Assertions.assertThrows(BusinessException.class, () -> orderService.cancelOrder(ID_ORDER));
    }

    @Test
    void checkCloseOrderShouldReturn() {
        Master master = new Master("test name");
        master.setId(ID_MASTER);
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Place place = new Place(NUMBER_PLACE);
        place.setId(ID_PLACE);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);
        orderService.closeOrder(ID_PLACE);
        Assertions.assertEquals(StatusOrder.COMPLETED, order.getStatus());
        Assertions.assertEquals(false, order.getPlace().getBusy());
        Assertions.assertEquals(UPDATE_NUMBER_ORDERS, master.getNumberOrders());
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
        Mockito.verify(placeDao, Mockito.atLeastOnce()).updateRecord(place);
        Mockito.verify(masterDao, Mockito.atLeastOnce()).updateAllRecords(masters);
    }

    @Test
    void checkCloseOrderShouldThrowException() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);
        Assertions.assertThrows(DaoException.class, () -> orderService.closeOrder(ID_ORDER));
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);
        Assertions.assertThrows(BusinessException.class, () -> orderService.closeOrder(ID_ORDER));
        order.setDeleteStatus(false);
        order.setStatus(StatusOrder.COMPLETED);
        Assertions.assertThrows(BusinessException.class, () -> orderService.closeOrder(ID_ORDER));
        order.setStatus(StatusOrder.CANCELED);
        Assertions.assertThrows(BusinessException.class, () -> orderService.closeOrder(ID_ORDER));
    }

    @Test
    void checkDeleteOrderShouldChangeStatusToDelete() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        order.setStatus(StatusOrder.CANCELED);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        orderService.deleteOrder(ID_ORDER);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
        Assertions.assertTrue(order.isDeleteStatus());
    }

    @Test
    void checkDeleteOrderShouldThrowException() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);
        Assertions.assertThrows(DaoException.class, () -> orderService.deleteOrder(ID_ORDER));
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);
        Assertions.assertThrows(BusinessException.class, () -> orderService.deleteOrder(ID_ORDER));
        order.setDeleteStatus(false);
        order.setStatus(StatusOrder.WAIT);
        Assertions.assertThrows(BusinessException.class, () -> orderService.deleteOrder(ID_ORDER));
        order.setStatus(StatusOrder.PERFORM);
        Assertions.assertThrows(BusinessException.class, () -> orderService.deleteOrder(ID_ORDER));
    }

    @Test
    void checkShiftLeadTimeShouldChangeTimeInOrder() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        orderService.shiftLeadTime(ID_ORDER, executionStartTime, leadTime);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).updateRecord(order);
        Assertions.assertEquals(executionStartTime, order.getExecutionStartTime());
        Assertions.assertEquals(leadTime, order.getLeadTime());
    }

    @Test
    void checkShiftLeadTimeShouldThrowException() {
        Date rightExecutionStartTime = DateUtil.addDays(new Date(), 1);
        Date wrongExecutionStartTime = new Date();
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);
        Assertions.assertThrows(DaoException.class,
                                () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        order.setDeleteStatus(true);
        Assertions.assertThrows(BusinessException.class,
                                () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        order.setDeleteStatus(false);
        order.setStatus(StatusOrder.COMPLETED);
        Assertions.assertThrows(BusinessException.class,
                                () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        order.setStatus(StatusOrder.CANCELED);
        Assertions.assertThrows(BusinessException.class,
                                () -> orderService.shiftLeadTime(ID_ORDER, rightExecutionStartTime, rightLeadTime));
        order.setStatus(StatusOrder.WAIT);
        Assertions.assertThrows(DateException.class,
                                () -> orderService.shiftLeadTime(ID_ORDER, wrongExecutionStartTime, rightLeadTime));
    }

    @Test
    void checkGetSortOrdersShouldReturnList() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        List<Order> orders = Collections.singletonList(order);
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByFilingDate();
        List<Order> resultOrders = orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getOrdersSortByFilingDate();
        Assertions.assertEquals(orders, resultOrders);
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByExecutionDate();
        resultOrders = orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getOrdersSortByExecutionDate();
        Assertions.assertEquals(orders, resultOrders);
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByPlannedStartDate();
        resultOrders = orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getOrdersSortByPlannedStartDate();
        Assertions.assertEquals(orders, resultOrders);
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByPrice();
        resultOrders = orderService.getSortOrders(SortParameter.SORT_BY_PRICE);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getOrdersSortByPrice();
        Assertions.assertEquals(orders, resultOrders);
        Mockito.doReturn(orders).when(orderDao).getExecuteOrderSortByFilingDate();
        resultOrders = orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getExecuteOrderSortByFilingDate();
        Assertions.assertEquals(orders, resultOrders);
        Mockito.doReturn(orders).when(orderDao).getExecuteOrderSortExecutionDate();
        resultOrders = orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getExecuteOrderSortExecutionDate();
        Assertions.assertEquals(orders, resultOrders);
    }

    @Test
    void checkGetSortOrdersShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByFilingDate();
        Assertions
            .assertThrows(DaoException.class, () -> orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE));
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByExecutionDate();
        Assertions
            .assertThrows(DaoException.class, () -> orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE));
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByPlannedStartDate();
        Assertions
            .assertThrows(DaoException.class, () -> orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE));
        Mockito.doThrow(DaoException.class).when(orderDao).getOrdersSortByPrice();
        Assertions.assertThrows(DaoException.class, () -> orderService.getSortOrders(SortParameter.SORT_BY_PRICE));
        Mockito.doThrow(DaoException.class).when(orderDao).getExecuteOrderSortByFilingDate();
        Assertions.assertThrows(DaoException.class,
                                () -> orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE));
        Mockito.doThrow(DaoException.class).when(orderDao).getExecuteOrderSortExecutionDate();
        Assertions.assertThrows(DaoException.class,
                                () -> orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE));
    }

    @Test
    void checkGetSortOrdersByPeriodShouldReturnList() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        List<Order> orders = Collections.singletonList(order);
        checkGetCompletedOrdersSortByFilingDate(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkGetCompletedOrdersSortByExecutionDate(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkGetCompletedOrdersSortByPrice(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkGetCanceledOrdersSortByFilingDate(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkGetCanceledOrdersSortByExecutionDate(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkGetCanceledOrdersSortByPrice(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkDeletedOrdersSortByFilingDate(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkDeletedOrdersSortByExecutionDate(orders, rightStartPeriodDate, rightEndPeriodDate);
        checkDeletedOrdersSortByPrice(orders, rightStartPeriodDate, rightEndPeriodDate);
    }

    void checkGetCompletedOrdersSortByFilingDate(
        List<Order> orders,
        Date rightStartPeriodDate,
        Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao)
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkGetCompletedOrdersSortByExecutionDate(
        List<Order> orders,
        Date rightStartPeriodDate,
        Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao)
            .getCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkGetCompletedOrdersSortByPrice(List<Order> orders, Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao).getCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                                                      SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkGetCanceledOrdersSortByFilingDate(
        List<Order> orders,
        Date rightStartPeriodDate,
        Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao)
            .getCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                                                      SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkGetCanceledOrdersSortByExecutionDate(
        List<Order> orders,
        Date rightStartPeriodDate,
        Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao)
            .getCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                                                      SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkGetCanceledOrdersSortByPrice(List<Order> orders, Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao).getCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                                                      SortParameter.CANCELED_ORDERS_SORT_BY_PRICE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkDeletedOrdersSortByFilingDate(List<Order> orders, Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao)
            .getDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                                                      SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkDeletedOrdersSortByExecutionDate(List<Order> orders, Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao)
            .getDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                                                      SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    void checkDeletedOrdersSortByPrice(List<Order> orders, Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doReturn(orders).when(orderDao).getDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        List<Order> resultOrders = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                                                      SortParameter.DELETED_ORDERS_SORT_BY_PRICE);
        Mockito.verify(orderDao, Mockito.atLeastOnce())
            .getDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertEquals(orders, resultOrders);
    }

    @Test
    void checkGetSortOrdersByPeriodShouldThrowException() {
        Date rightStartPeriodDate = DateUtil.addDays(new Date(), 1);
        Date wrongStartPeriodDate = DateUtil.addDays(new Date(), 3);
        Date rightEndPeriodDate = DateUtil.addDays(new Date(), 2);
        checkWrongDate(wrongStartPeriodDate, rightEndPeriodDate);
        checkEmptyListOrders(rightStartPeriodDate, rightEndPeriodDate);
        checkCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        checkCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        checkCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        checkCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        checkCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        checkCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        checkDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        checkDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        checkDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
    }

    void checkWrongDate(Date wrongStartPeriodDate, Date rightEndPeriodDate) {
            Assertions.assertThrows(DateException.class, () -> orderService
                .getSortOrdersByPeriod(wrongStartPeriodDate, rightEndPeriodDate, SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
        }

    void checkEmptyListOrders(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doReturn(new ArrayList<>()).when(orderDao)
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(BusinessException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
    }

    void checkCompletedOrdersSortByFilingDate(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
    }

    void checkCompletedOrdersSortByExecutionDate(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE));
    }

    void checkCompletedOrdersSortByPrice(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE));
    }

    void checkCanceledOrdersSortByFilingDate(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE));
    }
    void checkCanceledOrdersSortByExecutionDate(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE));
    }

    void checkCanceledOrdersSortByPrice(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.CANCELED_ORDERS_SORT_BY_PRICE));
    }

    void checkDeletedOrdersSortByFilingDate(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE));
    }

    void checkDeletedOrdersSortByExecutionDate(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE));
    }

    void checkDeletedOrdersSortByPrice(Date rightStartPeriodDate, Date rightEndPeriodDate) {
        Mockito.doThrow(DaoException.class).when(orderDao)
            .getDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);
        Assertions.assertThrows(DaoException.class, () -> orderService
            .getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
                                   SortParameter.DELETED_ORDERS_SORT_BY_PRICE));
    }

    @Test
    void checkGetMasterOrdersShouldReturnList() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Master master = new Master("test name");
        master.setId(ID_MASTER);
        List<Order> orders = Collections.singletonList(order);
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doReturn(orders).when(orderDao).getMasterOrders(master);
        List<Order> resultOrders = orderService.getMasterOrders(ID_MASTER);
        Mockito.verify(masterDao, Mockito.atLeastOnce()).findById(ID_MASTER);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getMasterOrders(master);
        Assertions.assertEquals(resultOrders, orders);
    }

    @Test
    void checkGetMasterOrdersShouldThrowException() {
        Master master = new Master("test name");
        master.setId(ID_MASTER);
        Mockito.doThrow(DaoException.class).when(masterDao).findById(ID_MASTER);
        Assertions.assertThrows(DaoException.class, () -> orderService.getMasterOrders(ID_MASTER));
        Mockito.doReturn(master).when(masterDao).findById(ID_MASTER);
        Mockito.doThrow(DaoException.class).when(orderDao).getMasterOrders(master);
        Assertions.assertThrows(DaoException.class, () -> orderService.getMasterOrders(ID_MASTER));
    }

    @Test
    void checkGetOrderMastersShouldReturn() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Master master = new Master("test name");
        master.setId(ID_MASTER);
        master.setNumberOrders(NUMBER_ORDERS);
        List<Master> masters = Collections.singletonList(master);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);
        List<Master> resultMasters = orderService.getOrderMasters(ID_ORDER);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.atLeastOnce()).getOrderMasters(order);
        Assertions.assertEquals(resultMasters, masters);
    }

    @Test
    void checkGetNumberOrdersShouldReturn() {
        Order order = new Order(PARAMETER_AUTOMAKER, PARAMETER_MODEL, PARAMETER_REGISTRATION_NUMBER);
        order.setId(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_MASTER);
        Assertions.assertThrows(DaoException.class, () -> orderService.getOrderMasters(ID_ORDER));
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doThrow(DaoException.class).when(orderDao).getOrderMasters(order);
        Assertions.assertThrows(DaoException.class, () -> orderService.getOrderMasters(ID_ORDER));
    }
}