package com.senla.carservice.service;

import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.OrderRepositoryImpl;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;
import com.senla.carservice.util.DateUtil;

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
    public Date getNearestFreeDate() {
        Date startDayDate = new Date();
        if (masterRepository.getMasters().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        if (orderRepository.getOrders().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
        for (Date endDayDate = DateUtil.bringEndOfDayDate(startDayDate);
             orderRepository.getLastOrder().getLeadTime().compareTo(endDayDate) <= 0; DateUtil.addDays(endDayDate, 1)) {
            startDayDate = DateUtil.bringStartOfDayDate(endDayDate);
            List<Order> sortArrayOrder = new ArrayList<>();
            for (Order order : orderRepository.getOrders()) {
                if (order.getLeadTime().compareTo(startDayDate) >= 0 &&
                    order.getLeadTime().compareTo(endDayDate) <= 0) {
                    sortArrayOrder.add(order);
                }
            }
            if (!masterRepository.getFreeMasters(sortArrayOrder).isEmpty() &&
                !placeRepository.getFreePlaces(sortArrayOrder).isEmpty()) {
                return DateUtil.addDays(startDayDate, -1);
            }
        }
        return startDayDate;
    }
}