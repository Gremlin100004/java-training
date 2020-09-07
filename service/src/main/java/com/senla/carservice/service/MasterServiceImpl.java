package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.domain.Master;
import com.senla.carservice.hibernatedao.MasterDao;
import com.senla.carservice.service.exception.BusinessException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;

@Singleton
public class MasterServiceImpl implements MasterService {

    @Dependency
    private MasterDao masterDao;
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);

    public MasterServiceImpl() {
    }

    @Override
    public List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        Session session = masterDao.getSessionFactory().openSession();
        List<Master> masters = masterDao.getAllRecords(Master.class);
        if (masters.isEmpty()) {
            session.close();
            throw new BusinessException("There are no masters");
        }
        session.close();
        return masters;
    }

    @Override
    public void addMaster(String name) {
        LOGGER.debug("Method addMaster");
        LOGGER.trace("Parameter name: {}", name);
        Session session = masterDao.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            masterDao.saveRecord(new Master(name));
            transaction.commit();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            transaction.rollback();
            throw new BusinessException("Error transaction add masters");
        }
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate) {
        LOGGER.debug("Method getFreeMastersByDate");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        Session session = masterDao.getSessionFactory().openSession();
        List<Master> freeMasters = masterDao.getAllRecords(Master.class);
        List<Master> busyMastersMasters = masterDao.getBusyMasters(executeDate);
        freeMasters.removeAll(busyMastersMasters);
        if (freeMasters.isEmpty()) {
            session.close();
            throw new BusinessException("There are no free masters");
        }
        session.close();
        return freeMasters;
    }

    @Override
    public Long getNumberFreeMastersByDate(Date startDayDate) {
        //TODO refactor method
        LOGGER.debug("Method getNumberFreeMastersByDate");
        LOGGER.trace("Parameter startDayDate: {}", startDayDate);
        Session session = masterDao.getSessionFactory().openSession();
        Long numberGeneralMasters = masterDao.getNumberMasters();
        Long numberBusyMasters = masterDao.getNumberBusyMasters(startDayDate);
        session.close();
        return numberGeneralMasters-numberBusyMasters;
    }

    @Override
    public void deleteMaster(Master master) {
        LOGGER.debug("Method deleteMaster");
        LOGGER.trace("Parameter master: {}", master);
        Session session = masterDao.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try (session) {
            masterDao.updateRecord(master);
            transaction.commit();
        } catch (BusinessException e) {
            LOGGER.error(e.getMessage());
            transaction.rollback();
            throw new BusinessException("Error transaction delete master");
        }
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        LOGGER.debug("Method getMasterByAlphabet");
        Session session = masterDao.getSessionFactory().openSession();
        session.beginTransaction();
        List<Master> masters = masterDao.getMasterSortByAlphabet();
        if (masters.isEmpty()) {
            session.close();
            throw new BusinessException("There are no masters");
        }
        session.close();
        return masters;
    }

    @Override
    public List<Master> getMasterByBusy() {
        LOGGER.debug("Method getMasterByBusy");
        List<Master> masters = masterDao.getMasterSortByBusy();
        Session session = masterDao.getSessionFactory().openSession();
        if (masters.isEmpty()) {
            session.close();
            throw new BusinessException("There are no masters");
        }
        session.close();
        return masters;
    }

    @Override
    public Long getNumberMasters() {
        LOGGER.debug("Method getNumberMasters");
        Session session = masterDao.getSessionFactory().openSession();
        Long numberMasters = masterDao.getNumberMasters();
        session.close();
        return numberMasters;
    }
}