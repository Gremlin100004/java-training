package com.senla.carservice.service;

import com.senla.carservice.dto.MasterDto;

import java.util.Date;
import java.util.List;

public interface MasterService {

    List<MasterDto> getMasters();

    void addMaster(MasterDto masterDto);

    List<MasterDto> getFreeMastersByDate(Date executeDate);

    Long getNumberFreeMastersByDate(Date startDate);

    void deleteMaster(Long idMaster);

    List<MasterDto> getMasterByAlphabet();

    List<MasterDto> getMasterByBusy();

    Long getNumberMasters();
}