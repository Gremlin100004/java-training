package com.senla.carservice.ui.client;

import com.senla.carservice.dto.MasterDto;

import java.util.List;

public interface MasterClient {
    List<MasterDto> getMasters();

    String addMaster(String name);

    String checkMasters();

    String deleteMaster(Long idMaster);

    String getSortMasters(String sortParameter);

    List<MasterDto> getFreeMasters(String stringExecuteDate);

    String getMasterOrders(Long masterId);

}
