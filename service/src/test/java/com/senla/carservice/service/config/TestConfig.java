package com.senla.carservice.service.config;

import com.senla.carservice.csv.CsvMaster;
import com.senla.carservice.csv.CsvOrder;
import com.senla.carservice.csv.CsvPlace;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.CarOfficeServiceImpl;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;

public class TestConfig {
    @Bean
    MasterService masterService() {
        return new MasterServiceImpl();
    }

    @Bean
    MasterDao masterDao() {
        return Mockito.mock(MasterDao.class);
    }

    @Bean
    OrderService orderService() {
        return new OrderServiceImpl();
    }

    @Bean
    OrderDao orderDao() {
        return Mockito.mock(OrderDao.class);
    }

    @Bean
    PlaceDao placeDao() {
        return Mockito.mock(PlaceDao.class);
    }

    @Bean
    CarOfficeService carOfficeService() {
        return new CarOfficeServiceImpl();
    }

    @Bean
    CsvPlace csvPlace() {
        return Mockito.mock(CsvPlace.class);
    }

    @Bean
    CsvOrder csvOrder() {
        return Mockito.mock(CsvOrder.class);
    }

    @Bean
    CsvMaster csvMaster() {
        return Mockito.mock(CsvMaster.class);
    }

    @Bean
    PlaceService placeService() {
        return new PlaceServiceImpl();
    }
}