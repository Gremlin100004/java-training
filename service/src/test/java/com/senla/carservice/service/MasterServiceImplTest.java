package com.senla.carservice.service;

import com.senla.carservice.dao.MasterDao;
import com.senla.carservice.dao.exception.DaoException;
import com.senla.carservice.domain.Master;
import com.senla.carservice.service.config.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
class MasterServiceTest {
    @Autowired
    private MasterService masterService;
    @Autowired
    private MasterDao masterDao;
    private List<Master> masters;

    @BeforeEach
    public void init() {
        masters = new ArrayList<>();
        masters.add(new Master("name1"));
        masters.add(new Master("name2"));
        masters.add(new Master("name3"));
        masters.add(new Master("name4"));
    }

    @Test
    void checkGetMastersShouldReturnList() {
        when(masterDao.getAllRecords()).thenReturn(masters);
        List<Master> resultMasters = masterService.getMasters();
        Mockito.verify(masterDao, Mockito.times(1)).getAllRecords();
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetMastersShouldThrowException() {
//        Mockito.when(masterDao.getAllRecords()).thenThrow(DaoException.class);
//        Assertions.assertThrows(DaoException.class, () -> {
//            masterService.getMasters();
//        });
    }

    @Test
    void checkAddMasterShouldCreateMaster() {
        masterService.addMaster(ArgumentMatchers.anyString());
        Mockito.verify(masterDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Master.class));
    }

    @Test
    void checkGetFreeMastersByDateShouldReturnList() {
        Date date = new Date();
        when(masterDao.getFreeMasters(date)).thenReturn(masters);
        List<Master> resultMasters = masterService.getFreeMastersByDate(date);
        Mockito.verify(masterDao, Mockito.times(1)).getFreeMasters(date);
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetFreeMastersByDateShouldThrowError() {
        Date date = new Date();
        when(masterDao.getFreeMasters(date)).thenThrow(DaoException.class);
        Assertions.assertThrows(DaoException.class, () -> masterService.getFreeMastersByDate(date));
    }

    @Test
    void checkGetNumberFreeMastersByDateShouldReturnLong() {
        Date date = new Date();
        Long numberFreeMasters = 4L;
        when(masterDao.getNumberFreeMasters(date)).thenReturn(numberFreeMasters);
        Long resultNumberFreeMasters = masterService.getNumberFreeMastersByDate(date);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(date);
        Assertions.assertEquals(numberFreeMasters, resultNumberFreeMasters);
    }

    @Test
    void checkDeleteMasterShouldDeleteRecordById() {
        Master master = new Master("test");
        when(masterDao.findById(ArgumentMatchers.anyLong())).thenReturn(master);
        masterService.deleteMaster(ArgumentMatchers.anyLong());
        Mockito.verify(masterDao, Mockito.times(1)).findById(ArgumentMatchers.anyLong());
        Mockito.verify(masterDao, Mockito.times(1)).updateRecord(master);
    }

    @Test
    void checkGetMasterByAlphabetShouldReturnList() {
        when(masterDao.getMasterSortByAlphabet()).thenReturn(masters);
        List<Master> resultMasters = masterService.getMasterByAlphabet();
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByAlphabet();
        Assertions.assertEquals(masters, resultMasters);
    }

    //TODO throw test

    @Test
    void checkGetMasterByBusyShouldReturnList() {
        when(masterDao.getMasterSortByBusy()).thenReturn(masters);
        List<Master> resultMasters = masterService.getMasterByBusy();
        Mockito.verify(masterDao, Mockito.times(1)).getMasterSortByBusy();
        Assertions.assertEquals(masters, resultMasters);
    }

    //TODO throw test

    @Test
    void getNumberMasters() {
        Long numberMasters = 4L;
        when(masterDao.getNumberMasters()).thenReturn(numberMasters);
        Long resultNumberMasters = masterService.getNumberMasters();
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Assertions.assertEquals(numberMasters, resultNumberMasters);
    }
}