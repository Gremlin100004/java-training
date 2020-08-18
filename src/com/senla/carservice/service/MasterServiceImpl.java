package com.senla.carservice.service;

import com.senla.carservice.container.annotation.Singleton;
import com.senla.carservice.container.objectadjuster.dependencyinjection.annotation.Dependency;
import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.exception.BusinessException;
import com.senla.carservice.repository.OrderRepository;

import java.util.Date;
import java.util.List;

@Singleton
public class MasterServiceImpl implements MasterService {
    @Dependency
    private MasterDao masterDao;
    @Dependency
    private OrderRepository orderRepository;

    public MasterServiceImpl() {
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Master> getMasters() {
        List<Master> masters = masterDao.getAllRecords();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void addMaster(String name) {
        masterDao.updateRecord(new Master(name));
    }

    @Override
    public List<Master> getFreeMastersByDate(Date executeDate) {
        List<Master> freeMasters = masterDao.getFreeMasters(executeDate);
        if (freeMasters.isEmpty()) {
            throw new BusinessException("There are no free masters");
        }
        return freeMasters;
    }

    @Override
    public int getNumberFreeMastersByDate(Date startDayDate) {
        return masterDao.getFreeMasters(startDayDate).size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void deleteMaster(Master master) {
        masterDao.deleteRecord(master);
    }

    @Override
    public List<Master> getMasterByAlphabet() {
        List<Master> masters = masterDao.getMasterByAlphabet();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }

    @Override
    public List<Master> getMasterByBusy() {
        List<Master> masters = masterDao.getMasterByBusy();
        if (masters.isEmpty()) {
            throw new BusinessException("There are no masters");
        }
        return masters;
    }
}