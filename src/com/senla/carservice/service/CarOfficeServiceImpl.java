package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.csvutil.CsvMaster;
import com.senla.carservice.util.csvutil.CsvOrder;
import com.senla.carservice.util.csvutil.CsvPlace;

import java.util.Date;
import java.util.List;

@Singleton
public class CarOfficeServiceImpl implements CarOfficeService {
    @Dependency
    private MasterRepository masterRepository;
    @Dependency
    private PlaceRepository placeRepository;
    @Dependency
    private OrderRepository orderRepository;
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
        if (masterRepository.getMasters().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        if (orderRepository.getOrders().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
        Date leadTimeOrder = orderRepository.getLastOrder().getLeadTime();
        Date dayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.before(currentDay); currentDay = DateUtil.addDays(currentDay, NUMBER_DAY)) {
            if (masterRepository.getFreeMasters(currentDay).isEmpty() ||
                placeRepository.getFreePlaces(currentDay).isEmpty()) {
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
        masterRepository.updateListMaster(csvMaster.importMasters(orderRepository.getOrders()));
        placeRepository.updateListPlace(csvPlace.importPlaces(orderRepository.getOrders()));
        List<Order> orders = csvOrder.importOrder(masterRepository.getMasters(), placeRepository.getPlaces());
        orderRepository.updateListOrder(orders);
        masterRepository.updateListMaster(csvMaster.importMasters(orders));
        placeRepository.updateListPlace(csvPlace.importPlaces(orders));
    }

    @Override
    public void exportEntities() {
        checkOrders();
        checkMasters();
        checkPlaces();
        csvOrder.exportOrder(orderRepository.getOrders());
        csvMaster.exportMasters(masterRepository.getMasters());
        csvPlace.exportPlaces(placeRepository.getPlaces());
    }

    private void checkOrders() {
        if (orderRepository.getOrders().isEmpty()) {
            throw new BusinessException("There are no orders");
        }
    }

    private void checkMasters() {
        if (masterRepository.getMasters().isEmpty()) {
            throw new BusinessException("There are no masters");
        }
    }

    private void checkPlaces() {
        if (placeRepository.getPlaces().isEmpty()) {
            throw new BusinessException("There are no places");
        }
    }
}