package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.connection.DatabaseConnection;
import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;

import java.util.Date;
import java.util.List;

@Singleton
public class MasterServiceImpl implements MasterService {
    @Dependency
    private MasterDao masterDao;
    @Dependency
    private DatabaseConnection databaseConnection;

    public MasterServiceImpl() {
    }

    @Override
    public List<Master> getMasters() {
        List<Master> masters = masterDao.getAllRecords(databaseConnection);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public void addMaster(String name) {
        try {
            databaseConnection.disableAutoCommit();
            masterDao.createRecord(new Master(name), databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate) {
        List<Master> freeMasters = masterDao.getFreeMasters(executeDate, databaseConnection);
        if (freeMasters.isEmpty()) {
            throw new BusinessException("There are no free masters");
        }
        return freeMasters;
    }

    @Override
    public int getNumberFreeMastersByDate(Date startDayDate) {
        return masterDao.getFreeMasters(startDayDate, databaseConnection).size();
    }

    @Override
    public void deleteMaster(Master master) {
        try {
            databaseConnection.disableAutoCommit();
            masterDao.deleteRecord(master, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction delete master");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        List<Master> masters = masterDao.getMasterSortByAlphabet(databaseConnection);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public List<Master> getMasterByBusy() {
        List<Master> masters = masterDao.getMasterSortByBusy(databaseConnection);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }
}