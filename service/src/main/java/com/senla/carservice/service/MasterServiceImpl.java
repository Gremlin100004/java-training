package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.service.util.MasterMapper;
import com.senla.carservice.service.util.OrderMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class MasterServiceImpl implements MasterService {

    @Autowired
    private MasterDao masterDao;

    @Override
    @Transactional
    public List<MasterDto> getMasters() {
        log.debug("Method getMasters");
        return MasterMapper.getMasterDto(masterDao.getAllRecords());
    }

    @Override
    @Transactional
    public MasterDto addMaster(MasterDto masterDto) {
        log.debug("Method addMaster");
        log.trace("Parameter masterDto: {}", masterDto);
        Master master = new Master();
        master.setName(masterDto.getName());
        return MasterMapper.getMasterDto(masterDao.saveRecord(master));
    }

    @Override
    @Transactional
    public List<MasterDto> getFreeMastersByDate(Date executeDate) {
        log.debug("Method getFreeMastersByDate");
        log.trace("Parameter executeDate: {}", executeDate);
        return MasterMapper.getMasterDto(masterDao.getFreeMasters(executeDate));
    }

    @Override
    @Transactional
    public Long getNumberFreeMastersByDate(Date startDayDate) {
        log.debug("Method getNumberFreeMastersByDate");
        log.trace("Parameter startDayDate: {}", startDayDate);
        return masterDao.getNumberFreeMasters(startDayDate);
    }

    @Override
    @Transactional
    public void deleteMaster(Long masterId) {
        log.debug("Method deleteMaster");
        log.trace("Parameter masterId: {}", masterId);
        Master master = masterDao.findById(masterId);
        if (master.getDeleteStatus()) {
            throw new BusinessException("Error, master has already been deleted");
        }
        master.setDeleteStatus(true);
        masterDao.updateRecord(master);
    }

    @Override
    @Transactional
    public List<MasterDto> getMasterByAlphabet() {
        log.debug("Method getMasterByAlphabet");
        return MasterMapper.getMasterDto(masterDao.getMasterSortByAlphabet());
    }

    @Override
    @Transactional
    public List<MasterDto> getMasterByBusy() {
        log.debug("Method getMasterByBusy");
        return MasterMapper.getMasterDto(masterDao.getMasterSortByBusy());
    }

    @Override
    @Transactional
    public void checkMasters() {
        log.debug("Method getNumberMasters");
        if (masterDao.getNumberMasters() == 0) {
            throw new BusinessException("Error, there are no masters");
        }
    }

    @Override
    @Transactional
    public List<OrderDto> getMasterOrders(Long masterId) {
        log.debug("Method getMasterOrders");
        log.trace("Parameter masterId: {}", masterId);
        return OrderMapper.getOrderDto(
            masterDao.getMasterOrders(masterDao.findById(masterId)));
    }

}
