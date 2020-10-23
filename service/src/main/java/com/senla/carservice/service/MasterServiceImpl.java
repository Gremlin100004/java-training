package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.enumaration.MasterSortParameter;
import com.senla.carservice.service.exception.BusinessException;
import com.senla.carservice.service.util.MasterMapper;
import com.senla.carservice.service.util.OrderMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
        log.debug("[getMasters]");
        return MasterMapper.getMasterDto(masterDao.getAllRecords());
    }

    @Override
    @Transactional
    public MasterDto addMaster(MasterDto masterDto) {
        log.debug("[addMaster]");
        log.trace("[masterDto: {}]", masterDto);
        Master master = new Master();
        master.setName(masterDto.getName());
        return MasterMapper.getMasterDto(masterDao.saveRecord(master));
    }

    @Override
    @Transactional
    public List<MasterDto> getFreeMastersByDate(Date executeDate) {
        log.debug("[getFreeMastersByDate]");
        log.trace("[executeDate: {}]", executeDate);
        return MasterMapper.getMasterDto(masterDao.getFreeMasters(executeDate));
    }

    @Override
    @Transactional
    public Long getNumberFreeMastersByDate(Date startDayDate) {
        log.debug("[getNumberFreeMastersByDate]");
        log.trace("[startDayDate: {}]", startDayDate);
        return masterDao.getNumberFreeMasters(startDayDate);
    }

    @Override
    @Transactional
    public Long getNumberMasters() {
        log.debug("[getNumberMasters]");
        return masterDao.getNumberMasters();
    }

    @Override
    @Transactional
    public void deleteMaster(Long masterId) {
        log.debug("[Method deleteMaster");
        log.trace("[masterId: {}]", masterId);
        Master master = masterDao.findById(masterId);
        if (master.getDeleteStatus()) {
            throw new BusinessException("Error, master has already been deleted");
        }
        master.setDeleteStatus(true);
        masterDao.updateRecord(master);
    }

    @Override
    @Transactional
    public List<MasterDto> getSortMasters(MasterSortParameter sortParameter) {
        log.debug("[getMasterByAlphabet]");
        log.trace("[sortParameter: {}]", sortParameter);
        List<Master> masters = new ArrayList<>();
        if (sortParameter == MasterSortParameter.NAME) {
            masters = masterDao.getMasterSortByAlphabet();
        } else if (sortParameter == MasterSortParameter.BUSY_STATUS) {
            masters = masterDao.getMasterSortByBusy();
        }
        return MasterMapper.getMasterDto(masters);
    }

    @Override
    @Transactional
    public List<OrderDto> getMasterOrders(Long masterId) {
        log.debug("[getMasterOrders]");
        log.trace("[masterId: {}]", masterId);
        return OrderMapper.getOrderDto(
            masterDao.getMasterOrders(masterDao.findById(masterId)));
    }

}
