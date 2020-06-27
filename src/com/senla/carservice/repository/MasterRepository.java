package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;
import com.senla.carservice.util.IdGenerator;

import java.util.List;

public interface MasterRepository {
    void addMaster(Master master);

    void updateMaster(Master master);

    void deleteMaster(Master master);

    List<Master> getMasters();

    IdGenerator getIdGeneratorMaster();

}