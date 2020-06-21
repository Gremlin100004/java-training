package com.senla.carservice.repository;

import com.senla.carservice.domain.Master;
import com.senla.carservice.util.IdGenerator;

import java.util.List;

public interface MasterRepository {
    List<Master> getMasters();

    IdGenerator getIdGeneratorMaster();

    void setMasters(List<Master> masters);
}