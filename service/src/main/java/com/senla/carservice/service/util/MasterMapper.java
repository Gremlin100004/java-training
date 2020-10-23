package com.senla.carservice.service.util;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.domain.Master;
import com.senla.carservice.dto.MasterDto;

import java.util.ArrayList;
import java.util.List;

public class MasterMapper {

    public static List<Master> getMaster(List<MasterDto> mastersDto, MasterDao masterDao) {
        List<Master> list = new ArrayList<>();
        for (MasterDto masterDto : mastersDto) {
            Master master = getMaster(masterDto, masterDao);
            list.add(master);
        }
        return list;
    }

    public static Master getMaster(MasterDto masterDto, MasterDao masterDao) {
        Master master;
        if (masterDto.getId() == null) {
            master = new Master();
        } else {
            master = masterDao.findById(masterDto.getId());
        }
        master.setName(masterDto.getName());
        master.setNumberOrders(masterDto.getNumberOrders());
        master.setDeleteStatus(masterDto.getDeleteStatus());
        return master;
    }

    public static MasterDto getMasterDto(Master master) {
        MasterDto masterDto = new MasterDto();
        masterDto.setId(master.getId());
        masterDto.setName(master.getName());
        masterDto.setNumberOrders(master.getNumberOrders());
        masterDto.setDeleteStatus(master.getDeleteStatus());
        return masterDto;
    }

    public static List<MasterDto> getMasterDto(List<Master> masters) {
        List<MasterDto> list = new ArrayList<>();
        for (Master master : masters) {
            MasterDto transferDataFromMasterToMasterDto = getMasterDto(master);
            list.add(transferDataFromMasterToMasterDto);
        }
        return list;
    }

}
