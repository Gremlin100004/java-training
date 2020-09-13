package com.senla.carservice.controller;

import com.senla.carservice.controller.util.StringMaster;
import com.senla.carservice.controller.util.StringOrder;
import com.senla.carservice.domain.Order;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderService orderService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private PlaceService placeService;

    public OrderController() {
    }

    public String addOrder(String automaker, String model, String registrationNumber) {
        LOGGER.info("Method addOrder");
        LOGGER.trace("Parameter automaker: {}", automaker);
        LOGGER.trace("Parameter model: {}", model);
        LOGGER.trace("Parameter registrationNumber: {}", registrationNumber);
        try {
            orderService.addOrder(automaker, model, registrationNumber);
            return "order add successfully!";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderDeadlines(String stringExecutionStartTime, String stringLeadTime) {
        LOGGER.info("Method addOrderDeadlines");
        LOGGER.trace("Parameter stringExecutionStartTime: {}", stringExecutionStartTime);
        LOGGER.trace("Parameter stringLeadTime: {}", stringLeadTime);
        try {
            Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime, true);
            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
            orderService.addOrderDeadlines(executionStartTime, leadTime);
            return "deadline add to order successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderMasters(Long idMaster) {
        LOGGER.info("Method addOrderMasters");
        LOGGER.trace("Parameter idMaster: {}", idMaster);
        try {
            orderService.addOrderMasters(idMaster);
            return "masters add successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderPlace(Long idPlace) {
        LOGGER.info("Method addOrderPlace");
        LOGGER.trace("Parameter index: {}", idPlace);
        try {
            orderService.addOrderPlace(idPlace);
            return "place add to order successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderPrice(BigDecimal price) {
        LOGGER.info("Method addOrderPrice");
        LOGGER.trace("Parameter price: {}", price);
        try {
            orderService.addOrderPrice(price);
            return "price add to order successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String checkOrders() {
        LOGGER.info("Method checkPlaces");
        try {
            if (orderService.getNumberOrders() == 0) {
                throw new  BusinessException("There are no orders");
            }
            return "verification was successfully";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public List<String> getOrders() {
        LOGGER.info("Method getOrders");
        List<String> stringList = new ArrayList<>();
        try {
            List<Order> orders = orderService.getOrders();
            stringList.add(StringOrder.getStringFromOrder(orders));
            stringList.addAll(StringOrder.getListId(orders));
            return stringList;
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            stringList.add(e.getMessage());
            return stringList;
        }
    }

    public String completeOrder(Long idOrder) {
        LOGGER.info("Method completeOrder");
        LOGGER.trace("Parameter index: {}", idOrder);
        try {
            if (orderService.getNumberOrders() < idOrder || idOrder < 0) {
                return "There are no such order";
            } else {
                orderService.completeOrder(idOrder);
            }
            return " - the order has been transferred to execution status";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String closeOrder(Long idOrder) {
        LOGGER.info("Method closeOrder");
        LOGGER.trace("Parameter idOrder: {}", idOrder);
        try {
            orderService.closeOrder(idOrder);
            return " -the order has been completed.";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String cancelOrder(Long idOrder) {
        LOGGER.info("Method cancelOrder");
        LOGGER.trace("Parameter idOrder: {}", idOrder);
        try {
            orderService.cancelOrder(idOrder);
            return " -the order has been canceled.";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteOrder(Long idOrder) {
        LOGGER.info("Method deleteOrder");
        LOGGER.trace("Parameter index: {}", idOrder);
        try {
            orderService.deleteOrder(idOrder);
            return " -the order has been deleted.";
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String shiftLeadTime(Long idOrder, String stringStartTime, String stringLeadTime) {
        LOGGER.info("Method shiftLeadTime");
        LOGGER.trace("Parameter index: {}", idOrder);
        LOGGER.trace("Parameter stringStartTime: {}", stringStartTime);
        LOGGER.trace("Parameter stringLeadTime: {}", stringLeadTime);
        try {
            Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
            if (orderService.getNumberOrders() < idOrder || idOrder < 0) {
                return "There are no such order";
            } else {
                orderService.shiftLeadTime(idOrder, executionStartTime, leadTime);
                return " -the order lead time has been changed.";
            }
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByFilingDate() {
        LOGGER.info("Method getOrdersSortByFilingDate");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByExecutionDate() {
        LOGGER.info("Method getOrdersSortByExecutionDate");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByPlannedStartDate() {
        LOGGER.info("Method getOrdersSortByPlannedStartDate");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByPrice() {
        LOGGER.info("Method getOrdersSortByPrice");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_PRICE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getExecuteOrderFilingDate() {
        LOGGER.info("Method getExecuteOrderFilingDate");
        try {
            return StringOrder
                .getStringFromOrder(orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getExecuteOrderExecutionDate() {
        LOGGER.info("Method getExecuteOrderExecutionDate");
        try {
            return StringOrder
                .getStringFromOrder(orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCompletedOrdersFilingDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCompletedOrdersFilingDate");
        LOGGER.trace("Parameter startPeriod: {}", startPeriod);
        LOGGER.trace("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCompletedOrdersExecutionDate");
        LOGGER.trace("Parameter startPeriod: {}", startPeriod);
        LOGGER.trace("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCompletedOrdersPrice(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCompletedOrdersPrice");
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCanceledOrdersFilingDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCanceledOrdersFilingDate");
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCanceledOrdersExecutionDate");
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCanceledOrdersPrice(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCanceledOrdersPrice");
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getDeletedOrdersFilingDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getDeletedOrdersFilingDate");
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getDeletedOrdersExecutionDate");
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getDeletedOrdersPrice(String startPeriod, String endPeriod) {
        LOGGER.info("Method getDeletedOrdersPrice");
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterOrders(Long idMaster) {
        LOGGER.info("Method getMasterOrders");
        LOGGER.debug("Parameter idOrder: {}", idMaster);
        try {
            return StringOrder
                .getStringFromOrder(orderService.getMasterOrders(idMaster));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrderMasters(Long idOrder) {
        LOGGER.info("Method getOrderMasters");
        LOGGER.debug("Parameter idOrder: {}", idOrder);
        try {
            return StringMaster
                .getStringFromMasters(orderService.getOrderMasters(idOrder));
        } catch (BusinessException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}