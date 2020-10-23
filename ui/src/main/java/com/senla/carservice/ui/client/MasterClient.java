package com.senla.carservice.ui.client;

import com.senla.carservice.dto.MasterDto;
import com.senla.carservice.dto.OrderDto;

import java.util.List;

public interface MasterClient {
    List<MasterDto> getMasters();

    String addMaster(String name);

    String deleteMaster(Long idMaster);

    List<MasterDto> getSortMasters(String sortParameter);

    Long getNumberMasters();

    Long getNumberFreeMasters(String date);

    List<MasterDto> getFreeMasters(String stringExecuteDate);

    List<OrderDto> getMasterOrders(Long masterId);

}
