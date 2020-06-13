package com.senla.carservice.service;

import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.repository.CarOfficeRepository;
import com.senla.carservice.repository.CarOfficeRepositoryImpl;

public final class CarOfficeServiceImpl implements CarOfficeService{
    private static CarOfficeServiceImpl instance;
    private final CarOfficeRepository carOfficeRepository;

    public CarOfficeServiceImpl() {
        this.carOfficeRepository = CarOfficeRepositoryImpl.getInstance();
    }

    public static CarOfficeServiceImpl getInstance() {
        if (instance == null) {
            instance = new CarOfficeServiceImpl();
        }
        return instance;
    }


    @Override
    public int getNumberFreePlaceDate(Order[] orders) {
        int numberGeneralPlace = 0;
        int numberPlaceOrders = orders.length;
        for (Garage garage : this.carOfficeRepository.getGarages())
            numberGeneralPlace += garage.getPlaces().length;
        return numberGeneralPlace - numberPlaceOrders;
    }

    @Override
    public int getNumberFreeMasters(Order[] orders) {
        int numberMastersOrders = 0;
        int numberGeneralMasters = this.carOfficeRepository.getMasters().length;
        for (Order order : orders)
            numberMastersOrders += order.getMasters().length;
        return numberGeneralMasters - numberMastersOrders;
    }
}
