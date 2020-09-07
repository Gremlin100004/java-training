package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.MasterDao;
import com.senla.carservice.hibernatedao.session.HibernateSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Singleton
public class MasterServiceImpl implements MasterService {

    @Dependency
    private MasterDao masterDao;
    @Dependency
    private HibernateSessionFactory hibernateSessionFactory;
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

    public MasterServiceImpl() {
    }

    @Override
    public List<Master> getMasters() {
        //TODO refactor method
        LOGGER.debug("Method getMasters");
        List<Master> masters = masterDao.getAllRecords(hibernateSessionFactory.getSession(), Master.class);
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public void addMaster(String name) {
        LOGGER.debug("Method addMaster");
        LOGGER.trace("Parameter name: {}", name);
        try {
            hibernateSessionFactory.openTransaction();
            masterDao.saveRecord(new Master(name), hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction add masters");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate) {
        //TODO refactor method
        LOGGER.debug("Method getFreeMastersByDate");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        List<Master> freeMasters = masterDao.getAllRecords(hibernateSessionFactory.getSession(), Master.class);
        List<Master> busyMastersMasters = masterDao.getBusyMasters(executeDate, hibernateSessionFactory.getSession());
        freeMasters.removeAll(busyMastersMasters);
        if (freeMasters.isEmpty()) {
            throw new BusinessException("There are no free masters");
        }
        return freeMasters;
    }

    @Override
    public Long getNumberFreeMastersByDate(Date startDayDate) {
        //TODO refactor method
        LOGGER.debug("Method getNumberFreeMastersByDate");
        LOGGER.trace("Parameter startDayDate: {}", startDayDate);
        Long numberGeneralMasters = masterDao.getNumberMasters(hibernateSessionFactory.getSession());
        Long numberBusyMasters = masterDao.getNumberBusyMasters(startDayDate, hibernateSessionFactory.getSession());
        return numberGeneralMasters-numberBusyMasters;
    }

    @Override
    public void deleteMaster(Master master) {
        LOGGER.debug("Method deleteMaster");
        LOGGER.trace("Parameter master: {}", master);
        try {
            hibernateSessionFactory.openTransaction();
            masterDao.updateRecord(master, hibernateSessionFactory.getSession());
            hibernateSessionFactory.commitTransaction();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            hibernateSessionFactory.rollBackTransaction();
            throw new BusinessException("Error transaction delete master");
        } finally {
            hibernateSessionFactory.closeSession();
        }
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        LOGGER.debug("Method getMasterByAlphabet");
        List<Master> masters = masterDao.getMasterSortByAlphabet(hibernateSessionFactory.getSession());
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public List<Master> getMasterByBusy() {
        LOGGER.debug("Method getMasterByBusy");
        List<Master> masters = masterDao.getMasterSortByBusy(hibernateSessionFactory.getSession());
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public Long getNumberMasters() {
        LOGGER.debug("Method getNumberMasters");
        return masterDao.getNumberMasters(hibernateSessionFactory.getSession());
    }
}