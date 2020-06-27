package com.senla.carservice.service;

import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.repository.PlaceRepositoryImpl;

import java.util.List;

public class CarOfficeServiceImpl implements CarOfficeService {
    private static CarOfficeService instance;
    private final MasterRepository masterRepository;
    private final PlaceRepository placeRepository;

    private CarOfficeServiceImpl() {
        masterRepository = MasterRepositoryImpl.getInstance();
        placeRepository = PlaceRepositoryImpl.getInstance();
    }

    public static CarOfficeService getInstance() {
        if (instance == null) {
            instance = new CarOfficeServiceImpl();
        }
        return instance;
    }

    @Override
    public int getNumberFreePlaceDate(List<Order> orders) {
        return placeRepository.getPlaces().size() - orders.size();
    }

    @Override
    public int getNumberFreeMasters(List<Order> orders) {
        int numberGeneralMasters = masterRepository.getMasters().size();
        int numberMastersOrders = orders.stream().mapToInt(order -> order.getMasters().size()).sum();
        return numberGeneralMasters - numberMastersOrders;
    }
}