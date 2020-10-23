package com.senla.carservice.controller;

import com.senla.carservice.controller.exception.ControllerException;
import com.senla.carservice.dto.ClientMessageDto;
import com.senla.carservice.dto.DateDto;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.enumaration.OrderSortParameter;
import com.senla.carservice.util.DateUtil;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addOrder(@RequestBody OrderDto orderDto) {
        return orderService.addOrder(orderDto);
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/freeDate")
    @ResponseStatus(HttpStatus.OK)
    public DateDto getNearestFreeDate() {
        DateDto dateDto = new DateDto();
        dateDto.setDate(orderService.getNearestFreeDate());
        return dateDto;
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrders(@RequestParam(required = false) OrderSortParameter sortParameter,
           @RequestParam (required = false) String startPeriod, @RequestParam (required = false) String endPeriod) {
        if (sortParameter == null && startPeriod == null && endPeriod == null) {
            return orderService.getOrders();
        } else if (startPeriod == null && endPeriod == null) {
            return orderService.getSortOrders(sortParameter);
        } else if (sortParameter != null && startPeriod != null && endPeriod != null) {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod.replace('%', ' '), true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod.replace('%', ' '), true);
            return orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate, sortParameter);
        } else {
            throw new ControllerException("Wrong request parameters");
        }
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{id}/complete")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto completeOrder(@PathVariable("id") Long orderId) {
        orderService.completeOrder(orderId);
        return new ClientMessageDto("The order has been transferred to execution status successfully");
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{id}/close")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto closeOrder(@PathVariable("id") Long orderId) {
        orderService.closeOrder(orderId);
        return new ClientMessageDto("The order has been completed successfully");
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto cancelOrder(@PathVariable("id") Long orderId) {
        orderService.cancelOrder(orderId);
        return new ClientMessageDto("The order has been canceled successfully");
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto deleteOrder(@PathVariable("id") Long orderId) {
        orderService.deleteOrder(orderId);
        return new ClientMessageDto("The order has been deleted successfully");
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @PutMapping("/shiftLeadTime")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto shiftLeadTime(@RequestBody OrderDto orderDto) {
        orderService.shiftLeadTime(orderDto);
        return new ClientMessageDto("The order lead time has been changed successfully");
    }

    @Secured({"ROLE_USER", "ROLE_ADMIN"})
    @GetMapping("/{id}/masters")
    @ResponseStatus(HttpStatus.OK)
    public List<MasterDto> getOrderMasters(@PathVariable("id") Long orderId) {
        return orderService.getOrderMasters(orderId);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/csv/export")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto exportEntities() {
        orderService.exportEntities();
        return new ClientMessageDto("Export completed successfully!");
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/csv/import")
    @ResponseStatus(HttpStatus.OK)
    public ClientMessageDto importEntities() {
        orderService.importEntities();
        return new ClientMessageDto("Imported completed successfully!");
    }

}
