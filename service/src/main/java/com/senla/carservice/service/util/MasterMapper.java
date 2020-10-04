package com.senla.carservice.service.util;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.dto.MasterDto;

import java.util.ArrayList;
import java.util.List;

public class MasterMapper {

    public static List<Master> transferDataFromMasterDtoToMaster(List<MasterDto> mastersDto, MasterDao masterDao) {
        List<Master> list = new ArrayList<>();
        for (MasterDto masterDto : mastersDto) {
            Master master = transferDataFromMasterDtoToMaster(masterDto, masterDao);
            list.add(master);
        }
        return list;
    }

    public static Master transferDataFromMasterDtoToMaster(MasterDto masterDto, MasterDao masterDao) {
        Master master;
        if (masterDto.getId() != null) {
            master = masterDao.findById(masterDto.getId());
        } else {
            master = new Master();
        }
        master.setName(masterDto.getName());
        master.setNumberOrders(masterDto.getNumberOrders());
        master.setDeleteStatus(masterDto.getDeleteStatus());
        return master;
    }

    public static MasterDto transferDataFromMasterToMasterDto(Master master) {
        MasterDto masterDto = new MasterDto();
        masterDto.setId(master.getId());
        masterDto.setName(master.getName());
        masterDto.setNumberOrders(master.getNumberOrders());
        masterDto.setDeleteStatus(master.getDeleteStatus());
        return masterDto;
    }

    public static List<MasterDto> transferDataFromMasterToMasterDto(List<Master> masters) {
        List<MasterDto> list = new ArrayList<>();
        for (Master master : masters) {
            MasterDto transferDataFromMasterToMasterDto = transferDataFromMasterToMasterDto(master);
            list.add(transferDataFromMasterToMasterDto);
        }
        return list;
    }
}