package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.hibernatedao.MasterDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

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
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Master> masters = masterDao.getAllRecords(Master.class);
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            transaction.commit();
            return masters;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get masters");
        }
    }

    @Override
    public void addMaster(String name) {
        LOGGER.debug("Method addMaster");
        LOGGER.trace("Parameter name: {}", name);
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            masterDao.saveRecord(new Master(name));
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();    
            }
            throw new BusinessException("Error transaction add masters");
        }
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate) {
        LOGGER.debug("Method getFreeMastersByDate");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Master> freeMasters = masterDao.getFreeMasters(executeDate);
            if (freeMasters.isEmpty()) {
                throw new BusinessException("There are no free masters");
            }
            transaction.commit();
            return freeMasters;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get free masters");
        }
    }

    @Override
    public Long getNumberFreeMastersByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreeMastersByDate");
        LOGGER.trace("Parameter startDayDate: {}", startDayDate);
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long numberBusyMasters = masterDao.getNumberFreeMasters(startDayDate);
            transaction.commit();
            return numberBusyMasters;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction number free masters");
        }
    }

    @Override
    public void deleteMaster(Master master) {
        LOGGER.debug("Method deleteMaster");
        LOGGER.trace("Parameter master: {}", master);
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            masterDao.updateRecord(master);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();    
            }
            throw new BusinessException("Error transaction delete master");
        }
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        LOGGER.debug("Method getMasterByAlphabet");
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Master> masters = masterDao.getMasterSortByAlphabet();
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            transaction.commit();
            return masters;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction get sort masters by alphabet");
        }
    }

    @Override
    public List<Master> getMasterByBusy() {
        LOGGER.debug("Method getMasterByBusy");
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            List<Master> masters = masterDao.getMasterSortByBusy();
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            transaction.commit();
            return masters;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction sort masters by busy");
        }
    }

    @Override
    public Long getNumberMasters() {
        LOGGER.debug("Method getNumberMasters");
        Session session = masterDao.getSessionFactory().getCurrentSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Long numberMasters = masterDao.getNumberMasters();
            transaction.commit();
            return numberMasters;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if(transaction != null){
                transaction.rollback();
            }
            throw new BusinessException("Error transaction number masters");
        }
    }
}