package com.senla.carservice.service;

import com.senla.carservice.comporator.OrderCreationComparator;
import com.senla.carservice.comporator.OrderLeadComparator;
import com.senla.carservice.comporator.OrderPriceComparator;
import com.senla.carservice.comporator.OrderStartComparator;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Status;
import com.senla.carservice.repository.CarOfficeRepository;
import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.CarOfficeRepositoryImpl;

import java.util.Arrays;
import java.util.Date;

public final class OrderServiceImpl implements OrderService {
    private static OrderServiceImpl instance;
    private final CarOfficeRepository carOfficeRepository;

    public OrderServiceImpl() {
        this.carOfficeRepository = CarOfficeRepositoryImpl.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public Order[] getOrders() {
        return Arrays.copyOf(this.carOfficeRepository.getOrders(), this.carOfficeRepository.getOrders().length);
    }

    @Override
    public void addOrder(Order order) {
        int index = this.carOfficeRepository.getOrders().length;
        order.setId(this.carOfficeRepository.getIdGeneratorOrder().getId());
        this.carOfficeRepository.setOrders(Arrays.copyOf(this.carOfficeRepository.getOrders(), index + 1));
        this.carOfficeRepository.getOrders()[index] = order;
        for (Master master : order.getMasters()) {
            if (master.getNumberOrder()!= null){
                master.setNumberOrder(master.getNumberOrder() + 1);
            } else {
                master.setNumberOrder(1);
            }
        }
    }

    @Override
    public boolean completeOrder(Order order) {
        if (!order.isDeleteStatus()) {
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
        if (!order.isDeleteStatus()) {
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
        if (!order.isDeleteStatus()) {
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
        if (order.getStatus().equals(Status.CANCELED) || order.getStatus().equals(Status.COMPLETED)) {
            order.setDeleteStatus(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean shiftLeadTime(Order order, Date executionStartTime, Date leadTime) {
        if (!order.isDeleteStatus()) {
            order.setLeadTime(leadTime);
            order.setExecutionStartTime(executionStartTime);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Order[] sortOrderCreationTime(Order[] order) {
        Order[] sortArrayOrder = this.carOfficeRepository.getOrders().clone();
        OrderCreationComparator orderCreationComparator = new OrderCreationComparator();
        Arrays.sort(sortArrayOrder, orderCreationComparator);
        return sortArrayOrder;
    }

    @Override
    public Order[] sortOrderByLeadTime(Order[] order) {
        Order[] sortArrayOrder = this.carOfficeRepository.getOrders().clone();
        OrderLeadComparator orderLeadComparator = new OrderLeadComparator();
        Arrays.sort(sortArrayOrder, orderLeadComparator);
        return sortArrayOrder;
    }

    @Override
    public Order[] sortOrderByPrice(Order[] order) {
        Order[] sortArrayOrder = this.carOfficeRepository.getOrders().clone();
        OrderPriceComparator orderPriceComparator = new OrderPriceComparator();
        Arrays.sort(sortArrayOrder, orderPriceComparator);
        return sortArrayOrder;
    }

    @Override
    public Order[] sortOrderByStartTime(Order[] order) {
        Order[] sortArrayOrder = this.carOfficeRepository.getOrders().clone();
        OrderStartComparator orderStartComparator = new OrderStartComparator();
        Arrays.sort(sortArrayOrder, orderStartComparator);
        return sortArrayOrder;
    }

    @Override
    public Order[] sortOrderByPeriod(Order[] orders, Date startPeriod, Date endPeriod) {
        int lengthArray;
        Order[] sortArrayOrder = new Order[0];
        if (startPeriod == null || endPeriod == null){
            return sortArrayOrder;
        }
        for (Order order : orders) {
            lengthArray = sortArrayOrder.length;
            if (order.getLeadTime().compareTo(startPeriod) >= 0 & order.getLeadTime().compareTo(endPeriod) <= 0) {
                sortArrayOrder = Arrays.copyOf(sortArrayOrder, lengthArray + 1);
                sortArrayOrder[lengthArray] = order;
            }
        }
        return sortArrayOrder;
    }

    @Override
    public Order[] getCurrentRunningOrders() {
        int indexOrder;
        Order[] arrayOder = new Order[0];
        for (Order order : this.carOfficeRepository.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            if (order.getStatus().equals(Status.PERFORM)) {
                indexOrder = arrayOder.length;
                arrayOder = Arrays.copyOf(arrayOder, indexOrder + 1);
                arrayOder[indexOrder] = order;
            }
        }
        return arrayOder;
    }

    @Override
    public Order[] getMasterOrders(Master master) {
        int lengthArray;
        Order[] orders = new Order[0];
        for (Order order : this.carOfficeRepository.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            for (Master masterService : order.getMasters()) {
                if (masterService.equals(master)) {
                    lengthArray = orders.length;
                    orders = Arrays.copyOf(orders, lengthArray + 1);
                    orders[lengthArray] = order;
                    break;
                }
            }
        }
        return orders;
    }

    @Override
    public Master[] getOrderMasters(Order order) {
        return order.getMasters();
    }

    @Override
    public Order[] getCompletedOrders(Order[] orders) {
        int lengthArray;
        Order[] sortOrders = new Order[0];
        for (Order order : orders) {
            if (order.isDeleteStatus()) {
                continue;
            }
            lengthArray = sortOrders.length;
            if (order.getStatus().equals(Status.COMPLETED)) {
                sortOrders = Arrays.copyOf(sortOrders, lengthArray + 1);
                sortOrders[lengthArray] = order;
            }
        }
        return sortOrders;
    }

    @Override
    public Order[] getCanceledOrders(Order[] orders) {
        int lengthArray;
        Order[] sortOrders = new Order[0];
        for (Order order : orders) {
            if (order.isDeleteStatus()) {
                continue;
            }
            lengthArray = sortOrders.length;
            if (order.getStatus().equals(Status.CANCELED)) {
                sortOrders = Arrays.copyOf(sortOrders, lengthArray + 1);
                sortOrders[lengthArray] = order;
            }
        }
        return sortOrders;
    }

    @Override
    public Order[] getDeletedOrders(Order[] orders) {
        int lengthArray;
        Order[] sortOrders = new Order[0];
        for (Order order : orders) {
            lengthArray = sortOrders.length;
            if (order.isDeleteStatus()) {
                sortOrders = Arrays.copyOf(sortOrders, lengthArray + 1);
                sortOrders[lengthArray] = order;
            }
        }
        return sortOrders;
    }
}