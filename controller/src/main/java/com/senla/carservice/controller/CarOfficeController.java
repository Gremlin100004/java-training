package com.senla.carservice.controller;

import com.senla.carservice.csv.exception.CsvException;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.service.CarOfficeService;
import com.senla.carservice.service.MasterService;
import com.senla.carservice.service.PlaceService;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.exception.DateException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
@NoArgsConstructor
@Slf4j
public class CarOfficeController {
    @Autowired
    private CarOfficeService carOfficeService;
    @Autowired
    private MasterService masterService;
    @Autowired
    private PlaceService placeService;

    public String getFreePlacesMastersByDate(String date) {
        log.info("Method getFreePlacesMastersByDate");
        log.trace("Parameter date: {}", date);
        Date dateFree = DateUtil.getDatesFromString(date, false);
        if (dateFree == null) {
            return "error date";
        }
        try {
            Date startDayDate = DateUtil.bringStartOfDayDate(dateFree);
            Long numberFreeMasters = masterService.getNumberFreeMastersByDate(startDayDate);
            Long numberFreePlace = placeService.getNumberFreePlaceByDate(startDayDate);
            return "- number free places in service: " + numberFreePlace + "\n- number free masters in service: " +
                   numberFreeMasters;
        } catch (BusinessException | DateException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getNearestFreeDate() {
        log.info("Method getNearestFreeDate");
        try {
            return "Nearest free date: " + DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate(), false);
        } catch (BusinessException | DateException | DaoException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String exportEntities() {
        log.info("Method exportEntities");
        try {
            carOfficeService.exportEntities();
            return "Export completed successfully!";
        } catch (BusinessException | CsvException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String importEntities() {
        log.info("Method importEntities");
        try {
            carOfficeService.importEntities();
            return "Imported completed successfully!";
        } catch (BusinessException | CsvException e) {
            log.warn(e.getMessage());
            return e.getMessage();
        }
    }
}