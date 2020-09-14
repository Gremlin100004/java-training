package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.service.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class MasterServiceImpl implements MasterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServiceImpl.class);
    @Autowired
    private MasterDao masterDao;

    public MasterServiceImpl() {
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> getMasters() {
        LOGGER.debug("Method getMasters");
        List<Master> masters = masterDao.getAllRecords();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    @Transactional
    public void addMaster(String name) {
        LOGGER.debug("Method addMaster");
        LOGGER.trace("Parameter name: {}", name);
        masterDao.saveRecord(new Master(name));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> getFreeMastersByDate(Date executeDate) {
        LOGGER.debug("Method getFreeMastersByDate");
        LOGGER.trace("Parameter executeDate: {}", executeDate);
        List<Master> freeMasters = masterDao.getFreeMasters(executeDate);
        if (freeMasters.isEmpty()) {
            throw new BusinessException("There are no free masters");
        }
        return freeMasters;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumberFreeMastersByDate(Date startDayDate) {
        LOGGER.debug("Method getNumberFreeMastersByDate");
        LOGGER.trace("Parameter startDayDate: {}", startDayDate);
        return masterDao.getNumberFreeMasters(startDayDate);
    }

    @Override
    @Transactional
    public void deleteMaster(Long idMaster) {
        LOGGER.debug("Method deleteMaster");
        LOGGER.trace("Parameter idMaster: {}", idMaster);
        masterDao.updateRecord(masterDao.findById(idMaster));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> getMasterByAlphabet() {
        LOGGER.debug("Method getMasterByAlphabet");
        List<Master> masters = masterDao.getMasterSortByAlphabet();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Master> getMasterByBusy() {
        LOGGER.debug("Method getMasterByBusy");
        List<Master> masters = masterDao.getMasterSortByBusy();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    @Transactional(readOnly = true)
    public Long getNumberMasters() {
        LOGGER.debug("Method getNumberMasters");
        return masterDao.getNumberMasters();
    }
}