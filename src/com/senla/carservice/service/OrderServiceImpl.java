package com.senla.carservice.service;

import com.senla.carservice.comparator.OrderCreationComparator;
import com.senla.carservice.comparator.OrderLeadComparator;
import com.senla.carservice.comparator.OrderPriceComparator;
import com.senla.carservice.comparator.OrderStartComparator;
import com.senla.carservice.domain.*;
import com.senla.carservice.repository.GarageRepository;
import com.senla.carservice.repository.GarageRepositoryImpl;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.ExportUtil;

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
    public void addOrder(String automaker, String model, String registrationNumber) {
        Order order = new Order(this.orderRepository.getIdGeneratorOrder().getId(),
                new Car(this.orderRepository.getIdGeneratorCar().getId(), automaker, model, registrationNumber));
        this.orderRepository.getOrders().add(order);
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

    @Override
    public String exportOrder(){
        OrderRepository orderRepository = OrderRepositoryImpl.getInstance();
        List<Order> orders = orderRepository.getOrders();
        List<Car> cars = orderRepository.getCars();
        StringBuilder valueOrderCsv = new StringBuilder();
        StringBuilder valueCarCsv = new StringBuilder();
        String message;
        for (int i = 0; i < orders.size(); i++){
            if (i == orders.size()-1){
                valueOrderCsv.append(convertOrderToCsv(orders.get(i), false));
            } else {
                valueOrderCsv.append(convertOrderToCsv(orders.get(i), true));
            }
        }
        for (int i = 0; i < cars.size(); i++){
            if (i == cars.size()-1){
                valueCarCsv.append(convertCarToCsv(cars.get(i), false));
            } else {
                valueCarCsv.append(convertCarToCsv(cars.get(i), true));
            }
        }
        message = ExportUtil.SaveCsv(valueOrderCsv, "csv//order.csv");
        if (!message.equals("save successfully")){
            return message;
        }
        message = ExportUtil.SaveCsv(valueCarCsv, "csv//car.csv");
        if (!message.equals("save successfully")){
            return message;
        }
        return message;
    }

    private String convertCarToCsv(Car car, boolean isLineFeed){
        if(isLineFeed){
            return String.format("%s,%s,%s,%s\n", car.getId(),
                    car.getAutomaker(), car.getModel(), car.getRegistrationNumber());
        } else {
            return String.format("%s,%s,%s,%s", car.getId(),
                    car.getAutomaker(), car.getModel(), car.getRegistrationNumber());
        }
    }

    private String convertOrderToCsv(Order order, boolean isLineFeed){
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"",order.getId(),
                DateUtil.getStringFromDate(order.getCreationTime()),
                DateUtil.getStringFromDate(order.getExecutionStartTime()),
                DateUtil.getStringFromDate(order.getLeadTime()),
                order.getGarage().getId(), order.getPlace().getId(),
                order.getCar().getId(), order.getPrice(), order.getStatus(),
                order.isDeleteStatus()));
        for (int i = 0; i < order.getMasters().size(); i++){
            if (i == order.getMasters().size()-1){
                stringValue.append(order.getMasters().get(i).getId());
            } else {
                stringValue.append(order.getMasters().get(i).getId()).append(",");
            }
        }
        stringValue.append("\"");
        if(isLineFeed){
            stringValue.append("\n");
        }
        return stringValue.toString();
    }
}