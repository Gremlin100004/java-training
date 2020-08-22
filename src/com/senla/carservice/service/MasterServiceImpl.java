package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
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
    @SuppressWarnings("unchecked")
    public List<Master> getMasters() {
        try {
            databaseConnection.disableAutoCommit();
            List<Master> masters = masterDao.getAllRecords();
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            databaseConnection.commitTransaction();
            return masters;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addMaster(String name) {
        try {
            databaseConnection.disableAutoCommit();
            masterDao.createRecord(new Master(name));
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
        try {
            databaseConnection.disableAutoCommit();
            List<Master> freeMasters = masterDao.getFreeMasters(executeDate);
            if (freeMasters.isEmpty()) {
                throw new BusinessException("There are no free masters");
            }
            databaseConnection.commitTransaction();
            return freeMasters;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get free masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public int getNumberFreeMastersByDate(Date startDayDate) {
        try {
            databaseConnection.disableAutoCommit();
            int numberMasters = masterDao.getFreeMasters(startDayDate).size();
            databaseConnection.commitTransaction();
            return numberMasters;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get number free masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteMaster(Master master) {
        try {
            databaseConnection.disableAutoCommit();
            masterDao.deleteRecord(master);
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
        try {
            databaseConnection.disableAutoCommit();
            List<Master> masters = masterDao.getMasterByAlphabet();
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            databaseConnection.commitTransaction();
            return masters;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<Master> getMasterByBusy() {
        try {
            databaseConnection.disableAutoCommit();
            List<Master> masters = masterDao.getMasterByBusy();
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            databaseConnection.commitTransaction();
            return masters;
        } catch (BusinessException e) {
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction get masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }
}