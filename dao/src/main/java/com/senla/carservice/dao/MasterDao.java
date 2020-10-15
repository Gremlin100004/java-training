package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.domain.Order;

import java.util.Date;
import java.util.List;

public interface MasterDao extends GenericDao<Master, Long> {

    List<Master> getFreeMasters(Date date);

    List<Master> getMasterSortByAlphabet();

    List<Master> getMasterSortByBusy();

    Long getNumberMasters();

    Long getNumberFreeMasters(Date executeDate);

    List<Order> getMasterOrders(Master master);

}
