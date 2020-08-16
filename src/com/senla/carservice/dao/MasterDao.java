package com.senla.carservice.dao;

import com.senla.carservice.domain.Master;
import com.senla.carservice.repository.IdGenerator;

import java.util.Date;
import java.util.List;

public interface MasterDao {

    void addMaster(Master master);

    void updateMaster(Master master);

    void updateListMaster(List<Master> masters);

    void deleteMaster(Master master);

    List<Master> getMasters();

    List<Master> getFreeMasters(Date date);
}
