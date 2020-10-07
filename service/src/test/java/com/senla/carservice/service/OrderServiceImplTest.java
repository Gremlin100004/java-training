package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.enumaration.StatusOrder;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
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
    private static final Long WRONG_NUMBER_ORDERS = 0L;
    private static final Long ID_PLACE = 1L;
    private static final Long ID_MASTER = 1L;
    private static final Long ID_OTHER_MASTER = 2L;
    private static final Long ID_ORDER = 1L;
    private static final Long ID_ORDER_OTHER = 2L;
    private static final int NUMBER_PLACE = 1;
    private static final int NUMBER_ORDERS = 1;
    private static final int UPDATE_NUMBER_ORDERS = 0;
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getAllRecords();

        List<OrderDto> resultOrdersDto = orderService.getOrders();
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        Order order = getTestOrder();
        OrderDto orderDto = getTestOrderDto();
        Mockito.doReturn(order).when(orderDao).saveRecord(order);

        Assertions.assertDoesNotThrow(
            () -> orderService.addOrder(orderDto));
        Mockito.verify(orderDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Order.class));
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_checkOrderDeadlines() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);
        Mockito.doReturn(RIGHT_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(executionStartTime);

        Assertions.assertDoesNotThrow(() -> orderService.checkOrderDeadlines(executionStartTime, leadTime));
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_checkOrderDeadlines_wrongDate() {
        Date wrongExecutionStartTime = DateUtil.addDays(new Date(), -2);
        Date leadTime = DateUtil.addDays(new Date(), 2);

        Assertions.assertThrows(DateException.class,
           () -> orderService.checkOrderDeadlines(wrongExecutionStartTime, leadTime));
        Mockito.verify(masterDao, Mockito.never()).getNumberFreeMasters(wrongExecutionStartTime);
        Mockito.verify(placeDao, Mockito.never()).getNumberFreePlaces(wrongExecutionStartTime);
    }

    @Test
    void OrderServiceImpl_checkOrderDeadlines_zeroNumberOfMasters() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Mockito.doReturn(WRONG_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);

        Assertions.assertThrows(BusinessException.class,
           () -> orderService.checkOrderDeadlines(executionStartTime, leadTime));
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_checkOrderDeadlines_businessException_zeroNumberOfPlaces() {
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Mockito.doReturn(RIGHT_NUMBER_MASTERS).when(masterDao).getNumberFreeMasters(executionStartTime);
        Mockito.doReturn(WRONG_NUMBER_PLACES).when(placeDao).getNumberFreePlaces(executionStartTime);

        Assertions.assertThrows(BusinessException.class,
           () -> orderService.checkOrderDeadlines(executionStartTime, leadTime));
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
        Mockito.reset(placeDao);
        Mockito.reset(masterDao);
    }

    @Test
    void OrderServiceImpl_completeOrder() {
        Place place = getTestPlace();
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertDoesNotThrow(() -> orderService.completeOrder(ID_PLACE));
        Assertions.assertEquals(StatusOrder.PERFORM, order.getStatus());
        Assertions.assertEquals(true, order.getPlace().getIsBusy());
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
        place.setIsBusy(true);
        Order order = getTestOrder();
        order.setPlace(place);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);

        Assertions.assertDoesNotThrow(() -> orderService.cancelOrder(ID_PLACE));
        Assertions.assertEquals(StatusOrder.CANCELED, order.getStatus());
        Assertions.assertEquals(false, order.getPlace().getIsBusy());
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
        Assertions.assertEquals(false, order.getPlace().getIsBusy());
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
        String rightExecutionStartTime = DateUtil.getStringFromDate(DateUtil.addDays(new Date(), 1), true);
        String rightLeadTime = DateUtil.getStringFromDate(DateUtil.addDays(new Date(), 2), true);
        Order order = getTestOrder();
        OrderDto orderDto = getTestOrderDto();
        orderDto.setExecutionStartTime(rightExecutionStartTime);
        orderDto.setLeadTime(rightLeadTime);
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);

        Assertions.assertDoesNotThrow(() -> orderService.shiftLeadTime(orderDto));
        Assertions.assertEquals(rightExecutionStartTime, DateUtil.getStringFromDate(order.getExecutionStartTime(), true));
        Assertions.assertEquals(rightLeadTime, DateUtil.getStringFromDate(order.getLeadTime(), true));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime_wrongDate() {
        Date wrongExecutionStartTime = DateUtil.addDays(new Date(), -2);
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        OrderDto orderDto = getTestOrderDto();
        orderDto.setExecutionStartTime(DateUtil.getStringFromDate(wrongExecutionStartTime, true));
        orderDto.setLeadTime(DateUtil.getStringFromDate(rightLeadTime, true));

        Assertions.assertThrows(DateException.class,
            () -> orderService.shiftLeadTime(orderDto));
        Mockito.verify(orderDao, Mockito.never()).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_shiftLeadTime_orderDao_findById_wrongId() {
        Date rightExecutionStartTime = DateUtil.addDays(new Date(), 1);
        Date rightLeadTime = DateUtil.addDays(new Date(), 2);
        Order order = getTestOrder();
        OrderDto orderDto = getTestOrderDto();
        orderDto.setExecutionStartTime(DateUtil.getStringFromDate(rightExecutionStartTime, true));
        orderDto.setLeadTime(DateUtil.getStringFromDate(rightLeadTime, true));
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(DaoException.class, () -> orderService.shiftLeadTime(orderDto));
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
        OrderDto orderDto = getTestOrderDto();
        orderDto.setExecutionStartTime(DateUtil.getStringFromDate(rightExecutionStartTime, true));
        orderDto.setLeadTime(DateUtil.getStringFromDate(rightLeadTime, true));
        orderDto.setDeleteStatus(true);

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.shiftLeadTime(orderDto));
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
        OrderDto orderDto = getTestOrderDto();
        orderDto.setExecutionStartTime(DateUtil.getStringFromDate(rightExecutionStartTime, true));
        orderDto.setLeadTime(DateUtil.getStringFromDate(rightLeadTime, true));
        orderDto.setStatus(String.valueOf(StatusOrder.COMPLETED));

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.shiftLeadTime(orderDto));
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
        OrderDto orderDto = getTestOrderDto();
        orderDto.setExecutionStartTime(DateUtil.getStringFromDate(rightExecutionStartTime, true));
        orderDto.setLeadTime(DateUtil.getStringFromDate(rightLeadTime, true));
        orderDto.setStatus(String.valueOf(StatusOrder.CANCELED));

        Assertions.assertThrows(BusinessException.class,
            () -> orderService.shiftLeadTime(orderDto));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).updateRecord(order);
        Mockito.reset(orderDao);
        }

    @Test
    void OrderServiceImpl_getSortOrders_orderDao_getOrdersSortByFilingDate() {
        List<Order> orders = getTestOrders();
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByFilingDate();

        List<OrderDto> resultOrdersDto = orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByExecutionDate();

        List<OrderDto> resultOrdersDto = orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByPlannedStartDate();

        List<OrderDto> resultOrdersDto = orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getOrdersSortByPrice();

        List<OrderDto> resultOrdersDto = orderService.getSortOrders(SortParameter.SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getExecuteOrderSortByFilingDate();

        List<OrderDto> resultOrdersDto = orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getExecuteOrderSortExecutionDate();

        List<OrderDto> resultOrdersDto = orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao)
            .getCompletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao)
            .getCompletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getCompletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao)
            .getCanceledOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao)
            .getCanceledOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);
        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getCanceledOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.CANCELED_ORDERS_SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao)
            .getDeletedOrdersSortByFilingDate(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao)
            .getDeletedOrdersSortByExecutionDate(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
        List<OrderDto> ordersDto = getTestOrdersDto();
        Mockito.doReturn(orders).when(orderDao).getDeletedOrdersSortByPrice(rightStartPeriodDate, rightEndPeriodDate);

        List<OrderDto> resultOrdersDto = orderService.getSortOrdersByPeriod(rightStartPeriodDate, rightEndPeriodDate,
            SortParameter.DELETED_ORDERS_SORT_BY_PRICE);
        Assertions.assertNotNull(resultOrdersDto);
        Assertions.assertEquals(RIGHT_NUMBER_ORDERS, resultOrdersDto.size());
        Assertions.assertFalse(resultOrdersDto.isEmpty());
        Assertions.assertEquals(ordersDto, resultOrdersDto);
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
    void OrderServiceImpl_getOrderMasters() {
        Order order = getTestOrder();
        List<Master> masters = getTestMasters();
        List<MasterDto> mastersDto = getTestMastersDto();
        Mockito.doReturn(order).when(orderDao).findById(ID_ORDER);
        Mockito.doReturn(masters).when(orderDao).getOrderMasters(order);

        List<MasterDto> resultMastersDto = orderService.getOrderMasters(ID_ORDER);
        Assertions.assertNotNull(resultMastersDto);
        Assertions.assertEquals(RIGHT_NUMBER_MASTERS, resultMastersDto.size());
        Assertions.assertFalse(resultMastersDto.isEmpty());
        Assertions.assertEquals(mastersDto, resultMastersDto);
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.times(1)).getOrderMasters(order);
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
    void OrderServiceImpl_getOrderMasters_orderDao_findById_wrongId() {
        Order order = getTestOrder();
        Mockito.doThrow(DaoException.class).when(orderDao).findById(ID_ORDER);

        Assertions.assertThrows(DaoException.class, () -> orderService.getOrderMasters(ID_ORDER));
        Mockito.verify(orderDao, Mockito.times(1)).findById(ID_ORDER);
        Mockito.verify(orderDao, Mockito.never()).getOrderMasters(order);
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getNumberOrders() {
        Mockito.doReturn(RIGHT_NUMBER_ORDERS).when(orderDao).getNumberOrders();

        Assertions.assertDoesNotThrow(() -> orderService.checkOrders());
        Mockito.verify(orderDao, Mockito.times(1)).getNumberOrders();
        Mockito.reset(orderDao);
    }

    @Test
    void OrderServiceImpl_getNumberOrders_orderDao_getNumberOrders_zeroNumberOrders() {
        Mockito.doReturn(WRONG_NUMBER_ORDERS).when(orderDao).getNumberOrders();

        Assertions.assertThrows(BusinessException.class, () -> orderService.checkOrders());
        Mockito.verify(orderDao, Mockito.times(1)).getNumberOrders();
        Mockito.reset(orderDao);
    }

    private Master getTestMaster() {
        Master master = new Master();
        master.setId(ID_MASTER);
        master.setName(PARAMETER_NAME);
        return master;
    }

    private MasterDto getTestMasterDto() {
        MasterDto masterDto = new MasterDto();
        masterDto.setName(PARAMETER_NAME);
        masterDto.setId(ID_MASTER);
        return masterDto;
    }

    private Order getTestOrder() {
        Order order = new Order();
        order.setId(ID_ORDER);
        order.setAutomaker(PARAMETER_AUTOMAKER);
        order.setModel(PARAMETER_MODEL);
        order.setRegistrationNumber(PARAMETER_REGISTRATION_NUMBER);
        order.setCreationTime(new Date());
        order.setExecutionStartTime(new Date());
        order.setLeadTime(new Date());
        return order;
    }

    private OrderDto getTestOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(ID_ORDER);
        orderDto.setAutomaker(PARAMETER_AUTOMAKER);
        orderDto.setModel(PARAMETER_MODEL);
        orderDto.setRegistrationNumber(PARAMETER_REGISTRATION_NUMBER);
        return orderDto;
    }

    private Place getTestPlace() {
        Place place = new Place();
        place.setId(ID_PLACE);
        place.setNumber(NUMBER_PLACE);
        return place;
    }

    private List<Order> getTestOrders() {
        Order orderOne = getTestOrder();
        Order orderTwo = getTestOrder();
        orderTwo.setId(ID_ORDER_OTHER);
        return Arrays.asList(orderOne, orderTwo);
    }

    private List<OrderDto> getTestOrdersDto() {
        OrderDto orderDtoOne = getTestOrderDto();
        OrderDto orderDtoTwo = getTestOrderDto();
        orderDtoTwo.setId(ID_ORDER_OTHER);
        return Arrays.asList(orderDtoOne, orderDtoTwo);
    }

    private List<Master> getTestMasters() {
        Master masterOne = getTestMaster();
        Master masterTwo = getTestMaster();
        masterTwo.setId(ID_OTHER_MASTER);
        return Arrays.asList(masterOne, masterTwo);
    }

    private List<MasterDto> getTestMastersDto() {
        MasterDto masterDtoOne = getTestMasterDto();
        MasterDto masterDtoTwo = getTestMasterDto();
        masterDtoTwo.setId(ID_OTHER_MASTER);
        return Arrays.asList(masterDtoOne, masterDtoTwo);
    }
}