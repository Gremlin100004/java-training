package com.senla.carservice.service;

import com.senla.carservice.domain.Master;

import java.util.Date;
import java.util.List;

public interface MasterService {

    List<Master> getMasters();

    void addMaster(String name);

    List<Master> getFreeMastersByDate(Date executeDate);

    Long getNumberFreeMastersByDate(Date startDate);

    void deleteMaster(Long idMaster);

    List<Master> getMasterByAlphabet();

    List<Master> getMasterByBusy();

    Long getNumberMasters();
}