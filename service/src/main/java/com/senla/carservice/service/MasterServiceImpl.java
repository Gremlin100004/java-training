package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.service.exception.BusinessException;
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
        log.debug("Method getMasters");
        return transferDataFromMasterToMasterDto(masterDao.getAllRecords());
    }

    @Override
    @Transactional
    public void addMaster(MasterDto masterDto) {
        log.debug("Method addMaster");
        log.trace("Parameter masterDto: {}", masterDto);
        masterDao.saveRecord(new Master(masterDto.getName()));
    }

    @Override
    @Transactional
    public List<MasterDto> getFreeMastersByDate(Date executeDate) {
        log.debug("Method getFreeMastersByDate");
        log.trace("Parameter executeDate: {}", executeDate);
        return transferDataFromMasterToMasterDto(masterDao.getFreeMasters(executeDate));
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
    public void deleteMaster(Long idMaster) {
        log.debug("Method deleteMaster");
        log.trace("Parameter idMaster: {}", idMaster);
        Master master = masterDao.findById(idMaster);
        if (master.getDeleteStatus()) {
            throw new BusinessException("error, master has already been deleted");
        }
        master.setDeleteStatus(true);
        masterDao.updateRecord(master);
    }

    @Override
    @Transactional
    public List<MasterDto> getMasterByAlphabet() {
        log.debug("Method getMasterByAlphabet");
        return transferDataFromMasterToMasterDto(masterDao.getMasterSortByAlphabet());
    }

    @Override
    @Transactional
    public List<MasterDto> getMasterByBusy() {
        log.debug("Method getMasterByBusy");
        return transferDataFromMasterToMasterDto(masterDao.getMasterSortByBusy());
    }

    @Override
    @Transactional
    public Long getNumberMasters() {
        log.debug("Method getNumberMasters");
        return masterDao.getNumberMasters();
    }

    private List<MasterDto> transferDataFromMasterToMasterDto(List<Master> masters) {
        List<MasterDto> mastersDto = new ArrayList<>();
        for (Master master: masters) {
            MasterDto masterDto = new MasterDto();
            masterDto.setId(master.getId());
            masterDto.setName(master.getName());
            masterDto.setNumberOrders(master.getNumberOrders());
            masterDto.setDeleteStatus(master.getDeleteStatus());
            mastersDto.add(masterDto);
        }
        return mastersDto;
    }
}