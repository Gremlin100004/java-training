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
        Mockito.doReturn(masters).when(masterDao).getAllRecords();
        List<Master> resultMasters = masterService.getMasters();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getAllRecords();
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetMastersShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getAllRecords();
        Assertions.assertThrows(DaoException.class, () -> masterService.getMasters());
    }

    @Test
    void checkAddMasterShouldCreateMaster() {
        masterService.addMaster(ArgumentMatchers.anyString());
        Mockito.verify(masterDao, Mockito.times(1)).saveRecord(ArgumentMatchers.any(Master.class));
    }

    @Test
    void checkGetFreeMastersByDateShouldReturnList() {
        Date date = new Date();
        Mockito.doReturn(masters).when(masterDao).getFreeMasters(date);
        List<Master> resultMasters = masterService.getFreeMastersByDate(date);
        Mockito.verify(masterDao, Mockito.times(1)).getFreeMasters(date);
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetFreeMastersByDateShouldThrowException() {
        Date date = new Date();
        Mockito.doThrow(DaoException.class).when(masterDao).getFreeMasters(date);
        Assertions.assertThrows(DaoException.class, () -> masterService.getFreeMastersByDate(date));
    }

    @Test
    void checkGetNumberFreeMastersByDateShouldReturnLong() {
        Date date = new Date();
        Long numberFreeMasters = 4L;
        Mockito.doReturn(numberFreeMasters).when(masterDao).getNumberFreeMasters(date);
        Long resultNumberFreeMasters = masterService.getNumberFreeMastersByDate(date);
        Mockito.verify(masterDao, Mockito.times(1)).getNumberFreeMasters(date);
        Assertions.assertEquals(numberFreeMasters, resultNumberFreeMasters);
    }

    @Test
    void checkDeleteMasterShouldDeleteRecordById() {
        Master master = new Master("test name");
        Long masterId = 1L;
        Mockito.doReturn(master).when(masterDao).findById(masterId);
        masterService.deleteMaster(masterId);
        Mockito.verify(masterDao, Mockito.atLeastOnce()).findById(masterId);
        Mockito.verify(masterDao, Mockito.times(1)).updateRecord(master);
        Assertions.assertTrue(master.getDeleteStatus());
    }

    @Test
    void checkDeleteMasterShouldThrowException() {
        Long masterId = 1L;
        Mockito.doThrow(DaoException.class).when(masterDao).findById(masterId);
        Assertions.assertThrows(DaoException.class, () -> masterService.deleteMaster(masterId));
    }

    @Test
    void checkGetMasterByAlphabetShouldReturnList() {
        Mockito.doReturn(masters).when(masterDao).getMasterSortByAlphabet();
        List<Master> resultMasters = masterService.getMasterByAlphabet();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getMasterSortByAlphabet();
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetMasterByAlphabetShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByAlphabet();
        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByAlphabet());
    }

    @Test
    void checkGetMasterByBusyShouldReturnList() {
        Mockito.doReturn(masters).when(masterDao).getMasterSortByBusy();
        List<Master> resultMasters = masterService.getMasterByBusy();
        Mockito.verify(masterDao, Mockito.atLeastOnce()).getMasterSortByBusy();
        Assertions.assertEquals(masters, resultMasters);
    }

    @Test
    void checkGetMasterByBusyShouldThrowException() {
        Mockito.doThrow(DaoException.class).when(masterDao).getMasterSortByBusy();
        Assertions.assertThrows(DaoException.class, () -> masterService.getMasterByBusy());
    }

    @Test
    void checkGetNumberMastersShouldReturnNumber() {
        Long numberMasters = 4L;
        Mockito.doReturn(numberMasters).when(masterDao).getNumberMasters();
        Long resultNumberMasters = masterService.getNumberMasters();
        Mockito.verify(masterDao, Mockito.times(1)).getNumberMasters();
        Assertions.assertEquals(numberMasters, resultNumberMasters);
    }
}