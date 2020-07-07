package com.senla.carservice.service;

import com.senla.carservice.csvutil.CsvMaster;
import com.senla.carservice.csvutil.CsvOrder;
import com.senla.carservice.csvutil.CsvPlace;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import com.senla.carservice.util.DateUtil;

import java.util.Date;
import java.util.List;

public class CarOfficeServiceImpl implements CarOfficeService {
    private static CarOfficeService instance;
    private final MasterRepository masterRepository;
    private final PlaceRepository placeRepository;
    private final OrderRepository orderRepository;

    private CarOfficeServiceImpl() {
        masterRepository = MasterRepositoryImpl.getInstance();
        placeRepository = PlaceRepositoryImpl.getInstance();
        orderRepository = OrderRepositoryImpl.getInstance();
    }

    public static CarOfficeService getInstance() {
        if (instance == null) {
            instance = new CarOfficeServiceImpl();
        }
        return instance;
    }

    @Override
    public Date getNearestFreeDate() {
        if (masterRepository.getMasters().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        if (orderRepository.getOrders().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
        Date leadTimeOrder = orderRepository.getLastOrder().getLeadTime();
        Date DayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.compareTo(currentDay) <= 0; DateUtil.addDays(currentDay, 1)) {
            if (!masterRepository.getFreeMasters(currentDay).isEmpty() &&
                !placeRepository.getFreePlaces(currentDay).isEmpty()) {
                break;
            }
            DayDate = currentDay;
            currentDay = DateUtil.bringStartOfDayDate(currentDay);
        }
        return DayDate;
    }

    @Override
    public void importEntities() {
        masterRepository.updateListMaster(CsvMaster.importMasters(orderRepository.getOrders()));
        placeRepository.updateListPlace(CsvPlace.importPlaces(orderRepository.getOrders()));
        List<Order> orders = CsvOrder.importOrder(masterRepository.getMasters(), placeRepository.getPlaces());
        orderRepository.updateListOrder(orders);
        masterRepository.updateListMaster(CsvMaster.importMasters(orders));
        placeRepository.updateListPlace(CsvPlace.importPlaces(orders));
    }

    @Override
    public void exportEntities() {
        checkOrders();
        checkMasters();
        checkPlaces();
        CsvOrder.exportOrder(orderRepository.getOrders());
        CsvMaster.exportMasters(masterRepository.getMasters());
        CsvPlace.exportPlaces(placeRepository.getPlaces());
    }

    private void checkOrders() {
        if (orderRepository.getOrders().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
    }

    private void checkMasters() {
        if (masterRepository.getMasters().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }


}