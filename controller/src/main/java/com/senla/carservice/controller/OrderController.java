//package com.senla.carservice.controller;
//
//import com.senla.carservice.controller.util.StringMaster;
//import com.senla.carservice.controller.util.StringOrder;
//import com.senla.carservice.dao.exception.DaoException;
//import com.senla.carservice.dto.OrderDto;
//import com.senla.carservice.service.OrderService;
//import com.senla.carservice.service.enumaration.SortParameter;
//import com.senla.carservice.service.exception.BusinessException;
//import com.senla.carservice.util.DateUtil;
//import com.senla.carservice.util.exception.DateException;
//import lombok.NoArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Controller
//@NoArgsConstructor
//@Slf4j
//public class OrderController {
//    @Autowired
//    private OrderService orderService;
//
//    public String addOrder(String automaker, String model, String registrationNumber) {
//        log.info("Method addOrder");
//        log.trace("Parameters automaker: {}, model: {}, registrationNumber: {}", automaker, model, registrationNumber);
//        try {
//            orderService.addOrder(automaker, model, registrationNumber);
//            return "order add successfully!";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String addOrderDeadlines(String stringExecutionStartTime, String stringLeadTime) {
//        log.info("Method addOrderDeadlines");
//        log.trace("Parameter stringExecutionStartTime: {}, stringLeadTime: {}",
//            stringExecutionStartTime, stringLeadTime);
//        try {
//            Date executionStartTime = DateUtil.getDatesFromString(stringExecutionStartTime, true);
//            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
//            orderService.addOrderDeadlines(executionStartTime, leadTime);
//            return "deadline add to order successfully";
//        } catch (BusinessException | DateException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String addOrderMasters(Long idMaster) {
//        log.info("Method addOrderMasters");
//        log.trace("Parameter idMaster: {}", idMaster);
//        try {
//            orderService.addOrderMasters(idMaster);
//            return "masters add successfully";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String addOrderPlace(Long idPlace) {
//        log.info("Method addOrderPlace");
//        log.trace("Parameter index: {}", idPlace);
//        try {
//            orderService.addOrderPlace(idPlace);
//            return "place add to order successfully";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String addOrderPrice(BigDecimal price) {
//        log.info("Method addOrderPrice");
//        log.trace("Parameter price: {}", price);
//        try {
//            orderService.addOrderPrice(price);
//            return "price add to order successfully";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String checkOrders() {
//        log.info("Method checkPlaces");
//        try {
//            if (orderService.getNumberOrders() == 0) {
//                throw new BusinessException("There are no orders");
//            }
//            return "verification was successfully";
//        } catch (BusinessException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public List<String> getOrders() {
//        log.info("Method getOrders");
//        List<String> stringList = new ArrayList<>();
//        try {
//            List<OrderDto> ordersDto = orderService.getOrders();
//            stringList.add(StringOrder.getStringFromOrder(ordersDto));
//            stringList.addAll(StringOrder.getListId(ordersDto));
//            return stringList;
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            stringList.add(e.getMessage());
//            return stringList;
//        }
//    }
//
//    public String completeOrder(Long idOrder) {
//        log.info("Method completeOrder");
//        log.trace("Parameter index: {}", idOrder);
//        try {
//            if (orderService.getNumberOrders() < idOrder || idOrder < 0) {
//                return "There are no such order";
//            } else {
//                orderService.completeOrder(idOrder);
//            }
//            return " - the order has been transferred to execution status";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String closeOrder(Long idOrder) {
//        log.info("Method closeOrder");
//        log.trace("Parameter idOrder: {}", idOrder);
//        try {
//            orderService.closeOrder(idOrder);
//            return " -the order has been completed.";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String cancelOrder(Long idOrder) {
//        log.info("Method cancelOrder");
//        log.trace("Parameter idOrder: {}", idOrder);
//        try {
//            orderService.cancelOrder(idOrder);
//            return " -the order has been canceled.";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String deleteOrder(Long idOrder) {
//        log.info("Method deleteOrder");
//        log.trace("Parameter index: {}", idOrder);
//        try {
//            orderService.deleteOrder(idOrder);
//            return " -the order has been deleted.";
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String shiftLeadTime(Long idOrder, String stringStartTime, String stringLeadTime) {
//        log.info("Method shiftLeadTime");
//        log.trace("Parameters index: {}, stringStartTime: {}, stringLeadTime: {}",
//            idOrder, stringStartTime, stringLeadTime);
//        try {
//            Date executionStartTime = DateUtil.getDatesFromString(stringStartTime, true);
//            Date leadTime = DateUtil.getDatesFromString(stringLeadTime, true);
//            if (orderService.getNumberOrders() < idOrder || idOrder < 0) {
//                return "There are no such order";
//            } else {
//                orderService.shiftLeadTime(idOrder, executionStartTime, leadTime);
//                return " -the order lead time has been changed.";
//            }
//        } catch (BusinessException | DateException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getOrdersSortByFilingDate() {
//        log.info("Method getOrdersSortByFilingDate");
//        try {
//            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_FILING_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getOrdersSortByExecutionDate() {
//        log.info("Method getOrdersSortByExecutionDate");
//        try {
//            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_EXECUTION_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getOrdersSortByPlannedStartDate() {
//        log.info("Method getOrdersSortByPlannedStartDate");
//        try {
//            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.BY_PLANNED_START_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getOrdersSortByPrice() {
//        log.info("Method getOrdersSortByPrice");
//        try {
//            return StringOrder.getStringFromOrder(orderService.getSortOrders(SortParameter.SORT_BY_PRICE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getExecuteOrderFilingDate() {
//        log.info("Method getExecuteOrderFilingDate");
//        try {
//            return StringOrder
//                .getStringFromOrder(orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_FILING_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getExecuteOrderExecutionDate() {
//        log.info("Method getExecuteOrderExecutionDate");
//        try {
//            return StringOrder
//                .getStringFromOrder(orderService.getSortOrders(SortParameter.EXECUTE_ORDER_SORT_BY_EXECUTION_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getCompletedOrdersFilingDate(String startPeriod, String endPeriod) {
//        log.info("Method getCompletedOrdersFilingDate");
//        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_FILING_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getCompletedOrdersExecutionDate(String startPeriod, String endPeriod) {
//        log.info("Method getCompletedOrdersExecutionDate");
//        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_EXECUTION_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getCompletedOrdersPrice(String startPeriod, String endPeriod) {
//        log.info("Method getCompletedOrdersPrice");
//        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.COMPLETED_ORDERS_SORT_BY_PRICE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getCanceledOrdersFilingDate(String startPeriod, String endPeriod) {
//        log.info("Method getCanceledOrdersFilingDate");
//        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_FILING_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getCanceledOrdersExecutionDate(String startPeriod, String endPeriod) {
//        log.info("Method getCanceledOrdersExecutionDate");
//        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_EXECUTION_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getCanceledOrdersPrice(String startPeriod, String endPeriod) {
//        log.info("Method getCanceledOrdersPrice");
//        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.CANCELED_ORDERS_SORT_BY_PRICE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getDeletedOrdersFilingDate(String startPeriod, String endPeriod) {
//        log.info("Method getDeletedOrdersFilingDate");
//        log.trace("Parameters startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_FILING_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getDeletedOrdersExecutionDate(String startPeriod, String endPeriod) {
//        log.info("Method getDeletedOrdersExecutionDate");
//        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_EXECUTION_DATE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getDeletedOrdersPrice(String startPeriod, String endPeriod) {
//        log.info("Method getDeletedOrdersPrice");
//        log.trace("Parameter startPeriod: {}, endPeriod: {}", startPeriod, endPeriod);
//        try {
//            Date startPeriodDate = DateUtil.getDatesFromString(startPeriod, true);
//            Date endPeriodDate = DateUtil.getDatesFromString(endPeriod, true);
//            return StringOrder.getStringFromOrder(orderService.getSortOrdersByPeriod(startPeriodDate, endPeriodDate,
//                                                                                     SortParameter.DELETED_ORDERS_SORT_BY_PRICE));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getMasterOrders(Long idMaster) {
//        log.info("Method getMasterOrders");
//        log.trace("Parameter idOrder: {}", idMaster);
//        try {
//            return StringOrder.getStringFromOrder(orderService.getMasterOrders(idMaster));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//
//    public String getOrderMasters(Long idOrder) {
//        log.info("Method getOrderMasters");
//        log.trace("Parameter idOrder: {}", idOrder);
//        try {
//            return StringMaster.getStringFromMasters(orderService.getOrderMasters(idOrder));
//        } catch (BusinessException | DaoException e) {
//            log.warn(e.getMessage());
//            return e.getMessage();
//        }
//    }
//}