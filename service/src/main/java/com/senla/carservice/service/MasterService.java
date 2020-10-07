package com.senla.carservice.service;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;

import java.util.Date;
import java.util.List;

public interface MasterService {

    List<MasterDto> getMasters();

    MasterDto addMaster(MasterDto masterDto);

    List<MasterDto> getFreeMastersByDate(Date executeDate);

    Long getNumberFreeMastersByDate(Date startDate);

    void deleteMaster(Long masterId);

    List<MasterDto> getMasterByAlphabet();

    List<MasterDto> getMasterByBusy();

    List<OrderDto> getMasterOrders(Long masterId);

    void checkMasters();

}
