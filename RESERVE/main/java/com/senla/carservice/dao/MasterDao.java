package com.senla.carservice.dao;

import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;

import java.util.Date;
import java.util.List;

public interface MasterDao extends GenericDao <Master> {
    List<Master> getFreeMasters(Date date, DatabaseConnection databaseConnection);
    List<Master> getMasterSortByAlphabet(DatabaseConnection databaseConnection);
    List<Master> getMasterSortByBusy(DatabaseConnection databaseConnection);

    int getNumberMasters(DatabaseConnection databaseConnection);
}