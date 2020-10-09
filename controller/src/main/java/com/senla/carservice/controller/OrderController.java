package com.senla.carservice.controller;

import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.enumaration.OrderSortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/orders")
@NoArgsConstructor
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderDto addOrder(@RequestBody OrderDto orderDto) {
        log.info("Method addOrder");
        log.trace("Parameters orderDto: {}", orderDto);
        return orderService.addOrder(orderDto);
    }

    @GetMapping("check/dates")
    public ClientMessageDto checkOrderDeadlines(@RequestParam String stringExecutionStartTime, @RequestParam String stringLeadTime) {
        log.info("Method addOrderDeadlines");
        log.trace("Parameters stringExecutionStartTime: {}, stringLeadTime: {}", stringExecutionStartTime, stringLeadTime);
        Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime.replace('%', ' '), true);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime.replace('%', ' '), true);
        orderService.checkOrderDeadlines(executionStartTime, leadTime);
        return new ClientMessageDto("dates are right");
    }
    //ToDo unite sort methods
    @GetMapping("check")
    public ClientMessageDto checkOrders() {
        log.info("Method checkPlaces");
        orderService.checkOrders();
        return new ClientMessageDto("verification was successfully");
    }

    @GetMapping
    public List<OrderDto> getOrders() {
        log.info("Method getOrders");
        return orderService.getOrders();
    }

    @PutMapping("{id}/complete")
    public ClientMessageDto completeOrder(@PathVariable("id") Long orderId) {
        log.info("Method completeOrder");
        log.trace("Parameter orderId: {}", orderId);
            orderService.completeOrder(orderId);
            return new ClientMessageDto("The order has been transferred to execution status successfully");
    }

    @PutMapping("{id}/close")
    public ClientMessageDto closeOrder(@PathVariable("id") Long orderId) {
        log.info("Method closeOrder");
        log.trace("Parameter orderId: {}", orderId);
            orderService.closeOrder(orderId);
            return new ClientMessageDto("The order has been completed successfully");
    }

    @PutMapping("{id}/cancel")
    public ClientMessageDto cancelOrder(@PathVariable("id") Long orderId) {
        log.info("Method cancelOrder");
        log.trace("Parameter orderId: {}", orderId);
            orderService.cancelOrder(orderId);
            return new ClientMessageDto("The order has been canceled successfully");
    }

    @DeleteMapping("{id}")
    public ClientMessageDto deleteOrder(@PathVariable("id") Long orderId) {
        log.info("Method deleteOrder");
        log.trace("Parameter orderId: {}", orderId);
            orderService.deleteOrder(orderId);
            return new ClientMessageDto("The order has been deleted successfully");
    }

    @PutMapping("shiftLeadTime")
    public ClientMessageDto shiftLeadTime(@RequestBody OrderDto orderDto) {
        log.info("Method shiftLeadTime");
        log.trace("Parameters orderDto: {}", orderDto);
        orderService.shiftLeadTime(orderDto);
        return new ClientMessageDto("The order lead time has been changed successfully");

    }

    @GetMapping("sort")
    public List<OrderDto> getSortOrders(String sortParameter) {
        log.info("Method getOrdersSortByFilingDate");
        try {
            OrderSortParameter orderSortParameter = OrderSortParameter.valueOf(sortParameter);
            return orderService.getSortOrders(orderSortParameter);
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Wrong sortParameter");
        }
    }
    //ToDo check mapping with the same url
    @GetMapping("sort")
    public List<OrderDto> getSortOrdersByPeriod(@RequestParam String sortParameter, @RequestParam String startPeriod,
            @RequestParam String endPeriod) {
        log.info("Method getOrdersSortByFilingDate");
        try {
            OrderSortParameter orderSortParameter = OrderSortParameter.valueOf(sortParameter);
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod.replace('%', ' '), true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod.replace('%', ' '), true);
            return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate, orderSortParameter);
        } catch (IllegalArgumentException exception) {
            throw new BusinessException("Wrong sortParameter");
        }
    }

    @GetMapping("{id}/masters")
    public List<MasterDto> getOrderMasters(@PathVariable("id") Long orderId) {
        log.info("Method getOrderMasters");
        log.trace("Parameter orderId: {}", orderId);
        return orderService.getOrderMasters(orderId);
    }

}
