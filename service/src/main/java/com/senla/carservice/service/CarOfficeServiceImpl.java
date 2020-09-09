package com.senla.carservice.service;

import com.senla.carservice.util.DateUtil;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.csv.CsvMaster;
import com.senla.carservice.csv.CsvOrder;
import com.senla.carservice.csv.CsvPlace;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.MasterDao;
import com.senla.carservice.hibernatedao.OrderDao;
import com.senla.carservice.hibernatedao.PlaceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

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

    private static final int NUMBER_DAY = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(CarOfficeServiceImpl.class);

    public CarOfficeServiceImpl() {
    }

    @Override
    public Date getNearestFreeDate() {
        LOGGER.debug("Method getNearestFreeDate");
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            checkMasters();
            checkPlaces();
            checkOrders();
            Date leadTimeOrder = orderDao.getLastOrder().getLeadTime();
            Date dayDate = new Date();
            for (Date currentDay = new Date(); leadTimeOrder.before(currentDay);
                 currentDay = DateUtil.addDays(currentDay, NUMBER_DAY)) {
                if (masterDao.getFreeMasters(currentDay).isEmpty() ||
                    placeDao.getBusyPlaces(currentDay).isEmpty()) {
                    dayDate = currentDay;
                    currentDay = DateUtil.bringStartOfDayDate(currentDay);
                } else {
                    break;
                }
            }
            transaction.commit();
            return dayDate;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get date");
        }
    }

    @Override
    public void importEntities() {
        LOGGER.debug("Method importEntities");
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            masterDao.updateAllRecords(csvMaster.importMasters(orderDao.getAllRecords(Order.class)));
            placeDao.updateAllRecords(csvPlace.importPlaces());
            List<Order> orders = csvOrder.importOrder(masterDao.getAllRecords(Master.class),
                    placeDao.getAllRecords(Place.class));
            orderDao.updateAllRecords(orders);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction import entities");
        }
    }

    @Override
    public void exportEntities() {
        LOGGER.debug("Method exportEntities");
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Order> orders = getOrders();
            List<Master> masters = getMasters();
            List<Place> places = getPlaces();
            csvOrder.exportOrder(orders);
            csvMaster.exportMasters(masters);
            csvPlace.exportPlaces(places);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction export entities");
        }
    }
    @Override
    public void closeSessionFactory() {
        if (orderDao.getSessionFactory().isOpen()){
            orderDao.getSessionFactory().close();
        }
    }

    private void checkMasters() {
        LOGGER.debug("Method checkMasters");
        if (masterDao.getNumberMasters() == 0) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        LOGGER.debug("Method checkPlaces");
        if (placeDao.getNumberPlaces() == 0) {
            throw new BusinessException("There are no places");
        }
    }

    private void checkOrders() {
        LOGGER.debug("Method checkOrders");
        if (orderDao.getNumberOrders() == 0) {
            throw new BusinessException("There are no orders");
        }
    }

    private List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        List<Order> orders = orderDao.getAllRecords(Order.class);
        if (orders.isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        return orders;
    }

    private List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        List<Master> masters = masterDao.getAllRecords(Master.class);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    private List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(Place.class);
        if (places.isEmpty()) {
            throw new BusinessException("There are no places");
        }
        return places;
    }
}