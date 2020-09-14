package com.senla.carservice.service;

import com.senla.carservice.csv.CsvMaster;
import com.senla.carservice.csv.CsvOrder;
import com.senla.carservice.csv.CsvPlace;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.domain.Place;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class CarOfficeServiceImpl implements CarOfficeService {

    private static final int NUMBER_DAY = 1;
    private static final Logger LOGGER = LoggerFactory.getLogger(CarOfficeServiceImpl.class);
    @Autowired
    private MasterDao masterDao;
    @Autowired
    private PlaceDao placeDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CsvPlace csvPlace;
    @Autowired
    private CsvOrder csvOrder;
    @Autowired
    private CsvMaster csvMaster;

    public CarOfficeServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public Date getNearestFreeDate() {
        LOGGER.debug("Method getNearestFreeDate");
        checkMasters();
        checkPlaces();
        checkOrders();
        Date leadTimeOrder = orderDao.getLastOrder().getLeadTime();
        Date dayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.before(currentDay);
             currentDay = DateUtil.addDays(currentDay, NUMBER_DAY)) {
            if (masterDao.getFreeMasters(currentDay).isEmpty() || placeDao.getFreePlaces(currentDay).isEmpty()) {
                dayDate = currentDay;
                currentDay = DateUtil.bringStartOfDayDate(currentDay);
            } else {
                break;
            }
        }
        return dayDate;
    }

    @Override
    @Transactional
    public void importEntities() {
        LOGGER.debug("Method importEntities");
        masterDao.updateAllRecords(csvMaster.importMasters(orderDao.getAllRecords(Order.class)));
        placeDao.updateAllRecords(csvPlace.importPlaces());
        List<Order> orders =
            csvOrder.importOrder(masterDao.getAllRecords(Master.class), placeDao.getAllRecords(Place.class));
        orderDao.updateAllRecords(orders);
    }

    @Override
    @Transactional(readOnly = true)
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