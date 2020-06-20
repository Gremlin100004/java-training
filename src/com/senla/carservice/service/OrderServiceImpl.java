package com.senla.carservice.service;

import com.senla.carservice.comparator.OrderCreationComparator;
import com.senla.carservice.comparator.OrderLeadComparator;
import com.senla.carservice.comparator.OrderPriceComparator;
import com.senla.carservice.comparator.OrderStartComparator;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Status;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance;
    private final OrderRepository orderRepository;

    private OrderServiceImpl() {
        this.orderRepository = OrderRepositoryImpl.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> getOrders() {
        return this.orderRepository.getOrders();
    }

    @Override
    public void addOrder(Order order) {
        order.setId(this.orderRepository.getIdGeneratorOrder().getId());
        this.orderRepository.getOrders().add(order);
        for (Master master : order.getMasters()) {
            if (master.getNumberOrder() != null) {
                master.setNumberOrder(master.getNumberOrder() + 1);
            } else {
                master.setNumberOrder(1);
            }
        }
    }

    @Override
    public boolean completeOrder(Order order) {
        if (!order.isDeleteStatus() && !order.getStatus().equals(Status.COMPLETED) &&
                !order.getStatus().equals(Status.CANCELED) && !order.getStatus().equals(Status.PERFORM)) {
            order.setStatus(Status.PERFORM);
            order.setExecutionStartTime(new Date());
            order.getPlace().setBusyStatus(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean cancelOrder(Order order) {
        if (!order.isDeleteStatus() && !order.getStatus().equals(Status.COMPLETED) &&
                !order.getStatus().equals(Status.CANCELED) && !order.getStatus().equals(Status.PERFORM)) {
            order.setStatus(Status.CANCELED);
            order.setLeadTime(new Date());
            for (Master master : order.getMasters()) {
                master.setNumberOrder(master.getNumberOrder() - 1);
            }
            order.getPlace().setBusyStatus(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean closeOrder(Order order) {
        if (!order.isDeleteStatus() && !order.getStatus().equals(Status.COMPLETED) &&
                !order.getStatus().equals(Status.CANCELED) && !order.getStatus().equals(Status.PERFORM)) {
            order.setStatus(Status.COMPLETED);
            order.setLeadTime(new Date());
            for (Master master : order.getMasters()) {
                master.setNumberOrder(master.getNumberOrder() - 1);
            }
            order.getPlace().setBusyStatus(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteOrder(Order order) {
        if (!order.isDeleteStatus() && !order.getStatus().equals(Status.PERFORM)
                && !order.getStatus().equals(Status.WAIT)) {
            order.setDeleteStatus(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        if (!order.isDeleteStatus() && !order.getStatus().equals(Status.COMPLETED) && !order.getStatus().equals(Status.CANCELED)) {
            order.setLeadTime(leadTime);
            order.setExecutionStartTime(executionStartTime);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Order> sortOrderCreationTime(List<Order> orders) {
        List<Order> sortArrayOrder = new ArrayList<>(orders);
        OrderCreationComparator orderCreationComparator = new OrderCreationComparator();
        sortArrayOrder.sort(orderCreationComparator);
        return sortArrayOrder;
    }

    @Override
    public List<Order> sortOrderByLeadTime(List<Order> orders) {
        List<Order> sortArrayOrder = new ArrayList<>(orders);
        OrderLeadComparator orderLeadComparator = new OrderLeadComparator();
        sortArrayOrder.sort(orderLeadComparator);
        return sortArrayOrder;
    }

    @Override
    public List<Order> sortOrderByPrice(List<Order> orders) {
        List<Order> sortArrayOrder = new ArrayList<>(orders);
        OrderPriceComparator orderPriceComparator = new OrderPriceComparator();
        sortArrayOrder.sort(orderPriceComparator);
        return sortArrayOrder;
    }

    @Override
    public List<Order> sortOrderByStartTime(List<Order> orders) {
        List<Order> sortArrayOrder = new ArrayList<>(orders);
        OrderStartComparator orderStartComparator = new OrderStartComparator();
        sortArrayOrder.sort(orderStartComparator);
        return sortArrayOrder;
    }

    @Override
    public List<Order> sortOrderByPeriod(List<Order> orders, Date startPeriod, Date endPeriod) {
        ArrayList<Order> sortArrayOrder = new ArrayList<>();
        if (startPeriod == null || endPeriod == null) {
            return sortArrayOrder;
        }
        for (Order order : orders) {
            if (order.getLeadTime().compareTo(startPeriod) >= 0 && order.getLeadTime().compareTo(endPeriod) <= 0) {
                sortArrayOrder.add(order);
            }
        }
        return sortArrayOrder;
    }

    @Override
    public List<Order> getCurrentRunningOrders() {
        List<Order> arrayOder = new ArrayList<>();
        for (Order order : this.orderRepository.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            if (order.getStatus().equals(Status.PERFORM)) {
                arrayOder.add(order);
            }
        }
        return arrayOder;
    }

    @Override
    public List<Order> getMasterOrders(Master master) {
        List<Order> orders = new ArrayList<>();
        for (Order order : this.orderRepository.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            for (Master masterService : order.getMasters()) {
                if (masterService.equals(master)) {
                    orders.add(order);
                    break;
                }
            }
        }
        return orders;
    }

    @Override
    public List<Master> getOrderMasters(Order order) {
        return order.getMasters();
    }

    @Override
    public List<Order> getCompletedOrders(List<Order> orders) {
        List<Order> sortOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.isDeleteStatus()) {
                continue;
            }
            if (order.getStatus().equals(Status.COMPLETED)) {
                sortOrders.add(order);
            }
        }
        return sortOrders;
    }

    @Override
    public List<Order> getCanceledOrders(List<Order> orders) {
        List<Order> sortOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.isDeleteStatus()) {
                continue;
            }
            if (order.getStatus().equals(Status.CANCELED)) {
                sortOrders.add(order);
            }
        }
        return sortOrders;
    }

    @Override
    public List<Order> getDeletedOrders(List<Order> orders) {
        List<Order> sortOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order.isDeleteStatus()) {
                sortOrders.add(order);
            }
        }
        return sortOrders;
    }
}