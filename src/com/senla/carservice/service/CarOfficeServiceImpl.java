package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.GarageRepository;
import com.senla.carservice.repository.GarageRepositoryImpl;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.MasterRepositoryImpl;

import java.util.List;

public class CarOfficeServiceImpl implements CarOfficeService {
    private static CarOfficeService instance;
    private final MasterRepository masterRepository;
    private final GarageRepository garageRepository;

    private CarOfficeServiceImpl() {
        this.masterRepository = MasterRepositoryImpl.getInstance();
        this.garageRepository = GarageRepositoryImpl.getInstance();
    }

    public static CarOfficeService getInstance() {
        if (instance == null) {
            instance = new CarOfficeServiceImpl();
        }
        return instance;
    }

    @Override
    public int getNumberFreePlaceDate(List<Order> orders) {
        int numberGeneralPlace = 0;
        int numberPlaceOrders = orders.size();
        // фигурные скобки ставить ВСЕГДА!!!
        for (Garage garage : this.garageRepository.getGarages())
            numberGeneralPlace += garage.getPlaces().size();
        return numberGeneralPlace - numberPlaceOrders;
    }

    @Override
    public int getNumberFreeMasters(List<Order> orders) {
        // можно использовать Стрим АПИ
        int numberMastersOrders = 0;
        int numberGeneralMasters = this.masterRepository.getMasters().size();
        for (Order order : orders)
            numberMastersOrders += order.getMasters().size();
        return numberGeneralMasters - numberMastersOrders;
    }
}