package com.senla.carservice.controller;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
@NoArgsConstructor
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("orders")
    public String addOrder(@RequestBody OrderDto orderDto) {
        log.info("Method addOrder");
        log.trace("Parameters orderDto: {}", orderDto);
        orderService.addOrder(orderDto);
        return "order add successfully!";
    }

    @GetMapping("orders/check-dates")
    public String checkOrderDeadlines(@RequestParam String stringExecutionStartTime, @RequestParam String stringLeadTime) {
        log.info("Method addOrderDeadlines");
        log.trace("Parameter stringExecutionStartTime: {}, stringLeadTime: {}", stringExecutionStartTime,
                  stringLeadTime);
        Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime, true);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
        orderService.checkOrderDeadlines(executionStartTime, leadTime);
        return "dates are right";
    }

    @GetMapping("orders/check")
    public String checkOrders() {
        log.info("Method checkPlaces");
        if (orderService.getNumberOrders() == 0) {
                throw new BusinessException("There are no orders");
        }
        return "verification was successfully";
    }

    @GetMapping("orders")
    public List<OrderDto> getOrders() {
        log.info("Method getOrders");
        return orderService.getOrders();
    }

    @PutMapping("orders/complete")
    public String completeOrder(@RequestBody OrderDto orderDto) {
        log.info("Method completeOrder");
        log.trace("Parameter orderDto: {}", orderDto);
            orderService.completeOrder(orderDto);
            return " - the order has been transferred to execution status";
    }

    @PutMapping("orders/close")
    public String closeOrder(@RequestBody OrderDto orderDto) {
        log.info("Method closeOrder");
        log.trace("Parameter orderDto: {}", orderDto);
            orderService.closeOrder(orderDto);
            return " -the order has been completed.";
    }

    @PutMapping("orders/cancel")
    public String cancelOrder(@RequestBody OrderDto orderDto) {
        log.info("Method cancelOrder");
        log.trace("Parameter orderDto: {}", orderDto);
            orderService.cancelOrder(orderDto);
            return " -the order has been canceled.";
    }

    @DeleteMapping("orders")
    public String deleteOrder(@RequestBody OrderDto orderDto) {
        log.info("Method deleteOrder");
        log.trace("Parameter orderDto: {}", orderDto);
            orderService.deleteOrder(orderDto);
            return " -the order has been deleted.";
    }

    @PutMapping("orders/shift-lead-time")
    public String shiftLeadTime(@RequestBody OrderDto orderDto, @RequestParam String stringStartTime,
                                @RequestParam String stringLeadTime) {
        log.info("Method shiftLeadTime");
        log.trace("Parameters orderDto: {}, stringStartTime: {}, stringLeadTime: {}", orderDto, stringStartTime,
                  stringLeadTime);
        Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
        orderService.shiftLeadTime(orderDto, executionStartTime, leadTime);
        return " -the order lead time has been changed.";

    }

    @GetMapping("orders/sort-by-filing-date")
    public List<OrderDto> getOrdersSortByFilingDate() {
        log.info("Method getOrdersSortByFilingDate");
        return orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE);
    }

    @GetMapping("orders/sort-by-execution-date")
    public List<OrderDto> getOrdersSortByExecutionDate() {
        log.info("Method getOrdersSortByExecutionDate");
        return orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE);
    }

    @GetMapping("orders/sort-by-planned-start-date")
    public List<OrderDto> getOrdersSortByPlannedStartDate() {
        log.info("Method getOrdersSortByPlannedStartDate");
        return orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE);
    }

    @GetMapping("orders/sort-by-price")
    public List<OrderDto> getOrdersSortByPrice() {
        log.info("Method getOrdersSortByPrice");
        return orderService.getSortOrders(SortParameter.SORT_BY_PRICE);
    }

    @GetMapping("orders/execute/sort-by-filing-date")
    public List<OrderDto> getExecuteOrderFilingDate() {
        log.info("Method getExecuteOrderFilingDate");
        return orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE);
    }

    @GetMapping("orders/execute/sort-by-execution-date")
    public List<OrderDto> getExecuteOrderExecutionDate() {
        log.info("Method getExecuteOrderExecutionDate");
        return orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE);
    }

    @GetMapping("orders/complete/sort-by-filing-date")
    public List<OrderDto> getCompletedOrdersFilingDate(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getCompletedOrdersFilingDate");
        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE);
    }

    @GetMapping("orders/complete/sort-by-execution-date")
    public List<OrderDto> getCompletedOrdersExecutionDate(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getCompletedOrdersExecutionDate");
        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE);
    }

    @GetMapping("orders/complete/sort-by-price")
    public List<OrderDto> getCompletedOrdersPrice(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getCompletedOrdersPrice");
        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE);
    }

    @GetMapping("orders/canceled/sort-by-filing-date")
    public List<OrderDto> getCanceledOrdersFilingDate(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getCanceledOrdersFilingDate");
        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE);
    }

    @GetMapping("orders/canceled/sort-by-execution-date")
    public List<OrderDto> getCanceledOrdersExecutionDate(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getCanceledOrdersExecutionDate");
        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE);
    }

    @GetMapping("orders/canceled/sort-by-price")
    public List<OrderDto> getCanceledOrdersPrice(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getCanceledOrdersPrice");
        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.CANCELED_ORDERS_SORT_BY_PRICE);
    }

    @GetMapping("orders/deleted/sort-by-filing-date")
    public List<OrderDto> getDeletedOrdersFilingDate(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getDeletedOrdersFilingDate");
        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE);
    }

    @GetMapping("orders/deleted/sort-by-execution-date")
    public List<OrderDto> getDeletedOrdersExecutionDate(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getDeletedOrdersExecutionDate");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE);
    }

    @GetMapping("orders/deleted/sort-by-price")
    public List<OrderDto> getDeletedOrdersPrice(@RequestParam String startPeriod, @RequestParam String endPeriod) {
        log.info("Method getDeletedOrdersPrice");
        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
           SortParameter.DELETED_ORDERS_SORT_BY_PRICE);
    }

    @GetMapping("orders/master-orders")
    public List<OrderDto> getMasterOrders(@RequestBody MasterDto masterDto) {
        log.info("Method getMasterOrders");
        log.trace("Parameter masterDto: {}", masterDto);
        return orderService.getMasterOrders(masterDto);
    }

    @GetMapping("orders/masters")
    public List<MasterDto> getOrderMasters(@RequestBody OrderDto orderDto) {
        log.info("Method getOrderMasters");
        log.trace("Parameter orderDto: {}", orderDto);
        return orderService.getOrderMasters(orderDto);
    }
}