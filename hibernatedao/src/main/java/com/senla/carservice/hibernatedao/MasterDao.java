package com.senla.carservice.hibernatedao;

import com.senla.carservice.domain.Master;

import java.util.Date;
import java.util.List;

public interface MasterDao extends GenericDao<Master, Long> {

    List<Master> getBusyMasters(Date date);

    List<Master> getMasterSortByAlphabet();

    List<Master> getMasterSortByBusy();

    Long getNumberMasters();

    Master getMasterById(Long index);

    Long getNumberBusyMasters(Date executeDate);
}