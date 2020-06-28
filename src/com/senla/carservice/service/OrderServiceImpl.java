package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.domain.Status;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.exception.OrderStatusException;
import com.senla.carservice.repository.*;

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
    private final String ORDER_PATH = "csv//order.csv";
    private final String CAR_PATH = "csv//car.csv";

    private OrderServiceImpl() {
        this.orderRepository = OrderRepositoryImpl.getInstance();
        this.placeRepository = PlaceRepositoryImpl.getInstance();
        this.masterRepository = MasterRepositoryImpl.getInstance();
    }

    public static OrderService getInstance() {
        if (instance == null) {
            instance = new OrderServiceImpl();
        }
        return instance;
    }

    @Override
    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

    @Override
    public void addOrder(String automaker, String model, String registrationNumber) {
        orderRepository.addOrder(new Order(this.orderRepository.getIdGeneratorOrder().getId(),
                automaker, model, registrationNumber));
    }

    @Override
    public void addOrderDeadlines(Date executionStartTime, Date leadTime) throws NullDateException,
            DateException, NumberObjectZeroException {
        checkDateTime(executionStartTime, leadTime);
        Order currentOrder = orderRepository.getLastOrder();
        List<Order> orders = new ArrayList<>(orderRepository.getOrders());
        orders.remove(currentOrder);
        orders = sortOrderByPeriod(orders, executionStartTime, leadTime);
        int numberFreeMasters = masterRepository.getMasters().size() -
                orders.stream().mapToInt(order -> order.getMasters().size()).sum();
        int numberFreePlace = orderRepository.getOrders().size() - orders.size();
        if (numberFreeMasters == 0) throw new
               NumberObjectZeroException("The number of masters is zero", numberFreeMasters);
        if (numberFreePlace == 0) throw new
                NumberObjectZeroException("The number of places is zero", numberFreePlace);
        currentOrder.setExecutionStartTime(executionStartTime);
        currentOrder.setLeadTime(leadTime);
        orderRepository.updateOrder(currentOrder);
    }
    @Override
    public void addOrderMasters(List<Master> masters) throws NumberObjectZeroException {
        if (masters.size() == 0) throw new
                NumberObjectZeroException("The number of masters is zero", masters.size());
        for (Master master : masters) {
            if (master.getNumberOrder() != null) {
                master.setNumberOrder(master.getNumberOrder() + 1);
            } else {
                master.setNumberOrder(1);
            }
        }
        Order currentOrder = orderRepository.getLastOrder();
        currentOrder.setMasters(masters);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void addOrderPlace(Place place) {
        Order currentOrder = orderRepository.getLastOrder();
        currentOrder.setPlace(place);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void addOrderPrice(BigDecimal price) {
        Order currentOrder = orderRepository.getLastOrder();
        currentOrder.setPrice(price);
        orderRepository.updateOrder(currentOrder);
    }

    @Override
    public void completeOrder(Order order) throws OrderStatusException {
        checkStatusOrder(order);
        order.setStatus(Status.PERFORM);
        order.setExecutionStartTime(new Date());
        order.getPlace().setBusyStatus(true);
        orderRepository.updateOrder(order);
    }

    @Override
    public void cancelOrder(Order order) throws OrderStatusException {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        for (Master master : order.getMasters()) {
            master.setNumberOrder(master.getNumberOrder() - 1);
        }
        order.getPlace().setBusyStatus(false);
        orderRepository.updateOrder(order);
    }

    @Override
    public void closeOrder(Order order) throws OrderStatusException {
        checkStatusOrder(order);
        order.setLeadTime(new Date());
        for (Master master : order.getMasters()) {
            master.setNumberOrder(master.getNumberOrder() - 1);
        }
        order.getPlace().setBusyStatus(false);
        orderRepository.updateOrder(order);
    }

    @Override
    public void deleteOrder(Order order) throws OrderStatusException {
        checkStatusOrderDelete(order);
        order.setDeleteStatus(true);
        orderRepository.updateOrder(order);
    }

    @Override
    public void shiftLeadTime(Order order, Date executionStartTime, Date leadTime) throws NullDateException, OrderStatusException, DateException {
        checkDateTime(executionStartTime, leadTime);
        checkStatusOrderShiftTime(order);
        order.setLeadTime(leadTime);
        order.setExecutionStartTime(executionStartTime);
        orderRepository.updateOrder(order);
    }

    @Override
    public List<Order> sortOrderCreationTime(List<Order> orders) {
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
    public List<Order> getOrderByPeriod(Date startPeriod, Date endPeriod) throws NullDateException {
        if(startPeriod == null) throw new NullDateException("The date is null", startPeriod);
        if(endPeriod == null) throw new NullDateException("The date is null", endPeriod);
        return sortOrderByPeriod(orderRepository.getOrders(), startPeriod, endPeriod);
    }

    @Override
    public List<Order> getCurrentRunningOrders() {
        return orderRepository.getCurrentRunningOrders();
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
        return orderRepository.getOrderMasters(order);
    }

    @Override
    public List<Order> getCompletedOrders(List<Order> orders) {
        return orderRepository.getCompletedOrders();
    }

    @Override
    public List<Order> getCanceledOrders(List<Order> orders) {
        return orderRepository.getCanceledOrders();
    }

    @Override
    public List<Order> getDeletedOrders(List<Order> orders) {
        return orderRepository.getDeletedOrders();
    }

    private List<Order> sortOrderByPeriod(List<Order> orders, Date startPeriod, Date endPeriod){
        List<Order> sortArrayOrder = new ArrayList<>();
        orders.forEach(order -> {
            if (order.getLeadTime().compareTo(startPeriod) >= 0 && order.getLeadTime().compareTo(endPeriod) <= 0) {
                sortArrayOrder.add(order);
            }
        });
        return sortArrayOrder;
    }

    private void checkStatusOrder(Order order) throws OrderStatusException {
        if (order.isDeleteStatus()) throw new
                OrderStatusException("The order has been deleted", order.getStatus());
        if (order.getStatus().equals(Status.COMPLETED)) throw new
                OrderStatusException("The order has been completed", order.getStatus());
        if (order.getStatus().equals(Status.PERFORM)) throw new
                OrderStatusException("Order is being executed", order.getStatus());
        if (order.getStatus().equals(Status.CANCELED)) throw new
                OrderStatusException("The order has been canceled", order.getStatus());
    }

    private void checkStatusOrderDelete(Order order) throws OrderStatusException {
        if (order.isDeleteStatus()) throw new
                OrderStatusException("The order has been deleted", order.getStatus());
        if (order.getStatus().equals(Status.PERFORM)) throw new
                OrderStatusException("Order is being executed", order.getStatus());
        if (order.getStatus().equals(Status.WAIT)) throw new
                OrderStatusException("The order is being waited", order.getStatus());
    }

    private void checkStatusOrderShiftTime(Order order) throws OrderStatusException {
        if (order.isDeleteStatus()) throw new
                OrderStatusException("The order has been deleted", order.getStatus());
        if (order.getStatus().equals(Status.COMPLETED)) throw new
                OrderStatusException("The order has been completed", order.getStatus());
        if (order.getStatus().equals(Status.CANCELED)) throw new
                OrderStatusException("The order has been canceled", order.getStatus());
    }

    private void checkDateTime(Date executionStartTime, Date leadTime) throws NullDateException, DateException {
        if(executionStartTime == null) throw new NullDateException("The date is null", executionStartTime);
        if(leadTime == null) throw new NullDateException("The date is null", leadTime);
        if (executionStartTime.compareTo(leadTime) > 0) throw new
                DateException("The execution start time is greater than lead time", executionStartTime, leadTime);
        if (executionStartTime.compareTo(new Date()) < 1) throw new
                DateException("The execution start time is less than current Date", executionStartTime, new Date());
    }

//    @Override
//    public String exportOrder() {
//        OrderRepository orderRepository = OrderRepositoryImpl.getInstance();
//        List<Order> orders = orderRepository.getOrders();
//        List<Car> cars = orderRepository.getCars();
//        StringBuilder valueOrderCsv = new StringBuilder();
//        StringBuilder valueCarCsv = new StringBuilder();
//        String message;
//        for (int i = 0; i < orders.size(); i++) {
//            if (i == orders.size() - 1) {
//                valueOrderCsv.append(convertOrderToCsv(orders.get(i), false));
//            } else {
//                valueOrderCsv.append(convertOrderToCsv(orders.get(i), true));
//            }
//        }
//        for (int i = 0; i < cars.size(); i++) {
//            if (i == cars.size() - 1) {
//                valueCarCsv.append(convertCarToCsv(cars.get(i), false));
//            } else {
//                valueCarCsv.append(convertCarToCsv(cars.get(i), true));
//            }
//        }
//        message = FileUtil.SaveCsv(valueOrderCsv, ORDER_PATH);
//        if (!message.equals("save successfully")) {
//            return message;
//        }
//        message = FileUtil.SaveCsv(valueCarCsv, CAR_PATH);
//        if (!message.equals("save successfully")) {
//            return message;
//        }
//        return message;
//    }
//
//    @Override
//    public String importOrder() {
//        List<String> csvLinesOrder = FileUtil.GetCsv(ORDER_PATH);
//        List<Car> cars = importCar();
//        if (cars.isEmpty() || csvLinesOrder.isEmpty()) {
//            return "import problem";
//        }
//        List<Order> orders = this.orderRepository.getOrders();
//        for (String line : csvLinesOrder) {
//            Order order = getOrderFromCsv(line, cars);
//            if (order.getMasters().isEmpty()) {
//                return "you don't import masters!!!";
//            }
//            if (order.getGarage() == null) {
//                return "you don't import garages!!!";
//            }
//            orders.remove(order);
//            orders.add(order);
//        }
//        return "import successfully";
//    }
//
//    private List<Car> importCar() {
//        List<String> csvLinesPlace = FileUtil.GetCsv(CAR_PATH);
//        List<Car> cars = new ArrayList<>();
//        csvLinesPlace.forEach(line -> cars.add(getCarFromCsv(line)));
//        return cars;
//    }
//
//    private Car getCarFromCsv(String line) {
//        List<String> values = Arrays.asList(line.split(","));
//        Car car = new Car();
//        car.setId(Long.valueOf(values.get(0)));
//        car.setAutomaker(values.get(1));
//        car.setModel(values.get(2));
//        car.setRegistrationNumber(values.get(3));
//        return car;
//    }
//
//    private String convertCarToCsv(Car car, boolean isLineFeed) {
//        if (isLineFeed) {
//            return String.format("%s,%s,%s,%s\n", car.getId(),
//                    car.getAutomaker(), car.getModel(), car.getRegistrationNumber());
//        } else {
//            return String.format("%s,%s,%s,%s", car.getId(),
//                    car.getAutomaker(), car.getModel(), car.getRegistrationNumber());
//        }
//    }
//
//    private Order getOrderFromCsv(String line, List<Car> cars) {
//        List<String> values = Arrays.asList((line.split("\""))[0].split(","));
//        List<String> arrayIdMaster = Arrays.asList(line.split("\"")[1].replaceAll("\"", "").split(","));
//        Car car = getCarById(cars, Long.valueOf(values.get(6)));
//        Order order = new Order(Long.valueOf(values.get(0)), car);
//        order.setCreationTime(DateUtil.getDatesFromString(values.get(1)));
//        order.setExecutionStartTime(DateUtil.getDatesFromString(values.get(2)));
//        order.setLeadTime(DateUtil.getDatesFromString(values.get(3)));
//        order.setGarage(getGarageById(garageRepository.getGarages(), Long.valueOf(values.get(4))));
//        order.setPlace(getPlaceById(placeRepository.getPlaces(), Long.valueOf(values.get(5))));
//        order.setPrice(new BigDecimal(values.get(7)));
//        order.setStatus(Status.valueOf(values.get(8)));
//        order.setDeleteStatus(Boolean.parseBoolean(values.get(9)));
//        order.setMasters(getMastersById(MasterRepositoryImpl.getInstance().getMasters(), arrayIdMaster));
//        return order;
//    }
//
//    private List<Master> getMastersById(List<Master> masters, List<String> arrayIdMaster) {
//        List<Master> orderMasters = new ArrayList<>();
//        for (String stringIndex : arrayIdMaster) {
//            masters.forEach(master -> {
//                if (master.getId().equals(Long.valueOf(stringIndex))) {
//                    orderMasters.add(master);
//                }
//            });
//        }
//        return orderMasters;
//    }
//
//    private Garage getGarageById(List<Garage> garages, Long id) {
//        for (Garage garage : garages) {
//            if (garage.getId().equals(id)) {
//                return garage;
//            }
//        }
//        return null;
//    }
//
//    private Place getPlaceById(List<Place> places, Long id) {
//        for (Place place : places) {
//            if (place.getId().equals(id)) {
//                return place;
//            }
//        }
//        return null;
//    }
//
//    private Car getCarById(List<Car> cars, Long id) {
//        for (Car importCar : cars) {
//            if (importCar.getId().equals(id)) {
//                return importCar;
//            }
//        }
//        return null;
//    }
//
//    private String convertOrderToCsv(Order order, boolean isLineFeed) {
//        StringBuilder stringValue = new StringBuilder();
//        stringValue.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,\"", order.getId(),
//                DateUtil.getStringFromDate(order.getCreationTime()),
//                DateUtil.getStringFromDate(order.getExecutionStartTime()),
//                DateUtil.getStringFromDate(order.getLeadTime()),
//                order.getGarage().getId(), order.getPlace().getId(),
//                order.getCar().getId(), order.getPrice(), order.getStatus(),
//                order.isDeleteStatus()));
//        for (int i = 0; i < order.getMasters().size(); i++) {
//            if (i == order.getMasters().size() - 1) {
//                stringValue.append(order.getMasters().get(i).getId());
//            } else {
//                stringValue.append(order.getMasters().get(i).getId()).append(",");
//            }
//        }
//        stringValue.append("\"");
//        if (isLineFeed) {
//            stringValue.append("\n");
//        }
//        return stringValue.toString();
//    }
}