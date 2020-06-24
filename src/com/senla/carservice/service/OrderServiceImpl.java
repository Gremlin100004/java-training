package com.senla.carservice.service;

import com.senla.carservice.domain.*;
import com.senla.carservice.repository.*;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.FileUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private static OrderService instance;
    private final OrderRepository orderRepository;
    private final GarageRepository garageRepository;
    private final String ORDER_PATH = "csv//order.csv";
    private final String CAR_PATH = "csv//car.csv";

    private OrderServiceImpl() {
        this.orderRepository = OrderRepositoryImpl.getInstance();
        this.garageRepository = GarageRepositoryImpl.getInstance();
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
        sortArrayOrder.sort((orderOne, orderTwo) -> {
            if (orderOne.getCreationTime() == null && orderTwo.getCreationTime() == null) return 0;
            if (orderOne.getCreationTime() == null) return -1;
            if (orderTwo.getCreationTime() == null) return 1;
            return orderOne.getCreationTime().compareTo(orderTwo.getCreationTime());
        });
        return sortArrayOrder;
    }

    @Override
    public List<Order> sortOrderByLeadTime(List<Order> orders) {
        List<Order> sortArrayOrder = new ArrayList<>(orders);
        sortArrayOrder.sort((orderOne, orderTwo) -> {
            if (orderOne.getLeadTime() == null && orderTwo.getLeadTime() == null) return 0;
            if (orderOne.getLeadTime() == null) return -1;
            if (orderTwo.getLeadTime() == null) return 1;
            return orderOne.getLeadTime().compareTo(orderTwo.getLeadTime());
        });
        return sortArrayOrder;
    }

    @Override
    public List<Order> sortOrderByPrice(List<Order> orders) {
        List<Order> sortArrayOrder = new ArrayList<>(orders);
        sortArrayOrder.sort((orderOne, orderTwo) -> {
            if (orderOne.getPrice() == null && orderTwo.getPrice() == null) return 0;
            if (orderOne.getPrice() == null) return -1;
            if (orderTwo.getPrice() == null) return 1;
            return orderOne.getPrice().compareTo(orderTwo.getPrice());
        });
        return sortArrayOrder;
    }

    @Override
    public List<Order> sortOrderByStartTime(List<Order> orders) {
        List<Order> sortArrayOrder = new ArrayList<>(orders);
        sortArrayOrder.sort((orderOne, orderTwo) -> {
            if (orderOne.getExecutionStartTime() == null && orderTwo.getExecutionStartTime() == null) return 0;
            if (orderOne.getExecutionStartTime() == null) return -1;
            if (orderTwo.getExecutionStartTime() == null) return 1;
            return orderOne.getExecutionStartTime().compareTo(orderTwo.getExecutionStartTime());
        });
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
    public String exportOrder() {
        OrderRepository orderRepository = OrderRepositoryImpl.getInstance();
        List<Order> orders = orderRepository.getOrders();
        List<Car> cars = orderRepository.getCars();
        StringBuilder valueOrderCsv = new StringBuilder();
        StringBuilder valueCarCsv = new StringBuilder();
        String message;
        for (int i = 0; i < orders.size(); i++) {
            if (i == orders.size() - 1) {
                valueOrderCsv.append(convertOrderToCsv(orders.get(i), false));
            } else {
                valueOrderCsv.append(convertOrderToCsv(orders.get(i), true));
            }
        }
        for (int i = 0; i < cars.size(); i++) {
            if (i == cars.size() - 1) {
                valueCarCsv.append(convertCarToCsv(cars.get(i), false));
            } else {
                valueCarCsv.append(convertCarToCsv(cars.get(i), true));
            }
        }
        message = FileUtil.SaveCsv(valueOrderCsv, ORDER_PATH);
        if (!message.equals("save successfully")) {
            return message;
        }
        message = FileUtil.SaveCsv(valueCarCsv, CAR_PATH);
        if (!message.equals("save successfully")) {
            return message;
        }
        return message;
    }

    @Override
    public String importOrder() {
        List<String> csvLinesOrder = FileUtil.GetCsv(ORDER_PATH);
        List<Car> cars = importCar();
        if (cars.isEmpty() || csvLinesOrder.isEmpty()) {
            return "import problem";
        }
        List<Order> orders = this.orderRepository.getOrders();
        for (String line : csvLinesOrder) {
            Order order = getOrderFromCsv(line, cars);
            if (order.getMasters().isEmpty()) {
                return "you don't import masters!!!";
            }
            if (order.getGarage() == null) {
                return "you don't import garages!!!";
            }
            orders.remove(order);
            orders.add(order);
        }
        return "import successfully";
    }

    private List<Car> importCar() {
        List<String> csvLinesPlace = FileUtil.GetCsv(CAR_PATH);
        List<Car> cars = new ArrayList<>();
        csvLinesPlace.forEach(line -> cars.add(getCarFromCsv(line)));
        return cars;
    }

    private Car getCarFromCsv(String line) {
        List<String> values = Arrays.asList(line.split(","));
        Car car = new Car();
        car.setId(Long.valueOf(values.get(0)));
        car.setAutomaker(values.get(1));
        car.setModel(values.get(2));
        car.setRegistrationNumber(values.get(3));
        return car;
    }

    private String convertCarToCsv(Car car, boolean isLineFeed) {
        if (isLineFeed) {
            return String.format("%s,%s,%s,%s\n", car.getId(),
                    car.getAutomaker(), car.getModel(), car.getRegistrationNumber());
        } else {
            return String.format("%s,%s,%s,%s", car.getId(),
                    car.getAutomaker(), car.getModel(), car.getRegistrationNumber());
        }
    }

    private Order getOrderFromCsv(String line, List<Car> cars) {
        List<String> values = Arrays.asList((line.split("\""))[0].split(","));
        List<String> arrayIdMaster = Arrays.asList(line.split("\"")[1].replaceAll("\"", "").split(","));
        Car car = getCarById(cars, Long.valueOf(values.get(6)));
        Order order = new Order(Long.valueOf(values.get(0)), car);
        order.setCreationTime(DateUtil.getDatesFromString(values.get(1)));
        order.setExecutionStartTime(DateUtil.getDatesFromString(values.get(2)));
        order.setLeadTime(DateUtil.getDatesFromString(values.get(3)));
        order.setGarage(getGarageById(this.garageRepository.getGarages(), Long.valueOf(values.get(4))));
        order.setPlace(getPlaceById(this.garageRepository.getPlaces(), Long.valueOf(values.get(5))));
        order.setPrice(new BigDecimal(values.get(7)));
        order.setStatus(Status.valueOf(values.get(8)));
        order.setDeleteStatus(Boolean.parseBoolean(values.get(9)));
        order.setMasters(getMastersById(MasterRepositoryImpl.getInstance().getMasters(), arrayIdMaster));
        return order;
    }

    private List<Master> getMastersById(List<Master> masters, List<String> arrayIdMaster) {
        List<Master> orderMasters = new ArrayList<>();
        for (String stringIndex : arrayIdMaster) {
            masters.forEach(master -> {
                if (master.getId().equals(Long.valueOf(stringIndex))) {
                    orderMasters.add(master);
                }
            });
        }
        return orderMasters;
    }

    private Garage getGarageById(List<Garage> garages, Long id) {
        for (Garage garage : garages) {
            if (garage.getId().equals(id)) {
                return garage;
            }
        }
        return null;
    }

    private Place getPlaceById(List<Place> places, Long id) {
        for (Place place : places) {
            if (place.getId().equals(id)) {
                return place;
            }
        }
        return null;
    }

    private Car getCarById(List<Car> cars, Long id) {
        for (Car importCar : cars) {
            if (importCar.getId().equals(id)) {
                return importCar;
            }
        }
        return null;
    }

    private String convertOrderToCsv(Order order, boolean isLineFeed) {
        StringBuilder stringValue = new StringBuilder();
        stringValue.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"", order.getId(),
                DateUtil.getStringFromDate(order.getCreationTime()),
                DateUtil.getStringFromDate(order.getExecutionStartTime()),
                DateUtil.getStringFromDate(order.getLeadTime()),
                order.getGarage().getId(), order.getPlace().getId(),
                order.getCar().getId(), order.getPrice(), order.getStatus(),
                order.isDeleteStatus()));
        for (int i = 0; i < order.getMasters().size(); i++) {
            if (i == order.getMasters().size() - 1) {
                stringValue.append(order.getMasters().get(i).getId());
            } else {
                stringValue.append(order.getMasters().get(i).getId()).append(",");
            }
        }
        stringValue.append("\"");
        if (isLineFeed) {
            stringValue.append("\n");
        }
        return stringValue.toString();
    }
}