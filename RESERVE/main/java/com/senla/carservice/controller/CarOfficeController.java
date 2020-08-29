package com.senla.carservice.controller;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.util.DateUtil;

import java.util.Date;

@Singleton
public class CarOfficeController {
    @Dependency
    private CarOfficeService carOfficeService;
    @Dependency
    private MasterService masterService;
    @Dependency
    private PlaceService placeService;

    public CarOfficeController() {
    }

    public String getFreePlacesMastersByDate(String date) {
        Date dateFree = DateUtil.getDatesFromString(date, false);
        if (dateFree == null) {
            return "error date";
        }
        try {
            Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
            int numberFreeMasters = masterService.getNumberFreeMastersByDate(startDayDate);
            int numberFreePlace = placeService.getNumberFreePlaceByDate(startDayDate);
            return "- number free places in service: " + numberFreePlace + "\n- number free masters in service: " +
                   numberFreeMasters;
        } catch (BusinessException e) {
            return e.getMessage();
        }
    }

    public String getNearestFreeDate() {
        try {
            return "Nearest free date: " + DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate(), false);
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