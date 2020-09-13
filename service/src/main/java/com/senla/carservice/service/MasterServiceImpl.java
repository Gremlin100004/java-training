package com.senla.carservice.service;

import com.senla.carservice.domain.Master;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.dao.MasterDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

@Component
public class MasterServiceImpl implements MasterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);
    @Autowired
    private MasterDao masterDao;

    public MasterServiceImpl() {
    }

    @Override
    public List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        try (Session session = masterDao.getSession()) {
            session.beginTransaction();
            List<Master> masters = masterDao.getAllRecords(Master.class);
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            return masters;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get masters");
        }
    }

    @Override
    public void addMaster(String name) {
        LOGGER.debug("Method addMaster");
        LOGGER.trace("Parameter name: {}", name);
        Session session = masterDao.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            masterDao.saveRecord(new Master(name));
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction add masters");
        }
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate) {
        LOGGER.debug("Method getFreeMastersByDate");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        try (Session session = masterDao.getSession()) {
            session.beginTransaction();
            List<Master> freeMasters = masterDao.getFreeMasters(executeDate);
            if (freeMasters.isEmpty()) {
                throw new BusinessException("There are no free masters");
            }
            return freeMasters;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get free masters");
        }
    }

    @Override
    public Long getNumberFreeMastersByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreeMastersByDate");
        LOGGER.trace("Parameter startDayDate: {}", startDayDate);
        try (Session session = masterDao.getSession()) {
            session.beginTransaction();
            return masterDao.getNumberFreeMasters(startDayDate);
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction number free masters");
        }
    }

    @Override
    public void deleteMaster(Long idMaster) {
        LOGGER.debug("Method deleteMaster");
        LOGGER.trace("Parameter idMaster: {}", idMaster);
        Session session = masterDao.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            masterDao.updateRecord(masterDao.getRecordById(Master.class, idMaster));
            transaction.commit();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new BusinessException("Error transaction delete master");
        }
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        LOGGER.debug("Method getMasterByAlphabet");
        try (Session session = masterDao.getSession()) {
            session.beginTransaction();
            List<Master> masters = masterDao.getMasterSortByAlphabet();
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            return masters;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction get sort masters by alphabet");
        }
    }

    @Override
    public List<Master> getMasterByBusy() {
        LOGGER.debug("Method getMasterByBusy");
        try (Session session = masterDao.getSession()) {
            session.beginTransaction();
            List<Master> masters = masterDao.getMasterSortByBusy();
            if (masters.isEmpty()) {
                throw new BusinessException("There are no masters");
            }
            return masters;
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction sort masters by busy");
        }
    }

    @Override
    public Long getNumberMasters() {
        LOGGER.debug("Method getNumberMasters");
        try (Session session = masterDao.getSession()) {
            session.beginTransaction();
            return masterDao.getNumberMasters();
        } catch (BusinessException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException(e.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            throw new BusinessException("Error transaction number masters");
        }
    }
}