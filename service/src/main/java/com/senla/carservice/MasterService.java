package com.senla.carservice;

import java.util.Date;
import java.util.List;

public interface MasterService {

    List<Master> getMasters();

    void addMaster(String name);

    List<Master> getFreeMastersByDate(Date executeDate);

    int getNumberFreeMastersByDate(Date startDate);

    void deleteMaster(Master master);

    List<Master> getMasterByAlphabet();

    List<Master> getMasterByBusy();

    int getNumberMasters();
}