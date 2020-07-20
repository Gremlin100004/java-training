package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.dependencyinjection.annotation.Dependency;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.ApplicationState;
import com.senla.carservice.repository.MasterRepository;
import com.senla.carservice.repository.OrderRepository;
import com.senla.carservice.repository.PlaceRepository;
import com.senla.carservice.util.DateUtil;
import com.senla.carservice.util.Serializer;
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
    private Serializer serializer;
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
        Date DayDate = new Date();
        for (Date currentDay = new Date(); leadTimeOrder.before(currentDay); DateUtil.addDays(currentDay, NUMBER_DAY)) {
            if (masterRepository.getFreeMasters(currentDay).isEmpty() ||
                placeRepository.getFreePlaces(currentDay).isEmpty()) {
                DayDate = currentDay;
                currentDay = DateUtil.bringStartOfDayDate(currentDay);
            } else {
                break;
            }
        }
        return DayDate;
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

    @Override
    public void serializeEntities() {
        ApplicationState applicationState = new ApplicationState();
        applicationState.setIdGeneratorMaster(masterRepository.getIdGeneratorMaster());
        applicationState.setIdGeneratorPlace(placeRepository.getIdGeneratorPlace());
        applicationState.setIdGeneratorOrder(orderRepository.getIdGeneratorOrder());
        applicationState.setMasters(masterRepository.getMasters());
        applicationState.setPlaces(placeRepository.getPlaces());
        applicationState.setOrders(orderRepository.getOrders());
        serializer.serializeEntities(applicationState);
    }

    @Override
    public void deserializeEntities() {
        ApplicationState applicationState = serializer.deserializeEntities();
        if (applicationState == null) {
            return;
        }
        masterRepository.updateGenerator(applicationState.getIdGeneratorMaster());
        masterRepository.updateListMaster(applicationState.getMasters());
        placeRepository.updateGenerator(applicationState.getIdGeneratorPlace());
        placeRepository.updateListPlace(applicationState.getPlaces());
        orderRepository.updateGenerator(applicationState.getIdGeneratorOrder());
        orderRepository.updateListOrder(applicationState.getOrders());
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