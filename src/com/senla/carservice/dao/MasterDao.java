package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;

import java.util.Date;
import java.util.List;

public interface MasterDao extends GenericDao <Master> {
    List<Master> getFreeMasters(Date date);
    List<Master> getMasterSortByAlphabet();
    List<Master> getMasterSortByBusy();

    int getNumberMasters();
}