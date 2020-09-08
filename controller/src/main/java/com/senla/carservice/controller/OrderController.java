package com.senla.carservice.controller;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.controller.util.StringMaster;
import com.senla.carservice.controller.util.StringOrder;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.exception.DateException;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.domain.enumaration.SortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.exception.DaoException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Date;

@Singleton
public class OrderController {

    @Dependency
    private OrderService orderService;
    @Dependency
    private MasterService masterService;
    @Dependency
    private PlaceService placeService;
    private static final Logger LOGGER = LogManager.getLogger(OrderController.class);

    public OrderController() {
    }

    public String addOrder(String automaker, String model, String registrationNumber) {
        LOGGER.info("Method addOrder");
        LOGGER.trace("Parameter automaker: " + automaker);
        LOGGER.trace("Parameter model: " + model);
        LOGGER.trace("Parameter registrationNumber: " + registrationNumber);
        try {
            orderService.addOrder(automaker, model, registrationNumber);
            return "order add successfully!";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderDeadlines(String stringExecutionStartTime, String stringLeadTime) {
        LOGGER.info("Method addOrderDeadlines");
        LOGGER.trace("Parameter stringExecutionStartTime: " + stringExecutionStartTime);
        LOGGER.trace("Parameter stringLeadTime: " + stringLeadTime);
        try {
            Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime, true);
            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
            orderService.addOrderDeadlines(executionStartTime, leadTime);
            return "deadline add to order successfully";
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderMasters(int index) {
        LOGGER.info("Method addOrderMasters");
        LOGGER.trace("Parameter index: " + index);
        try {
            if (masterService.getNumberMasters() < index || index < 0) {
                return "There is no such master";
            } else {
                orderService.addOrderMasters(index);
                return "masters add successfully";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderPlace(int index, String stringExecuteDate) {
        LOGGER.info("Method addOrderPlace");
        LOGGER.trace("Parameter index: " + index);
        LOGGER.trace("Parameter stringExecuteDate: " + stringExecuteDate);
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            if (placeService.getNumberFreePlaceByDate(executeDate) < index || index < 0) {
                return "There is no such place!";
            } else {
                orderService.addOrderPlace(placeService.getPlaces().get(index));
                return "place add to order successfully";
            }
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderPrice(BigDecimal price) {
        LOGGER.info("Method addOrderPrice");
        LOGGER.trace("Parameter price: " + price);
        try {
            orderService.addOrderPrice(price);
            return "price add to order successfully";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrders() {
        LOGGER.info("Method getOrders");
        try {
            return StringOrder.getStringFromOrder(orderService.getOrders());
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String completeOrder(int index) {
        LOGGER.info("Method completeOrder");
        LOGGER.trace("Parameter index: " + index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.completeOrder(orderService.getOrders().get(index));
            }
            return " - the order has been transferred to execution status";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String closeOrder(int index) {
        LOGGER.info("Method closeOrder");
        LOGGER.trace("Parameter index: " + index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.closeOrder(orderService.getOrders().get(index));
                return " -the order has been completed.";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String cancelOrder(int index) {
        LOGGER.info("Method cancelOrder");
        LOGGER.trace("Parameter index: " + index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.cancelOrder(orderService.getOrders().get(index));
            }
            return " -the order has been canceled.";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteOrder(int index) {
        LOGGER.info("Method deleteOrder");
        LOGGER.trace("Parameter index: " + index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.deleteOrder(orderService.getOrders().get(index));
                return " -the order has been deleted.";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String shiftLeadTime(int index, String stringStartTime, String stringLeadTime) {
        LOGGER.info("Method shiftLeadTime");
        LOGGER.trace("Parameter index: " + index);
        LOGGER.trace("Parameter stringStartTime: " + stringStartTime);
        LOGGER.trace("Parameter stringLeadTime: " + stringLeadTime);
        try {
            Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.shiftLeadTime(orderService.getOrders().get(index), executionStartTime, leadTime);
                return " -the order lead time has been changed.";
            }
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByFilingDate() {
        LOGGER.info("Method getOrdersSortByFilingDate");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE));
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByExecutionDate() {
        LOGGER.info("Method getOrdersSortByExecutionDate");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE));
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByPlannedStartDate() {
        LOGGER.info("Method getOrdersSortByPlannedStartDate");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE));
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrdersSortByPrice() {
        LOGGER.info("Method getOrdersSortByPrice");
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_PRICE));
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getExecuteOrderFilingDate() {
        LOGGER.info("Method getExecuteOrderFilingDate");
        try {
            return StringOrder
                .getStringFromOrder(orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE));
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getExecuteOrderExecutionDate() {
        LOGGER.info("Method getExecuteOrderExecutionDate");
        try {
            return StringOrder
                .getStringFromOrder(orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCompletedOrdersFilingDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCompletedOrdersFilingDate");
        LOGGER.trace("Parameter startPeriod: " + startPeriod);
        LOGGER.trace("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCompletedOrdersExecutionDate");
        LOGGER.trace("Parameter startPeriod: " + startPeriod);
        LOGGER.trace("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCompletedOrdersPrice(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCompletedOrdersPrice");
        LOGGER.debug("Parameter startPeriod: " + startPeriod);
        LOGGER.debug("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCanceledOrdersFilingDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCanceledOrdersFilingDate");
        LOGGER.debug("Parameter startPeriod: " + startPeriod);
        LOGGER.debug("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCanceledOrdersExecutionDate");
        LOGGER.debug("Parameter startPeriod: " + startPeriod);
        LOGGER.debug("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getCanceledOrdersPrice(String startPeriod, String endPeriod) {
        LOGGER.info("Method getCanceledOrdersPrice");
        LOGGER.debug("Parameter startPeriod: " + startPeriod);
        LOGGER.debug("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getDeletedOrdersFilingDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getDeletedOrdersFilingDate");
        LOGGER.debug("Parameter startPeriod: " + startPeriod);
        LOGGER.debug("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        LOGGER.info("Method getDeletedOrdersExecutionDate");
        LOGGER.debug("Parameter startPeriod: " + startPeriod);
        LOGGER.debug("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getDeletedOrdersPrice(String startPeriod, String endPeriod) {
        LOGGER.info("Method getDeletedOrdersPrice");
        LOGGER.debug("Parameter startPeriod: " + startPeriod);
        LOGGER.debug("Parameter endPeriod: " + endPeriod);
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getMasterOrders(int index) {
        LOGGER.info("Method getMasterOrders");
        LOGGER.debug("Parameter index: " + index);
        try {
            if (masterService.getNumberMasters() < index || index < 0) {
                return "There are no such master";
            } else {
                return StringOrder
                    .getStringFromOrder(orderService.getMasterOrders(masterService.getMasters().get(index)));
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrderMasters(int index) {
        LOGGER.info("Method getOrderMasters");
        LOGGER.debug("Parameter index: " + index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                return StringMaster
                    .getStringFromMasters(orderService.getOrderMasters(orderService.getOrders().get(index)));
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}