package com.senla.carservice.controller;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import com.senla.carservice.stringutil.StringMaster;
import com.senla.carservice.stringutil.StringOrder;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderController {
    private static OrderController instance;
    private final OrderService orderService;
    private final MasterService masterService;
    private final PlaceService placeService;

    private OrderController() {
        orderService = OrderServiceImpl.getInstance();
        masterService = MasterServiceImpl.getInstance();
        placeService = PlaceServiceImpl.getInstance();
    }

    public static OrderController getInstance() {
        if (instance == null) {
            instance = new OrderController();
        }
        return instance;
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
        Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime, true);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
        try {
            orderService.addOrderDeadlines(executionStartTime, leadTime);
            return "deadline add to order successfully";
        } catch (DateException | BusinessException e) {
            return e.getMessage();
        }
    }

    public String addOrderMasters(int index) {
        try {
            if (masterService.getMasters().size() < index || index < 0) {
                return "There is no such master";
            } else {
                orderService.addOrderMasters(masterService.getMasters().get(index));
                return "masters add successfully";
            }
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String addOrderPlace(int index, String stringExecuteDate, String stringLeadDate) {
        Date executeDate = DateUtil.getDatesFromString(stringExecuteDate, true);
        Date leadDate = DateUtil.getDatesFromString(stringLeadDate, true);
        try {
            List<Order> orders = orderService.getOrderByPeriod(executeDate, leadDate);
            if (placeService.getFreePlaceByDate(executeDate, leadDate, orders).size() < index || index < 0) {
                return "There is no such place!";
            } else {
                orderService.addOrderPlace(placeService.getFreePlaceByDate(executeDate, leadDate, orders).get(index));
                return "place add to order successfully";
            }
        } catch (BusinessException | DateException e) {
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
        Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
        Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
        try {
            if (orderService.getOrders().size() < index || index < 0) {
                return "There are no such order";
            } else {
                orderService.shiftLeadTime(orderService.getOrders().get(index), executionStartTime, leadTime);
                return " -the order lead time has been changed.";
            }
        } catch (DateException | BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByFilingDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime(orderService.getOrders()));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByExecutionDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByLeadTime(orderService.getOrders()));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByPlannedStartDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime(orderService.getOrders()));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getOrdersSortByPrice() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice(orderService.getOrders()));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getExecuteOrderFilingDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                (orderService.getCurrentRunningOrders()));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getExecuteOrderExecutionDate() {
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByLeadTime
                (orderService.getCurrentRunningOrders()));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersFilingDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                (orderService.getCompletedOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime
                (orderService.getCompletedOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCompletedOrdersPrice(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice
                (orderService.getCompletedOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersFilingDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                (orderService.getCanceledOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime
                (orderService.getCanceledOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getCanceledOrdersPrice(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice
                    // при переносе оставляй скобку на предыдущей строке
                (orderService.getCanceledOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersFilingDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByCreationTime
                (orderService.getDeletedOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByStartTime
                (orderService.getDeletedOrders(startPeriodDate, endPeriodDate)));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getDeletedOrdersPrice(String startPeriod, String endPeriod) {
        Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
        Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
        try {
            return StringOrder.getStringFromOrder(orderService.sortOrderByPrice
                (orderService.getDeletedOrders(startPeriodDate, endPeriodDate)));
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

    public String exportOrders() {
        try {
            orderService.exportOrder();
            return "Orders have been export successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String importOrders() {
        try {
            orderService.importOrder();
            return "orders imported successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String serializeOrder() {
        try {
            orderService.serializeOrder();
            return "Orders have been serialize successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String deserializeOrder() {
        try {
            orderService.deserializeOrder();
            return "Orders have been deserialize successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}