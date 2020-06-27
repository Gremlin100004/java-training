package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.Date;
import java.util.List;

public interface MasterService {
    List<Master> getMasters();

    void addMaster(String name);

    List<Master> getFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrder);

    void deleteMaster(Master master);

    List<Master> sortMasterByAlphabet(List<Master> masters);

    List<Master> sortMasterByBusy(List<Master> masters);

    String exportMasters();

    String importMasters();
}