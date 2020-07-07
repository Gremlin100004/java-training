package com.senla.carservice.controller;

import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.CarOfficeServiceImpl;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.MasterServiceImpl;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.PlaceServiceImpl;
import com.senla.carservice.util.DateUtil;

import java.util.Date;

public class CarOfficeController {
    private static CarOfficeController instance;
    private final CarOfficeService carOfficeService;
    private final MasterService masterService;
    private final PlaceService placeService;

    private CarOfficeController() {
        carOfficeService = CarOfficeServiceImpl.getInstance();
        masterService = MasterServiceImpl.getInstance();
        placeService = PlaceServiceImpl.getInstance();
    }

    public static CarOfficeController getInstance() {
        if (instance == null) {
            instance = new CarOfficeController();
        }
        return instance;
    }

    public String getFreePlacesMastersByDate(String date) {
        Date dateFree = DateUtil.getDatesFromString(date, false);
        if (dateFree == null) {
            return "error date";
        }
        Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
        try {
            int numberFreeMasters = masterService.getNumberFreeMastersByDate(startDayDate);
            int numberFreePlace = placeService.getNumberFreePlaceByDate(startDayDate);
            return String.format("- number free places in service: %s\n- number free masters in service: %s",
                                 numberFreePlace, numberFreeMasters);
        } catch (BusinessException | DateException e) {
            return e.getMessage();
        }
    }

    public String getNearestFreeDate() {
        try {
            return String
                .format("Nearest free date: %s", DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate(), false));
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String exportEntities() {
        try {
            carOfficeService.exportEntities();
            return "Export completed successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
    public String importEntities() {
        try {
            carOfficeService.importEntities();
            return "Imported completed successfully!";
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }
}