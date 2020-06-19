package com.senla.carservice.service;

import com.senla.carservice.domain.Master;

import java.util.ArrayList;

public interface MasterService {
    ArrayList<Master> getMasters();

    void addMaster(String name);

    void deleteMaster(Master master);

    ArrayList<Master> sortMasterByAlphabet(ArrayList<Master> masters);

    ArrayList<Master> sortMasterByBusy(ArrayList<Master> masters);
}