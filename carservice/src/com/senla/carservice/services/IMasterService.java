package com.senla.carservice.services;

import com.senla.carservice.domain.IMaster;

public interface IMasterService {
    IMaster[] getMasters();

    void addMaster(String name);

    void deleteMaster(IMaster master);

    IMaster[] getMasterByAlphabet();

    IMaster[] getMasterByBusy();
}