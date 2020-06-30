package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.Date;
import java.util.List;

public interface MasterService {
    List<Master> getMasters();

    void addMaster(String name);

    List<Master> getFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrder);

    int getNumberFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrders);

    void deleteMaster(Master master);

    List<Master> getMasterByAlphabet();

    List<Master> getMasterByBusy();

    void exportMasters();

    String importMasters();
}