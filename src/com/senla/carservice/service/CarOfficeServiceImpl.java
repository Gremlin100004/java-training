package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.csvutil.CsvMaster;
import com.senla.carservice.util.csvutil.CsvOrder;
import com.senla.carservice.util.csvutil.CsvPlace;

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

    public CarOfficeServiceImpl() {
    }

    @Override
    public Date getNearestFreeDate() {
        checkMasters();
        checkPlaces();
        checkOrders();
        Date leadTimeOrder = orderDao.getLastOrder().getLeadTime();
        Date dayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.before(currentDay);
             currentDay = DateUtil.addDays(currentDay, NUMBER_DAY)) {
            if (masterDao.getFreeMasters(currentDay).isEmpty() ||
                placeDao.getFreePlaces(currentDay).isEmpty()) {
                dayDate = currentDay;
                currentDay = DateUtil.bringStartOfDayDate(currentDay);
            } else {
                break;
            }
        }
        return dayDate;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void importEntities() {
        try {
            databaseConnection.disableAutoCommit();
            masterDao.updateAllRecords(csvMaster.importMasters());
            placeDao.updateAllRecords(csvPlace.importPlaces());
            List<Order> orders = csvOrder.importOrder(masterDao.getAllRecords(), placeDao.getAllRecords());
            orderDao.updateAllRecords(orders);
            orders.forEach(orderDao::addRecordToTableManyToMany);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction import entities");
        } finally {
            databaseConnection.enableAutoCommit();
        }

    }

    @Override
    public void exportEntities() {
        List<Order> orders = getOrders();
        List<Master> masters = getMasters();
        List<Place> places = getPlaces();
        csvOrder.exportOrder(orders);
        csvMaster.exportMasters(masters);
        csvPlace.exportPlaces(places);
    }

    private void checkMasters() {
        if (masterDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        if (placeDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkOrders() {
        if (orderDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
    }

    @SuppressWarnings("unchecked")
    private List<Order> getOrders() {
        List<Order> orders = orderDao.getAllRecords();
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    @SuppressWarnings("unchecked")
    private List<Master> getMasters() {
        List<Master> masters = masterDao.getAllRecords();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @SuppressWarnings("unchecked")
    private List<Place> getPlaces() {
        List<Place> places = placeDao.getAllRecords();
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }
}