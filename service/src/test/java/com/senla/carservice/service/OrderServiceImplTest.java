package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.config.TestConfig;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private PlaceDao placeDao;
    private List<Order> orders;

    @BeforeEach
    public void init() {
        orders = new ArrayList<>();
        orders.add(new Order("automaker1", "model1", "registrationNumber1"));
        orders.add(new Order("automaker2", "model2", "registrationNumber2"));
        orders.add(new Order("automaker3", "model3", "registrationNumber3"));
    }

    @Test
    void checkGetOrdersShouldReturnList() {
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
        Long idPlace = 1L;
        int numberPlace = 1;
        Long numberMasters = 2L;
        Long numberPlaces = 2L;
        String automaker = "test automaker";
        String model = "test model";
        String registrationNumber = "test registration number";
        Place place = new Place(numberPlace);
        place.setId(idPlace);
        Mockito.doReturn(numberMasters).when(masterDao).getNumberMasters();
        Mockito.doReturn(numberPlaces).when(placeDao).getNumberPlaces();
        Mockito.doReturn(place).when(placeDao).findById(idPlace);
        orderService.addOrder(automaker, model, registrationNumber);
        Mockito.verify(placeDao, Mockito.atLeastOnce()).findById(idPlace);
        Mockito.verify(orderDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Order.class));
    }

    @Test
    void checkAddOrderShouldThrowExceptionOnCheckMasters() {
        Long numberMasters = 0L;
        Long numberPlaces = 2L;
        String automaker = "test automaker";
        String model = "test model";
        String registrationNumber = "test registration number";
        Mockito.doReturn(numberMasters).when(masterDao).getNumberMasters();
        Mockito.doReturn(numberPlaces).when(placeDao).getNumberPlaces();
        Assertions.assertThrows(BusinessException.class, () -> orderService.addOrder(automaker, model, registrationNumber));
    }
// TODO join check throws
    @Test
    void checkAddOrderShouldThrowExceptionOnCheckPlaces() {
        Long numberMasters = 2L;
        Long numberPlaces = 0L;
        String automaker = "test automaker";
        String model = "test model";
        String registrationNumber = "test registration number";
        Mockito.doReturn(numberMasters).when(masterDao).getNumberMasters();
        Mockito.doReturn(numberPlaces).when(placeDao).getNumberPlaces();
        Assertions.assertThrows(BusinessException.class, () -> orderService.addOrder(automaker, model, registrationNumber));
    }

    @Test
    void checkAddOrderDeadlinesAddDeadlineToOrder() {
        Long numberMasters = 2L;
        Long numberPlaces = 2L;
        Date executionStartTime = DateUtil.addDays(new Date(), 1);
        Date leadTime = DateUtil.addDays(new Date(), 2);
        Order order = new Order();
        Mockito.doReturn(order).when(orderDao).getLastOrder();
        Mockito.doReturn(numberMasters).when(masterDao).getNumberFreeMasters(executionStartTime);
        Mockito.doReturn(numberPlaces).when(placeDao).getNumberFreePlaces(executionStartTime);
        orderService.addOrderDeadlines(executionStartTime, leadTime);
        Mockito.verify(orderDao, Mockito.times(1)).updateRecord(order);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(executionStartTime);
        Mockito.verify(placeDao, Mockito.times(1)).getNumberFreePlaces(executionStartTime);
    }
    //TODO THROWS

    @Test
    void checkAddOrderMastersShouldReturn() {
    }

    @Test
    void checkAddOrderPlaceShouldReturn() {
    }

    @Test
    void checkAddOrderPriceShouldReturn() {
    }

    @Test
    void checkCompleteOrderShouldReturn() {
    }

    @Test
    void checkCancelOrderShouldReturn() {
    }

    @Test
    void checkCloseOrderShouldReturn() {
    }

    @Test
    void checkDeleteOrderShouldReturn() {
    }

    @Test
    void checkShiftLeadTimeShouldReturn() {
    }

    @Test
    void checkGetSortOrdersShouldReturn() {
    }

    @Test
    void checkGetSortOrdersByPeriodShouldReturn() {
    }

    @Test
    void checkGetMasterOrdersShouldReturn() {
    }

    @Test
    void checkGetOrderMastersShouldReturn() {
    }

    @Test
    void checkGetNumberOrdersShouldReturn() {
    }
}