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
import com.senla.carservice.hibernatedao.session.HibernateSessionFactory;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger(CarOfficeServiceImpl.class);

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
            if (masterDao.getBusyMasters(currentDay, hibernateSessionFactory.getSession()).isEmpty() ||
                    placeDao.getBusyPlaces(currentDay, hibernateSessionFactory.getSession()).isEmpty()) {
                dayDate = currentDay;
                currentDay = DateUtil.bringStartOfDayDate(currentDay);
            } else {
                break;
            }
        }
        hibernateSessionFactory.closeSession();
        return dayDate;
    }

    @Override
    public void importEntities() {
        LOGGER.debug("Method importEntities");
        try {
            hibernateSessionFactory.openTransaction();
            masterDao.updateAllRecords(csvMaster.importMasters(
                    orderDao.getAllRecords(hibernateSessionFactory.getSession(), Order.class)),
                    hibernateSessionFactory.getSession());
            placeDao.updateAllRecords(csvPlace.importPlaces(), hibernateSessionFactory.getSession());
            List<Order> orders = csvOrder.importOrder(masterDao.getAllRecords(hibernateSessionFactory.getSession(), Master.class),
                    placeDao.getAllRecords(hibernateSessionFactory.getSession(), Place.class));
            orderDao.updateAllRecords(orders, hibernateSessionFactory.getSession());
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
    @Override
    public void closeSessionFactory() {
        hibernateSessionFactory.closeSessionFactory();
    }

    private void checkMasters() {
        LOGGER.debug("Method checkMasters");
        if (masterDao.getNumberMasters(hibernateSessionFactory.getSession()) == 0) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no masters");
        }
        hibernateSessionFactory.closeSession();
    }

    private void checkPlaces() {
        LOGGER.debug("Method checkPlaces");
        if (placeDao.getNumberPlaces(hibernateSessionFactory.getSession()) == 0) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no places");
        }
        hibernateSessionFactory.closeSession();
    }

    private void checkOrders() {
        LOGGER.debug("Method checkOrders");
        if (orderDao.getNumberOrders(hibernateSessionFactory.getSession()) == 0) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no orders");
        }
        hibernateSessionFactory.closeSession();
    }

    private List<Order> getOrders() {
        LOGGER.debug("Method getOrders");
        List<Order> orders = orderDao.getAllRecords(hibernateSessionFactory.getSession(), Order.class);
        if (orders.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no orders");
        }
        hibernateSessionFactory.closeSession();
        return orders;
    }

    private List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        List<Master> masters = masterDao.getAllRecords(hibernateSessionFactory.getSession(), Master.class);
        if (masters.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no masters");
        }
        hibernateSessionFactory.closeSession();
        return masters;
    }

    private List<Place> getPlaces() {
        LOGGER.debug("Method getPlaces");
        List<Place> places = placeDao.getAllRecords(hibernateSessionFactory.getSession(), Place.class);
        if (places.isEmpty()) {
            hibernateSessionFactory.closeSession();
            throw new BusinessException("There are no places");
        }
        hibernateSessionFactory.closeSession();
        return places;
    }
}