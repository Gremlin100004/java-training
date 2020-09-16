package com.senla.carservice.service.config;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import org.springframework.context.annotation.Bean;

import static org.mockito.Mockito.mock;

public class TestConfig {

    @Bean
    public MasterService masterService(){
        return new MasterServiceImpl();
    }

    @Bean
    MasterDao masterDao() {
        return mock(MasterDao.class);
    }
}
