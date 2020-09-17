package com.senla.carservice.service.config;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.OrderService;
import com.senla.carservice.service.OrderServiceImpl;
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
    OrderService orderService(){
        return new OrderServiceImpl();
    }

    @Bean
    OrderDao orderDao(){
        return Mockito.mock(OrderDao.class);
    }

    @Bean
    PlaceDao placeDao(){
        return Mockito.mock(PlaceDao.class);
    }
}