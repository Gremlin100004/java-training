package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.enumaration.SortParameter;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DaoException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    public OrderController() {
    }

    public String addOrder(String automaker, String model, String registrationNumber) {
        LOGGER.info("Method addOrder");
        LOGGER.debug("Parameter automaker: {}", automaker);
        LOGGER.debug("Parameter model: {}", model);
        LOGGER.debug("Parameter registrationNumber: {}", registrationNumber);
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
        LOGGER.debug("Parameter stringExecutionStartTime: {}", stringExecutionStartTime);
        LOGGER.debug("Parameter stringLeadTime: {}", stringLeadTime);
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
        LOGGER.debug("Parameter index: {}", index);
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
        LOGGER.debug("Parameter index: {}", index);
        LOGGER.debug("Parameter stringExecuteDate: {}", stringExecuteDate);
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            if (placeService.getNumberFreePlaceByDate(executeDate) < index || index < 0) {
                return "There is no such place!";
            } else {
                orderService.addOrderPlace(placeService.getPlaceByIndex((long) index));
                return "place add to order successfully";
            }
        } catch (BusinessException | DaoException | DateException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String addOrderPrice(BigDecimal price) {
        LOGGER.info("Method addOrderPrice");
        LOGGER.debug("Parameter price: {}", price);
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
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.completeOrder(orderService.getOrderById((long) index));
            }
            return " - the order has been transferred to execution status";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String closeOrder(int index) {
        LOGGER.info("Method closeOrder");
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.closeOrder(orderService.getOrderById((long) index));
                return " -the order has been completed.";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String cancelOrder(int index) {
        LOGGER.info("Method cancelOrder");
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.cancelOrder(orderService.getOrderById((long) index));
            }
            return " -the order has been canceled.";
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String deleteOrder(int index) {
        LOGGER.info("Method deleteOrder");
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.deleteOrder(orderService.getOrderById((long) index));
                return " -the order has been deleted.";
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String shiftLeadTime(int index, String stringStartTime, String stringLeadTime) {
        LOGGER.info("Method shiftLeadTime");
        LOGGER.debug("Parameter index: {}", index);
        LOGGER.debug("Parameter stringStartTime: {}", stringStartTime);
        LOGGER.debug("Parameter stringLeadTime: {}", stringLeadTime);
        try {
            Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.shiftLeadTime(orderService.getOrderById((long) index), executionStartTime, leadTime);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter startPeriod: {}", startPeriod);
        LOGGER.debug("Parameter endPeriod: {}", endPeriod);
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
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (masterService.getNumberMasters() < index || index < 0) {
                return "There are no such master";
            } else {
                return StringOrder
                    .getStringFromOrder(orderService.getMasterOrders(masterService.getMasterByIndex((long) index)));
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getOrderMasters(int index) {
        LOGGER.info("Method getOrderMasters");
        LOGGER.debug("Parameter index: {}", index);
        try {
            if (orderService.getNumberOrders() < index || index < 0) {
                return "There are no such order";
            } else {
                return StringMaster
                    .getStringFromMasters(orderService.getOrderMasters(orderService.getOrderById((long) index)));
            }
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}