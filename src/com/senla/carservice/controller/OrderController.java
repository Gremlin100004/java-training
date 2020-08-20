package com.senla.carservice.controller;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.enumaration.SortParameter;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.stringutil.StringMaster;
import com.senla.carservice.util.stringutil.StringOrder;

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

    public OrderController() {
    }

    public String addOrder(String automaker, String model, String registrationNumber) {
        try {
            orderService.addOrder(automaker, model, registrationNumber);
            return "order add successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String addOrderDeadlines(String stringExecutionStartTime, String stringLeadTime) {
        try {
            Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime, true);
            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
            orderService.addOrderDeadlines(executionStartTime, leadTime);
            return "deadline add to order successfully";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String addOrderMasters(int index) {
        try {
            if (masterService.getMasters().size() < index || index < 0) {
                return "There is no such master";
            } else {
                orderService.addOrderMasters(index);
                return "masters add successfully";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String addOrderPlace(int index, String stringExecuteDate) {
        try {
            Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
            if (placeService.getFreePlaceByDate(executeDate).size() < index || index < 0) {
                return "There is no such place!";
            } else {
                orderService.addOrderPlace(placeService.getFreePlaceByDate(executeDate).get(index));
                return "place add to order successfully";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String addOrderPrice(BigDecimal price) {
        try {
            orderService.addOrderPrice(price);
            return "price add to order successfully";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrders() {
        try {
            return StringOrder.getStringFromOrder(orderService.getOrders());
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String completeOrder(int index) {
        try {
            if (orderService.getOrders().size() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.completeOrder(orderService.getOrders().get(index));
            }
            return " - the order has been transferred to execution status";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String closeOrder(int index) {
        try {
            if (orderService.getOrders().size() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.closeOrder(orderService.getOrders().get(index));
                return " -the order has been completed.";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String cancelOrder(int index) {
        try {
            if (orderService.getOrders().size() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.cancelOrder(orderService.getOrders().get(index));
            }
            return " -the order has been canceled.";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String deleteOrder(int index) {
        try {
            if (orderService.getOrders().size() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.deleteOrder(orderService.getOrders().get(index));
                return " -the order has been deleted.";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String shiftLeadTime(int index, String stringStartTime, String stringLeadTime) {
        try {
            Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
            if (orderService.getOrders().size() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.shiftLeadTime(orderService.getOrders().get(index), executionStartTime, leadTime);
                return " -the order lead time has been changed.";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByFilingDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByExecutionDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByPlannedStartDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByPrice() {
        try {
            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_PRICE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getExecuteOrderFilingDate() {
        try {
            return StringOrder.getStringFromOrder(
                orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getExecuteOrderExecutionDate() {
        try {
            return StringOrder.getStringFromOrder(
                orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersFilingDate(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE ));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersPrice(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersFilingDate(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersPrice(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.CANCELED_ORDERS_SORT_BY_PRICE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersFilingDate(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersPrice(String startPeriod, String endPeriod) {
        try {
            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(
                    startPeriodDate, endPeriodDate, SortParameter.DELETED_ORDERS_SORT_BY_PRICE
            ));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getMasterOrders(int index) {
        try {
            if (masterService.getMasters().size() < index || index < 0) {
                return "There are no such master";
            } else {
                return StringOrder.getStringFromOrder(
                    orderService.getMasterOrders(masterService.getMasters().get(index)));
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrderMasters(int index) {
        try {
            if (orderService.getOrders().size() < index || index < 0) {
                return "There are no such order";
            } else {
                return StringMaster
                    .getStringFromMasters(orderService.getOrderMasters(orderService.getOrders().get(index)));
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}