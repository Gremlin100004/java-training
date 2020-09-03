package com.senla.carservice.service;

import com.senla.carservice.DateUtil;
import com.senla.carservice.Master;
import com.senla.carservice.Order;
import com.senla.carservice.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.csv.CsvMaster;
import com.senla.carservice.csv.CsvOrder;
import com.senla.carservice.csv.CsvPlace;
import com.senla.carservice.service.exception.BusinessException;
import hibernatedao.MasterDao;
import hibernatedao.OrderDao;
import hibernatedao.PlaceDao;
import hibernatedao.session.HibernateSessionFactory;
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
    private HibernateSessionFactory hibernateSessionFactory;
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
        Date leadTimeOrder = orderDao.getLastOrder(hibernateSessionFactory.getSession()).getLeadTime();
        Date dayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.before(currentDay);
             currentDay = DateUtil.addDays(currentDay, NUMBER_DAY)) {
            if (masterDao.getFreeMasters(currentDay, hibernateSessionFactory.getSession()).isEmpty() ||
                placeDao.getFreePlaces(currentDay, hibernateSessionFactory.getSession()).isEmpty()) {
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
            hibernateSessionFactory.openTransaction();
            masterDao.updateAllRecords(csvMaster.importMasters(), hibernateSessionFactory.getSession());
            placeDao.updateAllRecords(csvPlace.importPlaces(), hibernateSessionFactory.getSession());
            List<Order> orders = csvOrder.importOrder(masterDao.getAllRecords(hibernateSessionFactory.getSession()),
                                                      placeDao.getAllRecords(hibernateSessionFactory.getSession()));
            orderDao.updateAllRecords(orders, hibernateSessionFactory.getSession());
            orders.forEach(order -> orderDao.addRecordToTableManyToMany(order, hibernateSessionFactory.getSession()));
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction import entities");
        } finally {
            hibernateSessionFactory.closeSession();
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
        if (masterDao.getAllRecords(hibernateSessionFactory.getSession()).isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        LOGGER.debug("Method checkPlaces");
        if (placeDao.getAllRecords(hibernateSessionFactory.getSession()).isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkOrders() {
        LOGGER.debug("Method checkOrders");
        if (orderDao.getAllRecords(hibernateSessionFactory.getSession()).isEmpty()) {
            throw new BusinessException("There are no orders");
        }
    }

    private List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        List<Order> orders = orderDao.getAllRecords(hibernateSessionFactory.getSession());
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    private List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        List<Master> masters = masterDao.getAllRecords(hibernateSessionFactory.getSession());
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    private List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(hibernateSessionFactory.getSession());
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }
}