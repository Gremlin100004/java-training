package com.senla.carservice;

import com.senla.carservice.annotation.Singleton;
import com.senla.carservice.connection.DatabaseConnection;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.objectadjuster.dependencyinjection.annotation.Dependency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Singleton
public class MasterServiceImpl implements MasterService {
    @Dependency
    private MasterDao masterDao;
    @Dependency
    private DatabaseConnection databaseConnection;
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

    public MasterServiceImpl() {
    }

    @Override
    public List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        List<Master> masters = masterDao.getAllRecords(databaseConnection);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public void addMaster(String name) {
        LOGGER.debug("Method addMaster");
        LOGGER.debug("Parameter name: {}", name);
        try {
            databaseConnection.disableAutoCommit();
            masterDao.createRecord(new Master(name), databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction add masters");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate) {
        LOGGER.debug("Method getFreeMastersByDate");
        LOGGER.debug("Parameter executeDate: {}", executeDate);
        List<Master> freeMasters = masterDao.getFreeMasters(executeDate, databaseConnection);
        if (freeMasters.isEmpty()) {
            throw new BusinessException("There are no free masters");
        }
        return freeMasters;
    }

    @Override
    public int getNumberFreeMastersByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreeMastersByDate");
        LOGGER.debug("Parameter startDayDate: {}", startDayDate);
        return masterDao.getFreeMasters(startDayDate, databaseConnection).size();
    }

    @Override
    public void deleteMaster(Master master) {
        LOGGER.debug("Method deleteMaster");
        LOGGER.debug("Parameter master: {}", master);
        try {
            databaseConnection.disableAutoCommit();
            masterDao.deleteRecord(master, databaseConnection);
            databaseConnection.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            databaseConnection.rollBackTransaction();
            throw new BusinessException("Error transaction delete master");
        } finally {
            databaseConnection.enableAutoCommit();
        }
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        LOGGER.debug("Method getMasterByAlphabet");
        List<Master> masters = masterDao.getMasterSortByAlphabet(databaseConnection);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public List<Master> getMasterByBusy() {
        LOGGER.debug("Method getMasterByBusy");
        List<Master> masters = masterDao.getMasterSortByBusy(databaseConnection);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public int getNumberMasters() {
        LOGGER.debug("Method getNumberMasters");
        return masterDao.getNumberMasters(databaseConnection);
    }

    @Override
    public Master getMasterByIndex(Long index) {
        LOGGER.debug("Method getMasterByIndex");
        LOGGER.debug("Parameter index: {}", index);
        Master master = masterDao.getMasterById(index, databaseConnection);
        if (master == null) {
            throw new BusinessException("There are no such master");
        }
        return master;
    }
}