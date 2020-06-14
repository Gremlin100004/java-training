package com.senla.carservice;

import com.senla.carservice.api.MenuController;
import com.senla.carservice.api.printer.Printer;
import com.senla.carservice.controller.CarOfficeController;
import com.senla.carservice.controller.GarageController;
import com.senla.carservice.controller.MasterController;
import com.senla.carservice.controller.OrderController;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Garage;
import com.senla.carservice.domain.Order;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args){
        MenuController menuController = new MenuController();
        menuController.run();
    }
}