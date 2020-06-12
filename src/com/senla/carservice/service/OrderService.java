package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Status;
import com.senla.carservice.repository.CarService;
import com.senla.carservice.repository.Order;

import java.util.Arrays;
import java.util.Date;

public class OrderService implements IOrderService {
    private CarService carService;

    public OrderService(CarService carService) {
        this.carService = carService;
    }

    @Override
    public Order[] getOrders() {
        return Arrays.copyOf(this.carService.getOrders(), this.carService.getOrders().length);
    }

    @Override
    public void addOrder(Order order) {
        int index = this.carService.getOrders().length;
        order.setId(this.carService.getGeneratorIdOrder().getId());
        // не очень оптимально - увеличивать массив на 1 каждый раз, лучше применять коэффициент - так сделано в ArrayList
        this.carService.setOrders(Arrays.copyOf(this.carService.getOrders(), index + 1));
        this.carService.getOrders()[index] = order;
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
        Order[] sortArrayOrder = this.carService.getOrders().clone();
        OrderCreationComparator orderCreationComparator = new OrderCreationComparator();
        Arrays.sort(sortArrayOrder, orderCreationComparator);
        return sortArrayOrder;
    }

    @Override
    public Order[] sortOrderByLeadTime(Order[] order) {
        Order[] sortArrayOrder = this.carService.getOrders().clone();
        OrderLeadComparator orderLeadComparator = new OrderLeadComparator();
        Arrays.sort(sortArrayOrder, orderLeadComparator);
        return sortArrayOrder;
    }

    @Override
    public Order[] sortOrderByPrice(Order[] order) {
        Order[] sortArrayOrder = this.carService.getOrders().clone();
        OrderPriceComparator orderPriceComparator = new OrderPriceComparator();
        Arrays.sort(sortArrayOrder, orderPriceComparator);
        return sortArrayOrder;
    }

    @Override
    public Order[] sortOrderByStartTime(Order[] order) {
        Order[] sortArrayOrder = this.carService.getOrders().clone();
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
        for (Order order : this.carService.getOrders()) {
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
        for (Order order : this.carService.getOrders()) {
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
                // этот метод будет работать ужасно межденно из-за этой строчки
                sortOrders = Arrays.copyOf(sortOrders, lengthArray + 1);
                sortOrders[lengthArray] = order;
            }
        }
        return sortOrders;
    }
}