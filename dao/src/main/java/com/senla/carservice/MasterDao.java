package com.senla.carservice;

import com.senla.carservice.connection.DatabaseConnection;

import java.util.Date;
import java.util.List;

public interface MasterDao extends GenericDao<Master> {

    List<Master> getFreeMasters(Date date, DatabaseConnection databaseConnection);

    List<Master> getMasterSortByAlphabet(DatabaseConnection databaseConnection);

    List<Master> getMasterSortByBusy(DatabaseConnection databaseConnection);

    int getNumberMasters(DatabaseConnection databaseConnection);

    Master getMasterById(Long index, DatabaseConnection databaseConnection);
}