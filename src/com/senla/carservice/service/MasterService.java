package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;
import com.senla.carservice.exception.DateException;
import com.senla.carservice.exception.NullDateException;
import com.senla.carservice.exception.NumberObjectZeroException;

import java.util.Date;
import java.util.List;

public interface MasterService {
    List<Master> getMasters() throws NumberObjectZeroException;

    void addMaster(String name);

    List<Master> getFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrder)
            throws DateException, NullDateException, NumberObjectZeroException;

    int getNumberFreeMastersByDate(Date executeDate, Date leadDate, List<Order> sortOrders) throws NumberObjectZeroException, DateException, NullDateException;

    void deleteMaster(Master master) throws NumberObjectZeroException;

    List<Master> getMasterByAlphabet() throws NumberObjectZeroException;

    List<Master> getMasterByBusy() throws NumberObjectZeroException;

//    String exportMasters();

//    String importMasters();
}