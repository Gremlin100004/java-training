package com.senla.carservice.services;

import com.senla.carservice.domain.Car;
import com.senla.carservice.domain.ICarService;
import com.senla.carservice.domain.IGarage;
import com.senla.carservice.domain.IMaster;
import com.senla.carservice.domain.IOrder;
import com.senla.carservice.domain.IPlace;
import com.senla.carservice.domain.Order;
import com.senla.carservice.util.Sort;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class OrderService implements IOrderService {
    private ICarService carService;

    public OrderService(ICarService carService) {
        this.carService = carService;
    }

    @Override
    public IOrder[] getOrders() {
        return Arrays.copyOf(this.carService.getOrders(), this.carService.getOrders().length);
    }

    @Override
    public void addOrder(Calendar executionStartTime, Calendar leadTime, IMaster[] masters, IGarage garage,
                         IPlace place, String automaker, String model, String registrationNumber, BigDecimal price) {
        int index = this.carService.getOrders().length;
        this.carService.setOrders(Arrays.copyOf(this.carService.getOrders(), index + 1));
        this.carService.getOrders()[index] = new Order(executionStartTime, leadTime, masters, garage, place,
                new Car(automaker, model, registrationNumber), price);
        for (IMaster master : masters) {
            master.setNumberOrder(master.getNumberOrder() + 1);
        }
    }

    @Override
    public boolean completeOrder(IOrder order) {
        if (!order.isDeleteStatus()) {
            order.setStatus("perform");
            order.setExecutionStartTime(Calendar.getInstance());
            order.getPlace().setBusyStatus(true);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean cancelOrder(IOrder order) {
        if (!order.isDeleteStatus()) {
            order.setStatus("canceled");
            order.setLeadTime(Calendar.getInstance());
            for (IMaster master : order.getMasters()) {
                master.setNumberOrder(master.getNumberOrder() - 1);
            }
            order.getPlace().setBusyStatus(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean closeOrder(IOrder order) {
        if (!order.isDeleteStatus()) {
            order.setStatus("completed");
            order.setLeadTime(Calendar.getInstance());
            for (IMaster master : order.getMasters()) {
                master.setNumberOrder(master.getNumberOrder() - 1);
            }
            order.getPlace().setBusyStatus(false);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteOrder(IOrder order) {
        if (order.getStatus().equals("canceled") || order.getStatus().equals("completed")) {
            order.setDeleteStatus(true);
            for (IMaster master : order.getMasters()) {
                master.setNumberOrder(master.getNumberOrder() - 1);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean shiftLeadTime(IOrder order, Calendar executionStartTime, Calendar leadTime) {
        if (!order.isDeleteStatus()) {
            order.setLeadTime(leadTime);
            order.setExecutionStartTime(executionStartTime);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public IOrder[] sortOrderCreationTime(IOrder[] order) {
        Calendar[] arrayCreationTime = new GregorianCalendar[order.length];
        IOrder[] sortArrayOrder;
        for (int i = 0; i < order.length; i++) {
            arrayCreationTime[i] = order[i].getCreationTime();
        }
        Sort arrayMaintenance = new Sort();
        sortArrayOrder = arrayMaintenance.bubbleSort(arrayCreationTime, order);
        return sortArrayOrder;
    }

    @Override
    public IOrder[] sortOrderByLeadTime(IOrder[] order) {
        Calendar[] arrayLeadTime = new Calendar[order.length];
        IOrder[] sortArrayOrder;
        for (int i = 0; i < order.length; i++) {
            arrayLeadTime[i] = order[i].getLeadTime();
        }
        Sort arrayMaintenance = new Sort();
        sortArrayOrder = arrayMaintenance.bubbleSort(arrayLeadTime, order);
        return sortArrayOrder;
    }

    @Override
    public IOrder[] sortOrderByPrice(IOrder[] order) {
        BigDecimal[] arrayPrice = new BigDecimal[order.length];
        IOrder[] sortArrayOrder;
        for (int i = 0; i < order.length; i++) {
            arrayPrice[i] = order[i].getPrice();
        }
        Sort arrayMaintenance = new Sort();
        sortArrayOrder = arrayMaintenance.bubbleSort(arrayPrice, order);
        return sortArrayOrder;
    }

    @Override
    public IOrder[] sortOrderByStartTime(IOrder[] order) {
        Calendar[] arrayExecutionStartTime = new GregorianCalendar[order.length];
        IOrder[] sortArrayOrder;
        for (int i = 0; i < order.length; i++) {
            arrayExecutionStartTime[i] = order[i].getExecutionStartTime();
        }
        Sort arrayMaintenance = new Sort();
        sortArrayOrder = arrayMaintenance.bubbleSort(arrayExecutionStartTime, order);
        return sortArrayOrder;
    }

    @Override
    public IOrder[] sortOrderByPeriod(IOrder[] orders, Calendar startPeriod, Calendar EndPeriod) {
        int lengthArray;
        IOrder[] sortArrayOrder = new Order[0];
        for (IOrder order : orders) {
            lengthArray = sortArrayOrder.length;
            if (order.getLeadTime().compareTo(startPeriod) >= 0 & order.getLeadTime().compareTo(EndPeriod) <= 0) {
                sortArrayOrder = Arrays.copyOf(sortArrayOrder, lengthArray + 1);
                sortArrayOrder[lengthArray] = order;
            }
        }
        return sortArrayOrder;
    }

    @Override
    public IOrder[] getCurrentRunningOrders() {
        int indexOrder;
        IOrder[] arrayOder = new Order[0];
        for (IOrder order : this.carService.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            if (order.getStatus().equals("perform")) {
                indexOrder = arrayOder.length;
                arrayOder = Arrays.copyOf(arrayOder, indexOrder + 1);
                arrayOder[indexOrder] = order;
            }
        }
        return arrayOder;
    }

    @Override
    public IOrder[] getMasterOrders(IMaster master) {
        int lengthArray;
        IOrder[] orders = new Order[0];
        for (IOrder order : this.carService.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            for (IMaster masterService : order.getMasters()) {
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
    public IMaster[] getOrderMasters(IOrder order) {
        return order.getMasters();
    }

    @Override
    public IOrder[] getCompletedOrders() {
        int lengthArray;
        IOrder[] sortOrders = new Order[0];
        for (IOrder order : this.carService.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            lengthArray = sortOrders.length;
            if (order.getStatus().equals("completed")) {
                sortOrders = Arrays.copyOf(sortOrders, lengthArray + 1);
                sortOrders[lengthArray] = order;
            }
        }
        return sortOrders;
    }

    @Override
    public IOrder[] getCanceledOrders() {
        int lengthArray;
        IOrder[] sortOrders = new Order[0];
        for (IOrder order : this.carService.getOrders()) {
            if (order.isDeleteStatus()) {
                continue;
            }
            lengthArray = sortOrders.length;
            if (order.getStatus().equals("canceled")) {
                sortOrders = Arrays.copyOf(sortOrders, lengthArray + 1);
                sortOrders[lengthArray] = order;
            }
        }
        return sortOrders;
    }

    @Override
    public IOrder[] getDeletedOrders() {
        int lengthArray;
        IOrder[] sortOrders = new Order[0];
        for (IOrder order : this.carService.getOrders()) {
            lengthArray = sortOrders.length;
            if (order.isDeleteStatus()) {
                sortOrders = Arrays.copyOf(sortOrders, lengthArray + 1);
                sortOrders[lengthArray] = order;
            }
        }
        return sortOrders;
    }
}