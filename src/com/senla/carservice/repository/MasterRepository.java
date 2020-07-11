package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;

import java.util.Date;
import java.util.List;

public interface MasterRepository {

    IdGenerator getIdGeneratorMaster();

    void addMaster(Master master);

    void updateMaster(Master master);

    void updateListMaster(List<Master> masters);

    void updateGenerator(IdGenerator idGenerator);

    void deleteMaster(Master master);

    List<Master> getMasters();

    List<Master> getFreeMasters(Date date);
}