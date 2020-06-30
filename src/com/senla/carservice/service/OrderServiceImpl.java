package com.senla.carservice.service;

import com.senla.carservice.csvutil.CsvOrder;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Status;
import com.senla.carservice.exception.EqualObjectsException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.exception.OrderStatusException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import com.senla.carservice.util.DateUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance;
    private final OrderRepository orderRepository;
    private final PlaceRepository placeRepository;
    private final MasterRepository masterRepository;

    private OrderServiceImpl() {
        orderRepository = OrderRepositoryImpl.getInstance();
        placeRepository = PlaceRepositoryImpl.getInstance();
        masterRepository = MasterRepositoryImpl.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> getOrders() {
        checkOrders();
        return orderRepository.getOrders();
    }

    @Override
    public void addOrder(String automaker, String model, String registrationNumber) {
        checkMasters();
        checkPlaces();
        orderRepository.addOrder(new Order(orderRepository.getIdGeneratorOrder().getId(),
                automaker, model, registrationNumber));
    }

    @Override
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) {
        DateUtil.checkDateTime(executionStartTime, leadTime);
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        List<Order> orders = new ArrayList<>(orderRepository.getOrders());
        orders.remove(currentOrder);
        if (!orders.isEmpty()) orders = sortOrderByPeriod(orders, executionStartTime, leadTime);
        int numberFreeMasters = masterRepository.getMasters().size() -
                orders.stream().mapToInt(order -> order.getMasters().size()).sum();
        int numberFreePlace = orderRepository.getOrders().size() - orders.size();
        if (numberFreeMasters == 0) throw new
                NumberObjectZeroException("The number of masters is zero");
        if (numberFreePlace == 0) throw new
                NumberObjectZeroException("The number of places is zero");
        currentOrder.setExecutionStartTime(executionStartTime);
        currentOrder.setLeadTime(leadTime);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void addOrderMasters(Master master) {
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        List<Master> masters = currentOrder.getMasters();
        for (Master orderMaster : masters) {
            if (orderMaster.equals(master)) throw new EqualObjectsException("This master already exists");
        }
        masters.add(master);
        currentOrder.setMasters(masters);
        orderRepository.updateOrder(currentOrder);
        master.setNumberOrder(master.getNumberOrder() != null ? master.getNumberOrder() + 1 : 1);
        masterRepository.updateMaster(master);
    }

    @Override
    public void addOrderPlace(Place place) {
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        currentOrder.setPlace(place);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void addOrderPrice(BigDecimal price) {
        checkOrders();
        Order currentOrder = orderRepository.getLastOrder();
        currentOrder.setPrice(price);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void completeOrder(Order order) {
        checkStatusOrder(order);
        order.setStatus(Status.PERFORM);
        order.setExecutionStartTime(new Date());
        order.getPlace().setBusyStatus(true);
        orderRepository.updateOrder(order);
    }

    @Override
    public void cancelOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.CANCELED);
        for (Master master : order.getMasters()) {
            master.setNumberOrder(master.getNumberOrder() - 1);
            masterRepository.updateMaster(master);
        }
        order.getPlace().setBusyStatus(false);
        placeRepository.updatePlace(order.getPlace());
        orderRepository.updateOrder(order);
    }

    @Override
    public void closeOrder(Order order) {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        order.setStatus(Status.COMPLETED);
        for (Master master : order.getMasters()) {
            master.setNumberOrder(master.getNumberOrder() - 1);
        }
        order.getPlace().setBusyStatus(false);
        placeRepository.updatePlace(order.getPlace());
        orderRepository.updateOrder(order);
    }

    @Override
    public void deleteOrder(Order order) {
        checkStatusOrderDelete(order);
        order.setDeleteStatus(true);
        orderRepository.updateOrder(order);
    }

    @Override
    public void shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        DateUtil.checkDateTime(executionStartTime, leadTime);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        orderRepository.updateOrder(order);
    }

    @Override
    public List<Order> sortOrderByCreationTime(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::getCreationTime,
                Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public List<Order> sortOrderByLeadTime(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::getLeadTime,
                Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public List<Order> sortOrderByPrice(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::getPrice,
                Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public List<Order> sortOrderByStartTime(List<Order> orders) {
        return orders.stream().sorted(Comparator.comparing(Order::getExecutionStartTime,
                Comparator.nullsLast(Comparator.naturalOrder()))).collect(Collectors.toList());
    }

    @Override
    public List<Order> getOrderByPeriod(Date startPeriod, Date endPeriod) {
        checkOrders();
        DateUtil.checkPeriodTime(startPeriod, endPeriod);
        if (sortOrderByPeriod(orderRepository.getOrders(), startPeriod, endPeriod).isEmpty())
            throw new NumberObjectZeroException("There are no orders in this period");
        return sortOrderByPeriod(orderRepository.getOrders(), startPeriod, endPeriod);
    }

    @Override
    public List<Order> getCurrentRunningOrders() {
        checkOrders();
        if (orderRepository.getRunningOrders().isEmpty())
            throw new NumberObjectZeroException("There are no orders with status PERFORM");
        return orderRepository.getRunningOrders();
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        checkOrders();
        if (orderRepository.getMasterOrders(master).isEmpty())
            throw new NumberObjectZeroException("Master doesn't have any orders");
        return orderRepository.getMasterOrders(master);
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        if (orderRepository.getOrderMasters(order).isEmpty())
            throw new NumberObjectZeroException("There are no masters in order");
        return orderRepository.getOrderMasters(order);
    }

    @Override
    public List<Order> getCompletedOrders(Date startPeriod, Date endPeriod) {
        if (sortOrderByPeriod(orderRepository.getCompletedOrders(), startPeriod, endPeriod).isEmpty())
            throw new NumberObjectZeroException("There are no orders with status COMPLETED");
        return (sortOrderByPeriod(orderRepository.getCompletedOrders(), startPeriod, endPeriod));
    }

    @Override
    public List<Order> getCanceledOrders(Date startPeriod, Date endPeriod) {
        if (sortOrderByPeriod(orderRepository.getCanceledOrders(), startPeriod, endPeriod).isEmpty())
            throw new NumberObjectZeroException("There are no orders with status CANCELED");
        return sortOrderByPeriod(orderRepository.getCanceledOrders(), startPeriod, endPeriod);
    }

    @Override
    public List<Order> getDeletedOrders(Date startPeriod, Date endPeriod) {
        if (sortOrderByPeriod(orderRepository.getDeletedOrders(), startPeriod, endPeriod).isEmpty())
            throw new NumberObjectZeroException("There are no deleted orders");
        return sortOrderByPeriod(orderRepository.getDeletedOrders(), startPeriod, endPeriod);
    }

    private void checkOrders() {
        if (orderRepository.getOrders().isEmpty()) throw new NumberObjectZeroException("There are no orders");
    }

    private void checkMasters() {
        if (masterRepository.getMasters().isEmpty()) throw new NumberObjectZeroException("There are no masters");
    }

    private void checkPlaces() {
        if (placeRepository.getPlaces().isEmpty()) throw new NumberObjectZeroException("There are no places");
    }

    private List<Order> sortOrderByPeriod(List<Order> orders, Date startPeriod, Date endPeriod) {
        if (orders.isEmpty()) throw new NumberObjectZeroException("There are no orders");
        List<Order> sortArrayOrder = new ArrayList<>();
        orders.forEach(order -> {
            if (order.getLeadTime().compareTo(startPeriod) >= 0 && order.getLeadTime().compareTo(endPeriod) <= 0) {
                sortArrayOrder.add(order);
            }
        });
        return sortArrayOrder;
    }

    private void checkStatusOrder(Order order) {
        if (order.isDeleteStatus()) throw new
                OrderStatusException("The order has been deleted");
        if (order.getStatus().equals(Status.COMPLETED)) throw new
                OrderStatusException("The order has been completed");
        if (order.getStatus().equals(Status.PERFORM)) throw new
                OrderStatusException("Order is being executed");
        if (order.getStatus().equals(Status.CANCELED)) throw new
                OrderStatusException("The order has been canceled");
    }

    private void checkStatusOrderDelete(Order order) {
        if (order.isDeleteStatus()) throw new
                OrderStatusException("The order has been deleted");
        if (order.getStatus().equals(Status.PERFORM)) throw new
                OrderStatusException("Order is being executed");
        if (order.getStatus().equals(Status.WAIT)) throw new
                OrderStatusException("The order is being waited");
    }

    private void checkStatusOrderShiftTime(Order order) {
        if (order.isDeleteStatus()) throw new
                OrderStatusException("The order has been deleted");
        if (order.getStatus().equals(Status.COMPLETED)) throw new
                OrderStatusException("The order has been completed");
        if (order.getStatus().equals(Status.CANCELED)) throw new
                OrderStatusException("The order has been canceled");
    }

    @Override
    public void exportOrder() {
        checkOrders();
        checkMasters();
        checkPlaces();
        CsvOrder.exportOrder(orderRepository.getOrders());
    }

    @Override
    public String importOrder() {
        return CsvOrder.importOrder();
    }
}