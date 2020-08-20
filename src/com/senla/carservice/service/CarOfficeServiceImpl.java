package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.OrderDao;
import com.senla.carservice.dao.PlaceDao;
import com.senla.carservice.domain.Order;
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
    private static final int NUMBER_DAY = 1;

    public CarOfficeServiceImpl() {
    }

    @Override
    public Date getNearestFreeDate() {
        if (masterDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        if (orderDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        if (placeDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no places");
        }
        Date leadTimeOrder = orderDao.getLastOrder().getLeadTime();
        Date dayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.before(currentDay); currentDay = DateUtil.addDays(currentDay, NUMBER_DAY)) {
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
    public void importEntities() {
//        masterDao.updateListMaster(csvMaster.importMasters(orderDao.getOrders()));
//        placeDao.updateListPlace(csvPlace.importPlaces(orderDao.getOrders()));
//        List<Order> orders = csvOrder.importOrder(masterDao.getMasters(), placeDao.getPlaces());
//        orderDao.updateListOrder(orders);
//        masterDao.updateListMaster(csvMaster.importMasters(orders));
//        placeDao.updateListPlace(csvPlace.importPlaces(orders));
    }

    @Override
    public void exportEntities() {
        checkOrders();
        checkMasters();
        checkPlaces();
//        csvOrder.exportOrder(orderDao.getOrders());
//        csvMaster.exportMasters(masterDao.getMasters());
//        csvPlace.exportPlaces(placeDao.getPlaces());
    }

    private void checkOrders() {
        if (orderDao.getAllRecords().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
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
}