package com.senla.carservice.service;

import com.senla.carservice.domain.Master;

public interface MasterService {
    Master[] getMasters();

    void addMaster(String name);

    void deleteMaster(Master master);

    Master[] sortMasterByAlphabet(Master[] masters);

    Master[] sortMasterByBusy(Master[] masters);
}