package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Singleton
public class CarOfficeServiceImpl implements CarOfficeService {
    @Dependency
    private MasterDao masterDao;
    @Dependency
    private PlaceDao placeDao;
    @Dependency
    private OrderDao orderDao;
    @Dependency
    private CsvPlace csvPlace;
    @Dependency
    private CsvOrder csvOrder;
    @Dependency
    private CsvMaster csvMaster;
    @Dependency
    private DatabaseConnection databaseConnection;
    private static final int NUMBER_DAY = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(CarOfficeServiceImpl.class);

    public CarOfficeServiceImpl() {
    }

    @Override
    public Date getNearestFreeDate() {
        LOGGER.debug("Method getNearestFreeDate");
        checkMasters();
        checkPlaces();
        checkOrders();
        Date leadTimeOrder = orderDao.getLastOrder(databaseConnection).getLeadTime();
        Date dayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.before(currentDay);
             currentDay = DateUtil.addDays(currentDay, NUMBER_DAY)) {
            if (masterDao.getFreeMasters(currentDay, databaseConnection).isEmpty() ||
                placeDao.getFreePlaces(currentDay, databaseConnection).isEmpty()) {
                dayDate = currentDay;
                currentDay = DateUtil.bringStartOfDayDate(currentDay);
            } else {
                break;
            }
        }
        return dayDate;
    }

    @Override
    public void importEntities() {
        LOGGER.debug("Method importEntities");
        try {
            databaseConnection.disableAutoCommit();
            masterDao.updateAllRecords(csvMaster.importMasters(), databaseConnection);
            placeDao.updateAllRecords(csvPlace.importPlaces(), databaseConnection);
            List<Order> orders = csvOrder.importOrder(masterDao.getAllRecords(databaseConnection),
                    placeDao.getAllRecords(databaseConnection));
            orderDao.updateAllRecords(orders, databaseConnection);
            orders.forEach(order -> orderDao.addRecordToTableManyToMany(order, databaseConnection));
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction import entities");
        } finally {
            databaseConnection.enableAutoCommit();
        }

    }

    @Override
    public void exportEntities() {
        LOGGER.debug("Method exportEntities");
        List<Order> orders = getOrders();
        List<Master> masters = getMasters();
        List<Place> places = getPlaces();
        csvOrder.exportOrder(orders);
        csvMaster.exportMasters(masters);
        csvPlace.exportPlaces(places);
    }

    private void checkMasters() {
        LOGGER.debug("Method checkMasters");
        if (masterDao.getAllRecords(databaseConnection).isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        LOGGER.debug("Method checkPlaces");
        if (placeDao.getAllRecords(databaseConnection).isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkOrders() {
        LOGGER.debug("Method checkOrders");
        if (orderDao.getAllRecords(databaseConnection).isEmpty()) {
            throw new BusinessException("There are no orders");
        }
    }

    private List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        List<Order> orders = orderDao.getAllRecords(databaseConnection);
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    private List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        List<Master> masters = masterDao.getAllRecords(databaseConnection);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    private List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(databaseConnection);
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }
}