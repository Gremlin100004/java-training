package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.exception.CsvException;
import com.senla.carservice.exception.DaoException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

@Singleton
public class CarOfficeController {
    @Dependency
    private CarOfficeService carOfficeService;
    @Dependency
    private MasterService masterService;
    @Dependency
    private PlaceService placeService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CarOfficeController.class);

    public CarOfficeController() {
    }

    public String getFreePlacesMastersByDate(String date) {
        LOGGER.info("Method getFreePlacesMastersByDate");
        LOGGER.debug("Parameter date: {}", date);
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
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String getNearestFreeDate() {
        LOGGER.info("Method getNearestFreeDate");
        try {
            return "Nearest free date: " + DateUtil.getStringFromDate(carOfficeService.getNearestFreeDate(), false);
        } catch (BusinessException | DaoException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String exportEntities() {
        LOGGER.info("Method exportEntities");
        try {
            carOfficeService.exportEntities();
            return "Export completed successfully!";
        } catch (BusinessException | CsvException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }

    public String importEntities() {
        LOGGER.info("Method importEntities");
        try {
            carOfficeService.importEntities();
            return "Imported completed successfully!";
        } catch (BusinessException | CsvException e) {
            LOGGER.warn(e.getMessage());
            return e.getMessage();
        }
    }
}