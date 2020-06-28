package com.senla.carservice.service;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.NumberObjectZeroException;
import com.senla.carservice.repository.*;
import com.senla.carservice.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public String getNearestFreeDate() throws NumberObjectZeroException {
        Date dateFree = new Date();
        if (masterRepository.getMasters().isEmpty()) throw new NumberObjectZeroException("There are no masters", 0);
        if (orderRepository.getOrders().isEmpty()) throw new NumberObjectZeroException("There are no orders", 0);
        if (placeRepository.getPlaces().isEmpty()) throw new NumberObjectZeroException("There are no places", 0);
        int numberFreeMasters = 0;
        int numberFreePlace = 0;
        while (numberFreeMasters == 0 && numberFreePlace == 0) {
            Date endDay = DateUtil.bringEndOfDayDate(dateFree);
            List<Order> sortArrayOrder = new ArrayList<>();
            Date startDay = dateFree;
            orderRepository.getOrders().forEach(order -> {
                if (order.getLeadTime().compareTo(startDay) >= 0 && order.getLeadTime().compareTo(endDay) <= 0) {
                    sortArrayOrder.add(order);
                }
            });
            numberFreeMasters = masterRepository.getMasters().size() -
                    masterRepository.getFreeMasters(sortArrayOrder).size();
            numberFreePlace = placeRepository.getPlaces().size() - placeRepository.getFreePlaces(sortArrayOrder).size();
            dateFree = DateUtil.addDays(DateUtil.bringStartOfDayDate(dateFree), 1);
        }
        dateFree = DateUtil.addDays(dateFree, -1);
        return String.format("Nearest free date: %s", DateUtil.getStringFromDate(dateFree));
    }
}