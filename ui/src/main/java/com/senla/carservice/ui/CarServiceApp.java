package com.senla.carservice.ui;

//import com.senla.carservice.ui.menu.MenuController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Place;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.dto.PlaceDto;
import com.senla.carservice.ui.service.CarOfficeService;
import com.senla.carservice.ui.service.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@ComponentScan("com.senla.carservice.ui")
@PropertySource("classpath:application.properties")
public class CarServiceApp {

    public static void main(String[] args) {
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarServiceApp.class);
//        MenuController menuController = applicationContext.getBean(MenuController.class);
//        menuController.run();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(CarServiceApp.class);
        OrderService orderService = applicationContext.getBean(OrderService.class);
        String date = "10.15.2020";
        OrderDto orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setRegistrationNumber("111");
        orderDto.setModel("java");
        orderDto.setAutomaker("oracle");
        orderDto.setStatus("WAIT");
        orderDto.setPrice(BigDecimal.valueOf(123));
        orderDto.setCreationTime(new Date());
        orderDto.setLeadTime(new Date());
        orderDto.setExecutionStartTime(new Date());
        PlaceDto placeDto = new PlaceDto();
        placeDto.setId(1L);
        orderDto.setPlace(placeDto);
        MasterDto masterDto = new MasterDto();
        masterDto.setId(1L);
        masterDto.setName("test");
        masterDto.setNumberOrders(1);
        masterDto.setDeleteStatus(false);
        orderDto.setMasters(Collections.singletonList(masterDto));


        String startPeriod = "";
        String endPeriod = "";
        System.out.println(orderDto);
        System.out.println(orderService.shiftLeadTime(orderDto));
    }
}