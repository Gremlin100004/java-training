package com.senla.carservice.service;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;
import com.senla.carservice.service.enumaration.MasterSortParameter;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface MasterService {

    List<MasterDto> getMasters();

    MasterDto addMaster(MasterDto masterDto);

    List<MasterDto> getFreeMastersByDate(Date executeDate);

    Long getNumberFreeMastersByDate(Date startDate);

    @Transactional
    Long getNumberMasters();

    void deleteMaster(Long masterId);

    List<MasterDto> getSortMasters(MasterSortParameter sortParameter);

    List<OrderDto> getMasterOrders(Long masterId);

}
